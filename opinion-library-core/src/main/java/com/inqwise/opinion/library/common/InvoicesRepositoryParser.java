package com.inqwise.opinion.library.common;

import java.util.Date;

import org.json.JSONObject;


public class InvoicesRepositoryParser {
	public InvoiceModel parse(JSONObject json) {
		return InvoiceModel.builder()
				.withInvoiceId(json.optLongObject("invoice_id"))
				.withModifyDate((Date) json.opt("modify_date"))
				.withInvoiceStatusId(json.optIntegerObject("invoice_status_id"))
				.withInvoiceDate((Date) json.opt("invoice_date"))
				.withInsertDate((Date) json.opt("insert_date"))
				.withInvoiceFromDate((Date) json.opt("invoice_from_date"))
				.withInvoiceToDate((Date) json.opt("invoice_to_date"))
				.withAmount(json.optDoubleObject("amount"))
				.withAccountId(json.optLongObject("for_account_id"))
				.withAccountName(json.optString("account_name"))
				.withAccopId(json.optLongObject("accop_id"))
				.withAccopTypeId(json.optIntegerObject("accop_type_id"))
				.withBalance(json.optDoubleObject("balance"))
				.withReferenceId(json.optLongObject("reference_id"))
				.withComments(json.optString("comments"))
				.withCreditCardNumber(json.optString("credit_card_number"))
				.withCreditCardTypeId(json.optIntegerObject("credit_card_type_id"))
				.withChargeDescription(json.optString("charge_description"))
				.build();	
	}
}