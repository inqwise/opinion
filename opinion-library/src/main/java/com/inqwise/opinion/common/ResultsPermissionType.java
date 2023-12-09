package com.inqwise.opinion.opinion.common;

public enum ResultsPermissionType {
	Undefinded(0),
	None(1),
	Percentage(2),
	All(3);
	
	private final int value;

	public int getValue() {
		return value;
	}

	private ResultsPermissionType(int value) {
		this.value = value;
	}
	
	public static ResultsPermissionType fromInt(int value){
		
		for (ResultsPermissionType b : ResultsPermissionType.values()) { 
			if (value == b.value) { 
	          return b; 
	        }
        } 
		
		return Undefinded;
	}
	
	public static ResultsPermissionType fromInt(Integer value){
		return fromInt(null == value ? 0 : value.intValue());
	}
	
	public static boolean contains(int typeId) {
		for (ResultsPermissionType b : ResultsPermissionType.values()) {
			if (typeId == b.value) { 
		          return true; 
	        }
		}
		
		return false;
	}
}
