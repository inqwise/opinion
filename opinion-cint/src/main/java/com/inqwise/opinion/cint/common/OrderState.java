package com.cint.common;

public enum OrderState {
	Undefined(null),
	Hold("hold"),
	Cancelled("cancelled"),
	New("new"),
	Denied("denied"),
	Approved("approved"),
	Pending("pending"),
	Live("live"),
	Failed("failed"),
	Completed("completed"),
	Closed("closed");
	
	private final String value;

	public String getValue() {
		return value;
	}
	
	public String getValueOrNullWhenUndefined(){
		return value;
	}

	private OrderState(String value) {
		this.value = value;
	}
	
	public static OrderState fromString(String value){
		
		if(null != value){
			for (OrderState b : OrderState.values()) { 
				if (value.equalsIgnoreCase(b.getValue())) { 
		        	return b; 
		        }
	        } 
		}
		
		return Undefined;
	}
}
