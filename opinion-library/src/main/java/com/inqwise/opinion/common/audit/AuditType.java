package com.inqwise.opinion.opinion.common.audit;

public enum AuditType {
	Undefined(0),
	
	// Opinion
	OpinionIserted(1),
	OpinionDeleted(2),
	OpinionChanged(3),
	OpinionReopened(4),
	OpinionContentChanged(5),
	OpinionContentInserted(6),
	
	// Collector
	CollectorInserted(10),
	CollectorChanged(11),
	CollectorDeleted(12),
	
	// Control
	ControlInserted(20),
	ControlChanged(21),
	ControlDeleted(22),
	ControlContentInserted(23),
	ControlContentChanged(24),
	ControlContentDeleted(25),
	
	// Sheets
	SheetInserted(30),
	SheetChanged(31),
	SheetDeleted(32),
	SheetContentInserted(33),
	SheetContentChanged(34),
	SheetContentDeleted(35),
	;
	
	private final int value;

	public int getValue() {
		return value;
	}

	private AuditType(int value) {
		this.value = value;
	}
	
	public static AuditType fromInt(int value){
		
		for (AuditType b : AuditType.values()) { 
			if (value == b.value) { 
	          return b; 
	        }
        } 
		
		return Undefined;
	}
	
	public static AuditType fromInt(Integer value){
		return fromInt(null == value ? 0 : value.intValue());
	}
	
	public static boolean contains(int kindId) {
		for (AuditType b : AuditType.values()) {
			if (kindId == b.value && b != Undefined) { 
		          return true; 
	        }
		}
		
		return false;
	}
}
