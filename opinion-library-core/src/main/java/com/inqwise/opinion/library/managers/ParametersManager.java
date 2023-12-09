package com.inqwise.opinion.library.managers;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.javatuples.Quartet;

import com.inqwise.opinion.infrastructure.dao.IResultSetCallback;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.library.common.IProduct;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.common.parameters.EntityType;
import com.inqwise.opinion.library.common.parameters.IVariableSet;
import com.inqwise.opinion.library.common.servicePackages.IServicePackage;
import com.inqwise.opinion.library.dao.ParametersDataAccess;
import com.inqwise.opinion.library.entities.parameters.VariableSetEntity;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class ParametersManager {
	
	static ApplicationLog logger = ApplicationLog.getLogger(ParametersManager.class);
	
	private static Object _variablesCacheLocker = new Object(); 
	private static LoadingCache<Quartet<Long, EntityType, Integer[], String[]>, OperationResult<HashMap<String, IVariableSet>>> _variablesCache;
	private static LoadingCache<Quartet<Long, EntityType, Integer[], String[]>, OperationResult<HashMap<String, IVariableSet>>> getVariablesCache(){
		if(null == _variablesCache){
			synchronized (_variablesCacheLocker) {
				if(null == _variablesCache){
					_variablesCache = CacheBuilder.newBuilder().
							maximumSize(1000).
							expireAfterWrite(10, TimeUnit.MINUTES).
							build(new CacheLoader<Quartet<Long, EntityType, Integer[], String[]>, OperationResult<HashMap<String, IVariableSet>>>(){
								@Override
								public OperationResult<HashMap<String, IVariableSet>> load(
										Quartet<Long, EntityType, Integer[], String[]> args) throws Exception {
									return getVariablesInternal(args.getValue0(), args.getValue1(), args.getValue2(), args.getValue3());
								}
							});
				}
			}
		}
		
		return _variablesCache;
	}
	
	public static OperationResult<HashMap<String, IVariableSet>> getVariables(long entityId, EntityType entityType, Integer[] categories, String[] viaReferences){
		OperationResult<HashMap<String, IVariableSet>> result;
		try {
			result = getVariablesCache().get(new Quartet<Long, EntityType, Integer[], String[]>(entityId, entityType, categories, viaReferences));
		} catch (ExecutionException e) {
			UUID errorTicket = logger.error(e, "getProductByGuid: Unexpected error occured");
			result = new OperationResult<HashMap<String, IVariableSet>>(ErrorCode.GeneralError, errorTicket);
		}
		
		return result;
	}
	
	private static OperationResult<HashMap<String, IVariableSet>> getVariablesInternal(long entityId, EntityType entityType, Integer[] categories, String[] viaReferences){
		OperationResult<HashMap<String, IVariableSet>> result = new OperationResult<HashMap<String, IVariableSet>>();
		final HashMap<String, IVariableSet> variables = new LinkedHashMap<String, IVariableSet>();
		final HashMap<Long, VariableSetEntity> variablesByDefinitionId = new LinkedHashMap<Long, VariableSetEntity>();
		try{
			IResultSetCallback callback = new IResultSetCallback() {
				
				public void call(ResultSet reader, int generationId) throws Exception {
					while(reader.next()){
						switch(generationId){
						case 0: // Definition
							VariableSetEntity variable = new VariableSetEntity(reader);
							variables.put(variable.getKey(), variable);							
							variablesByDefinitionId.put(variable.getDefinitionId(), variable);
							break;
						case 1: // References 
							long definitionId = reader.getLong("ref_definition_id");
							try {
								VariableSetEntity actualVariable = variablesByDefinitionId.get(definitionId);
								actualVariable.addReference(reader);
								
							} catch(Exception ex) {
								throw new Error(String.format("getVariables : Unexpected error occured when trying to add reference to variable with definitionId '%s'", definitionId), ex);
							}
							break;
						}
					}
				}
			};
			
			ParametersDataAccess.getVariablesResultSet(entityId, entityType.getValue(), categories, callback, viaReferences);
			
			if(!result.hasError() && variables.size() == 0){
				result.setError(ErrorCode.NoResults);
			}
			
			if(!result.hasError()){
				result.setValue(variables);
			}
		}
		catch(Exception ex){
			UUID errorId = logger.error(ex, "getVariables() : Unexpected error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}
	
	public static BaseOperationResult updateVariable(EntityType entityType, long entityId, String key, Object value, long userId, String dependsOnKey){
		BaseOperationResult result = new BaseOperationResult();
		try{
			if(entityType == EntityType.Undefined || entityType == EntityType.General){
				logger.warn("updateVariable : Unable to update variable for entityType '%s'", entityType);
				result.setError(ErrorCode.ArgumentOutOfRange);
			}
			
			if(null == value){
				logger.warn("updateVariable : value is null");
				result.setError(ErrorCode.ArgumentNull);
			}
			
			if(null == key){
				logger.warn("updateVariable : key is null");
				result.setError(ErrorCode.ArgumentNull);
			}
			
			if(!result.hasError()){
				ParametersDataAccess.updateVariable(entityType.getValue(), entityId, key, value, userId, dependsOnKey);
				getVariablesCache().invalidateAll();
			}
		} catch(Exception ex){
			UUID errorId = logger.error(ex, "updateVariable() : Unexpected error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}
	
	public static BaseOperationResult deleteReferences(String definitionKey, String referenceKey, String dependsOnKey){
		BaseOperationResult result = new BaseOperationResult();
		try{
			
			if(!result.hasError()){
				ParametersDataAccess.deleteReferences(definitionKey, referenceKey, dependsOnKey);
				getVariablesCache().invalidateAll();
			}
		} catch(Exception ex){
			UUID errorId = logger.error(ex, "deleteReferences() : Unexpected error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}
	
	public static String getReferenceKey(EntityType entityType, long entityId){
		return entityType.getValue() + ":" + entityId;
	}
}
