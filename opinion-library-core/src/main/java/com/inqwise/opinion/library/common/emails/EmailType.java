package com.inqwise.opinion.library.common.emails;

import org.apache.commons.lang3.StringUtils;

public enum EmailType {
	Undefined(0),
	InvoiceUnpaidReminder(1),
	FollowUp(2), PackageExpiry(3), PackageBeforeExpiration(4),
	Invite(5);
	
	private final int value;

	public int getValue() {
		return value;
	}
	
	public Integer getValueOrNullWhenUndefined(){
		return Undefined.getValue() == value ? null : Integer.valueOf(value);
	}

	private EmailType(int value) {
		this.value = value;
	}
	
	public static EmailType fromInt(Integer value){
		return fromInt(null == value ? 0 : value.intValue());
	}
	
	public static EmailType fromInt(int value){
		
		for (EmailType a : EmailType.values()) { 
			if (value == a.value) { 
	          return a; 
	        }
        } 
		
		return Undefined;
	}
	
	public static String getUnpaidInvoiceAuditKey(long invoiceId){
		return StringUtils.join(new Object[] {"e", EmailType.InvoiceUnpaidReminder.getValue(), "i", invoiceId});
	}
}
