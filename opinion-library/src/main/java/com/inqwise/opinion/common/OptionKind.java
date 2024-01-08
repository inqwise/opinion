package com.inqwise.opinion.common;

public enum OptionKind {
	Simple(0),
	Other(1),
	DefaultUnselectable(2);
	
	private final int value;

	public int getValue() {
		return value;
	}

	private OptionKind(int value) {
		this.value = value;
	}
	
	public static OptionKind fromInt(int value){
		
		for (OptionKind b : OptionKind.values()) { 
			if (value == b.value) { 
	          return b; 
	        }
        } 
		
		return Simple;
	}
	
	public static OptionKind fromInt(Integer value){
		return fromInt(null == value ? 0 : value.intValue());
	}
	
	public static boolean contains(int kindId) {
		for (OptionKind b : OptionKind.values()) {
			if (kindId == b.value) { 
		          return true; 
	        }
		}
		
		return false;
	}
}
