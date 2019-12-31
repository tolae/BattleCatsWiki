import textwrap

ICON_TO_ABILITY_MAP = {
	# Effects
	1: "Weaken",
	2: "Freeze",
	3: "Slow",
	4: "Attacks Only",
	5: "Strong",
	6: "Resistant",
	7: "Insanely Tough",
	8: "Massive Damage",
	9: "Insane Damage",
	10: "Knockback",
	# Abilities
	12: "Strengthen",
	13: "Survive",
	14: "Base Destroyer",
	15: "Critical",
	16: "Zombie Killer",
	17: "Barrier Breaker",
	18: "Extra Money",
	19: "Metal",
	20: "Wave Attack",
	21: "Immune to Weaken",
	22: "Immune to Freeze",
	23: "Immune to Slow",
	24: "Immune to Knockback",
	25: "Immune to Waves",
	26: "Warp Blocker",
	27: "Immune to Curse",
	28: "Wave Shield",
	30: "Long Distance (Single)",
	31: "Long Distance (Area)",
	32: "Long Distance (Omni)",
	101: "Eva Angel Killer",
	102: "Witch Killer",
	900: "",
	999: "Multi-Hit",
}

class UnitDetailsDB(object):
	"""Holds details on a cat unit of a particular form."""
	def __init__(self, form):
		"""Constructor for a particular cat unit's form.

		Arguments:
			form {string} -- The form of this particular cat unit.
		"""
		self.form = form
		self.jpName = ""
		self.enName = ""
		self.version = ""
		self.img = ""
		self.jpDescription = ""
		self.enDescription = ""
		self.combos = {}
		self.stats = {}
		self.abilities = {
			"target": [],
			"abilities": []
		}

	@property
	def full_name(self):
		"""Concats the cat unit's English and Japanese name.

		Returns:
			string -- A string containing both the English and Japanese name of
			the cat unit.
		"""
		return "{jpName} ({enName})".format(
			jpName=self.jpName,
			enName=self.enName,
		)

	def __len__(self):
		"""A cat unit is considered 'None' or empty if it doesn't have a name.

		Returns:
			integer -- 0 for empty, 1 otherwise.
		"""
		return self.jpName != ""

	def __str__(self):
		return textwrap.dedent(
			"""\
			Version: {version}
			Image: {img}
			Jap Name: {jpName}
			En Name: {enName}
			Jp Desc: {jpDesc}
			En Desc: {enDesc}
			Abilities: {target}:{abilities}
			Combos: {combos}
			Stats: {stats}
			"""
		).format(
			version = self.version,
			img = self.img,
			jpName = self.jpName,
			enName = self.enName,
			jpDesc = self.jpDescription,
			enDesc = self.enDescription,
			target = ",".join(self.abilities["target"]) if self.abilities["target"] else "None",
			abilities = ",".join([ ICON_TO_ABILITY_MAP[ability] for ability in self.abilities["abilities"] ]),
			combos = UnitDetailsDB._stringify_dict(self.combos),
			stats = UnitDetailsDB._stringify_dict(self.stats)
		)

	@staticmethod
	def _stringify_dict(d):
		"""Helper function to create a pretty version of internal dictionaries.

		Arguments:
			d {dictionary} -- The dictionary to make pretty.

		Returns:
			[string] -- A pretty string frm the dictionary.
		"""
		return "\n".join(list("\t{key} - {value}".format(key=key, value=value) for key, value in d.items()))


class UnitDB(object):
	"""Holds details of a particular cat unit."""
	def __init__(self):
		"""Constructor for a particular cat."""
		self.unitNumber = ""
		self._unitDetails = {
			"Normal": UnitDetailsDB("Normal"),
			"Evolved": UnitDetailsDB("Evolved"),
			"True": UnitDetailsDB("True")
		}

	def __getitem__(self, item):
		return self._unitDetails[item]

	def __len__(self):
		return self.unitNumber != ""

	def __str__(self):
		return textwrap.dedent(
"""\
{number}\n
Normal:\n{normal}\n
Evolved:\n{evolved}\n
True:\n{true}\n
""".format(
				number=self.unitNumber,
				normal=self._unitDetails["Normal"],
				evolved=self._unitDetails["Evolved"],
				true=self._unitDetails["True"]
			)
		)
