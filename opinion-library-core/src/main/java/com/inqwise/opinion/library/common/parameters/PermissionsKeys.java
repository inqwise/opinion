package com.inqwise.opinion.library.common.parameters;

public enum PermissionsKeys {
	Undefined(null),
	BuyRespondents("purchaseRespondents"),
	MakePayment("makePayment"),
	CustomFinishBehaviour("customFinishBehaviour");
	
	private final String value;

	public String getValue() {
		return value;
	}
	
	public String getValueOrNullWhenUndefined(){
		return value;
	}

	private PermissionsKeys(String value) {
		this.value = value;
	}
	
	public static PermissionsKeys fromString(String value){
		
		for (PermissionsKeys v :PermissionsKeys.values()) { 
			if (null != v.value && value == v.value) { 
	          return v; 
	        }
        } 
		
		return Undefined;
	}
}
