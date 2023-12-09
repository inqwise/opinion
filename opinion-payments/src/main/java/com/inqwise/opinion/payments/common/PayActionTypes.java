package com.inqwise.opinion.payments.common;

public enum PayActionTypes {
	Undefined(0),
	DirectPayment(1),
	Refund(2),
	SetExpressCheckout(3),
	Ipn(4),
	DoExpressCheckout(5);
	
	private final int value;

	public int getValue() {
		return value;
	}
	
	public Integer getValueOrNullWhenUndefined(){
		return Undefined.getValue() == value ? null : Integer.valueOf(value);
	}

	private PayActionTypes(int value) {
		this.value = value;
	}
	
	public static PayActionTypes fromInt(Integer value){
		return fromInt(null == value ? 0 : value.intValue());
	}
	
	public static PayActionTypes fromInt(int value){
		
		for (PayActionTypes b : PayActionTypes.values()) { 
			if (value == b.value) { 
	          return b; 
	        }
        } 
		
		return Undefined;
	}
}
