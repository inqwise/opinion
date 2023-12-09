package com.inqwise.opinion.automation.common.events;

import java.io.Serializable;
import java.util.UUID;

import com.inqwise.opinion.automation.common.FireEventArgs;

public class ParticipantCompletedEventArgs extends FireEventArgs implements Serializable {
	
	private long opinionId;
	private int opinionTypeId;	
	private String participantGuid;
	private long collectorId;
	private long opinionOwnerId;
	
	private static final long serialVersionUID = -977985469134183807L;
	
	public long getOpinionId() {
		return opinionId;
	}

	public int getOpinionTypeId() {
		return opinionTypeId;
	}

	public String getParticipantGuid() {
		return participantGuid;
	}

	public long getCollectorId() {
		return collectorId;
	}

	public long getOpinionOwnerId() {
		return opinionOwnerId;
	}

	public ParticipantCompletedEventArgs(int sourceId, long opinionId,
			int opinionTypeId, String participantGuid, long collectorId,
			long opinionOwnerId) {
		super(sourceId);
		this.opinionId = opinionId;
		this.opinionTypeId = opinionTypeId;
		this.participantGuid = participantGuid;
		this.collectorId = collectorId;
		this.opinionOwnerId = opinionOwnerId;
	}
	
	
}
