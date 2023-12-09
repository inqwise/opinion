package com.inqwise.opinion.opinion.common;

public enum LinkType {
	Undefined(0),
	Image(1),
	EmbedCode(2);
	
	private final int value;

	public int getValue() {
		return value;
	}
	
	public Integer getValueOrNullWhenUndefined(){
		return Undefined.getValue() == value ? null : Integer.valueOf(value);
	}

	private LinkType(int value) {
		this.value = value;
	}
	
	public static LinkType fromInt(Integer value){
		return fromInt(null == value ? 0 : value.intValue());
	}
	
	public static LinkType fromInt(int value){
		
		for (LinkType b : LinkType.values()) { 
			if (value == b.value) { 
	          return b; 
	        }
        } 
		
		return Undefined;
	}
}
