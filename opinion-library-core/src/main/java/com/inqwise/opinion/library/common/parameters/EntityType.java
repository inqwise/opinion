package com.inqwise.opinion.library.common.parameters;

public enum EntityType {
	General(null),
	Undefined(0),
	Category(1),
	ServicePackage(2),
	Account(3);
	
	private final Integer value;
	
	public Integer getValue(){
		return value;
	}
	
	private EntityType(Integer value){
		this.value = value;
	}
	
	public static EntityType fromInt(int value){
		
		for (EntityType v : EntityType.values()) { 
			if (null != v.value && value == v.value) { 
	          return v; 
	        }
        } 
		
		return Undefined;
	}
}
