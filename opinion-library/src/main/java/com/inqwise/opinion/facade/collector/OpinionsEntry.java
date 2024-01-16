package com.inqwise.opinion.facade.collector;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.time.StopWatch;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import com.inqwise.opinion.common.GuidType;
import com.inqwise.opinion.common.IAnswererSession;
import com.inqwise.opinion.common.ICollectorPostmasterContext;
import com.inqwise.opinion.common.IControl;
import com.inqwise.opinion.common.IHttpAnswererSession;
import com.inqwise.opinion.common.IOpinionAccount;
import com.inqwise.opinion.common.IOpinionComplexData;
import com.inqwise.opinion.common.IPostmasterObject;
import com.inqwise.opinion.common.IResponseRequest;
import com.inqwise.opinion.common.ITheme;
import com.inqwise.opinion.common.OutputMode;
import com.inqwise.opinion.common.ParentType;
import com.inqwise.opinion.common.ResponseType;
import com.inqwise.opinion.common.ResultsPermissionType;
import com.inqwise.opinion.common.collectors.CollectorStatus;
import com.inqwise.opinion.common.collectors.ICollector;
import com.inqwise.opinion.common.collectors.ICollector.IMultiPageExtension;
import com.inqwise.opinion.common.collectors.ICollector.IMultiplyResponsesExtension;
import com.inqwise.opinion.common.collectors.IPollsCollector;
import com.inqwise.opinion.common.collectors.ISurveysCollector;
import com.inqwise.opinion.common.opinions.IOpinion;
import com.inqwise.opinion.common.opinions.IPoll;
import com.inqwise.opinion.common.opinions.ISheetsContainer;
import com.inqwise.opinion.common.opinions.ISurvey;
import com.inqwise.opinion.common.servicePackage.IServicePackageSettings;
import com.inqwise.opinion.common.sheet.ISheet;
import com.inqwise.opinion.common.sheet.StartSheetIndexData;
import com.inqwise.opinion.entities.ServicePackageSettingsEntity;
import com.inqwise.opinion.http.HttpClientSession;
import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;
import com.inqwise.opinion.library.common.IProduct;
import com.inqwise.opinion.library.common.accounts.IAccount;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.managers.ProductsManager;
import com.inqwise.opinion.managers.AccountsManager;
import com.inqwise.opinion.managers.AnswerersSessionsManager;
import com.inqwise.opinion.managers.CollectorsManager;
import com.inqwise.opinion.managers.ControlsManager;
import com.inqwise.opinion.managers.OpinionsManager;
import com.inqwise.opinion.managers.ResponsesManager;
import com.inqwise.opinion.managers.SheetsManager;
import com.inqwise.opinion.managers.ThemesManager;

public class OpinionsEntry extends Entry implements IPostmasterObject {
	
	private static final String ALLOW_RESULTS = "allowResults";

	public OpinionsEntry(ICollectorPostmasterContext context) {
		super(context);
	}

