import textwrap

class UnitDetailsDB(object):
	def __init__(self, form):
		self.form = form
		self.jpName = ""
		self.enName = ""
		self.version = ""
		self.description = ""
		self.combos = {}
		self.stats = {}

	@property
	def full_name(self):
		return "{jpName} ({enName})".format(
			jpName=self.jpName,
			enName=self.enName,
		)

	def __str__(self):
		return textwrap.dedent(
			"""\
			Version: {version}
			Jap Name: {jpName}
			En Name: {enName}
			Desc: {description}
			Combos: {combos}
			Stats: {stats}
			"""
		).format(
			version = self.version,
			jpName = self.jpName,
			enName = self.enName,
			description = self.description,
			combos = self._stringify_dict(self.combos),
			stats = self._stringify_dict(self.stats)
		)

	def _stringify_dict(self, stat):
		return "\n".join(list("\t{key} - {value}".format(key=key, value=value) for key, value in stat.items()))


class UnitDB(object):
	def __init__(self):
		self.unitNumber = ""
		self._unitDetails = {
			"Normal": UnitDetailsDB("Normal"),
			"Evolved": UnitDetailsDB("Evolved"),
			"True": UnitDetailsDB("True")
		}

	def __getitem__(self, item):
		return self._unitDetails[item]

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