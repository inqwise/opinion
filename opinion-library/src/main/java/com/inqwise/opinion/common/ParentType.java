package com.inqwise.opinion.opinion.common;

public enum ParentType {
	Undefined(0),
	Opinion(1),
	Sheet(2),
	Control(3);
	
	private final int value;

	public int getValue() {
		return value;
	}

	private ParentType(int value) {
		this.value = value;
	}
	
	public static ParentType fromInt(int value){
		
		for (ParentType b : ParentType.values()) { 
			if (value == b.value) { 
	          return b; 
	        }
        } 
		
		return Undefined;
				
	}
}
