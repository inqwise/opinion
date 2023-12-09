package com.inqwise.opinion.infrastructure.systemFramework;

public final class ValidationHelper {
	public static Boolean IsAllEqualValue(Object value, Object... args){
		for(Object arg : args){
			if(value == arg){
				return false;
			}
		}
		
		return true;
	}
	
	public static Boolean IsAnyEqualValue(Object value, Object... args){
		int count = 0;
		for(Object arg : args){
			if(value != arg){
				count++;
			}
		}
		
		return count > 0;
	}
	
	public static Boolean IsAllIsSame(Object... args){
		Boolean isInit = false;
		Object compareValue = null; 
		for(Object arg : args){
			if(!isInit){
				compareValue = arg;
			}  else if(compareValue != arg) {
				return false;
			}
		}
		
		return true;
	}
}
