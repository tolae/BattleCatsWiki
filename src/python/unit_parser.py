import re

import requests
from bs4 import BeautifulSoup

import traceback

from unit import UnitDB, UnitDetailsDB, ICON_TO_ABILITY_MAP

SCIPA_DB_BASE = "https://battlecats-db.com/"
SCIPA_DB_CAT_UNIT = lambda unit_id: SCIPA_DB_BASE + "unit/%03d.html" % unit_id

ENG_WIKI_CAT_NAMES = "https://battle-cats.fandom.com/wiki/Cat_Release_Order"
ENG_WIKI_ENEMY_NAMES = ""

MASSAGE = lambda data: data.text if data.font is None else data.font.text

NUMBER_FORM_REGEX = re.compile(r"No.(?P<number>(\d+))-(?P<form>(\d))")

UNIT_COMBO_SPLIT = "<hr class=\"line\"/>"
UNIT_COMBO_NAME_REGEX = re.compile(r"<font class=\"H\">(\D+)</font>")
UNIT_COMBO_UNIT_REGEX = re.compile(r"<a href=\"(\d+).html\">")

UNIT_ABIL_ICON_REGEX = re.compile(r"<img class=\"icon_s\" src=\"https://battlecats-db.imgs-server.com/icon_s_(?P<ability>\d+).png\"/>")
UNIT_ABIL_TARG_REGEX = re.compile(r"<a href=\"../enemy/atr_(?P<attribute>\w+).html\">")

got_a_form = 0
saved_n = -1
def parse_cat_unit(unit_num):
	print("Processing unit %03d" % unit_num)
	unit = UnitDB()
	scipa_soup = _url_to_soup(SCIPA_DB_CAT_UNIT(unit_num))
	eng_soup = _url_to_soup(ENG_WIKI_CAT_NAMES)

	if scipa_soup is None:
		print("Skipping unit %03d" % unit_num)
		return

	scipa_table = scipa_soup.find('table')
	eng_table = eng_soup.find('table')

	for n, scipa_data in enumerate(scipa_table.children):
		raw_data = list(scipa_data.children)
		_parse_cat_unit_raw(raw_data, eng_table, unit, n)

	with open(unit.unitNumber + ".cat", "w") as f:
		f.write(unit.__str__())

	return unit

def _parse_cat_unit_raw(raw_data, eng_table, unit, row):
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
			_parse_form(raw_data, eng_table, unit, row, "Normal")
		elif got_a_form is 2:
			_parse_form(raw_data, eng_table, unit, row, "Evolved")
		elif got_a_form is 3:
			_parse_form(raw_data, eng_table, unit, row, "True")

	except IndexError as ie:
		print("{} not avaliable for unit #{}-{}. Skipping".format(_get_data_from_row(row), unit.unitNumber, got_a_form))
	except Exception as e:
		print("Error parsing unit at row {}".format(row))
		print(raw_data)
		traceback.print_tb(e.__traceback__)
		print(e)

def _parse_form(raw_data, eng_table, unit, row, form):
	global saved_n
	if row == saved_n:
		unit[form].jpName = raw_data[1].text
		if form == "Normal":
			unit[form].enName = list(list(eng_table.children)[5 + ((int(unit.unitNumber) - 1) * 2)].children)[3].text.strip()
		elif form == "Evolved":
			unit[form].enName = list(list(eng_table.children)[5 + ((int(unit.unitNumber) - 1) * 2)].children)[4].text.split('/')[0].strip()
		elif form == "True":
			unit[form].enName = list(list(eng_table.children)[5 + ((int(unit.unitNumber) - 1) * 2)].children)[4].text.split('/')[1].strip()
		else:
			unit[form].enName = "No En Name Available"
		unit[form].version = raw_data[2].text
	elif row == saved_n + 2:
		unit[form].rarity = raw_data[0].a.text
		unit[form].img = raw_data[1].img.attrs['src']
		unit[form].stats['health'] = MASSAGE(list(raw_data[3].children)[0])
		unit[form].stats['knockback'] = MASSAGE(raw_data[5])
		unit[form].stats['attackRateF'] = MASSAGE(raw_data[7])
		unit[form].stats['attackRateS'] = MASSAGE(raw_data[8])
	elif row == saved_n + 3:
		unit[form].stats['attackPower'] = MASSAGE(raw_data[1])
		unit[form].stats['movementSpeed'] = MASSAGE(raw_data[3])
		unit[form].stats['attackAnimF'] = MASSAGE(raw_data[5])
		unit[form].stats['attackAnimS'] = MASSAGE(raw_data[6])
	elif row == saved_n + 4:
		unit[form].stats['range'] = MASSAGE(raw_data[5])
		unit[form].stats['respawnTimeF'] = MASSAGE(raw_data[7])
		unit[form].stats['respawnTimeS'] = MASSAGE(raw_data[8])
	elif row == saved_n + 5:
		unit[form].stats['attackType'] = MASSAGE(raw_data[3])
		unit[form].stats['cost'] = MASSAGE(raw_data[5])
	elif row == saved_n + 6:
		try:
			icon_arr = UNIT_ABIL_ICON_REGEX.findall(str(raw_data[1]))
			targ_arr = UNIT_ABIL_TARG_REGEX.findall(str(raw_data[1]))
			for icon_num in icon_arr:
				ability = ICON_TO_ABILITY_MAP[int(icon_num)]
				if ability:
					unit[form].abilities[1].append(int(icon_num))

			if "all" in targ_arr and "metal" in targ_arr and "white" not in targ_arr:
				unit[form].abilities[0].append("All (w/o Metal)")
			elif "all" in targ_arr and "metal" not in targ_arr and "white" in targ_arr:
				unit[form].abilities[0].append("All (w/o White)")
			elif "all" in targ_arr and "metal" not in targ_arr and "white" not in targ_arr:
				unit[form].abilities[0].append("All (w/o Metal/White)")
			else:
				for target in targ_arr:
					if target:
						unit[form].abilities[0].append(target.capitalize())
		except KeyError:
			print("Unit #{}-{}: Ability {} not in DB".format(unit.unitNumber, form, icon_num))
			raise IndexError
	elif row == saved_n + 7:
		unit[form].description = MASSAGE(raw_data[1])
	elif row == saved_n + 8:
		# TODO: Obtain condition
		pass
	elif row == saved_n + 9:
		for combo in str(raw_data[1]).split(UNIT_COMBO_SPLIT):
			name = UNIT_COMBO_NAME_REGEX.search(combo)
			if name == None:
				continue
			name = name.group(1)
			unit_list = UNIT_COMBO_UNIT_REGEX.findall(combo)
			unit[form].combos[name] = unit_list

def _get_data_from_row(row):
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
	soup_url = requests.get(url)
	if soup_url.url != url: # Page is not the requested page.
		return None
	return BeautifulSoup(soup_url.text, "lxml")