	public JSONObject getDetails(JSONObject input) throws JSONException,
			IOException {
		BaseOperationResult result = null;
		JSONObject output;
		IOpinionComplexData<IOpinion> complexData = null;
		String urlParams = JSONHelper.optString(input, "urlParams", null, "");
		ICollector.IEndDateExtension endDateExtension = null;
		ICollector.IMessagesExtension messagesExtension = null;
		ICollector.IMultiPageExtension multiPageExtension = null;
		ICollector.IMultiplyResponsesExtension multiplyResponsesExtension = null;
		IPollsCollector pollsCollector = null;
		// StopWatch
		StopWatch sw = new StopWatch();
		sw.start();
		// StopWatch
		
		OperationResult<IOpinionComplexData<IOpinion>> complexDataResult = identifySurveyCredentionals(
				input, false);
		if (complexDataResult.hasError()) {
			result = complexDataResult;
		} else {
			complexData = complexDataResult.getValue();
		}
		
		// StopWatch
		sw.stop();
		// point 1
		if(sw.getTime() >100)
		logger.warn("getDetails point 1:  %s", sw.toString());
		sw.reset();
		sw.start();
		// StopWatch		
		
		Integer themeId;
		
		// get Survey details
		IOpinion opinion = null;
		ITheme theme = null;
		if (null == result) {
			opinion = complexData.getOpinion();
			themeId = JSONHelper.optInt(input, "themeId", opinion.getThemeId());
			OperationResult<ITheme> themeResult = ThemesManager.get(themeId, null);
			if(!themeResult.hasError()){
				theme = themeResult.getValue();
			}
		}
		
		// StopWatch
		sw.stop();
		// point 2
		if(sw.getTime() >100)
		logger.warn("getDetails point 2:  %s", sw.toString());
		sw.reset();
		sw.start();
		// StopWatch
		
		if (null == result){
			output = opinion.getShortDetailsJson();
			
			output.put(IOpinion.JsonNames.HIGHLIGHT_REQUIRED_QUESTIONS, opinion.getIsHighlightRequiredQuestions());
			output.put(IOpinion.JsonNames.FINISH_BEHAVIOUR_TYPE_ID, opinion.getFinishBehaviourType().getValue());
			if(opinion.isHidePoweredBy()){
				output.put(IOpinion.JsonNames.HIDE_POWERED_BY, opinion.isHidePoweredBy());
			}
			
			if(null != theme){
				output.put(ITheme.JsonNames.CSS_CONTENT, theme.getCssContent());
			}
			
			if(opinion instanceof ISurvey){
				ISurvey survey = (ISurvey)opinion;
				output.put(IOpinion.JsonNames.SHOW_PROGRESS_BAR, survey.getShowProgressBar());
			}
			
			if (complexData.getMode() == OutputMode.Answer
					&& complexData.getGuidType() == GuidType.Collector) {

				ICollector collector = complexData.getCollector();
				
				if(collector instanceof ICollector.IMessagesExtension){
					messagesExtension = (ICollector.IMessagesExtension) collector;
				}
				
				if(collector instanceof ICollector.IEndDateExtension){
					endDateExtension = (ICollector.IEndDateExtension) collector;
				}
				
				if(collector instanceof ICollector.IMultiPageExtension){
					multiPageExtension = (IMultiPageExtension) collector;
				}
				
				if(collector instanceof ICollector.IMultiplyResponsesExtension){
					multiplyResponsesExtension = (IMultiplyResponsesExtension) collector;
				}
				
				if(collector instanceof IPollsCollector){
					pollsCollector = (IPollsCollector) collector;
				}
				
				output.put("statusId", collector.getCollectorStatus()
						.getValue());
				if(null != endDateExtension){
					output.put("endDate", endDateExtension.getEndDate());
				}
								
				output.put("answersAreFinished", complexData.isFinished());
				
				if (complexData.isOpen()) {
					output.put("isSendClientInfo", !complexData.getSession()
							.getIsClientInfoReceived());
					output.put("isSessionStart", complexData.getSession()
							.getIsClientInfoReceived());
					output.put("isPasswordRequired", complexData
							.isPasswordRequired());
					
					if(null != multiplyResponsesExtension){
						output.put(ICollector.JsonNames.IS_ALLOW_MULTIPLY_RESPONSES, multiplyResponsesExtension.isAllowMultiplyResponses());
					}
					
					if(null != multiPageExtension){
						output.put(ICollector.JsonNames.IS_ENABLE_PREVIOUS, multiPageExtension.isEnablePrevious());
					}
					
					if(null != pollsCollector){
						output.put(ALLOW_RESULTS, pollsCollector.getResultsType() == ResultsPermissionType.All 
												|| pollsCollector.getResultsType() == ResultsPermissionType.Percentage);
						output.put(ICollector.JsonNames.RESULTS_TYPE_ID, pollsCollector.getResultsType().getValue());
					}
				} else {
					output.put("isClosed", true);
					
					if(null != messagesExtension){
						output.put("closeMessage", messagesExtension.getCloseMessage());
					}
				}
			} else if(complexData.getMode() == OutputMode.View) {
				if(opinion instanceof IPoll){
					output.put(ALLOW_RESULTS, true);
					output.put(ICollector.JsonNames.RESULTS_TYPE_ID, 3);
				}
				output.put(ICollector.JsonNames.IS_ENABLE_PREVIOUS, true);
			}

			if (complexData.isOpen() && !complexData.isFinished() && opinion instanceof ISheetsContainer) {
				IHttpAnswererSession session = complexData.getSession();
				// sheets
				JSONObject sheetsJo = new JSONObject();
				sheetsJo.put("list", getSheetsJa((ISheetsContainer) opinion));
				sheetsJo.put("lastSelected", null == session ? 0 : session
						.getLastViewedSheetIndex());
				output.put("sheets", sheetsJo);
			}
		} else {
			output = result.toJson();
		}
		
		if(null != complexData && null != complexData.getCollector()){
			output.put("returnUrl", getReturnUrl(complexData, urlParams));
			output.put("screenOutUrl", getScreenOutUrl(complexData, urlParams));
			output.put("closedUrl", getClosedUrl(complexData, urlParams));
			output.put("sessionKey", complexData.getSession().getKey());
			output.put("sessionId", complexData.getSession().getSessionId());
		}

		// StopWatch
		sw.stop();
		// point 3
		if(sw.getTime() >100)
		logger.warn("getDetails point 3:  %s", sw.toString());
		// StopWatch		
		
		return output;
	}
	
	private String getScreenOutUrl(
			IOpinionComplexData<IOpinion> complexData, String urlParams) {
		String result = null;
		ICollector collector = complexData.getCollector();
		if(null != collector) {
			if(collector instanceof ISurveysCollector){
				ISurveysCollector surveysCollector = (ISurveysCollector) collector;
				if(null == surveysCollector.getScreenOutUrl()){
					result = getReturnUrl(complexData, urlParams);
				} else {
					result = combineUrl(surveysCollector.getScreenOutUrl(), urlParams);
				}
			}
		}
		return result;
	}

	private String combineUrl(String url, String urlParams) {
		
		if(null == urlParams){
			return url;
		}
		
		
		String result;
		if(url.contains("?")){
			if(url.endsWith("?")){
				result = url + urlParams;
			} else {
				result = url + "&" + urlParams;
			}
		} else {
			result = url + "?" + urlParams;
		}
		
		return result;
	}

	private String getReturnUrl(IOpinionComplexData<IOpinion> complexData,
			String urlParams) {
		
		String result = null;
		ICollector collector = complexData.getCollector();
		if(null != collector) {
			if(collector instanceof ISurveysCollector){
				ISurveysCollector surveysCollector = (ISurveysCollector) collector;
				if(null != surveysCollector.getReturnUrl()){
					result = combineUrl(surveysCollector.getReturnUrl(), urlParams);
				}
			}
		}
		return result;
	}
	
	private String getClosedUrl(IOpinionComplexData<IOpinion> complexData,
			String urlParams) {
		
		String result = null;
		ICollector collector = complexData.getCollector();
		if(null != collector) {
			if(collector instanceof ISurveysCollector){
				ISurveysCollector surveysCollector = (ISurveysCollector) collector;
				if(null != surveysCollector.getOpinionClosedUrl()){
					result = combineUrl(surveysCollector.getOpinionClosedUrl(), urlParams);
					if(null != complexData.getRespondentExternalId()){
						result += "?respondent=" + complexData.getRespondentExternalId();
					}
				}
			}
		}
		return result;
	}

