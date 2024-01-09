package com.inqwise.opinion.common;

public enum OwnerType {
	Undefined(0),
	User(1),
	Group(2);
	
	private final int value;

	public int getValue() {
		return value;
	}

	private OwnerType(int value) {
		this.value = value;
	}
	
	public static OwnerType fromInt(int value){
		
		for (OwnerType b : OwnerType.values()) { 
			if (value == b.value) { 
	          return b; 
	        }
        } 
		
		return Undefined;
	}
}
