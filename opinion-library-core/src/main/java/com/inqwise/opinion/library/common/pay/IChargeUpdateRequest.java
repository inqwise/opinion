package com.inqwise.opinion.library.common.pay;

public interface IChargeUpdateRequest {

	long getChargeId();
	Long getBillId();
	Integer getBillTypeId();
	String getName();
	Long getUserId();
	String getDescription();
	Long getAccountId();
}
