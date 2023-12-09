package com.inqwise.opinion.library.common.pay;

import java.util.UUID;

public interface ICancelChargeRequest {
	public long getChargeId();
	public String getComments();
	public Long getBackOfficeUserId();
	public String getClientIp();
	public String getGeoCountryCode();
	public int getSourceId();
}
