package com.inqwise.opinion.common.charts;

public enum TimePointRange {
	Unknown(0),
	Hour(2),
	Day(3),
	Week(4),
	Month(5);
	
	private final int value;

	public int getValue() {
		return value;
	}

	private TimePointRange(int value) {
		this.value = value;
	}
	
	public static TimePointRange fromInt(Integer value){
		
		if(null != value){
			for (TimePointRange b : TimePointRange.values()) { 
				if (value == b.value) { 
		          return b; 
		        }
	        } 
		}
		return TimePointRange.Unknown;
	}
}
