package com.inqwise.opinion.library.common.basicTypes;

public class Skipable<T> {
	
	private T value;
	
	public Skipable(T value){
		this.value = value;
	}

	public T getValue() {
		return value;
	}
}
