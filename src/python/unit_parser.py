import re
import wget

import requests
from bs4 import BeautifulSoup

import traceback

from unit import UnitDB, UnitDetailsDB, ICON_TO_ABILITY_MAP

SCIPA_DB_BASE = "https://battlecats-db.com/"
SCIPA_DB_CAT_UNIT = lambda unit_id: SCIPA_DB_BASE + "unit/%03d.html" % unit_id

ENG_WIKI_BASE = "https://battle-cats.fandom.com"
ENG_WIKI_CAT_NAMES = ENG_WIKI_BASE + "/wiki/Cat_Release_Order"
ENG_WIKI_COMBOS = ENG_WIKI_BASE + "/wiki/Cat_Combo"
ENG_WIKI_ENEMY_NAMES = ""

MASSAGE = lambda data: data.text if data.font is None else data.font.text

NUMBER_FORM_REGEX = re.compile(r"No.(?P<number>(\d+))-(?P<form>(\d))")

UNIT_EN_NAME_REGEX = re.compile(r"([^\(]+)")

UNIT_COMBO_SPLIT = "<hr class=\"line\"/>"
UNIT_COMBO_JP_NAME_REGEX = re.compile(r"<font class=\"H\">(\D+)</font>")
UNIT_COMBO_EN_NAME_REGEX = re.compile(r"<a[^>]+>(?P<name>[^<]+)</a>")
UNIT_COMBO_UNIT_REGEX = re.compile(r"<a href=\"(\d+).html\">")

UNIT_ABIL_ICON_REGEX = re.compile(r"<img class=\"icon_s\" src=\"https://battlecats-db.imgs-server.com/icon_s_(?P<ability>\d+).png\"/>")
UNIT_ABIL_TARG_REGEX = re.compile(r"<a href=\"../enemy/atr_(?P<attribute>\w+).html\">")

SINGLE_ATTACK = "単体"
AREA_ATTACK = "範囲"

VERSION_STRIP = "追加"
ATTACK_RATE_STRIP = "秒"

ILLEGAL_CHARS = ['$', '#', '[', ']', '/', '.']

got_a_form = 0
saved_n = -1
def parse_cat_unit(unit_num):
	"""Parses a particular cat unit.

	Arguments:
		unit_num {integer} -- The cat unit number to process.

	Returns:
		UnitDB -- A fully qualified cat UnitDB.
	"""
	print("Processing unit %03d" % unit_num)
	unit = UnitDB()
	scipa_soup = _url_to_soup(SCIPA_DB_CAT_UNIT(unit_num), check=True)
	eng_name_soup = _url_to_soup(ENG_WIKI_CAT_NAMES, check=True)
	eng_combo_soup = _url_to_soup(ENG_WIKI_COMBOS, check=True)

	if scipa_soup is None:
		print("Skipping unit %03d" % unit_num)
		return

	scipa_table = scipa_soup.find('table')
	eng_data = [ eng_name_soup.find('table'), eng_combo_soup ]

	if scipa_table is None or eng_data is None:
		print("Skipping unit %03d" % unit_num)

	for n, scipa_data in enumerate(scipa_table.children):
		raw_data = list(scipa_data.children)
		_parse_cat_unit_raw(raw_data, eng_data, unit, n)

	if len(unit) == 0:
		print("Skipping unit %03d" % unit_num)
		return

	try:
		_parse_cat_unit_en_description(eng_data, unit)
	except IndexError as ie:
		print("{} not avaliable for unit #{}. Skipping".format("Description", unit.unitNumber))
	except Exception as e:
		print("Error parsing unit #%03d." % unit_num)
		traceback.print_tb(e.__traceback__)
		print(e)

	with open(unit.unitNumber + ".cat", "w", encoding="utf8") as f:
		f.write(unit.__str__())

	return unit

def _parse_cat_unit_raw(raw_data, eng_data, unit, row):
	"""Helper method to begin filling the UnitDB.

	Arguments:
		raw_data {bs4.BeautifulSoup} -- A table containing the cat unit data from SPICA.
		eng_data {bs4.BeautifulSoup} -- An array containing cat unit data from BattleCatsWiki.
		unit {UnitDB} -- A reference to cat UnitDB object.
		row {integer} -- The current row to parse.
	"""
	global got_a_form, saved_n
	try:
		try:
			match = NUMBER_FORM_REGEX.search(raw_data[0].text)
		except IndexError:
			match = None

		if match is not None:
			unit.unitNumber = match.group("number")
			got_a_form = int(match.group("form"))
			saved_n = row

		if got_a_form is 1:
			_parse_form(raw_data, eng_data, unit, row, "Normal")
		elif got_a_form is 2:
			_parse_form(raw_data, eng_data, unit, row, "Evolved")
		elif got_a_form is 3:
			_parse_form(raw_data, eng_data, unit, row, "True")

	except IndexError as ie:
		print("{} not avaliable for unit #{}-{}. Skipping".format(_get_error_from_row(row), unit.unitNumber, got_a_form))
	except Exception as e:
		print("Error parsing unit at row {}".format(row))
		print(raw_data)
		traceback.print_tb(e.__traceback__)
		print(e)

