package com.inqwise.opinion.opinion.common.opinions;

public enum FinishBehaviourType {
	Void(0),
	FinishMessage(1),
	CustomRedirect(2);
	
	private final int value;

	public int getValue() {
		return value;
	}
	
	public Integer getValueOrNullWhenUndefined(){
		return Void.getValue() == value ? null : Integer.valueOf(value);
	}

	private FinishBehaviourType(int value) {
		this.value = value;
	}
	
	public static FinishBehaviourType fromInt(Integer value){
		return fromInt(null == value ? 0 : value.intValue());
	}
	
	public static FinishBehaviourType fromInt(int value){
		
		for (FinishBehaviourType a : FinishBehaviourType.values()) { 
			if (value == a.value) { 
	          return a; 
	        }
        } 
		
		return Void;
	}
}
