package com.inqwise.opinion.library.common.parameters;

public enum VariableType {
	Undefined(0),
	Access(1);
	
	private final int value;

	public int getValue() {
		return value;
	}
	
	public Integer getValueOrNullWhenUndefined(){
		return Undefined.getValue() == value ? null : Integer.valueOf(value);
	}

	private VariableType(int value) {
		this.value = value;
	}
	
	public static VariableType fromInt(Integer value){
		return fromInt(null == value ? 0 : value.intValue());
	}
	
	public static VariableType fromInt(int value){
		
		for (VariableType v : VariableType.values()) { 
			if (value == v.value) { 
	          return v; 
	        }
        } 
		
		return Undefined;
	}
}
