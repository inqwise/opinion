package com.inqwise.opinion.common.audit;

public enum AuditDataItemType{
	Undefined((char)0),
	Fixed('i'),
	Changed('c'),
	;
	
	private final char value;

	public char getValue() {
		return value;
	}

	private AuditDataItemType(char value) {
		this.value = value;
	}
	
	public static AuditDataItemType fromChar(char value){
		
		for (AuditDataItemType b : AuditDataItemType.values()) { 
			if (value == b.value) { 
	          return b; 
	        }
        } 
		
		return Undefined;
	}
	
	public static AuditDataItemType fromInt(Integer value){
		return fromInt(null == value ? 0 : value.intValue());
	}
	
	public static boolean contains(int kindId) {
		for (AuditDataItemType b : AuditDataItemType.values()) {
			if (kindId == b.value && b != Undefined) { 
		          return true; 
	        }
		}
		
		return false;
	}
}
