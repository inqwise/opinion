package com.inqwise.opinion.library.entities.parameters;

import com.inqwise.opinion.library.common.parameters.AccessValue;
import com.inqwise.opinion.library.common.parameters.EntityType;
import com.inqwise.opinion.library.common.parameters.IAccess;
import com.inqwise.opinion.library.common.parameters.VariableType;

public class AccessEntity extends VariableEntity implements IAccess {

	private AccessValue value;
	
	public AccessEntity(int priorityId, long entityId,
			EntityType entityType, Object value) {
		super(priorityId, entityId, entityType);
		this.value = IdentifyValue(value);
	}

	@Override
	public VariableType getType() {
		return VariableType.Access;
	}

	private static AccessValue IdentifyValue(Object value) {
		return AccessValue.fromInt(Integer.valueOf(value.toString()));
	}
	
	public AccessValue getValue(){
		return value;
	}

	public Object getJsonValue() {
		return value == AccessValue.Permitted;
	}
}
