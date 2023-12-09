package com.cint.core;

public class ResponseValue {
	private ValueType type;
	private String value;
	
	public ResponseValue(String value, ValueType type) {
		this.setValue(value);
		this.setType(type);
	}

	public ValueType getType() {
		return type;
	}

	public void setType(ValueType type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
