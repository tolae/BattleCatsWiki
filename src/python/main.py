import time
from multiprocessing import cpu_count, Pool

import firebase_admin
from firebase_admin import credentials
from firebase_admin import db

from unit_parser import parse_cat_unit

def upload(unit_arr):
	cred = credentials.Certificate("/home/etola/Projects/Personal/BattleCatsWiki/bcatwiki-firebase-adminsdk-qqxi6-3601769fe8.json")
	firebase_admin.initialize_app(cred, {
		'databaseURL': "https://bcatwiki.firebaseio.com/"
	})

	mFiredb = db.reference("cat_units")

	def _upload_form(db_ref, unit, form):
		if unit[form]:
			unit_db_ref_form = db_ref.child( form )
			unit_db_ref_form.set( unit[form].__dict__ )

	for unit in unit_arr:
		unit_db_ref = mFiredb.child( unit.unitNumber )
		_upload_form(unit_db_ref, unit, "Normal")
		_upload_form(unit_db_ref, unit, "Evolved")
		_upload_form(unit_db_ref, unit, "True")

if __name__ == "__main__":
	try:
		start_time = time.time()
		pools = Pool(processes=cpu_count())
		unit_arr = list(filter(None, pools.map(parse_cat_unit, range(1, 540))))

		# for unit_num in range(1, 540):
		# 	parse_cat_unit(unit_num)

		# print(unit_arr)
		# upload(unit_arr)
		# print("Done! Total cat processed: %d in %.03f" % (len(unit_arr), time.time() - start_time))
	except KeyboardInterrupt:
		pass
