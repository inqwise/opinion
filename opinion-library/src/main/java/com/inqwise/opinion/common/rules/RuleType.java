package com.inqwise.opinion.common.rules;


public enum RuleType {
	Undefined(0),
	Opinion(1),
	Sheet(2),
	Control(3);
	
	private final int value;

	public int getValue() {
		return value;
	}

	private RuleType(int value) {
		this.value = value;
	}
	
	public static RuleType fromInt(int value){
		
		for (RuleType b : RuleType.values()) { 
			if (value == b.value) { 
	          return b; 
	        }
        } 
		
		return Undefined;
				
	}
}
