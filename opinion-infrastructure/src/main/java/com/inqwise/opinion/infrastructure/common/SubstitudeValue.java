package com.inqwise.opinion.infrastructure.common;

/**
 * @description Substitute source value
 *
 */
public enum SubstitudeValue {
	Null(null),
	Empty(""),
	Zero(0);
	
	private Object value;
	
	private SubstitudeValue(Object value) {
		this.value = value;
	}
	
	public Object getValue(){
		return value;
	}
	
	public boolean Contains(Object value){
		for (SubstitudeValue v : SubstitudeValue.values()) { 
			if ((null == v.value && null == value) || (null != v.value && v.value.equals(value))){ 
	        	return true;
	        }
        } 
		
		return false;
	}
}
