package com.inqwise.opinion.payments.common;

public enum RefundStatus {
	Undefined(0),
	None(1),
	Completed(2),
	Failed(3),
	Pending(4),
	Denied(5),
	Refunded(6),
	Reversed(7),
	CanceledReversal(8),
	Processed(9),
	PartiallyRefunded(10),
	Voided(11),
	Expired(12),
	Inprogress(13),
	Created(14),
	CompletedFundsHeld(15),
	Instant(16),
	Delayed(17); 
	
	private final int value;

	public int getValue() {
		return value;
	}
	
	public Integer getValueOrNullWhenUndefined(){
		return Undefined.getValue() == value ? null : Integer.valueOf(value);
	}

	private RefundStatus(int value) {
		this.value = value;
	}
	
	public static RefundStatus fromInt(Integer value){
		return fromInt(null == value ? 0 : value.intValue());
	}
	
	public static RefundStatus fromInt(int value){
		
		for (RefundStatus b : RefundStatus.values()) { 
			if (value == b.value) { 
	          return b; 
	        }
        } 
		
		return Undefined;
	}
}
