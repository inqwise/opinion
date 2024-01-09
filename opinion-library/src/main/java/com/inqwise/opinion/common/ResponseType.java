package com.inqwise.opinion.common;

public enum ResponseType {
	Undefined(0),
	StartOpinion(1),
	FinishOpinion(2),
	NextSheet(3),
	PreviousSheet(4),
	Control(5),
	Option(6),
	Authorize(7);
	
	private final int value;

	public int getValue() {
		return value;
	}
	
	public Integer getValueOrNullWhenUndefined(){
		return Undefined.getValue() == value ? null : Integer.valueOf(value);
	}

	private ResponseType(int value) {
		this.value = value;
	}
	
	public static ResponseType fromInt(Integer value){
		return fromInt(null == value ? 0 : value.intValue());
	}
	
	public static ResponseType fromInt(int value){
		
		for (ResponseType b : ResponseType.values()) { 
			if (value == b.value) { 
	          return b; 
	        }
        } 
		
		return Undefined;
	}

	public static boolean contains(int statusId) {
		for (ResponseType b : ResponseType.values()) {
			if (statusId == b.value && b != Undefined) { 
		          return true; 
	        }
		}
		
		return false;
	}
}
