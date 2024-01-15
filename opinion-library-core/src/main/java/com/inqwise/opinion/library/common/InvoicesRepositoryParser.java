package com.inqwise.opinion.library.common;

import java.util.Date;

import org.json.JSONObject;


public class InvoicesRepositoryParser {
	public InvoicesModel parse(JSONObject json) {
		return InvoicesModel.builder()
				.withInvoiceId(json.optLongObject("invoice_id"))
				.withModifyDate((Date) json.opt("modify_date"))
				.withInvoiceStatusId(json.optIntegerObject("invoice_status_id"))
				.withInvoiceDate((Date) json.opt("invoice_date"))
				.withInsertDate((Date) json.opt("insert_date"))
				.withInvoiceFromDate((Date) json.opt("invoice_from_date"))
				.withInvoiceToDate((Date) json.opt("invoice_to_date"))
				.withAmount(json.optDoubleObject("amount"))
				.build();	
	}
}