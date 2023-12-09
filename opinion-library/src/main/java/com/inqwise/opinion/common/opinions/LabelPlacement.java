package com.inqwise.opinion.opinion.common.opinions;


public enum LabelPlacement {
	Undefined(null),
	Top(0),
	Left(1),
	Right(2);
	
	private Integer value;
	
	LabelPlacement(Integer value) {
		this.setValue(value);
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
	
	public static LabelPlacement fromInt(Integer value){
		
		for (LabelPlacement t : LabelPlacement.values()) { 
			if (null != value && value.equals(t.value)) { 
	          return t; 
	        }
        } 
		
		return Undefined;
	}
}
