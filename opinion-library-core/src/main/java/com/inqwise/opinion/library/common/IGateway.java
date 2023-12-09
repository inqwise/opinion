package com.inqwise.opinion.library.common;

public interface IGateway {
	//g.gateway_id, g.gateway_name, g.compaign_id, g.gateway_description, g.create_date, g.modify_date, c.first_login_landing_page
	
	long getId();
	String getName();
	long getCampaignId();
	String getFirstLoginLandingPage();
}
