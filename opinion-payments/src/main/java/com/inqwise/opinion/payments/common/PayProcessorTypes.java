package com.inqwise.opinion.payments.common;

public enum PayProcessorTypes {
	Undefined(0),
	Paypal(1);
	
	private final int value;

	public int getValue() {
		return value;
	}
	
	public Integer getValueOrNullWhenUndefined(){
		return Undefined.getValue() == value ? null : Integer.valueOf(value);
	}

	private PayProcessorTypes(int value) {
		this.value = value;
	}
	
	public static PayProcessorTypes fromInt(Integer value){
		return fromInt(null == value ? 0 : value.intValue());
	}
	
	public static PayProcessorTypes fromInt(int value){
		
		for (PayProcessorTypes b : PayProcessorTypes.values()) { 
			if (value == b.value) { 
	          return b; 
	        }
        } 
		
		return Undefined;
	}
}
