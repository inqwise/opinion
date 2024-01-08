package com.inqwise.opinion.common.servicePackage;

public enum OpinionServicePackages {
	Undefined(0),
	Free(1),
	Personal(3);
		
	private final int value;

	public int getValue() {
		return value;
	}
	
	public Integer getValueOrNullWhenUndefined(){
		return Undefined.getValue() == value ? null : Integer.valueOf(value);
	}

	private OpinionServicePackages(int value) {
		this.value = value;
	}
	
	public static OpinionServicePackages fromInt(Integer value){
		return fromInt(null == value ? 0 : value.intValue());
	}
	
	public static OpinionServicePackages fromInt(int value){
		
		for (OpinionServicePackages b : OpinionServicePackages.values()) { 
			if (value == b.value) { 
	          return b; 
	        }
        } 
		
		return Undefined;
	}

	public static boolean contains(int value) {
		for (OpinionServicePackages b : OpinionServicePackages.values()) {
			if (value == b.value && b != Undefined) { 
		          return true; 
	        }
		}
		
		return false;
	}
}
