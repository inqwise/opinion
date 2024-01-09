package com.inqwise.opinion.common.audit;

import org.json.JSONArray;



public interface IAuditRequest {

	AuditType getAuditType();

	Long getOpinionId();

	Long getUserId();

	String getAuditMessage();

	AuditData getAuditData();

	Long getSheetId();

	Long getControlId();

	Long getCollectorId();

	Long getTranslationId();

}
