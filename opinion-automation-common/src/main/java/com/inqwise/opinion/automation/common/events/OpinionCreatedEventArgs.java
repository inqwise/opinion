package com.inqwise.opinion.automation.common.events;

import java.util.UUID;

import com.inqwise.opinion.automation.common.FireEventArgs;

public class OpinionCreatedEventArgs extends FireEventArgs {
	
	public OpinionCreatedEventArgs(int sourceId) {
		super(sourceId);
	}

	private long opinionId;
	private int opinionTypeId;
	
	public long getOpinionId() {
		return opinionId;
	}
	
	public OpinionCreatedEventArgs setOpinionId(long opinionId) {
		this.opinionId = opinionId;
		return this;
	}
	
	public int getOpinionTypeId() {
		return opinionTypeId;
	}
	
	public OpinionCreatedEventArgs setOpinionTypeId(int opinionTypeId) {
		this.opinionTypeId = opinionTypeId;
		return this;
	}
}
