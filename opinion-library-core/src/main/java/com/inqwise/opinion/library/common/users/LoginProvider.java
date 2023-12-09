package com.inqwise.opinion.library.common.users;


public enum LoginProvider {
	Undefined(0, null),
	Inqwise(1, null),
	Facebook(2, "facebook");
	
	private final int value;
	private final String socialAuthId;

	public int getValue() {
		return value;
	}
	
	public String getSocialAuthId(){
		return socialAuthId;
	}
	
	public Integer getValueOrNullWhenUndefined(){
		return Undefined.getValue() == value ? null : Integer.valueOf(value);
	}

	private LoginProvider(int value, String socialAuthId) {
		this.value = value;
		this.socialAuthId = socialAuthId;
	}
	
	public static LoginProvider fromInt(Integer value){
		return fromInt(null == value ? 0 : value.intValue());
	}
	
	public static LoginProvider fromInt(int value){
		
		for (LoginProvider a : LoginProvider.values()) { 
			if (value == a.value) { 
	          return a; 
	        }
        } 
		
		return Undefined;
	}
}
