package com.inqwise.opinion.payments.common.errorHandle;


import org.apache.commons.lang3.math.NumberUtils;

import com.inqwise.opinion.infrastructure.common.IErrorCode;

public enum PayErrorCode implements IErrorCode {
	NoError(0),
	GeneralError(1),
	NoResults(2),
	ArgumentNull(3),
	ArgumentWrong(4),
	ArgumentOutOfRange(5),
	InvalidToken(6),
	InvalidCCNumerOrCCType(10527),
	BillingCityIsMandatory(10710),
	InvalidPostalCode(10712),
	InvalidAmount(10525),
	//InvalidExpiration(10562)
	InvalidExpiration(10508),
	AmountExcededTheUpperLimit(10553); 
	
	
	private int value;
	PayErrorCode(int value){
		this.value = value;
	}
	
	public static PayErrorCode fromValue(String strValue){
		int rawValue = NumberUtils.toInt(strValue);
		if(0 < rawValue){
			for (PayErrorCode errorCode : PayErrorCode.values()) {
				if(errorCode.value == rawValue){
					return errorCode;
				}
			}
		}
		return GeneralError;
	}
}
