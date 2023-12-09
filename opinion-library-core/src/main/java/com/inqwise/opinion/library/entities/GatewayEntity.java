package com.inqwise.opinion.library.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.inqwise.opinion.library.common.IGateway;

public class GatewayEntity implements IGateway {

	private long id;
	private String name;
	private String firstLoginLandingPage;
	private long campaignId;
	
	public GatewayEntity(ResultSet reader) throws SQLException {
		setId(reader.getLong("gateway_id"));
		setName(reader.getString("gateway_name"));
		setFirstLoginLandingPage(reader.getString("first_login_landing_page"));
		setCampaignId(reader.getLong("campaign_id"));
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstLoginLandingPage() {
		return firstLoginLandingPage;
	}

	public void setFirstLoginLandingPage(String firstLoginLandingPage) {
		this.firstLoginLandingPage = firstLoginLandingPage;
	}

	public long getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(long campaignId) {
		this.campaignId = campaignId;
	}

}
