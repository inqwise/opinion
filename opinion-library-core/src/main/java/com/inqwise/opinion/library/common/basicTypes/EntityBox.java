package com.inqwise.opinion.library.common.basicTypes;

public class EntityBox<T> {
	private T value = null;

	public void setValue(T value) {
		this.value = value;
	}

	public T getValue() {
		return value;
	}
	
	public boolean hasValue(){
		return null != value; 
	}
}
