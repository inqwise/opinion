package com.inqwise.opinion.common;


public enum Arrange {
	Unknown(0),
	Vertical(1),
	Horisontal(2);
	
	private final int value;

	public int getValue() {
		return value;
	}

	private Arrange(int value) {
		this.value = value;
	}
	
	public static Arrange fromInt(int value){
		
		for (Arrange b : Arrange.values()) { 
			if (value == b.value) { 
	          return b; 
	        }
        } 
		return Arrange.Unknown;
	}

	public Integer getValueOrNullWhenUnknown() {
		
		return (this == Unknown ? null : Integer.valueOf(getValue()));
	}
}