def _parse_form(raw_data, eng_data, unit, row, form):
	"""Fills the cat UnitDB with data for a particular form.

	Arguments:
		raw_data {bs4.BeautifulSoup} -- A table containing the cat unit data from SPICA.
		eng_data {bs4.BeautifulSoup} -- An array containing cat unit data from BattleCatsWiki.
		unit {UnitDB} -- A reference to cat UnitDB object.
		row {integer} -- The current row to parse.
		form {string} -- A key detailing which form to fill.
	"""
	global saved_n
	if row == saved_n:
		_parse_cat_unit_name_and_version(raw_data, eng_data, unit, form)
	elif row == saved_n + 2:
		_parse_cat_unit_stats_1(raw_data, eng_data, unit, form)
	elif row == saved_n + 3:
		_parse_cat_unit_stats_2(raw_data, eng_data, unit, form)
	elif row == saved_n + 4:
		_parse_cat_unit_stats_3(raw_data, eng_data, unit, form)
	elif row == saved_n + 5:
		_parse_cat_unit_stats_4(raw_data, eng_data, unit, form)
	elif row == saved_n + 6:
		_parse_cat_unit_abilities(raw_data, eng_data, unit, form)
	elif row == saved_n + 7:
		_parse_cat_unit_jp_description(raw_data, eng_data, unit, form)
	elif row == saved_n + 8:
		_parse_cat_unit_obtain_condition(raw_data, eng_data, unit, form)
	elif row == saved_n + 9:
		_parse_cat_unit_combos(raw_data, eng_data, unit, form)

def _parse_cat_unit_name_and_version(raw_data, eng_data, unit, form):
	"""Parses a cat UnitDB name and version.

	Arguments:
		raw_data {bs4.BeautifulSoup} -- A table containing the cat unit data from SPICA.
		eng_data {bs4.BeautifulSoup} -- An array containing cat unit data from BattleCatsWiki.
		unit {UnitDB} -- A reference to cat UnitDB object.
		form {string} -- A key detailing which form to fill.
	"""
	unit[form].jpName = raw_data[1].text
	if form == "Normal":
		unit[form].enName = UNIT_EN_NAME_REGEX.search(str(list(list(eng_data[0].children)[5 + ((int(unit.unitNumber) - 1) * 2)].children)[3].text)).group(0).strip()
	elif form == "Evolved":
		unit[form].enName = UNIT_EN_NAME_REGEX.search(str(list(list(eng_data[0].children)[5 + ((int(unit.unitNumber) - 1) * 2)].children)[4].text.split('/')[0])).group(0).strip()
	elif form == "True":
		unit[form].enName = UNIT_EN_NAME_REGEX.search(str(list(list(eng_data[0].children)[5 + ((int(unit.unitNumber) - 1) * 2)].children)[4].text.split('/')[1])).group(0).strip()
	else:
		unit[form].enName = "No En Name Available"
	unit[form].version = raw_data[2].text.strip(VERSION_STRIP)

def _parse_cat_unit_stats_1(raw_data, eng_data, unit, form):
	"""Parses a cat UnitDB health, knockback count, and attack rates.

	Arguments:
		raw_data {bs4.BeautifulSoup} -- A table containing the cat unit data from SPICA.
		eng_data {bs4.BeautifulSoup} -- An array containing cat unit data from BattleCatsWiki.
		unit {UnitDB} -- A reference to cat UnitDB object.
		form {string} -- A key detailing which form to fill.
	"""
	unit[form].rarity = "狂乱" if "Crazed" in unit[form].enName or "Manic" in unit[form].enName or "狂乱" in unit[form].jpName else raw_data[0].a.text
	unit[form].img = raw_data[1].img.attrs['src']
	unit[form].stats['health'] = _get_base_stat(unit.unitNumber, MASSAGE(list(raw_data[3].children)[0]))
	unit[form].stats['knockback'] = MASSAGE(raw_data[5])
	unit[form].stats['attackRateF'] = MASSAGE(raw_data[7])
	unit[form].stats['attackRateS'] = MASSAGE(raw_data[8]).strip(ATTACK_RATE_STRIP)

