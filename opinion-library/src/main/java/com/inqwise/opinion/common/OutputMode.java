package com.inqwise.opinion.common;

public enum OutputMode {
	View(0),
	Answer(1);

	private final int value;

	public int getValue() {
		return value;
	}

	private OutputMode(int value) {
		this.value = value;
	}
	
	public static OutputMode fromInt(int value){
		
		for (OutputMode b : OutputMode.values()) { 
			if (value == b.value) { 
	          return b; 
	        }
        } 
		
		return Answer;
				
	}
}
