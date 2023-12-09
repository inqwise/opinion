package com.inqwise.opinion.payments.common;

public enum CreditCardTypes {
	Undefined(0),
	Visa(1),
	MasterCard(2),
	Discover(3),
	Amex(4),
	Switch(5),
	Solo(6),
	Maestro(7);
	
	private final int value;

	public int getValue() {
		return value;
	}
	
	public Integer getValueOrNullWhenUndefined(){
		return Undefined.getValue() == value ? null : Integer.valueOf(value);
	}

	private CreditCardTypes(int value) {
		this.value = value;
	}
	
	public static CreditCardTypes fromInt(Integer value){
		return fromInt(null == value ? 0 : value.intValue());
	}
	
	public static CreditCardTypes fromInt(int value){
		
		for (CreditCardTypes b : CreditCardTypes.values()) { 
			if (value == b.value) { 
	          return b; 
	        }
        } 
		
		return Undefined;
	}
}
