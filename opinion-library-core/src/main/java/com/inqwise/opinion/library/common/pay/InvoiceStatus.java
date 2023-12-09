package com.inqwise.opinion.library.common.pay;

public enum InvoiceStatus {
	Undefined(0),
	Draft(1),
	Open(2);
	
	private final int value;

	public int getValue() {
		return value;
	}
	
	public Integer getValueOrNullWhenUndefined(){
		return Undefined.getValue() == value ? null : Integer.valueOf(value);
	}

	private InvoiceStatus(int value) {
		this.value = value;
	}
	
	public static InvoiceStatus fromInt(Integer value){
		return fromInt(null == value ? 0 : value.intValue());
	}
	
	public static InvoiceStatus fromInt(int value){
		
		for (InvoiceStatus b : InvoiceStatus.values()) { 
			if (value == b.value) { 
	          return b; 
	        }
        } 
		
		return Undefined;
	}
}
