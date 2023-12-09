package com.inqwise.opinion.payments.dao;

public enum Databases {
		Default(null),
		Office("office"),
		Payments("payments");

		private final String value;

		public String toString() {
			return value;
		}

		private Databases(String value) {
			this.value = value;
		}
}
