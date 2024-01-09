package com.inqwise.opinion.common;

public enum GuidType {
	Undefined(0),
	Collector(1),
	Draft(2);
	
	private final int value;

	public int getValue() {
		return value;
	}

	private GuidType(int value) {
		this.value = value;
	}
	
	public static GuidType fromInt(int value){
		
		for (GuidType b : GuidType.values()) { 
			if (value == b.value) { 
	          return b; 
	        }
        } 
		
		return Undefined;
				
	}
}
