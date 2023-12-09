package com.inqwise.opinion.opinion.common;

public enum ExportType {
	Undefined(null),
	Participants("participants"),
	Opinion("opinion");
	
	
	private String value;
	
	ExportType(String value) {
		this.setValue(value);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public static ExportType fromString(String value){
		
		for (ExportType t : ExportType.values()) { 
			if (null != value && value.equalsIgnoreCase(t.value)) { 
	          return t; 
	        }
        } 
		
		return Undefined;
	}
}
