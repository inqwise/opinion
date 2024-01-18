package com.inqwise.opinion.library.common.accounts;

import java.util.Date;

import org.json.JSONObject;

import com.inqwise.opinion.payments.common.CreditCardTypes;

public class AccountOperationRepositoryParser {
	public AccountOperationModel parse(JSONObject json) {
		var builder = AccountOperationModel.builder()
				.withId(json.optLongObject("accop_id"))
				
				.withUserId(json.optLongObject("user_id"))
				.withAmount(json.optDoubleObject("amount"))
				.withBalance(json.optDoubleObject("balance"))
				.withModifyDate((Date) json.opt("modify_date"))
				.withReferenceId(json.optLongObject("reference_id"))
				.withComments(json.optString("comments"))
				.withCreditCardNumber(json.optString("credit_card_number"))
				
				.withChargeDescription(json.optString("charge_description"));
		
				
		Integer typeId = json.optIntegerObject("accop_type_id");
		if(null != typeId) {
			builder.withType(AccountsOperationsType.fromInt(typeId));
		}
		
		Integer creditCardTypeId = json.optIntegerObject("credit_card_type_id");
		if(null != creditCardTypeId) {
			builder.withCreditCardType(CreditCardTypes.fromInt(creditCardTypeId));
		}
		
		return builder.build();
	}
}