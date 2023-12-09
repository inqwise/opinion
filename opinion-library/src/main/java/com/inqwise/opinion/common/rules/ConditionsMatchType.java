package com.inqwise.opinion.opinion.common.rules;

public enum ConditionsMatchType {
	Undefined(0),
	All(1),
	Any(2),
	None(3);
	
	private final int value;

	public int getValue() {
		return value;
	}

	private ConditionsMatchType(int value) {
		this.value = value;
	}
	
	public static ConditionsMatchType fromInt(int value){
		
		for (ConditionsMatchType b : ConditionsMatchType.values()) { 
			if (value == b.value) { 
	          return b; 
	        }
        } 
		
		return Undefined;
				
	}
}
