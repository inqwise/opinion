package com.inqwise.opinion.library.common.parameters;

public enum VariablesCategories {
	Undefined(0),
	Permissions(1);
	
	private final int value;

	public int getValue() {
		return value;
	}
	
	public Integer getValueOrNullWhenUndefined(){
		return Undefined.getValue() == value ? null : Integer.valueOf(value);
	}

	private VariablesCategories(int value) {
		this.value = value;
	}
	
	public static VariablesCategories fromInt(Integer value){
		return fromInt(null == value ? 0 : value.intValue());
	}
	
	public static VariablesCategories fromInt(int value){
		
		for (VariablesCategories v :VariablesCategories.values()) { 
			if (value == v.value) { 
	          return v; 
	        }
        } 
		
		return Undefined;
	}
}
