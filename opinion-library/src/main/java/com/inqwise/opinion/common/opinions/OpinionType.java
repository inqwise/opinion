package com.inqwise.opinion.opinion.common.opinions;

public enum OpinionType {
	Undefined(0),
	Survey(1),
	Poll(2);
	
	private final int value;

	public int getValue() {
		return value;
	}
	
	public Integer getValueOrNullWhenUndefined(){
		return Undefined.getValue() == value ? null : Integer.valueOf(value);
	}

	private OpinionType(int value) {
		this.value = value;
	}
	
	public static OpinionType fromInt(Integer value){
		return fromInt(null == value ? 0 : value.intValue());
	}
	
	public static OpinionType fromInt(int value){
		
		for (OpinionType a : OpinionType.values()) { 
			if (value == a.value) { 
	          return a; 
	        }
        } 
		
		return Undefined;
	}
}
