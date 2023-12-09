package com.inqwise.opinion.payments.common;

public enum PaymentStatus {
	Undefined(0),
	Success(1),
	Completed(2),
	None(3),
	Failed(4),
	Pending(5),
	Denied(6),
	Refunded(7),
	Reversed(8),
	CanceledReversal(9),
	Processed(10),
	PartiallyRefunded(11),
	Voided(12),
	Expired(13),
	InProgress(14),
	Created(15),
	CompletedFundsHeld(16),
	Instant(17),
	Delayed(18);
	
	private final int value;

	public int getValue() {
		return value;
	}
	
	public Integer getValueOrNullWhenUndefined(){
		return Undefined.getValue() == value ? null : Integer.valueOf(value);
	}

	private PaymentStatus(int value) {
		this.value = value;
	}
	
	public static PaymentStatus fromInt(Integer value){
		return fromInt(null == value ? 0 : value.intValue());
	}
	
	public static PaymentStatus fromInt(int value){
		
		for (PaymentStatus b : PaymentStatus.values()) { 
			if (value == b.value) { 
	          return b; 
	        }
        } 
		
		return Undefined;
	}
}