	private Object getSheetsJa(ISheetsContainer sheetsContainer) throws JSONException {
		Object result;
		OperationResult<List<ISheet>> sheetsResult = sheetsContainer.getSheets();
		if (sheetsResult.hasError()) {
			if (sheetsResult.getError() == ErrorCode.NoResults) {
				result = new JSONArray();
			} else {
				result = sheetsResult.toJson();
			}
		} else {
			JSONArray sheetsJa = new JSONArray();
			try {
				for (ISheet sheet : sheetsResult.getValue()) {
					sheetsJa.put(sheet.toJson());
				}
			} catch (Exception e) {
				UUID errorId = logger
						.error(e,
								"getSheetsJa() : Unable to get value from sheetsResult.");
				result = new BaseOperationResult(ErrorCode.GeneralError,
						errorId, e.toString()).toJson();
			}
			result = sheetsJa;
		}
		return result;
	}
	
	

	public JSONObject getStartIndexOfSheet(JSONObject input)
			throws JSONException, UnsupportedEncodingException {
		JSONObject output = new JSONObject();
		BaseOperationResult result = null;
		IOpinionComplexData<IOpinion> complexData = null;

		OperationResult<IOpinionComplexData<IOpinion>> complexDataResult = identifySurveyCredentionals(input);
		if (complexDataResult.hasError()) {
			result = complexDataResult;
		} else {
			try {
				complexData = complexDataResult.getValue();
			} catch (Exception e) {
				UUID errorId = logger
						.error(
								e,
								"getStartIndexOfSheet() : Failed to get value from identifySurveyCredentionals.");
				result = new BaseOperationResult(ErrorCode.GeneralError,
						errorId, e.toString());
			}
		}

		if (null == result && complexData.isFinished()){
			result = new BaseOperationResult(ErrorCode.AnswersAreFinished);
		}
		
		if (null == result
				&& complexData.getResponsesBallance(Long.MAX_VALUE) <= 0) {
			logger.warn("getStartIndexOfSheet() : Responses ballance is: "
					+ complexData.getResponsesBallance(0L));
			result = new OperationResult<Integer>(
					ErrorCode.MaxResponsesReached, UUID.randomUUID(),
					"Responses ballance is: "
							+ complexData.getResponsesBallance(0L));
		}

		if (null == result) {
			Long sheetId = input.getLong("sheetId"); // able to be null when not
			// moved to other sheet
			OperationResult<StartSheetIndexData> startIndexResult = SheetsManager
					.getStartIndexOfSheet(sheetId);
			if (startIndexResult.hasError()) {
				output = startIndexResult.toJson();
			} else {
				try {
					StartSheetIndexData startIndexData = startIndexResult.getValue();
					output.put("startIndex", startIndexData.getIndex());
					output.put("startNumerator", startIndexData.getNumerator());
				} catch (Exception e) {
					UUID errorId = logger
							.error(e,
									"getStartIndexOfSheet() : Failed to get value from getStartIndexOfSheet.");
					result = new BaseOperationResult(ErrorCode.GeneralError,
							errorId, e.toString());
				}
			}
		}

		if (null != result) {
			output = result.toJson();
		}

		return output;
	}

	public JSONObject getControls(JSONObject input) throws JSONException, UnsupportedEncodingException {
		JSONObject output = new JSONObject();
		BaseOperationResult result = null;
		IOpinionComplexData<IOpinion> complexData = null;

		OperationResult<IOpinionComplexData<IOpinion>> complexDataResult = identifySurveyCredentionals(input);
		
		if (complexDataResult.hasError()) {
			result = complexDataResult;
		} else {
			try {
				complexData = complexDataResult.getValue();
			} catch (Exception e) {
				UUID errorId = logger
						.error(e,
								"getControls() : Failed to get value from identifySurveyCredentionals.");
				result = new BaseOperationResult(ErrorCode.GeneralError,
						errorId, e.toString());
			}
		}

		/*
		if (null == result
				&& complexData.getResponsesBallance(Long.MAX_VALUE) <= 0) {
			logger.warn("getControls() : Control ballance is: "
					+ complexData.getResponsesBallance(0L));
			result = new OperationResult<Integer>(
					ErrorCode.MaxResponsesReached, UUID.randomUUID(),
					"Responses ballance is: "
							+ complexData.getResponsesBallance(0L));
		}
		*/

		Long sheetId = null;
		Long opinionId = null;
		List<IControl> controls = null;
		
		if (null == result && complexData.isFinished()){
			result = new BaseOperationResult(ErrorCode.AnswersAreFinished);
		}
		
		if (null == result && complexData.isOpen()) {
			
			sheetId = JSONHelper.optLong(input, "sheetId");
			opinionId = complexData.getOpinion().getId();
			
			if(null == sheetId && complexData.getOpinion() instanceof ISheetsContainer){
				result = new BaseOperationResult(ErrorCode.ArgumentMandatory, "sheetId required");
			}
			
			if(null != sheetId && !(complexData.getOpinion() instanceof ISheetsContainer)){
				result = new BaseOperationResult(ErrorCode.ArgumentWrong, "sheetId not expected");
			}
		}
		
		if(null == result && complexData.isOpen()) {
			Integer sheetIndex = input.optInt("lastSelected", 1);
			Long translationId = JSONHelper.optLong(input, "translationId");
			IHttpAnswererSession session = complexData.getSession();

			// save last viewed sheet index
			if (null != session
					&& session.getLastViewedSheetIndex() != sheetIndex) {
				session.setLastViewedSheetIndex(sheetIndex);
				session.save();
			}

			String answerSessionId = null;
			if (null != session) {
				answerSessionId = session.getSessionId();
			}

			long parentId;
			ParentType parentType;
			if(null == sheetId){
				parentId = opinionId;
				parentType = ParentType.Opinion;
			} else {
				parentId = sheetId;
				parentType = ParentType.Sheet;
			}
			
			OperationResult<List<IControl>> getControlsResult = ControlsManager
					.getControls(parentId, parentType, translationId, null, answerSessionId);
			if (getControlsResult.hasError()) {
				result = getControlsResult;
			} else {
				try {
					controls = getControlsResult.getValue();
				} catch (Exception e) {
					UUID errorId = logger
							.error(e,
									"getControls() : Failed to get value from getControls.");
					result = new BaseOperationResult(ErrorCode.GeneralError,
							errorId, e.toString());
				}
			}
		}

		if (null == result) {
			if (complexData.isOpen()) {
				JSONArray controlsJA = new JSONArray();
				output.put("list", controlsJA);
				for (IControl control : controls) {
					controlsJA.put(control.toJson(true));
				}
			}
		} else {
			output = result.toJson();
		}

		return output;
	}
	
