package com.inqwise.opinion.opinion.common.collectors;

public enum CollectorSourceType {
	Undefined(0),
	Default(1),
	BuyRespondents(2);
	
	private final int value;

	public int getValue() {
		return value;
	}
	
	public Integer getValueOrNullWhenUndefined(){
		return Undefined.getValue() == value ? null : Integer.valueOf(value);
	}

	private CollectorSourceType(int value) {
		this.value = value;
	}
	
	public static CollectorSourceType fromInt(Integer value){
		return fromInt(null == value ? 0 : value.intValue());
	}
	
	public static CollectorSourceType fromInt(int value){
		
		for (CollectorSourceType b : CollectorSourceType.values()) { 
			if (value == b.value) { 
	          return b; 
	        }
        } 
		
		return Undefined;
	}

	public static boolean contains(int statusId) {
		for (CollectorSourceType b : CollectorSourceType.values()) {
			if (statusId == b.value && b != Undefined) { 
		          return true; 
	        }
		}
		
		return false;
	}
}
