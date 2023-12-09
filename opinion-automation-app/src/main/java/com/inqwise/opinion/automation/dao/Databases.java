package com.inqwise.opinion.automation.dao;

public enum Databases {
	Default(null),
	Office("office");
	
	private final String value;

	public String toString() {
		return value;
	}

	private Databases(String value) {
		this.value = value;
	}
}