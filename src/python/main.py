import re
import time
import json

import requests
from bs4 import BeautifulSoup

import textwrap
import traceback

import firebase_admin
from firebase_admin import credentials
from firebase_admin import db

from unit import UnitDB, UnitDetailsDB

SCIPA_DB_BASE = "https://battlecats-db.com/"
SCIPA_DB_CAT_UNIT = lambda unit_id: SCIPA_DB_BASE + "unit/%03d.html" % unit_id

ENG_WIKI_CAT_NAMES = "https://battle-cats.fandom.com/wiki/Cat_Release_Order"
ENG_WIKI_ENEMY_NAMES = ""

MASSAGE = lambda data: data.text if data.font is None else data.font.text

NUMBER_FORM_REGEX = re.compile(r"No.(?P<number>(\d+))-(?P<form>(\d))")

UNIT_COMBO_SPLIT = "<hr class=\"line\"/>"
UNIT_COMBO_NAME_REGEX = re.compile(r"<font class=\"H\">(\D+)</font>")
UNIT_COMBO_UNIT_REGEX = re.compile(r"<a href=\"(\d+).html\">")

got_a_form = 0
saved_n = -1
def parse_cat_unit_raw(raw_data, unit):
	global got_a_form, saved_n
	try:
		try:
			match = NUMBER_FORM_REGEX.search(raw_data[0].text)
		except IndexError:
			match = None

		if match is not None:
			unit.unitNumber = match.group("number")
			got_a_form = int(match.group("form"))
			saved_n = n

		if got_a_form is 1:
			_parse_form(raw_data, unit, "Normal", saved_n)
		elif got_a_form is 2:
			_parse_form(raw_data, unit, "Evolved", saved_n)
		elif got_a_form is 3:
			_parse_form(raw_data, unit, "True", saved_n)

	except IndexError as ie:
		print("{} not avaliable for unit #{}-{}. Skipping".format(_get_data_from_row(n), unit.unitNumber, got_a_form))
	except Exception as e:
		print("Error parsing unit at row {}".format(n))
		print(raw_data)
		traceback.print_tb(e.__traceback__)
		print(e)

def _parse_form(raw_data, unit, form, saved_n):
	if n == saved_n:
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
	elif n == saved_n + 2:
		unit[form].rarity = raw_data[0].a.text
		unit[form].img = raw_data[1].img.attrs['src']
		unit[form].stats['health'] = MASSAGE(list(raw_data[3].children)[0])
		unit[form].stats['knockback'] = MASSAGE(raw_data[5])
		unit[form].stats['attackRateF'] = MASSAGE(raw_data[7])
		unit[form].stats['attackRateS'] = MASSAGE(raw_data[8])
	elif n == saved_n + 3:
		unit[form].stats['attackPower'] = MASSAGE(raw_data[1])
		unit[form].stats['movementSpeed'] = MASSAGE(raw_data[3])
		unit[form].stats['attackAnimF'] = MASSAGE(raw_data[5])
		unit[form].stats['attackAnimS'] = MASSAGE(raw_data[6])
	elif n == saved_n + 4:
		unit[form].stats['range'] = MASSAGE(raw_data[5])
		unit[form].stats['respawnTimeF'] = MASSAGE(raw_data[7])
		unit[form].stats['respawnTimeS'] = MASSAGE(raw_data[8])
	elif n == saved_n + 5:
		unit[form].stats['attackType'] = MASSAGE(raw_data[3])
		unit[form].stats['cost'] = MASSAGE(raw_data[5])
	elif n == saved_n + 6:
		# TODO: Special abilities
		pass
	elif n == saved_n + 7:
		unit[form].description = MASSAGE(raw_data[1])
	elif n == saved_n + 8:
		# TODO: Obtain condition
		pass
	elif n == saved_n + 9:
		for combo in str(raw_data[1]).split(UNIT_COMBO_SPLIT):
			name = UNIT_COMBO_NAME_REGEX.search(combo)
			if name == None:
				continue
			name = name.group(1)
			unit_list = UNIT_COMBO_UNIT_REGEX.findall(combo)
			unit[form].combos[name] = unit_list

def _get_data_from_row(n):
	global saved_n
	if n == saved_n:
		return "unit name and version"
	elif n >= saved_n + 2 and n <= saved_n + 5:
		return "unit stat row"
	elif n == saved_n + 6:
		return "special abilities"
	elif n == saved_n + 7:
		return "description"
	elif n == saved_n + 8:
		return "obtain condition"
	elif n == saved_n + 9:
		return "combos"
	else:
		return "ERROR - INVALID N"

def url_to_soup(url, check=False):
	soup_url = requests.get(url)
	if soup_url.url != url: # Page is not the requested page.
		return None
	return BeautifulSoup(soup_url.text, "lxml")

def upload():
	cred = credentials.Certificate("/home/etola/Projects/Personal/BattleCatsWiki/bcatwiki-firebase-adminsdk-qqxi6-3601769fe8.json")
	firebase_admin.initialize_app(cred, {
		'databaseURL': "https://bcatwiki.firebaseio.com/"
	})

	mFiredb = db.reference("cat_units")

	def _upload_form(db_ref, form):
		unit_db_ref_form = db_ref.child( form )
		unit_db_ref_form.set( unit[form].__dict__ )

	for unit in unit_arr:
		unit_db_ref = mFiredb.child( unit.unitNumber )
		_upload_form(unit_db_ref, "Normal")
		_upload_form(unit_db_ref, "Evolved")
		_upload_form(unit_db_ref, "True")

if __name__ == "__main__":
	try:
		unit_arr = []
		start_time = time.time()
		for i in range (1, 540):
			print("Processing unit %03d" % i)
			unit = UnitDB()
			scipa_soup = url_to_soup(SCIPA_DB_CAT_UNIT(i))
			eng_soup = url_to_soup(ENG_WIKI_CAT_NAMES)

			if scipa_soup is None:
				print("Skipping unit %03d" % i)
				continue

			scipa_table = scipa_soup.find('table')
			eng_table = eng_soup.find('table')

			start = time.time()
			for n, scipa_data in enumerate(scipa_table.children):
				raw_data = list(scipa_data.children)
				parse_cat_unit_raw(raw_data, unit)

			unit_arr.append(unit)

			with open(unit.unitNumber + ".cat", "w") as f:
				f.write(unit.__str__())

		# upload()
		print("Done! Total cat processed: %d in %.03f" % (len(unit_arr), time.time() - start_time))
	except KeyboardInterrupt:
		pass
	except Exception:
		print("Soup! {}".format(scipa_soup))
		print("Table! {}".formt(scipa_table))
