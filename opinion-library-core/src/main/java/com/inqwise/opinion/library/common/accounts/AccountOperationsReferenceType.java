package com.inqwise.opinion.library.common.accounts;

public enum AccountOperationsReferenceType {
	Undefined(0),
	Charges(1),
	PayTransaction(2),
	Invoices(3);
	
	private final int value;

	public int getValue() {
		return value;
	}
	
	public Integer getValueOrNullWhenUndefined(){
		return Undefined.getValue() == value ? null : Integer.valueOf(value);
	}

	private AccountOperationsReferenceType(int value) {
		this.value = value;
	}
	
	public static AccountOperationsReferenceType fromInt(Integer value){
		return fromInt(null == value ? 0 : value.intValue());
	}
	
	public static AccountOperationsReferenceType fromInt(int value){
		
		for (AccountOperationsReferenceType a : AccountOperationsReferenceType.values()) { 
			if (value == a.value) { 
	          return a; 
	        }
        } 
		
		return Undefined;
	}
}