def _parse_cat_unit_stats_2(raw_data, eng_data, unit, form):
	"""Parses a cat UnitDB attack power, movement speed, and attack animation time.

	Arguments:
		raw_data {bs4.BeautifulSoup} -- A table containing the cat unit data from SPICA.
		eng_data {bs4.BeautifulSoup} -- An array containing cat unit data from BattleCatsWiki.
		unit {UnitDB} -- A reference to cat UnitDB object.
		form {string} -- A key detailing which form to fill.
	"""
	unit[form].stats['attackPower'] = _get_base_stat(unit.unitNumber, MASSAGE(raw_data[1]))
	unit[form].stats['movementSpeed'] = MASSAGE(raw_data[3])
	unit[form].stats['attackAnimF'] = MASSAGE(raw_data[5])
	unit[form].stats['attackAnimS'] = MASSAGE(raw_data[6]).strip(ATTACK_RATE_STRIP)

def _parse_cat_unit_stats_3(raw_data, eng_data, unit, form):
	"""Parses a cat UnitDB range and respawn times.

	Arguments:
		raw_data {bs4.BeautifulSoup} -- A table containing the cat unit data from SPICA.
		eng_data {bs4.BeautifulSoup} -- An array containing cat unit data from BattleCatsWiki.
		unit {UnitDB} -- A reference to cat UnitDB object.
		form {string} -- A key detailing which form to fill.
	"""
	unit[form].stats['range'] = MASSAGE(raw_data[5])
	unit[form].stats['respawnTimeF'] = MASSAGE(raw_data[7])
	unit[form].stats['respawnTimeS'] = MASSAGE(raw_data[8]).strip(ATTACK_RATE_STRIP)

def _parse_cat_unit_stats_4(raw_data, eng_data, unit, form):
	"""Parses a cat UnitDB attack type and cost.

	Arguments:
		raw_data {bs4.BeautifulSoup} -- A table containing the cat unit data from SPICA.
		eng_data {bs4.BeautifulSoup} -- An array containing cat unit data from BattleCatsWiki.
		unit {UnitDB} -- A reference to cat UnitDB object.
		form {string} -- A key detailing which form to fill.
	"""
	unit[form].stats['attackType'] = MASSAGE(raw_data[3])
	unit[form].stats['attackType'] += "(Single Attack)" if MASSAGE(raw_data[3]) == SINGLE_ATTACK else "(Area Attack)"
	unit[form].stats['cost'] = MASSAGE(raw_data[5])

def _parse_cat_unit_abilities(raw_data, eng_data, unit, form):
	"""Parses a cat UnitDB abilities.

	Arguments:
		raw_data {bs4.BeautifulSoup} -- A table containing the cat unit data from SPICA.
		eng_data {bs4.BeautifulSoup} -- An array containing cat unit data from BattleCatsWiki.
		unit {UnitDB} -- A reference to cat UnitDB object.
		form {string} -- A key detailing which form to fill.

	Raises:
		IndexError: Raised when an unregistered ability is requested.
	"""
	try:
		icon_arr = UNIT_ABIL_ICON_REGEX.findall(str(raw_data[1]))
		targ_arr = UNIT_ABIL_TARG_REGEX.findall(str(raw_data[1]))
		for icon_num in icon_arr:
			ability = ICON_TO_ABILITY_MAP[int(icon_num)]
			if ability:
				unit[form].abilities["abilities"].append(int(icon_num))

		if "all" in targ_arr and "metal" in targ_arr and "white" not in targ_arr:
			unit[form].abilities["target"].append("All (w/o Metal)")
		elif "all" in targ_arr and "metal" not in targ_arr and "white" in targ_arr:
			unit[form].abilities["target"].append("All (w/o White)")
		elif "all" in targ_arr and "metal" not in targ_arr and "white" not in targ_arr:
			unit[form].abilities["target"].append("All (w/o Metal/White)")
		else:
			for target in targ_arr:
				if target and target.capitalize() not in unit[form].abilities["target"]:
					unit[form].abilities["target"].append(target.capitalize())
	except KeyError:
		print("Unit #{}-{}: Ability {} not in DB".format(unit.unitNumber, form, icon_num))
		raise IndexError

