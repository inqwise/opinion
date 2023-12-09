package com.inqwise.opinion.automation.common.events;

import java.io.Serializable;
import java.util.UUID;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.inqwise.opinion.automation.common.FireEventArgs;

public final class ChargeStatusChangedEventArgs extends FireEventArgs implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1338367030602046710L;

	public ChargeStatusChangedEventArgs(long chargeId, int statusId, int sourceId) {
		super(sourceId);
		this.chargeId = chargeId;
		this.statusId = statusId;
	}
	
	private long chargeId;
	private int statusId;
	
	public long getChargeId(){
		return chargeId;
	}
	
	public int getStatusId(){
		return statusId;
	}
}
