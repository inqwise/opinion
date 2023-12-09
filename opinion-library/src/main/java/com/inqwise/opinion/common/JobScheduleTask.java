package com.inqwise.opinion.opinion.common;

public enum JobScheduleTask {
	Undefinded(0),
	Daily(1);
	
	private final int value;

	public int getValue() {
		return value;
	}

	private JobScheduleTask(int value) {
		this.value = value;
	}
	
	public static JobScheduleTask fromInt(int value){
		
		for (JobScheduleTask b : JobScheduleTask.values()) { 
			if (value == b.value) { 
	          return b; 
	        }
        } 
		
		return Undefinded;
	}
	
	public static JobScheduleTask fromInt(Integer value){
		return fromInt(null == value ? 0 : value.intValue());
	}
	
	public static boolean contains(int typeId) {
		for (JobScheduleTask b : JobScheduleTask.values()) {
			if (typeId == b.value) { 
		          return true; 
	        }
		}
		
		return false;
	}
}
