package com.inqwise.opinion.facade.front;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.infrastructure.common.IOperationResult;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;
import com.inqwise.opinion.library.common.accounts.IAccount;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.common.IPostmasterContext;
import com.inqwise.opinion.common.IPostmasterObject;
import com.inqwise.opinion.common.ITheme;
import com.inqwise.opinion.common.opinions.IOpinion;
import com.inqwise.opinion.common.opinions.OpinionType;
import com.inqwise.opinion.managers.OpinionsManager;
import com.inqwise.opinion.managers.ThemesManager;

public class ThemesEntry extends Entry implements IPostmasterObject {

	static ApplicationLog logger = ApplicationLog.getLogger(ThemesEntry.class);
	
	public ThemesEntry(IPostmasterContext context) {
		super(context);
	}
	
	public JSONObject getThemes(JSONObject input) throws JSONException, IOException, NullPointerException, ExecutionException {
		JSONObject output;
		Long accountId = null;
		IOperationResult result = validateSignIn();
		
		IAccount account = null;
		if(null == result) {
			OperationResult<IAccount> accountResult = getAccount();
			if(accountResult.hasError()){
				result = accountResult;
			} else {
				account = accountResult.getValue();
			}
		}
		
		if(null == result){
			accountId = account.getId();
			Integer top = JSONHelper.optInt(input, "top");
			int opinionTypeId = JSONHelper.optInt(input, "opinionTypeId", OpinionType.Survey.getValue());
		
			CDataCacheContainer ds = ThemesManager.getMeny(accountId, top, opinionTypeId);
		
			CDataRowSet rowSet = ds.getAll();
			JSONArray themesJa = new JSONArray();
			
			while(rowSet.next()){
				JSONObject themeJo = new JSONObject().put(ITheme.JsonNames.ID, rowSet.getInt(ITheme.ResultSetNames.THEME_ID));
				themeJo.put(ITheme.JsonNames.NAME, rowSet.getString(ITheme.ResultSetNames.NAME));
				themeJo.put(ITheme.JsonNames.IS_TEMPLATE, rowSet.getBoolean(ITheme.ResultSetNames.IS_TEMPLATE));
				themesJa.put(themeJo);
			}
			
			output = new JSONObject();
			output.put("themes", themesJa);
		} else {
			output = result.toJson();
		}
		
		return output;
	}

	public JSONObject getTheme(JSONObject input) throws IOException, JSONException, NullPointerException, ExecutionException{
		JSONObject output;
		Long accountId = null;
		IOperationResult result = validateSignIn();
		ITheme theme = null;
		IAccount account = null;
		if(null == result) {
			OperationResult<IAccount> accountResult = getAccount();
			if(accountResult.hasError()){
				result = accountResult;
			} else {
				account = accountResult.getValue();
			}
		}
		
		if(null == result){
			accountId = account.getId();
			int themeId = input.getInt(ITheme.JsonNames.ID);
			
			OperationResult<ITheme> themeResult = ThemesManager.get(themeId, accountId);
			if(themeResult.hasError()){
				result = themeResult;
			} else {
				theme = themeResult.getValue();
			}
		}
		
		if(null == result){
			output = new JSONObject();
			output.put(ITheme.JsonNames.CSS_CONTENT, theme.getCssContent());
			output.put(ITheme.JsonNames.NAME, theme.getName());
			output.put(ITheme.JsonNames.ID, theme.getId());
		} else {
			output = result.toJson();
		}
		
		return output;
	}
	
	public JSONObject updateTheme(JSONObject input) throws IOException, JSONException, NullPointerException, ExecutionException {
		JSONObject output;
		IOperationResult result = null;
		
		Long userId = null;
		Long accountId = null;
		Integer themeId = null;
		String name = null;
		Long opinionId = null;
		OpinionType opinionType = null;
		String cssContent = null;
		
		if(IsSignedIn()){
			userId = getUserId().getValue();
		} else {
			logger.warn("updateTheme : Not Signed in.");
			result = new BaseOperationResult(ErrorCode.NotSignedIn);
		}
		
		IAccount account = null;
		if(null == result) {
			OperationResult<IAccount> accountResult = getAccount();
			if(accountResult.hasError()){
				result = accountResult;
			} else {
				account = accountResult.getValue();
			}
		}
		
		if(null == result){
			accountId = account.getId();
			themeId = JSONHelper.optInt(input, ITheme.JsonNames.ID);
			name = JSONHelper.optString(input, ITheme.JsonNames.NAME);
			opinionId = JSONHelper.optLong(input, IOpinion.JsonNames.OPINION_ID);
			Integer opinionTypeId = JSONHelper.optInt(input, ITheme.JsonNames.OPINION_TYPE_ID);
			opinionType = OpinionType.fromInt(opinionTypeId);
			cssContent = JSONHelper.optString(input, ITheme.JsonNames.CSS_CONTENT);
		}
		
		if(null == result){
			// Insert new theme
			if(null == themeId){
				OperationResult<Integer> insertThemeResult = ThemesManager.createTheme(name, opinionType.getValue(), cssContent, accountId, false);
				if(insertThemeResult.hasError()){
					result = insertThemeResult;
				} else {
					themeId = insertThemeResult.getValue();
				}
			}
			// Update theme content
			else if(null != cssContent){
				BaseOperationResult updateThemeResult = ThemesManager.updateThemeCssContent(themeId, cssContent, accountId);
				if(updateThemeResult.hasError()){
					result = updateThemeResult;
				}
			}
		}
		
		// update opinion theme
		if(null == result && null != opinionId){
			BaseOperationResult changeThemeResult = OpinionsManager.changeTheme(opinionId, themeId, accountId,
					null /* actionGuid */, userId);
			if(changeThemeResult.hasError()){
				result = changeThemeResult;
			}
		}
		
		if(null == result){
			output = new JSONObject();
			output.put(ITheme.JsonNames.ID, themeId);
		} else {
			output = result.toJson();
		}
		
		return output;
	}
	
	public JSONObject deleteTheme(JSONObject input) throws IOException, JSONException, NullPointerException, ExecutionException {
		JSONObject output;
		IOperationResult result = null;
		
		Long userId = null;
		Long accountId = null;
		Integer themeId = null;
		
		if(IsSignedIn()){
			userId = getUserId().getValue();
		} else {
			logger.warn("deleteTheme : Not Signed in.");
			result = new BaseOperationResult(ErrorCode.NotSignedIn);
		}
		
		IAccount account = null;
		if(null == result) {
			OperationResult<IAccount> accountResult = getAccount();
			if(accountResult.hasError()){
				result = accountResult;
			} else {
				account = accountResult.getValue();
			}
		}
		
		if(null == result){
			accountId = account.getId();
			themeId = JSONHelper.optInt(input, ITheme.JsonNames.ID);
		}
		
		if(null == result){
			result = ThemesManager.deleteTheme(themeId, accountId);
		}
		
		if(result.hasError()){
			output = result.toJson();
		} else {
			output = BaseOperationResult.JsonOk;
		}
		
		return output;
	}
}