def _parse_cat_unit_jp_description(raw_data, eng_data, unit, form):
	"""Parses a cat UnitDB japanese descriptions.

	Arguments:
		raw_data {bs4.BeautifulSoup} -- A table containing the cat unit data from SPICA.
		eng_data {bs4.BeautifulSoup} -- An array containing cat unit data from BattleCatsWiki.
		unit {UnitDB} -- A reference to cat UnitDB object.
		form {string} -- A key detailing which form to fill.
	"""
	unit[form].jpDescription = MASSAGE(raw_data[1])

def _parse_cat_unit_en_description(eng_data, unit):
	"""Parses a cat UnitDB english descriptions.

	This is called outside the main loop as the English Wiki doesn't separate
	cats based on their form.

	Arguments:
		eng_data {bs4.BeautifulSoup} -- An array containing cat unit data from BattleCatsWiki.
		unit {UnitDB} -- A reference to cat UnitDB object.

	Raises:
		IndexError: Raised when there is no english version of the japanese cat.
	"""
	unit_soup = _url_to_soup(ENG_WIKI_BASE + str(list(list(eng_data[0].children)[5 + ((int(unit.unitNumber) - 1) * 2)].children)[3].a["href"]))
	if unit_soup is None:
		print("Can't find unit at " + ENG_WIKI_BASE + str(list(list(eng_data[0].children)[5 + ((int(unit.unitNumber) - 1) * 2)].children)[3].a["href"]))
		raise IndexError
	unit_en_table = unit_soup.find_all('table')

	# Captures the english descriptions that are separated and not in a table.
	# These are typically for obscure cats that no one will find.
	tags = list(filter(lambda tag: "Normal:" in str(tag) or "Evolved:" in \
		str(tag) or "True:" in str(tag), unit_soup.find_all('li')))

	if tags and "English Version" in str(unit_soup):
		unit["Normal"].enDescription = MASSAGE(tags[0]).replace("Normal:", "").strip()
		unit["Evolved"].enDescription = MASSAGE(tags[1]).replace("Evolved:", "").strip()
		if unit["True"]:
			unit["True"].enDescription = MASSAGE(tags[2]).replace("True:", "").strip()
		return

	for i, tag in enumerate(unit_en_table[1].children):
		# There are 2 different forms of tables on the english wiki.
		if "Description" in str(tag):
			# Images
			_parse_cat_unit_thumbnail(list(unit_en_table[1].children)[i].img, unit, "Normal")
			_parse_cat_unit_thumbnail(list(unit_en_table[1].children)[i+6].img, unit, "Evolved")
			# Description
			unit["Normal"].enDescription = MASSAGE(list(unit_en_table[1].children)[i + 2]).strip()
			unit["Evolved"].enDescription = MASSAGE(list(unit_en_table[1].children)[i + 8]).strip()
			if unit["True"]:
				_parse_cat_unit_thumbnail(list(unit_en_table[1].children)[i+12].img, unit, "True")
				unit["True"].enDescription = MASSAGE(list(unit_en_table[1].children)[i + 14]).strip()
			return
		if "description" in str(tag):
			unit["Normal"].enDescription = MASSAGE(list(unit_en_table[1].children)[i].find_all('td')[1]).strip()
			unit["Evolved"].enDescription = MASSAGE(list(unit_en_table[1].children)[i+2].find_all('td')[1]).strip()
			if unit["True"]:
				unit["True"].enDescription = MASSAGE(list(unit_en_table[1].children)[i+4].find_all('td')[1]).strip()
			return
	for i, tag in enumerate(unit_en_table[2].children):
		# For with additional text boxes above indicating incompleteness for
		# cats that also include a combo box.
		if "Description" in str(tag):
			# Images
			_parse_cat_unit_thumbnail(list(unit_en_table[2].children)[i].img, unit, "Normal")
			_parse_cat_unit_thumbnail(list(unit_en_table[2].children)[i+6].img, unit, "Evolved")
			# Description
			unit["Normal"].enDescription = MASSAGE(list(unit_en_table[2].children)[i + 2]).strip()
			unit["Evolved"].enDescription = MASSAGE(list(unit_en_table[2].children)[i + 8]).strip()
			if unit["True"]:
				_parse_cat_unit_thumbnail(list(unit_en_table[2].children)[i+12].img, unit, "True")
				unit["True"].enDescription = MASSAGE(list(unit_en_table[2].children)[i + 14]).strip()
			return
		if "description" in str(tag):
			unit["Normal"].enDescription = MASSAGE(list(unit_en_table[2].children)[i].find_all('td')[1]).strip()
			unit["Evolved"].enDescription = MASSAGE(list(unit_en_table[2].children)[i+2].find_all('td')[1]).strip()
			if unit["True"]:
				unit["True"].enDescription = MASSAGE(list(unit_en_table[2].children)[i+4].find_all('td')[1]).strip()
			return

