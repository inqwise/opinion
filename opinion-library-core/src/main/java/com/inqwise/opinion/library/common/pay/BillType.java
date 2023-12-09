package com.inqwise.opinion.library.common.pay;

public enum BillType {
	Undefined(0),
	Invoice(1),
	Recurent(2);
	
	private final int value;

	public int getValue() {
		return value;
	}
	
	public Integer getValueOrNullWhenUndefined(){
		return Undefined.getValue() == value ? null : Integer.valueOf(value);
	}

	private BillType(int value) {
		this.value = value;
	}
	
	public static BillType fromInt(Integer value){
		return fromInt(null == value ? 0 : value.intValue());
	}
	
	public static BillType fromInt(int value){
		
		for (BillType b : BillType.values()) { 
			if (value == b.value) { 
	          return b; 
	        }
        } 
		
		return Undefined;
	}
}
