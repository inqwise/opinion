package com.inqwise.opinion.payments.common;

public enum RefundTypes {
	Undefined(0),
	Full(1),
	Partial(2);
	
	private final int value;

	public int getValue() {
		return value;
	}
	
	public Integer getValueOrNullWhenUndefined(){
		return Undefined.getValue() == value ? null : Integer.valueOf(value);
	}

	private RefundTypes(int value) {
		this.value = value;
	}
	
	public static RefundTypes fromInt(Integer value){
		return fromInt(null == value ? 0 : value.intValue());
	}
	
	public static RefundTypes fromInt(int value){
		
		for (RefundTypes b : RefundTypes.values()) { 
			if (value == b.value) { 
	          return b; 
	        }
        } 
		
		return Undefined;
	}
}
