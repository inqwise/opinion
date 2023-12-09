package com.inqwise.opinion.payments.common;

public enum PendingReason {
	Undefined(0),
	None(1),
	Echeck(2),
	Intl(3),
	Verify(4),
	Address(5),
	Unilateral(6),
	Other(7),
	Upgrade(8),
	MultiCurrency(9),
	Authorization(10),
	Order(11),
	PaymentReview(12); 
	
	private final int value;

	public int getValue() {
		return value;
	}
	
	public Integer getValueOrNullWhenUndefined(){
		return Undefined.getValue() == value ? null : Integer.valueOf(value);
	}

	private PendingReason(int value) {
		this.value = value;
	}
	
	public static PendingReason fromInt(Integer value){
		return fromInt(null == value ? 0 : value.intValue());
	}
	
	public static PendingReason fromInt(int value){
		
		for (PendingReason b : PendingReason.values()) { 
			if (value == b.value) { 
	          return b; 
	        }
        } 
		
		return Undefined;
	}
}