def _parse_cat_unit_obtain_condition(raw_data, eng_data, unit, form):
	"""Parses a cat UnitDB various obtain conditions.

	Arguments:
		raw_data {bs4.BeautifulSoup} -- A table containing the cat unit data from SPICA.
		eng_data {bs4.BeautifulSoup} -- An array containing cat unit data from BattleCatsWiki.
		unit {UnitDB} -- A reference to cat UnitDB object.
		form {string} -- A key detailing which form to fill.
	"""
	# TODO: Obtain condition
	pass

def _parse_cat_unit_combos(raw_data, eng_data, unit, form):
	"""Parses a cat UnitDB combo list.

	Arguments:
		raw_data {bs4.BeautifulSoup} -- A table containing the cat unit data from SPICA.
		eng_data {bs4.BeautifulSoup} -- An array containing cat unit data from BattleCatsWiki.
		unit {UnitDB} -- A reference to cat UnitDB object.
		form {string} -- A key detailing which form to fill.
	"""
	for combo in str(raw_data[1]).split(UNIT_COMBO_SPLIT):
		jp_name = UNIT_COMBO_JP_NAME_REGEX.search(combo)
		if jp_name == None:
			continue
		jp_name = jp_name.group(1)
		if "</font>" in jp_name:
			jp_name = jp_name.split("</font>")[0]
		unit_list = UNIT_COMBO_UNIT_REGEX.findall(combo)

		en_name = ""
		eng_combo_list = str(eng_data[1]).splitlines()
		for i, combo_line in enumerate(eng_combo_list):
			if jp_name in combo_line:
				en_name = UNIT_COMBO_EN_NAME_REGEX.search(eng_combo_list[i-1]).group('name')
				en_name = en_name.replace('amp;', '')
				en_name = _remove_illegal_chars(en_name)
				break

		unit[form].combos[jp_name + "|" + en_name] = unit_list

def _parse_cat_unit_thumbnail(img_tag, unit, form):
	"""Parses a cat unit's image and downloads it.

	Arguments:
		img_tag {bs4.Tag} -- The tag containing the URL for the image.
	"""
	try:
		unit[form].img = str(img_tag['data-src'])
		return
	except KeyError:
		pass
	unit[form].img = str(img_tag['src'])

def _remove_illegal_chars(string):
	"""Removes Firebase illegal characters.

	Arguments:
		string {str} -- The string to remove the characters from

	Returns:
		str -- The same string with the removed characters.
	"""
	for char in ILLEGAL_CHARS:
		string = string.replace(char, '')
	return string

def _get_base_stat(unit_number, health_lv):
	"""Helper method to get base stat from SPICA's default database level.

	Arguments:
		unit_number {string} -- The unit number of the cat.
		health_lv {string} -- A string number (may be ',' separated).

	Returns:
		float -- The base stat.
	"""
	health_lv = float(health_lv.replace(',', ''))
	# Normal cats have a different modifier than other cats. They also start at
	# a different level than other cats on SPICA.
	if int(unit_number) in range(1, 10):
		return "%.3f" % (health_lv / 16.8)
	# Crazed cats also are special. They have different growth rates than other
	# super rare cats.
	elif int(unit_number) in range(92, 101):
		return "%.3f" % (health_lv / 5.8)
	else:
		return "%.3f" % (health_lv / 6.8)

def _get_error_from_row(row):
	"""Helper method for when a particular row throws an error.

	Arguments:
		row {integer} -- The current row count.

	Returns:
		string -- A brief description of the error.
	"""
	global saved_n
	if row == saved_n:
		return "unit name and version"
	elif row >= saved_n + 2 and row <= saved_n + 5:
		return "unit stat row"
	elif row == saved_n + 6:
		return "special abilities"
	elif row == saved_n + 7:
		return "description"
	elif row == saved_n + 8:
		return "obtain condition"
	elif row == saved_n + 9:
		return "combos"
	else:
		return "ERROR - INVALID ROW"

def _url_to_soup(url, check=False):
	"""Converts a url to a BeautifulSoup object.

	Arguments:
		url {string} -- The URL to connect to.

	Keyword Arguments:
		check {bool} -- Checks if the page was successfully connected to or not.
		(default: {False})
	Returns:
		BeautifulSoup -- A bs4.BeautifulSoup object.
	"""
	soup_url = requests.get(url)
	if soup_url.url != url and check: # Page is not the requested page.
		return None
	return BeautifulSoup(soup_url.text, "lxml")