	public JSONObject createResponse(final JSONObject input)
			throws JSONException {
		JSONObject output = new JSONObject();
		BaseOperationResult result = null;
		try {
			
			// StopWatch
			StopWatch sw = new StopWatch();
			sw.start();
			// StopWatch
			
			IOpinionComplexData<IOpinion> _complexData = null;
			OperationResult<IOpinionComplexData<IOpinion>> complexDataResult = identifySurveyCredentionals(input);

			// StopWatch
			sw.stop();
			// point 1
			if(sw.getTime() >100)
			logger.warn("createResponse point 1:  %s", sw.toString());
			sw.reset();
			sw.start();
			// StopWatch		
			
			if (complexDataResult.hasError()) {
				result = complexDataResult;
			} else {
				_complexData = complexDataResult.getValue();
			}

			if (null == result
					&& _complexData.getGuidType() != GuidType.Collector) {
				logger
						.warn(
								"createResponse() : Only Collector guidType supported in collectRequest. supplied Type is : '%s'",
								_complexData.getGuidType());
				result = new BaseOperationResult(ErrorCode.ArgumentWrong,
						"Only Collector guid supported");
			}

			if (null == result && _complexData.getMode() != OutputMode.Answer) {
				logger
						.warn(
								"createResponse() : Only Answer mode supported in collectRequest. supplied mode is : '%s'",
								_complexData.getMode());
				result = new BaseOperationResult(ErrorCode.ArgumentWrong,
						"Only Answer mode supported");
			}
			
			HttpClientSession _session = null;
			if (null == result) {
				_session = getContext().getSession();
			}
			
			if (null == result && _complexData.getSession().isOpinionFinished()){
				logger.warn("createResponse() : Received response for finished Opinion. guid: '%s'", _complexData.getGuid());
				result = new BaseOperationResult(ErrorCode.AnswersAreFinished);
			}
			
			if (null == result && null == _complexData.getSession().getSessionId()){
				UUID errorId = logger.error("createResponse() : sessionId is null");
				result = new BaseOperationResult(ErrorCode.InvalidOperation, errorId, "session not found");
			}

			ICollector.IMultiplyResponsesExtension multiplyResponsesExtension = null;
			if(null == result && _complexData.getCollector() instanceof ICollector.IMultiplyResponsesExtension){
				multiplyResponsesExtension = (IMultiplyResponsesExtension) _complexData.getCollector();
			}
			
			/*
			 * {"surveys":{"createResponse":{"guidTypeId":1,"guid":"68620fdc-2d84-46ab-9f96-cd982e35f8ce"
			 * ,"modeId":1,"responseTypeId":1,
			 * "os":{"name":"Win 7 (x32)","platform"
			 * :"Win32","language":"en-US","timeZone":"+02:00"},
			 * "screen":{"width":1848,"height":1155,"color":24},
			 * "browser":{"appName"
			 * :"Netscape","version":"Firefox 3.x or +","isCookieEnabled"
			 * :true,"isJavaInstalled":true,
			 * "isFlashInstalled":true,"flashVersion":"10.1 r82"}}}}
			 */
			if (null == result) {
				
				final HttpClientSession session = _session;
				final IOpinionComplexData<IOpinion> complexData = _complexData;
				final JSONObject osInfoJo = input.optJSONObject("os");
				final JSONObject screenInfoJo = input.optJSONObject("screen");
				final JSONObject browserInfoJo = input.optJSONObject("browser");
				final JSONObject responseData = input
						.optJSONObject("responseData");
				final IAccount opinionAccount = complexData.getAccount();

				// StopWatch
				sw.stop();
				// point 2
				if(sw.getTime() >100)
				logger.warn("createResponse point 2:  %s", sw.toString());
				sw.reset();
				sw.start();
				// StopWatch		
				
				
				IResponseRequest request = new IResponseRequest() {

					@Override
					public Long getTranslationId() {
						return complexData.getOpinion().getTranslationId();
					}

					@Override
					public Integer getResponseTypeId() {
						return JSONHelper.optInt(input, "responseTypeId");
					}

					@Override
					public Integer getParentTypeId() {
						return JSONHelper.optInt(responseData,
								"controlParentTypeId");
					}

					@Override
					public Long getParentId() {
						return JSONHelper.optLong(responseData,
								"controlParentId");
					}

					@Override
					public Integer getPageNumer() {
						return JSONHelper.optInt(responseData, "sheetIndex"); // Sheet
						// index
					}

					@Override
					public Long getOptionId() {
						return JSONHelper.optLong(responseData, "optionId");
					}

					@Override
					public Long getOpinionId() {
						return complexData.getOpinion().getId();
					}

					@Override
					public Integer getControlTypeId() {
						return JSONHelper.optInt(responseData, "controlTypeId");
					}

					@Override
					public Integer getControlIndex() {
						return JSONHelper.optInt(responseData, "controlIndex");
					}

					@Override
					public Long getControlId() {
						return JSONHelper.optLong(responseData, "controlId");
					}

					@Override
					public String getControlContent() {
						return JSONHelper.optString(responseData,
								"controlContent", null, "");
					}

					@Override
					public String getComment() {
						return JSONHelper.optString(responseData, "comment",
								null, "");
					}

					@Override
					public Long getCollectorId() {
						return complexData.getCollector().getId();
					}

					@Override
					public String getClientIp() {
						return getContext().getClientIp();
					}

					@Override
					public String getAnswerValue() {
						return JSONHelper.optString(responseData,
								"answerValue", null, "");
					}

					@Override
					public String getAnswerText() {
						return JSONHelper.optString(responseData, "answerText",
								null, "");
					}

					@Override
					public String getGuid() {
						return complexData.getGuid();
					}

					@Override
					public Integer getGuidTypeId() {
						return complexData.getGuidType().getValue();
					}

					@Override
					public Integer getOpinionVersionId() {
						return complexData.getOpinion().getVersion();
					}

					@Override
					public String getBrowserAppName() {
						return JSONHelper.optString(browserInfoJo, "appName");
					}

					@Override
					public String getBrowserVersion() {
						return JSONHelper.optString(browserInfoJo, "version");
					}

					@Override
					public String getFlashVersion() {
						return JSONHelper.optString(browserInfoJo,
								"flashVersion");
					}

					@Override
					public Boolean getIsCookieEnabled() {
						return JSONHelper.optBoolean(browserInfoJo,
								"isCookieEnabled");
					}

					@Override
					public Boolean getIsJavaInstalled() {
						return JSONHelper.optBoolean(browserInfoJo,
								"isJavaInstalled");
					}

					@Override
					public String getOsLanguage() {
						return JSONHelper.optString(osInfoJo, "language");
					}

					@Override
					public String getOsName() {
						return JSONHelper.optString(osInfoJo, "name");
					}

					@Override
					public String getOsPlatform() {
						return JSONHelper.optString(osInfoJo, "platform");
					}

					@Override
					public String getOsTimeZone() {
						return JSONHelper.optString(osInfoJo, "timeZone");
					}

					@Override
					public Integer getScreenColorDepth() {
						return JSONHelper.optInt(screenInfoJo, "color");
					}

					@Override
					public Integer getScreenHeight() {
						return JSONHelper.optInt(screenInfoJo, "height");
					}

					@Override
					public Integer getScreenWidth() {
						return JSONHelper.optInt(screenInfoJo, "width");
					}

					@Override
					public String getAnswerSessionId() {
						return complexData.getSession().getSessionId();
					}

					@Override
					public String getBrowserName() {
						return JSONHelper.optString(browserInfoJo, "name");
					}

					@Override
					public Long getSheetId() {
						return JSONHelper.optLong(responseData, "sheetId");
					}

					@Override
					public Integer getTargetPageNumber() {
						return JSONHelper.optInt(responseData, "targetIndex");
					}

					@Override
					public Long getTargetSheetId() {
						return JSONHelper.optLong(responseData, "targetId");
					}

					@Override
					public Integer getControlInputTypeId() {
						return JSONHelper.optInt(responseData, "inputTypeId");
					}

					@Override
					public Integer getSelectTypeId() {
						return JSONHelper.optInt(responseData, "selectTypeId");
					}

					@Override
					public Integer getOptionIndex() {
						return JSONHelper.optInt(responseData, "optionIndex");
					}

					@Override
					public Integer getOptionKindId() {
						return JSONHelper.optInt(responseData, "optionKindId");
					}

					@Override
					public Boolean getIsSelected() {
						return JSONHelper
								.optBoolean(responseData, "isSelected");
					}

					@Override
					public boolean isCreateNewSession() {
						return null != getBrowserAppName();
					}

					@Override
					public Long getUserId() {
						return session.hasError() ? (Long) null : session
								.getUserId();
					}

					@Override
					public Integer getAccountServicePackageId() {
						return null == complexData.getAccountServicePackage() ? null
								: complexData.getAccountServicePackage()
										.getServicePackageId();
					}

					@Override
					public Long getOpinionAccountId() {
						return opinionAccount.getId();
					}

					@Override
					public Integer getOpinionAccountServicePackageId() {
						return opinionAccount.getServicePackageId();
					}

					@Override
					public Boolean isUnpanned() {
						return complexData.getResponsesBallance(Long.MAX_VALUE) == 0;
					}

					@Override
					public String getRespondentId() {
						return JSONHelper.optStringTrim(input, "respondent");
					}

					@Override
					public String getTargetUrl() {
						return JSONHelper.optStringTrim(input, "target");
					}

					@Override
					public String getControlKey() {
						return JSONHelper.optString(responseData, "controlKey");
					}
				};

				OperationResult<Long> createResult = ResponsesManager
						.createResponse(request, complexData.getCollector(),
								complexData.getOpinion(), complexData.getProduct(), complexData.getAccount());
				
				// StopWatch
				sw.stop();
				// point 3
				if(sw.getTime() >100)
				logger.warn("createResponse point 3:  %s", sw.toString());
				sw.reset();
				sw.start();
				// StopWatch		
				
				if (createResult.hasError()) {
					result = createResult;
				} else {
					boolean isSaveSession = false;
					IHttpAnswererSession answererSession = complexData
							.getSession();
					ResponseType responseType = ResponseType.fromInt(request
							.getResponseTypeId());

					// Check ClientInfo
					if (!answererSession.getIsClientInfoReceived()
							&& null != request.getBrowserAppName()) {
						answererSession.setIsClientInfoReceived(true);
						isSaveSession = true;
					}

					// IMPORTANT !!! Must to be the last condition, because
					// available deletion of session
					// Check finish type
					if (responseType == ResponseType.FinishOpinion) {
						if (null != multiplyResponsesExtension && multiplyResponsesExtension
								.isAllowMultiplyResponses()) {
							getContext().removeAnswererSession(
									complexData.getAnswererSessionKey());
							isSaveSession = false;
						} else {
							answererSession.setFinishDate(new Date());
							isSaveSession = true;
						}
					}

					if (isSaveSession) {
						answererSession.save();
					}

					output.put("responseId", createResult.getValue());
				}
				
				// StopWatch
				sw.stop();
				// point 4
				if(sw.getTime() >100)
				logger.warn("createResponse point 4:  %s", sw.toString());
				// StopWatch		

			}
		} catch (Throwable t) {
			UUID errorId = logger.error(t,
					"createResponse() : Unexpected error occured");
			result = new BaseOperationResult(ErrorCode.GeneralError, errorId,
					"Failed to collect response");
		}

		if (null != result) {
			output = result.toJson();
		}

		return output;
	}

