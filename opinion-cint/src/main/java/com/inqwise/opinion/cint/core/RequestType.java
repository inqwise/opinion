package com.inqwise.opinion.cint.core;

public enum RequestType {
	Undefined(""),
	Post("POST"),
	Get("GET"), 
	Put("PUT");
	
	private final String value;

	public String getValue() {
		return value;
	}
	
	public String getValueOrNullWhenUndefined(){
		return Undefined.getValue() == value ? null : value;
	}

	private RequestType(String value) {
		this.value = value;
	}
	
	public static RequestType fromString(String value){
		
		for (RequestType b : RequestType.values()) { 
			if (value == b.value) { 
	          return b; 
	        }
        } 
		
		return Undefined;
	}
}
