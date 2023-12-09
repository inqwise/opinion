package com.inqwise.opinion.automation.common;

import java.io.Serializable;
import java.util.UUID;

import org.json.JSONObject;

public class ChargePostPayActionArgs implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8277604074204990565L;
	private long chargeId; 
	private Long chargeReferenceId;
	private Integer referenceTypeId;
	private int sourceId;
	private int countOf;
	
	public long getChargeId(){
		return chargeId;
	}
	
	public Long getChargeReferenceId(){
		return chargeReferenceId;
	}
	
	public Integer getReferenceTypeId(){
		return referenceTypeId;
	}
	
	public int getSourceId(){
		return sourceId;
	}

	public void setChargeId(long chargeId) {
		this.chargeId = chargeId;
	}

	public void setChargeReferenceId(Long chargeReferenceId) {
		this.chargeReferenceId = chargeReferenceId;
	}

	public void setReferenceTypeId(Integer referenceTypeId) {
		this.referenceTypeId = referenceTypeId;
	}

	public void setSourceId(int sourceId) {
		this.sourceId = sourceId;
	}

	public int getCountOf() {
		return countOf;
	}

	public void setCountOf(int countOf) {
		this.countOf = countOf;
	} 
}
