package com.inqwise.opinion.library.common.accounts;

public enum AccountsOperationsType {
	Undefined(0),
	StartBalance(1),
	Fund(2),
	ChargePay(3),
	Credit(4),
	Debit(5), 
	Refund(6),
	CancelCharge(7),
	InvoiceOpen(8);
	
	private final int value;

	public int getValue() {
		return value;
	}
	
	public Integer getValueOrNullWhenUndefined(){
		return Undefined.getValue() == value ? null : Integer.valueOf(value);
	}

	private AccountsOperationsType(int value) {
		this.value = value;
	}
	
	public static AccountsOperationsType fromInt(Integer value){
		return fromInt(null == value ? 0 : value.intValue());
	}
	
	public static AccountsOperationsType fromInt(int value){
		
		for (AccountsOperationsType a : AccountsOperationsType.values()) { 
			if (value == a.value) { 
	          return a; 
	        }
        } 
		
		return Undefined;
	}
}
