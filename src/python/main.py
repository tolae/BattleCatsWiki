import re
import time
import json

import requests
from bs4 import BeautifulSoup

import textwrap
import traceback

import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore

SCIPA_DB_BASE = "https://battlecats-db.com/"
SCIPA_DB_CAT_UNIT = lambda unit_id: SCIPA_DB_BASE + "unit/%03d.html" % unit_id

ENG_WIKI_CAT_NAMES = "https://battle-cats.fandom.com/wiki/Cat_Release_Order"
ENG_WIKI_ENEMY_NAMES = ""

MASSAGE = lambda data: data.text if data.font is None else data.font.text

NUMBER_FORM_REGEX = re.compile(r"No.(?P<number>(\d+))-(?P<form>(\d))")

class UnitDB(object):
	def __init__(self):
		self.jp_name = ""
		self.en_name = ""
		self.unit_number = ""
		self.unit_form = ""
		self.stats = {}

	@property
	def full_name(self):
		return "{jp_name} ({en_name}) - {number}".format(
			jp_name=self.jp_name,
			en_name=self.en_name,
			number=self.unit_number
		)

	def __str__(self):
		return textwrap.dedent(
			"""
			{full_name}
			Stats:
			"""
		).format(full_name=self.full_name) + \
		"\n".join(list("\t{key}: {value}".format(key=key, value=value) for key, value in self.stats.items()))

def parse_cat_unit_raw(raw_data, unit):
	try:
		if n == 10:
			match = NUMBER_FORM_REGEX.search(raw_data[0].text)
			unit.unit_number = match.group("number")
			unit.unit_form = match.group("form")
			unit.jp_name = raw_data[1].text
			unit.en_name = list(list(eng_table.children)[5 + ((int(unit.unit_number[3:6]) - 1) * 2)].children)[3].text.strip()
			unit.version = raw_data[2].text
		elif n == 12:
			unit.rarity = raw_data[0].a.text
			unit.img = raw_data[1].img
			unit.stats['health'] = MASSAGE(list(raw_data[3].children)[0])
			unit.stats['knockback'] = MASSAGE(raw_data[5])
			unit.stats['attack rate (frames)'] = MASSAGE(raw_data[7])
			unit.stats['attack rate (seconds)'] = MASSAGE(raw_data[8])
		elif n == 13:
			unit.stats['attack power'] = MASSAGE(raw_data[1])
			unit.stats['movement speed'] = MASSAGE(raw_data[3])
			unit.stats['attack animation (frames)'] = MASSAGE(raw_data[5])
			unit.stats['attack animation (seconds)'] = MASSAGE(raw_data[6])
		elif n == 14:
			unit.stats['range'] = MASSAGE(raw_data[5])
			unit.stats['respawn time (frames)'] = MASSAGE(raw_data[7])
			unit.stats['respawn time (seconds)'] = MASSAGE(raw_data[8])
		elif n == 15:
			unit.stats['attack type'] = MASSAGE(raw_data[3])
			unit.stats['cost'] = MASSAGE(raw_data[5])
		elif n == 16:
			# TODO: Special abilities
			pass
		elif n == 19:
			# TODO: Associated Combos
			pass
		# TODO: Evolved forms
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
	cred = credentials.Certificate("/home/etola/Project/BattleCatsWikiScrap/bcatwiki-firebase-adminsdk-h34ri-5c1974ed8d.json")
	firebase_admin.initialize_app(cred)

	db = firestore.client()

	cat_unit_ref = db.collection('cat_units')
	for unit in unit_arr:
		unit_db_ref = cat_unit_ref.document( unit.full_name )
		unit_db_ref.set( unit.__dict__ )

if __name__ == "__main__":
	try:
		unit_arr = []

		for i in range(1, 504):
			unit = UnitDB()
			scipa_soup = url_to_soup(SCIPA_DB_CAT_UNIT(i))
			eng_soup = url_to_soup(ENG_WIKI_CAT_NAMES)
			scipa_table = scipa_soup.find('table')
			eng_table = eng_soup.find('table')

			start = time.time()
			for n, child in enumerate(scipa_table.children):
				raw_data = list(child.children)
				parse_cat_unit_raw(raw_data, unit)
			print(unit)
			print("Took %f seconds" % (time.time() - start))
			unit_arr.append(unit)


		print("Done! Total cat processed: %d" % len(unit_arr))
	except KeyboardInterrupt:
		pass
