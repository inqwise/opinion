package com.inqwise.opinion.common;

public enum ControlType {
	Undefined(0),
	Banner(1),
	OptionList(2),
	FreeText(3),
	Matrix(4),
	Rating(5),
	Date(6),
	Name(7),
	HtmlSnippet(8),
	MatrixQuestion(9),
	Scale(10), Attribute(11), Label(12);	
	
	private final int value;

	public int getValue() {
		return value;
	}

	private ControlType(int value) {
		this.value = value;
	}
	
	public static ControlType fromInt(int value){
		
		for (ControlType b : ControlType.values()) { 
			if (value == b.value) { 
	          return b; 
	        }
        } 
		
		return Undefined;
				
	}
}