	private <T extends IOpinion> OperationResult<IOpinionComplexData<T>> identifySurveyCredentionals(
			JSONObject input) throws JSONException, UnsupportedEncodingException {
		return identifySurveyCredentionals(input, true);
	}

	private <T extends IOpinion> OperationResult<IOpinionComplexData<T>> identifySurveyCredentionals(
			JSONObject input, boolean preventPasswordRestriction) throws JSONException, UnsupportedEncodingException {
		final String guid = JSONHelper.optString(input, "guid");
		final GuidType guidType = GuidType.fromInt(input.optInt("guidTypeId"));
		final OutputMode mode = OutputMode.fromInt(input.optInt("modeId"));
		final String actualPassword = JSONHelper.optString(input, "password");
		boolean includeSession = (OutputMode.View != mode);
		String expectedPassword = null;
		boolean passwordRequired = false;
		final String respondentId = JSONHelper.optString(input, "respondent", null, "");
		String ref = JSONHelper.optString(input, "ref", null, "");
		String targetUrl = JSONHelper.optString(input, "target");
		boolean isSaveSession = false;
		
		// StopWatch
		StopWatch sw = new StopWatch();
		sw.start();
		// StopWatch
		
		ICollector collector = null;
		T opinion = null;
		IHttpAnswererSession session = null;
		boolean isFinished = false;

		OperationResult<IOpinionComplexData<T>> result = null;

		// Validate input arguments
		if (null == guid) {
			UUID warnId = UUID.randomUUID();
			logger
					.warn(
							"identifySurveyCredentionals : Guid is null. guidType: '%s', warnId: '%s'",
							guidType.toString(), warnId.toString());
			result = new OperationResult<IOpinionComplexData<T>>(
					ErrorCode.ArgumentNull, warnId,
					"`guid` argument is mandatory");
		}

		if (null == result
				&& (null == guidType || guidType == GuidType.Undefined)) {
			UUID warnId = UUID.randomUUID();
			logger
					.warn(
							"identifySurveyCredentionals : GuidType is null or Undefined. guid: '%s', warnId: '%s'",
							guid, warnId.toString());
			result = new OperationResult<IOpinionComplexData<T>>(
					ErrorCode.ArgumentNull, warnId,
					"`guidTypeId` argument is mandatory");
		}
		
		// StopWatch
		sw.stop();
		// point 1
		if(sw.getTime() >100)
		logger.warn("identifySurveyCredentionals point 1:  %s", sw.toString());
		sw.reset();
		sw.start();
		// StopWatch
		
		ICollector.IMultiplyResponsesExtension multiplyResponsesExtension = null;
		ICollector.IEndDateExtension endDateExtension = null;
		
		// Collector Guid
		if (null == result && guidType == GuidType.Collector) {
			// Get collector by Guid
			OperationResult<ICollector> collectorResult = CollectorsManager
					.get(guid);
			if (collectorResult.hasError()) {
				result = collectorResult.toErrorResult();
			} else {
				collector = collectorResult.getValue();
				if(collector instanceof ICollector.IPasswordExtension){
					expectedPassword = ((ICollector.IPasswordExtension)collector).getPassword();
				}
				
				if(collector instanceof ICollector.IMultiplyResponsesExtension){
					multiplyResponsesExtension = (IMultiplyResponsesExtension) collector;
				}
				
				if(collector instanceof ICollector.IEndDateExtension){
					endDateExtension = (ICollector.IEndDateExtension) collector;
				}
			}

			// StopWatch
			sw.stop();
			// point 2
			if(sw.getTime() >100)
			logger.warn("identifySurveyCredentionals point 2:  %s", sw.toString());
			sw.reset();
			sw.start();
			// StopWatch
			
			// Get opinion by id
			if (null == result) {
				OperationResult<IOpinion> opinionResult = OpinionsManager
						.getOpinion(collector.getOpinionId(), collector
								.getTranslationId(), null, null);
				if (opinionResult.hasError()) {
					result = opinionResult.toErrorResult();
				} else {
					try {
						opinion = (T) opinionResult.getValue();
					} catch (Exception e) {
						UUID errorId = logger
								.error(e,
										"identifySurveyCredentionals() : Failed to get value from getSurveyById.");
						result = new OperationResult<IOpinionComplexData<T>>(
								ErrorCode.GeneralError, errorId, e.toString());
					}
				}
			}
			// Survey Guid
		} else if (null == result && guidType == GuidType.Draft) {
			// Get opinion by Guid
			OperationResult<IOpinion> opinionResult = OpinionsManager
					.getOpinion(guid);
			if (opinionResult.hasError()) {
				result = opinionResult.toErrorResult();
			} else {
				try {
					opinion = (T) opinionResult.getValue();
				} catch (Exception e) {
					UUID errorId = logger
							.error(e,
									"identifySurveyCredentionals() : Failed to get value from getSurveyByGuid.");
					result = new OperationResult<IOpinionComplexData<T>>(
							ErrorCode.GeneralError, errorId, e.toString());
				}
			}
		}
		
		// StopWatch
		sw.stop();
		// point 3
		if(sw.getTime() >100)
		logger.warn("identifySurveyCredentionals point 3:  %s", sw.toString());
		sw.reset();
		sw.start();
		// StopWatch

		IProduct currentProduct = null;
		if(null == result){
			currentProduct = ProductsManager.getCurrentProduct();
			if(null == currentProduct){
				result = new OperationResult<IOpinionComplexData<T>>(ErrorCode.NoResults);
			}
		}
		
		// StopWatch
		sw.stop();
		// point 4
		if(sw.getTime() >100)
		logger.warn("identifySurveyCredentionals point 4:  %s", sw.toString());
		sw.reset();
		sw.start();
		// StopWatch
		
		IOpinionAccount accountServicePackage = null;
		IAccount account = null;
		if(null == result){
			account = opinion.getAccount();
		}
		
		// StopWatch
		sw.stop();
		// point 5
		if(sw.getTime() >100)
		logger.warn("identifySurveyCredentionals point 5:  %s", sw.toString());
		sw.reset();
		sw.start();
		// StopWatch
		
		String answererSessionKey = null;
		if (null == result && includeSession) {
			answererSessionKey = createAnswererSessionKey(guid, respondentId, targetUrl); 
			session = getContext().getAnswererSession(answererSessionKey);
			if (session.isOpinionFinished()) {
				if (null != multiplyResponsesExtension && multiplyResponsesExtension.isAllowMultiplyResponses()) {
					logger
							.warn("identifySurveyCredentionals() : session not deleted in the end of opinion when isAllowMultiplyResponces flag. Deleting.");
					getContext().removeAnswererSession(answererSessionKey);
					session = getContext().getAnswererSession(answererSessionKey);
					session.setIsClientInfoReceived(false);
				} else {
					logger
							.warn(
									"identifySurveyCredentionals() : try to get opinion details after finished to answer. guid: '%s', guidType: '%s' ip: '%s'",
									guid, guidType, getContext().getClientIp());
					
					isFinished = true;
				}
			}
		}
		
		// StopWatch
		sw.stop();
		// point 6
		if(sw.getTime() >100)
		logger.warn("identifySurveyCredentionals point 6:  %s", sw.toString());
		sw.reset();
		sw.start();
		// StopWatch
		
		
		if(null == result && !isFinished && guidType == GuidType.Collector
				&& mode == OutputMode.Answer && null != respondentId && null != multiplyResponsesExtension && !multiplyResponsesExtension.isAllowMultiplyResponses()){
			OperationResult<IAnswererSession> sessionDetailsResult = AnswerersSessionsManager.getLast(collector.getId(), respondentId, ErrorCode.AnswersAreFinished, true);
			if(sessionDetailsResult.hasError()){
				
				if(sessionDetailsResult.getError() == ErrorCode.AnswersAreFinished){
					isFinished = true;
				}
				else if(sessionDetailsResult.getError() != ErrorCode.NoResults){
					result = sessionDetailsResult.toErrorResult();
				}
			}
		}
		
		// StopWatch
		sw.stop();
		// point 7
		if(sw.getTime() >100)
		logger.warn("identifySurveyCredentionals point 7:  %s", sw.toString());
		sw.reset();
		sw.start();
		// StopWatch		
		
		ISurveysCollector surveysCollector = null;
		if(null == result && guidType == GuidType.Collector){
			
			if(collector instanceof ISurveysCollector){
				surveysCollector = (ISurveysCollector) collector;
				if(null != surveysCollector.getReferer() && !surveysCollector.getReferer().equalsIgnoreCase(ref)){
					logger.warn("Received respondent from unexpected referrer '%s'", ref);
					result = new OperationResult<IOpinionComplexData<T>>(ErrorCode.NoResults);
				}
			}
		}

		// StopWatch
		sw.stop();
		// point 8
		if(sw.getTime() >100)
		logger.warn("identifySurveyCredentionals point 8:  %s", sw.toString());
		sw.reset();
		sw.start();
		// StopWatch
		
		if (null == result && !isFinished && includeSession){
			if (null != multiplyResponsesExtension && null != expectedPassword && !(session.isOpinionFinished() && !multiplyResponsesExtension.isAllowMultiplyResponses() && null != respondentId)) {
				if (expectedPassword.equals(actualPassword)) {
					try {
						logger.info("Update password in session");
						session.setPasswordRestrictionMarker();
						isSaveSession = true;
					} catch (JSONException e) {
						UUID errorId = logger
								.error(e,
										"identifySurveyCredentionals() : Unable to set PasswordRestrictionMarker.");
						result = new OperationResult<IOpinionComplexData<T>>(
								ErrorCode.GeneralError, errorId, e.toString());
					}
				} else if (!session.havePasswordRestrictionMarker()) {
					passwordRequired = true;
				}
			}
		}

		// StopWatch
		sw.stop();
		// point 9
		if(sw.getTime() >100)
		logger.warn("identifySurveyCredentionals point 9:  %s", sw.toString());
		sw.reset();
		sw.start();
		// StopWatch
		
		// Validations
		//
		
		if (null == result && !isFinished && passwordRequired && preventPasswordRestriction) {
			logger.warn("getControls() : Invalid password");
			result = new OperationResult<IOpinionComplexData<T>>(
					ErrorCode.InvalidPassword);
		}

		Long sessionsBalance = null;
		if (null == result && !isFinished && guidType == GuidType.Collector
				&& mode == OutputMode.Answer && includeSession) {
			if(!session.getIsClientInfoReceived() || null != input.optJSONObject("browser") || (!input.isNull("responseTypeId") && JSONHelper.optInt(input, "responseTypeId") == ResponseType.FinishOpinion.getValue())){
				
				// Get AccountServicePackage
				if (null == result) {
					OperationResult<IOpinionAccount> accountServicePackageResult = AccountsManager
							.getOpinionAccount(account);
					if (accountServicePackageResult.hasError()) {
						result = accountServicePackageResult.toErrorResult();
					} else {
						try {
							accountServicePackage = accountServicePackageResult
									.getValue();
						} catch (Exception e) {
							UUID errorId = logger
									.error(
											e,
											"identifySurveyCredentionals() : Failed to get  value from getAccountServicePackageByProductId");
							result = new OperationResult<IOpinionComplexData<T>>(
									ErrorCode.GeneralError, errorId);
						}
					}
				}
			
				// StopWatch
				sw.stop();
				// point 10
				if(sw.getTime() >100)
				logger.warn("identifySurveyCredentionals point 10:  %s", sw.toString());
				sw.reset();
				sw.start();
				// StopWatch
				
				// Get settings
				if (null == result) {
					OperationResult<IServicePackageSettings> settingsResult = ServicePackageSettingsEntity
							.getServicePackageSettings(account.getServicePackageId());
					if (settingsResult.hasError()) {
						result = settingsResult.toErrorResult();
					} else {
						try {
						} catch (Exception e) {
							UUID errorId = logger
									.error(
											e,
											"identifySurveyCredentionals() : Failed to get  value from getServicePackageType");
							result = new OperationResult<IOpinionComplexData<T>>(
									ErrorCode.GeneralError, errorId);
						}
					}
				}
				
				// StopWatch
				sw.stop();
				// point 11
				if(sw.getTime() >100)
				logger.warn("identifySurveyCredentionals point 11:  %s", sw.toString());
				sw.reset();
				sw.start();
				// StopWatch
				
				// Validate account sessions balance
				if (null == result
						&& null != accountServicePackage
								.getSessionsBalance()) {
					
					sessionsBalance = accountServicePackage
					.getSessionsBalance();
					if (sessionsBalance <= 0) {
						
						if(logger.IsDebugEnabled()){
							logger
									.debug(
											"Opinion owner doesn't have avalable sessions on his balance. accountType: '%s', sessionsBalance: '%s', opinionId: '%s'",
											accountServicePackage
													.getServicePackageId(),
											accountServicePackage
													.getSessionsBalance(), opinion
													.getId());
						}
					}
				}
				//
				// End Validations
			}
		}

		// StopWatch
		sw.stop();
		// point 12
		if(sw.getTime() >100)
		logger.warn("identifySurveyCredentionals point 12:  %s", sw.toString());
		// StopWatch		
		
		if (null == result && !isFinished && includeSession) {
			OperationResult<IAnswererSession> sessionResult = AnswerersSessionsManager.getAnswererSession(null, session.getSessionId(), null);
			
			if(sessionResult.hasValue() && null != sessionResult.getValue().getFinishDate()){
				session.setFinishDate(new Date());
				isSaveSession = true;
				isFinished = true;
			}
		}
		
		if(isSaveSession){
			session.save();
		}
		
		if (null == result) {
			return generateComplexData(guid, guidType, mode, passwordRequired,
					collector, opinion, session, currentProduct,
					accountServicePackage, account, sessionsBalance, isFinished,
					answererSessionKey, respondentId);
		} else {
			return result;
		}
	}

