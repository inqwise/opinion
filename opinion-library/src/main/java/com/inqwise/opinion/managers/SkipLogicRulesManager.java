package com.inqwise.opinion.managers;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.inqwise.opinion.infrastructure.dao.IResultSetCallback;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;
import com.inqwise.opinion.library.common.accounts.IAccountView;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.common.parameters.EntityType;
import com.inqwise.opinion.library.common.parameters.IVariableSet;
import com.inqwise.opinion.library.common.parameters.VariablesCategories;
import com.inqwise.opinion.library.dao.DAOBase;
import com.inqwise.opinion.library.managers.AccountsManager;
import com.inqwise.opinion.library.managers.ParametersManager;
import com.inqwise.opinion.common.opinions.IOpinion;
import com.inqwise.opinion.common.rules.IRule;
import com.inqwise.opinion.common.rules.RuleType;
import com.inqwise.opinion.common.rules.NewRuleArgs;
import com.inqwise.opinion.dao.OpinionsDataAccess;
import com.inqwise.opinion.dao.SkipLogicRulesDataAccess;
import com.inqwise.opinion.dao.ThemesDataAccess;
import com.inqwise.opinion.entities.OpinionEntity;

public class SkipLogicRulesManager {
	private static ApplicationLog logger = ApplicationLog
			.getLogger(SkipLogicRulesManager.class);
	
	public static OperationResult<List<IRule>> getMeny(long opinionId, RuleType ruleType, Long accountId, long userId){
		final OperationResult<List<IRule>> result = new OperationResult<List<IRule>>();
		try{
			final List<IRule> rules = new ArrayList<IRule>();
			
			IResultSetCallback callback = new IResultSetCallback() {
				
				public void call(ResultSet reader, int generationId) throws Exception {
					while(reader.next()){
						if(0 == generationId){
							//OpinionEntity opinion = OpinionEntity.identifyOpinion(reader, finalPermissions);
							//result.setValue(opinion);							
						}
					}
				}
			};
			
			SkipLogicRulesDataAccess.getMenyResultSet(callback, opinionId, accountId, userId, (null == ruleType ? null : ruleType.getValue()));
			
			
			if(!result.hasError()){
				result.setValue(rules);
			}
		}
		catch(Exception ex){
			UUID errorId = logger.error(ex, "getMeny() : Unexpected error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}
	
	public static BaseOperationResult update(final long opinionId, Long accountId, long userId, List<NewRuleArgs> argsList){
		final BaseOperationResult result = new BaseOperationResult();
		try{
			IResultSetCallback callback = new IResultSetCallback() {
				
				public void call(ResultSet reader, int generationId) throws Exception {
					while(reader.next()){
						if(0 == generationId){
							int errorCode = ResultSetHelper.optInt(reader, DAOBase.ERROR_CODE, 0);
							switch(errorCode){
							case 4:
								logger.warn("update : not permitted to update rules for opinion #%s", opinionId);
								result.setError(ErrorCode.NotPermitted);
								break;
							}
														
						}
					}
				}
			};
			
			for (NewRuleArgs newRuleArgs : argsList) {
				
			}
			
			//SkipLogicRulesDataAccess.update(callback, opinionId, accountId, userId);
		}
		catch(Exception ex){
			UUID errorId = logger.error(ex, "update() : Unexpected error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}
}
