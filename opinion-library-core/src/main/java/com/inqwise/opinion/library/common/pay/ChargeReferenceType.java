package com.inqwise.opinion.library.common.pay;

public enum ChargeReferenceType {
	Undefined(0),
	Collector(1),
	ServicePackage(2);
	
	private final int value;

	public int getValue() {
		return value;
	}
	
	public Integer getValueOrNullWhenUndefined(){
		return Undefined.getValue() == value ? null : Integer.valueOf(value);
	}

	private ChargeReferenceType(int value) {
		this.value = value;
	}
	
	public static ChargeReferenceType fromInt(Integer value){
		return fromInt(null == value ? 0 : value.intValue());
	}
	
	public static ChargeReferenceType fromInt(int value){
		
		for (ChargeReferenceType c : ChargeReferenceType.values()) { 
			if (value == c.value) { 
	          return c; 
	        }
        } 
		
		return Undefined;
	}
}
