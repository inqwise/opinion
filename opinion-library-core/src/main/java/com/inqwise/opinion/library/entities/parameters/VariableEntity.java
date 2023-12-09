package com.inqwise.opinion.library.entities.parameters;

import com.inqwise.opinion.library.common.parameters.EntityType;
import com.inqwise.opinion.library.common.parameters.IVariable;
import com.inqwise.opinion.library.common.parameters.VariableType;

public abstract class VariableEntity implements IVariable {
	private int priorityId;
	private long entityId;
	private EntityType entityType;
	
	public int getPriorityId() {
		return priorityId;
	}
	
	public long getEntityId() {
		return entityId;
	}
	
	public EntityType getEntityType() {
		return entityType;
	}
	
	public abstract VariableType getType();
	
	public VariableEntity(int priorityId, long entityId, EntityType entityType){
		this.priorityId = priorityId;
		this.entityId = entityId;
		this.entityType = entityType;
	}
}
