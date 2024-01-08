package com.inqwise.opinion.common.collectors;

public enum CollectorStatus {
	Undefined(0),
	Open(1),
	Closed(2),
	PendingPayment(3),
	Verify(4),
	PendingPurchase(7),
	Canceled(8);
	
	private final int value;

	public int getValue() {
		return value;
	}
	
	public Integer getValueOrNullWhenUndefined(){
		return Undefined.getValue() == value ? null : Integer.valueOf(value);
	}

	private CollectorStatus(int value) {
		this.value = value;
	}
	
	public static CollectorStatus fromInt(Integer value){
		return fromInt(null == value ? 0 : value.intValue());
	}
	
	public static CollectorStatus fromInt(int value){
		
		for (CollectorStatus b : CollectorStatus.values()) { 
			if (value == b.value) { 
	          return b; 
	        }
        } 
		
		return Undefined;
	}

	public static boolean contains(int statusId) {
		for (CollectorStatus b : CollectorStatus.values()) {
			if (statusId == b.value && b != Undefined) { 
		          return true; 
	        }
		}
		
		return false;
	}
}
