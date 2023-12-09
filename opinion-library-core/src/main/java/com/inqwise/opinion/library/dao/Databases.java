package com.inqwise.opinion.library.dao;

public enum Databases {
		Default(null),
		Website("website"),
		Office("office"),
		Opinion("opinion"),
		OpinionReplication("opinionReplication"),
		Resources("resources");

		private final String value;

		public String toString() {
			return value;
		}

		private Databases(String value) {
			this.value = value;
		}
}
