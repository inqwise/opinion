package com.inqwise.opinion.library.common.pay;

public enum ChargeStatus {
	Undefined(0),
	Unpaid(1),
	Paid(2),
	Void(3),
	Refund(4),
	Credit(5),
	//Pending(6),
	Canceled(7);
	
	private final int value;

	public int getValue() {
		return value;
	}
	
	public Integer getValueOrNullWhenUndefined(){
		return Undefined.getValue() == value ? null : Integer.valueOf(value);
	}

	private ChargeStatus(int value) {
		this.value = value;
	}
	
	public static ChargeStatus fromInt(Integer value){
		return fromInt(null == value ? 0 : value.intValue());
	}
	
	public static ChargeStatus fromInt(int value){
		
		for (ChargeStatus b : ChargeStatus.values()) { 
			if (value == b.value) { 
	          return b; 
	        }
        } 
		
		return Undefined;
	}
}
