package com.inqwise.opinion.opinion.common;

public enum OptionsType {
	None(0),
	CheckBox(1),
	RadioButton(2),
	ComboBox(3),
	ListBox(4),
	Slider(5),
	Spinner(6);
	
	private final int value;

	public int getValue() {
		return value;
	}

	private OptionsType(int value) {
		this.value = value;
	}
	
	public static OptionsType fromInt(int value) throws Exception{
		
		for (OptionsType b : OptionsType.values()) { 
			if (value == b.value) { 
	          return b; 
	        }
        } 
		
		throw new Exception("Unknown OptionsType: " + value);
	}
}
