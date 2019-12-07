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

SCIPA_DB_BASE = "https://battlecats-db.com/"
SCIPA_DB_CAT_UNIT = lambda unit_id: SCIPA_DB_BASE + "unit/%03d.html" % unit_id

ENG_WIKI_CAT_NAMES = "https://battle-cats.fandom.com/wiki/Cat_Release_Order"
ENG_WIKI_ENEMY_NAMES = ""

MASSAGE = lambda data: data.text if data.font is None else data.font.text

NUMBER_FORM_REGEX = re.compile(r"No.(?P<number>(\d+))-(?P<form>(\d))")

UNIT_COMBO_SPLIT = "<hr class=\"line\"/>"
UNIT_COMBO_NAME_REGEX = re.compile(r"<font class=\"H\">(\D+)</font>")
UNIT_COMBO_UNIT_REGEX = re.compile(r"<a href=\"(\d+).html\">")

class UnitDB(object):
	def __init__(self):
		self.jpName = []
		self.enName = []
		self.unitNumber = ""
		self.description = []
		self.combos = ({}, {}, {})
		self.stats = ({}, {}, {})

	@property
	def full_name(self):
		return "{jpName} ({enName}) - {number}".format(
			jpName=self.jpName,
			enName=self.enName,
			number=self.unitNumber
		)

	def __str__(self):
		stringfy = textwrap.dedent(
			"""\
			Jap Name: {jpName}
			En Name: {enName}
			Unit #: {unitNumber}
			Desc: {description}
			Combos: {combos}
			Stats:
			"""
		).format(
			jpName = self.jpName,
			enName = self.enName,
			unitNumber = self.unitNumber,
			description = self.description,
			combos = self._stringify_dict(self.combos)
		)
		stringfy += "Normal\n" + self._stringify_dict(self.stats[0])
		stringfy += "Evolved\n" + self._stringify_dict(self.stats[1])
		stringfy += "True\n" + self._stringify_dict(self.stats[2])
		return stringfy

	def _stringify_dict(self, stat):
		return "\n".join(list("\t{key} - {value}".format(key=key, value=value) for key, value in stat.items()))

def parse_cat_unit_raw(raw_data, unit):
	try:
		if n == 10:
			match = NUMBER_FORM_REGEX.search(raw_data[0].text)
			unit.unitNumber = match.group("number")
			unit.jpName = raw_data[1].text
			unit.enName = list(list(eng_table.children)[5 + ((int(unit.unitNumber) - 1) * 2)].children)[3].text.strip()
			unit.version = raw_data[2].text
		elif n == 12:
			unit.rarity = raw_data[0].a.text
			unit.img = raw_data[1].img.attrs['src']
			unit.stats[0]['health'] = MASSAGE(list(raw_data[3].children)[0])
			unit.stats[0]['knockback'] = MASSAGE(raw_data[5])
			unit.stats[0]['attackRateF'] = MASSAGE(raw_data[7])
			unit.stats[0]['attackRateS'] = MASSAGE(raw_data[8])
		elif n == 13:
			unit.stats[0]['attackPower'] = MASSAGE(raw_data[1])
			unit.stats[0]['movementSpeed'] = MASSAGE(raw_data[3])
			unit.stats[0]['attackAnimF'] = MASSAGE(raw_data[5])
			unit.stats[0]['attackAnimS'] = MASSAGE(raw_data[6])
		elif n == 14:
			unit.stats[0]['range'] = MASSAGE(raw_data[5])
			unit.stats[0]['respawnTimeF'] = MASSAGE(raw_data[7])
			unit.stats[0]['respawnTimeS'] = MASSAGE(raw_data[8])
		elif n == 15:
			unit.stats[0]['attackType'] = MASSAGE(raw_data[3])
			unit.stats[0]['cost'] = MASSAGE(raw_data[5])
		elif n == 16:
			# TODO: Special abilities
			pass
		elif n == 17:
			unit.description = MASSAGE(raw_data[1])
		elif n == 18:
			# TODO: Obtain condition
			pass
		elif n == 19:
			for combo in str(raw_data[1]).split(UNIT_COMBO_SPLIT):
				name = UNIT_COMBO_NAME_REGEX.search(combo)
				if name == None:
					continue
				name = name.group(1)
				unit_list = UNIT_COMBO_UNIT_REGEX.findall(combo)
				unit.combos[name] = unit_list
		elif n == 23: # Evolved Form
			match = NUMBER_FORM_REGEX.search(raw_data[0].text)
			unit.unitNumber = match.group("number")
			unit.jpName = raw_data[1].text
			unit.enName = list(list(eng_table.children)[5 + ((int(unit.unitNumber) - 1) * 2)].children)[3].text.strip()
			unit.version = raw_data[2].text

	except Exception as e:
		print("Error parsing unit at row {}".format(n))
		print(raw_data)
		traceback.print_tb(e.__traceback__)
		print(e)

def url_to_soup(url, check=False):
	soup_url = requests.get(url)
	if soup_url.url != url: # Page is not the requested page.
		return None
	return BeautifulSoup(soup_url.text, "lxml")

def upload():
	cred = credentials.Certificate("/home/etola/Projects/Personal/BattleCatsWiki/bcatwiki-firebase-adminsdk-h34ri-5c1974ed8d.json")
	firebase_admin.initialize_app(cred, {
		'databaseURL': "https://bcatwiki.firebaseio.com/"
	})

	mFiredb = db.reference("cat_units")

	for unit in unit_arr:
		unit_db_ref = mFiredb.child( unit.unitNumber )
		unit_db_ref.set( unit.__dict__ )

if __name__ == "__main__":
	try:
		unit_arr = []

		for i in range(1, 2):
			unit = UnitDB()
			scipa_soup = url_to_soup(SCIPA_DB_CAT_UNIT(i))
			eng_soup = url_to_soup(ENG_WIKI_CAT_NAMES)
			scipa_table = scipa_soup.find('table')
			eng_table = eng_soup.find('table')

			start = time.time()
			for n, scipa_data in enumerate(scipa_table.children):
				raw_data = list(scipa_data.children)
				parse_cat_unit_raw(raw_data, unit)

			print("Took %f seconds" % (time.time() - start))
			unit_arr.append(unit)

			with open(unit.unitNumber, "w") as f:
				f.write(unit.__str__())
		# upload()
		print("Done! Total cat processed: %d" % len(unit_arr))
	except KeyboardInterrupt:
		pass