	private static String createAnswererSessionKey(final String guid,
			String respondentId, String targetUrl) {
		
		//HashFunction hf = Hashing.md5();
		HashFunction hf = Hashing.murmur3_32();
		 Hasher hasher = hf.newHasher()
		       .putString(guid, Charsets.US_ASCII);
		 if(null != targetUrl){
			 hasher.putString(targetUrl, Charsets.US_ASCII);
		 }
		 
		 if(null != respondentId){
		 	hasher.putString(respondentId, Charsets.UTF_8);
		 }
		 HashCode hc = hasher.hash();
		
		return hc.toString();
		
		//return Integer.toHexString(ArrayUtils.hashCode(new String[] { guid, targetUrl, respondentId }));
		//return Integer.toHexString(HashCodeBuilder.reflectionHashCode(guid + (null == respondentId ? "" : respondentId)));
	}

	private <T extends IOpinion> OperationResult<IOpinionComplexData<T>> generateComplexData(
			final String guid, final GuidType guidType, final OutputMode mode,
			 final boolean passwordRequired, final ICollector collector, final T opinion,
			 final IHttpAnswererSession session,
			 final IProduct currentProduct, final IOpinionAccount accountServicePackage,
			 final IAccount account, final Long sessionsBalance, final boolean isFinished,
			 final String answererSessionKey, final String respondentId) {
		// Prepare return data
		return new OperationResult<IOpinionComplexData<T>>(
				new IOpinionComplexData<T>() {

					@Override
					public T getOpinion() {
						return opinion;
					}

					@Override
					public GuidType getGuidType() {
						return guidType;
					}

					@Override
					public ICollector getCollector() {
						return collector;
					}

					@Override
					public String getGuid() {
						return guid;
					}

					@Override
					public IHttpAnswererSession getSession() {
						return session;
					}

					@Override
					public boolean isOpen() {
						return guidType == GuidType.Draft
								|| mode == OutputMode.View
								|| collector.getCollectorStatus() == CollectorStatus.Open
								|| collector.getCollectorStatus() == CollectorStatus.Verify;
					}

					@Override
					public OutputMode getMode() {
						return mode;
					}

					@Override
					public Long getResponsesBallance(Long defaultValue) {
						return null == sessionsBalance ? defaultValue
								: sessionsBalance;
					}

					@Override
					public IOpinionAccount getAccountServicePackage() {
						return accountServicePackage;

					}

					@Override
					public boolean isClientInfoReceived() {
						return session.getIsClientInfoReceived();
					}

					@Override
					public boolean isPasswordRequired() {
						return passwordRequired;
					}

					@Override
					public IAccount getAccount() {
						return account;
					}

					@Override
					public IProduct getProduct() {
						return currentProduct;
					}
					
					@Override
					public boolean isFinished() {
						return isFinished;
					}

					@Override
					public String getAnswererSessionKey() {
						return answererSessionKey;
					}
					
					@Override
					public String getRespondentExternalId() {
						return respondentId;
					}
				});
	}
}
