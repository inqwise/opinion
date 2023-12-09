package com.inqwise.opinion.library.entities.parameters;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;
import com.inqwise.opinion.library.common.parameters.EntityType;
import com.inqwise.opinion.library.common.parameters.IVariable;
import com.inqwise.opinion.library.common.parameters.IVariableSet;
import com.inqwise.opinion.library.common.parameters.VariableType;

public class VariableSetEntity implements IVariableSet {
	private static final int GENERAL_VERSION_PRIORITY_ID = 0;
	private String key;
	private String name;
	private String description;
	private List<Long> categories;
	private SortedMap<Integer, VariableEntity> variablesByPriority;
	private VariableType variableType;
	private VariableEntity generalVersion;
	private VariableEntity actualVersion;
	
	public VariableSetEntity(ResultSet reader) throws SQLException {
		variablesByPriority = new TreeMap<Integer, VariableEntity>(Collections.reverseOrder());
		setCategories(new ArrayList<Long>()); 
		
		variableType = VariableType.fromInt(ResultSetHelper.optInt(reader, "def_value_type_id"));
		long definitionId = reader.getLong("def_id");
		Object value = reader.getObject("def_value");
		key = reader.getString("def_key");
		setName(reader.getString("def_name"));
		setDescription(reader.getString("def_description"));
		
		setActualVersion(generalVersion = GenerateVariable(GENERAL_VERSION_PRIORITY_ID, definitionId, EntityType.General, value, variableType));
		variablesByPriority.put(generalVersion.getPriorityId(), generalVersion);
	}

	private VariableEntity GenerateVariable(int priorityId,
			long entityId, EntityType entityType, Object value,
			VariableType valueType) {
		VariableEntity var = null;
		switch (valueType) {
		case Access:
			var = new AccessEntity(priorityId, entityId, entityType, value);
			break;
		default:
			throw new Error("Unimplemented VariableType: " + valueType);
		}
		
		return var;
	}

	public long getDefinitionId() {
		return generalVersion.getEntityId();
	}

	public void addReference(ResultSet reader) throws SQLException {
		EntityType entityType = EntityType.fromInt(reader.getInt("ref_entity_type_id"));
		long entityId = reader.getLong("ref_entity_id");
		long referenceId = reader.getLong("ref_id");
		
		switch (entityType) {
		case Category:
			categories.add(entityId);
			break;
		case Account:
		case ServicePackage:
			
			int priorityId = reader.getInt("ref_priority_id");
			Object value = reader.getObject("ref_value");
			
			VariableEntity version = GenerateVariable(priorityId, referenceId, entityType, value, variableType);
			if(actualVersion.getPriorityId() < version.getPriorityId()){
				setActualVersion(version);
			}
			
			variablesByPriority.put(version.getPriorityId(), version);
			
			break;
		default:
			throw new Error("Unimplemented EntityType: " + entityType);
		}
		
		
	}

	public String getKey() {
		return key;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Long> getCategories() {
		return categories;
	}

	public void setCategories(List<Long> categories) {
		this.categories = categories;
	}

	public VariableEntity getActualVersion() {
		return actualVersion;
	}

	public void setActualVersion(VariableEntity actualVersion) {
		this.actualVersion = actualVersion;
	}

	public IVariable getActual() {
		return actualVersion;
	}

	public IVariable getPrevious(int priorityId){
		IVariable result = null;
		Set<Integer> keys = variablesByPriority.keySet();
		for (Integer actualPriorityId  : keys) {
			if(actualPriorityId < priorityId){
				result = variablesByPriority.get(actualPriorityId);
				break;
			}
		}
		
		return result;
	}
}
