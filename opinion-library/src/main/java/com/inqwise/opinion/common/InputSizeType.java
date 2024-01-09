package com.inqwise.opinion.common;

public enum InputSizeType {
	
	Undefined(0),
	Default(1);
	
	private final int value;

	public int getValue() {
		return value;
	}

	private InputSizeType(int value) {
		this.value = value;
	}
	
	public static InputSizeType fromInt(int value){
		
		for (InputSizeType b : InputSizeType.values()) { 
			if (value == b.value) { 
	          return b; 
	        }
        } 
		
		return Undefined;	
	}
	
	public Integer getValueOrNullWhenUndefined() {
		return (Undefined == this ? null : Integer.valueOf(getValue()));
	}
}
