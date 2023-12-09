package com.inqwise.opinion.cms.common;

public enum EntityType {
	Undefined(0),
	Post(1),
	Faq(2);
	
	private final int value;

	public int getValue() {
		return value;
	}
	
	public Integer getValueOrNullWhenUndefined(){
		return Undefined.getValue() == value ? null : Integer.valueOf(value);
	}

	private EntityType(int value) {
		this.value = value;
	}
	
	public static EntityType fromInt(Integer value){
		return fromInt(null == value ? 0 : value.intValue());
	}
	
	public static EntityType fromInt(int value){
		
		for (EntityType b : EntityType.values()) { 
			if (value == b.value) { 
	          return b; 
	        }
        } 
		
		return Undefined;
	}

	public static boolean contains(int statusId) {
		for (EntityType b : EntityType.values()) {
			if (statusId == b.value && b != Undefined) { 
		          return true; 
	        }
		}
		
		return false;
	}
}
