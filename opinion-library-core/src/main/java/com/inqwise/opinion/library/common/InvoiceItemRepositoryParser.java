package com.inqwise.opinion.library.common;

import java.util.Date;

import org.json.JSONObject;

public class InvoiceItemRepositoryParser {
	public InvoiceItemModel parse(JSONObject json) {
		return InvoiceItemModel.builder()
				.withChargeId(json.optLongObject("charge_id"))
				.withAmount(json.optDoubleObject("amount"))
				.withChargeStatusId(json.optIntegerObject("charge_status_id"))
				.withChargeName(json.optString("charge_name"))
				.withChargeDescription(json.optString("charge_description"))
				.withInsertDate((Date) json.opt("insert_date"))
				.withAccopId(json.optLongObject("accop_id"))
				.withAccopTypeId(json.optIntegerObject("accop_type_id"))
				.withBalance(json.optDoubleObject("balance"))
				.withModifyDate((Date) json.opt("modify_date"))
				.withReferenceId(json.optLongObject("reference_id"))
				.withComments(json.optString("comments"))
				.withCreditCardNumber(json.optString("credit_card_number"))
				.withCreditCardTypeId(json.optIntegerObject("credit_card_type_id"))
				.build();
		
	}
}