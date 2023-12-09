package com.inqwise.opinion.cms.common;

public enum ResourceType {
	Undefined(0),
	Script(1),
	Css(2);
	
	private final int value;

	public int getValue() {
		return value;
	}
	
	public Integer getValueOrNullWhenUndefined(){
		return Undefined.getValue() == value ? null : Integer.valueOf(value);
	}

	private ResourceType(int value) {
		this.value = value;
	}
	
	public static ResourceType fromInt(Integer value){
		return fromInt(null == value ? 0 : value.intValue());
	}
	
	public static ResourceType fromInt(int value){
		
		for (ResourceType b : ResourceType.values()) { 
			if (value == b.value) { 
	          return b; 
	        }
        } 
		
		return Undefined;
	}
}
