package com.inqwise.opinion.marketplace.dao;

public enum Databases {
	Default(null),
	Market("market");
	

	private final String value;

	public String toString() {
		return value;
	}

	private Databases(String value) {
		this.value = value;
	}
}