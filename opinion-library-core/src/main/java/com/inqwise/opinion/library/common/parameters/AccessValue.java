package com.inqwise.opinion.library.common.parameters;

public enum AccessValue {
	Undefined(null),
	Denied(0),
	Permitted(1);
	
	private final Integer value;

	public Integer getValue() {
		return value;
	}
	
	private AccessValue(Integer value) {
		this.value = value;
	}
	
	public static AccessValue fromInt(int value){
		
		for (AccessValue v : AccessValue.values()) { 
			if (null != v.value && value == v.value) { 
	          return v; 
	        }
        } 
		
		return Undefined;
	}
}
