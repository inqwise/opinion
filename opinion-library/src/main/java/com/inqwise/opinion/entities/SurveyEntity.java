package com.inqwise.opinion.opinion.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONML;
import org.json.JSONObject;

import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.dao.SqlProcParameter;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.Convert;
import com.inqwise.opinion.infrastructure.systemFramework.DateConverter;
import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;
import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.common.parameters.IVariableSet;
import com.inqwise.opinion.library.systemFramework.ApplicationConfiguration;
import com.inqwise.opinion.opinion.actions.opinions.ICreateSurveyRequest;
import com.inqwise.opinion.opinion.actions.opinions.IModifySurveyRequest;
import com.inqwise.opinion.opinion.actions.opinions.OpinionsActionsFactory;
import com.inqwise.opinion.opinion.common.SurveyStatistics;
import com.inqwise.opinion.opinion.common.collectors.ICollector;
import com.inqwise.opinion.opinion.common.opinions.FinishBehaviourType;
import com.inqwise.opinion.opinion.common.opinions.IOpinion;
import com.inqwise.opinion.opinion.common.opinions.IPoll;
import com.inqwise.opinion.opinion.common.opinions.ISurvey;
import com.inqwise.opinion.opinion.common.opinions.LabelPlacement;
import com.inqwise.opinion.opinion.common.opinions.OpinionType;
import com.inqwise.opinion.opinion.common.opinions.IOpinion.JsonNames;
import com.inqwise.opinion.opinion.common.sheet.ISheet;
import com.inqwise.opinion.opinion.common.sheet.ISheetRequest;
import com.inqwise.opinion.opinion.dao.OpinionsDataAccess;
import com.inqwise.opinion.opinion.dao.Surveys;
import com.inqwise.opinion.opinion.managers.SheetsManager;

public class SurveyEntity extends OpinionEntity implements ISurvey {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7568687381550305055L;
	public static ApplicationLog logger = ApplicationLog.getLogger(SurveyEntity.class);
	private String startMessage;
	private String finishMessage;
	private Boolean showProgressBar;
	private String redirectUrl;
	private String startButtonTitle;
	private String finishButtonTitle;
	private String previousButtonTitle;
	private String nextButtonTitle;
	private Boolean usePageNumbering;
	private Boolean	useQuestionNumbering;
	private String logoUrl;
	private LabelPlacement labelPlacement;
	private Boolean showPaging;
	private String alreadyCompletedMessage;
	
	public SurveyEntity(ResultSet reader, HashMap<String, IVariableSet> permissions) throws SQLException {
		super(reader, permissions);
		
		setStartMessage(reader.getString(ResultSetNames.START_MESSAGE));
		setFinishMessage(reader.getString(ResultSetNames.FINISH_MESSAGE));
		setShowProgressBar((null == reader.getObject(ResultSetNames.SHOW_PROGRESS_BAR)) ? false : reader.getBoolean(ResultSetNames.SHOW_PROGRESS_BAR));
		setRedirectUrl(reader.getString(ResultSetNames.REDIRECT_URL));
		setStartButtonTitle(ResultSetHelper.optString(reader, ResultSetNames.START_BUTTON_TITLE));
		setFinishButtonTitle(ResultSetHelper.optString(reader, ResultSetNames.FINISH_BUTTON_TITLE));
		setPreviousButtonTitle(ResultSetHelper.optString(reader, ResultSetNames.PREVIOUS_BUTTON_TITLE));
		setNextButtonTitle(ResultSetHelper.optString(reader, ResultSetNames.NEXT_BUTTON_TITLE));
		setUsePageNumbering(ResultSetHelper.optBool(reader, ResultSetNames.USE_PAGE_NUMBERING, false));
		setUseQuestionNumbering(ResultSetHelper.optBool(reader, ResultSetNames.USE_QUESTION_NUMBERING, false));
		setLogoUrl(ResultSetHelper.optString(reader, ResultSetNames.LOGO_URL));
		setLabelPlacement(LabelPlacement.fromInt(ResultSetHelper.optInt(reader, ResultSetNames.LABEL_PLACEMENT_ID)));
		setShowPaging(ResultSetHelper.optBool(reader, ResultSetNames.SHOW_PAGING));
		setAlreadyCompletedMessage(ResultSetHelper.optString(reader, ResultSetNames.ALREADY_COMPLETED_MESSAGE));
	}

	public void setStartMessage(String startMessage) {
		this.startMessage = startMessage;
	}

	@Override
	public String getStartMessage() {
		return startMessage;
	}

	public void setFinishMessage(String finishMessage) {
		this.finishMessage = finishMessage;
	}

	@Override
	public String getFinishMessage() {
		return finishMessage;
	}

	@Override
	public BaseOperationResult createSheet(final Integer pageNumber, final Long accountId,
											final String title, final String description,
											final String actionGuid, final long userId) {
		final OpinionEntity s = this;
		ISheetRequest request = new ISheetRequest() {
			
			@Override
			public Long getTranslationId() {
				return s.getTransactionId();
			}
			
			@Override
			public String getTitle() {
				return title;
			}
			
			@Override
			public Integer getPageNumber() {
				return pageNumber;
			}
			
			@Override
			public Long getOpinionId() {
				return s.getId();
			}
			
			@Override
			public String getDescription() {
				return description;
			}
			
			@Override
			public String getActionGuid() {
				return actionGuid;
			}
			
			@Override
			public Long getAccountId() {
				return accountId;
			}

			@Override
			public long getUserId() {
				return userId;
			}
		};
		return SheetEntity.createSheet(request);
	}

	@Override
	public BaseOperationResult createSheetInTheEnd(Long accountId, String title, String description, long userId) {
		return createSheet(null, accountId, title, description, null, userId);
	}

	@Override
	public OperationResult<List<ISheet>> getSheets() {
		return SheetEntity.getSheets(getId(), getTranslationId(), null);
	}

	@Override
	public BaseOperationResult getTranslations() {
		
		// TODO Auto-generated method stub
		throw new Error("Not implemented");
	}

	@Override
	public Boolean getShowProgressBar() {
		return showProgressBar;
	}

	public void setShowProgressBar(Boolean showProgressBar) {
		this.showProgressBar = showProgressBar;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	private interface ICreateCopy extends ICreateSurveyRequest, ICreateSurveyRequest.IRequestExtension {
	}
	
	@Override
	public OperationResult<Long> copyTo(final Long translationId,
									final String translationName, final long accountId,
									final String name, final String actionGuid,
									final long userId){

		final SurveyEntity s = this;
		ICreateCopy request = new ICreateCopy() {
			
			@Override
			public String getTranslationName() {
				return null == translationName ? s.getTranslationName() : translationName;
			}
			
			@Override
			public Long getTranslationId() {
				return null == translationId ? s.getTranslationId() : translationId;
			}
			
			@Override
			public String getTitle() {
				return s.getTitle();
			}
			
			@Override
			public String getStartMessage() {
				return s.getStartMessage();
			}
			
			@Override
			public Integer getThemeId() {
				return s.getThemeId();
			}
			
			@Override
			public long getAccountId() {
				return accountId;
			}
			
			@Override
			public String getName() {
				return null == name ? s.getName() + DEFAULT_COPY_SUFFIX : name;
			}
			
			@Override
			public String getFinishMessage() {
				return s.getFinishMessage();
			}
			
			@Override
			public String getDescription() {
				return s.getDescription();
			}
			
			@Override
			public String getCulture() {
				return s.getCulture();
			}

			@Override
			public String getActionGuid() {
				return actionGuid;
			}

			@Override
			public String getFinishButtonTitle() {
				return s.getFinishButtonTitle();
			}

			@Override
			public Boolean getIsHighlightRequestedQuestions() {
				return s.getIsHighlightRequiredQuestions();
			}

			@Override
			public String getNextButtonTitle() {
				return s.getNextButtonTitle();
			}

			@Override
			public String getPreviousButtonTitle() {
				return s.getPreviousButtonTitle();
			}

			@Override
			public String getStartButtonTitle() {
				return s.getStartButtonTitle();
			}

			@Override
			public Boolean isUsePageNumbering() {
				return s.isUsePageNumbering();
			}

			@Override
			public Boolean isUseQuestionNumbering() {
				return s.isUseQuestionNumbering();
			}

			@Override
			public boolean isRtl() {
				return s.isRtl();
			}

			@Override
			public String getLogoUrl() {
				return s.getLogoUrl();
			}

			@Override
			public long getUserId() {
				return userId;
			}

			@Override
			public String getRedirectUrl() {
				return s.getRedirectUrl();
			}

			@Override
			public Integer getFinishBehaviourTypeId() {
				return s.getFinishBehaviourType().getValue();
			}

			@Override
			public boolean isCreateEmptySheet() {
				return false;
			}

			@Override
			public Integer getLabelPlacementId() {
				return s.getLabelPlacement().getValue();
			}

			@Override
			public Boolean isShowPaging() {
				return s.isShowPaging();
			}

			@Override
			public Boolean isHidePoweredBy() {
				return null;
			}

			@Override
			public String getAlreadyCompletedMessage() {
				return s.alreadyCompletedMessage;
			}
		};
		
		OperationResult<Long> result = OpinionsActionsFactory.getInstance().create(request);
		Long opinionId = null;
		
		
		List<ISheet> sheets = null;
		if(!result.hasError()){
			opinionId = result.getValue();
			OperationResult<List<ISheet>> sheetsResult = getSheets();
			if(sheetsResult.hasError() && sheetsResult.getError() != ErrorCode.NoResults){
				result = sheetsResult.toErrorResult();
			} else if (!sheetsResult.hasError()) {
				sheets = sheetsResult.getValue();
			}
		}
		
		if(!result.hasError() && null != sheets)
		{
			for(ISheet sheetInterface : sheets){
				SheetEntity sheet = (SheetEntity) sheetInterface;
				OperationResult<Long> sheetResult = sheet.copyTo(opinionId, request.getTranslationId(), null,
													sheet.getTitle(), sheet.getDescription(), actionGuid,
													userId, accountId);
				if(sheetResult.hasError()){
					result = sheetResult.toErrorResult();
					break;
				}
			}
		}
		
		return result;
	}
	
	@Override
	public JSONObject toJson() throws JSONException {
		
		JSONObject result = super.toJson();
		
		return result;
	}

	
	
	public static OperationResult<SurveyStatistics> getSurveyShortStatistics(Long opinionId){
		OperationResult<SurveyStatistics> result = null;
		try {
			result = Surveys.getSurveyShortStatistics(opinionId);
		} catch (DAOException e) {
			UUID errorId = logger.error(e, "getSurveyShortStatistics() : Unexpected eror occured");
			result = new OperationResult<SurveyStatistics>(ErrorCode.GeneralError, errorId);
		}
		return result;
	}

	public void setPreviousButtonTitle(String previousButtonTitle) {
		this.previousButtonTitle = previousButtonTitle;
	}

	public String getPreviousButtonTitle() {
		return previousButtonTitle;
	}

	public void setNextButtonTitle(String nextButtonTitle) {
		this.nextButtonTitle = nextButtonTitle;
	}

	public String getNextButtonTitle() {
		return nextButtonTitle;
	}

	public void setStartButtonTitle(String startButtonTitle) {
		this.startButtonTitle = startButtonTitle;
	}

	public String getStartButtonTitle() {
		return startButtonTitle;
	}

	public void setFinishButtonTitle(String finishButtonTitle) {
		this.finishButtonTitle = finishButtonTitle;
	}

	public String getFinishButtonTitle() {
		return finishButtonTitle;
	}

	public void setUsePageNumbering(Boolean usePageNumbering) {
		this.usePageNumbering = usePageNumbering;
	}

	public Boolean isUsePageNumbering() {
		return usePageNumbering;
	}

	public void setUseQuestionNumbering(Boolean useQuestionNumbering) {
		this.useQuestionNumbering = useQuestionNumbering;
	}

	public Boolean isUseQuestionNumbering() {
		return useQuestionNumbering;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	@Override
	public OpinionType getOpinionType() {
		return OpinionType.Survey;
	}
	
	@Override
	public JSONObject getSettingsJson() throws JSONException {
		JSONObject result = super.getSettingsJson();
		
		result.put(JsonNames.USE_CUSTOM_START_MESSAGE,	null != getStartMessage());
		result.put(JsonNames.SHOW_PROGRESS_BAR, JSONHelper.getNullable(getShowProgressBar()));
		result.put(JsonNames.USE_PAGE_NUMBERING,isUsePageNumbering());
		result.put(JsonNames.USE_QUESTION_NUMBERING,isUseQuestionNumbering());
		result.put(JsonNames.LOGO_URL, JSONHelper.getNullable(getLogoUrl()));
		result.put(JsonNames.MESSAGES, getMessagesJson());
		result.put(JsonNames.REDIRECT_URL, getRedirectUrl());
		result.put(JsonNames.LABEL_PLACEMENT_ID, getLabelPlacement().getValue());
		result.put(JsonNames.SHOW_PAGING, isShowPaging());
		result.put(JsonNames.HIDE_POWERED_BY, isHidePoweredBy());
		
		return result;
	}
	
	@Override
	protected JSONObject getMessagesJson() throws JSONException{
		JSONObject result = new JSONObject();
		
		result.put(JsonNames.START_BUTTON_TITLE, JSONHelper.getNullable(getStartButtonTitle()));
		result.put(JsonNames.FINISH_BUTTON_TITLE, JSONHelper.getNullable(getFinishButtonTitle()));
		result.put(JsonNames.PREVIOUS_BUTTON_TITLE, JSONHelper.getNullable(getPreviousButtonTitle()));
		result.put(JsonNames.NEXT_BUTTON_TITLE, JSONHelper.getNullable(getNextButtonTitle()));
		result.put(JsonNames.START_MESSAGE, JSONHelper.getNullable(getStartMessage()));
		result.put(JsonNames.FINISH_MESSAGE, JSONHelper.getNullable(getFinishMessage()));
		result.put(JsonNames.ALREADY_COMPLETED_MESSAGE, JSONHelper.getNullable(getAlreadyCompletedMessage()));
		result.put(JsonNames.REQUIRED_QUESTION_ERROR_MESSAGE, JSONHelper.getNullable(getRequiredQuestionErrorMessage()));
				
		return result;
	}
	
	private JSONObject getExportMessagesJson() throws JSONException{
		JSONObject result = new JSONObject();
		
		result.put(JsonNames.START_BUTTON_TITLE, getStartButtonTitle());
		result.put(JsonNames.FINISH_BUTTON_TITLE, getFinishButtonTitle());
		result.put(JsonNames.PREVIOUS_BUTTON_TITLE, getPreviousButtonTitle());
		result.put(JsonNames.NEXT_BUTTON_TITLE, getNextButtonTitle());
		result.put(JsonNames.START_MESSAGE, getStartMessage());
		result.put(JsonNames.FINISH_MESSAGE, getFinishMessage());
		result.put(JsonNames.ALREADY_COMPLETED_MESSAGE, getAlreadyCompletedMessage());
				
		return result;
	}
	
	@Override
	public BaseOperationResult modify(final JSONObject input, final long userId, final Long accountId)
			throws JSONException {
		
		final Boolean useCustomStartMessage = JSONHelper.optBoolean(input, JsonNames.USE_CUSTOM_START_MESSAGE);
		final boolean rtl = input.getBoolean(JsonNames.IS_RTL);
		final boolean highlightRequiredQuestions = input.getBoolean(JsonNames.HIGHLIGHT_REQUIRED_QUESTIONS);
		final boolean useQuestionNumbering = input.getBoolean(JsonNames.USE_QUESTION_NUMBERING);
		final boolean usePageNumbering = input.getBoolean(JsonNames.USE_PAGE_NUMBERING);
		
		BaseOperationResult modifyResult = OpinionsActionsFactory.getInstance().modify(new IModifySurveyRequest() {
			
			@Override
			public boolean isRtl() {
				return rtl;
			}
			
			@Override
			public boolean isHighlightRequiredQuestions() {
				return highlightRequiredQuestions;
			}
			
			@Override
			public long getUserId() {
				return userId;
			}
			
			@Override
			public Long getTranslationId() {
				return JSONHelper.optLong(input, JsonNames.TRANSLATION_ID, IOpinion.DEFAULT_TRANSLATION_ID);
			}
			
			@Override
			public long getOpinionId() {
				return getId();
			}
			
			@Override
			public Long getAccountId() {
				return accountId;
			}
			
			@Override
			public boolean isUseQuestionNumbering() {
				return useQuestionNumbering;
			}
			
			@Override
			public boolean isUsePageNumbering() {
				return usePageNumbering;
			}
			
			@Override
			public boolean isShowProgressBar() {
				return JSONHelper.optBoolean(input, JsonNames.SHOW_PROGRESS_BAR);
			}
			
			@Override
			public String getStartMessage() {
				return useCustomStartMessage ? JSONHelper.optStringTrim(input, JsonNames.START_MESSAGE) : null;
			}
			
			@Override
			public String getStartButtonTitle() {
				return JSONHelper.optString(input, JsonNames.START_BUTTON_TITLE);
			}
			
			@Override
			public String getPreviousButtonTitle() {
				return JSONHelper.optString(input, JsonNames.PREVIOUS_BUTTON_TITLE);
			}
			
			@Override
			public String getNextButtonTitle() {
				return JSONHelper.optString(input, JsonNames.NEXT_BUTTON_TITLE);
			}
			
			@Override
			public String getLogoUrl() {
				return JSONHelper.optStringTrim(input, JsonNames.LOGO_URL);
			}
			
			@Override
			public String getFinishMessage() {
				return JSONHelper.optStringTrim(input, JsonNames.FINISH_MESSAGE);
			}
			
			@Override
			public String getFinishButtonTitle() {
				return JSONHelper.optString(input, JsonNames.FINISH_BUTTON_TITLE);
			}
			
			@Override
			public String getRedirectUrl() {
				return JSONHelper.optStringTrim(input, JsonNames.REDIRECT_URL);
			}

			@Override
			public Integer getFinishBehaviourTypeId() {
				return JSONHelper.optInt(input, JsonNames.FINISH_BEHAVIOUR_TYPE_ID, FinishBehaviourType.Void.getValue());
			}

			@Override
			public Integer getLabelPlacementId() {
				return JSONHelper.optInt(input, JsonNames.LABEL_PLACEMENT_ID);
			}

			@Override
			public Boolean isShowPaging() {
				return JSONHelper.optBoolean(input, JsonNames.SHOW_PAGING);
			}

			@Override
			public Boolean isHidePoweredBy() {
				return JSONHelper.optBoolean(input, JsonNames.HIDE_POWERED_BY);
			}

			@Override
			public String getAlreadyCompletedMessage() {
				return JSONHelper.optStringTrim(input, JsonNames.ALREADY_COMPLETED_MESSAGE);
			}
			
			@Override
			public String getRequiredQuestionErrorMessage() {
				return JSONHelper.optString(input, JsonNames.REQUIRED_QUESTION_ERROR_MESSAGE);
			}
			
		});
		
		return modifyResult;
	}

	@Override
	public JSONObject getJson(Integer timezoneOffset) throws JSONException {
		JSONObject result = super.getJson(timezoneOffset);
		
		result.put(JsonNames.LOGO_URL, getLogoUrl())
		.put(JsonNames.USE_CUSTOM_START_MESSAGE, null != getStartMessage())
		.put(JsonNames.START_MESSAGE, getStartMessage())
		.put(JsonNames.USE_QUESTION_NUMBERING, isUseQuestionNumbering());
		
		result.put(JsonNames.MESSAGES, getMessagesJson());
		
		return result;
	}
	
	@Override
	public JSONObject getShortDetailsJson() throws JSONException {
		
		JSONObject output = super.getShortDetailsJson();
		output.put(JsonNames.USE_PAGE_NUMBERING, isUsePageNumbering())
		.put(JsonNames.USE_QUESTION_NUMBERING, isUseQuestionNumbering())
		.put(JsonNames.LOGO_URL, getLogoUrl())
		.put(JsonNames.IS_ENABLE_START_MESSAGE, null != getStartMessage())
		.put(JsonNames.REDIRECT_URL, getRedirectUrl());
		
		output.put(JsonNames.LABEL_PLACEMENT_ID, getLabelPlacement().getValue());
		output.put(JsonNames.SHOW_PAGING, isShowPaging());
		
		output.put(JsonNames.MESSAGES, getMessagesJson());
		
		return output;
	}
	
	@Override
	public JSONObject getExportJson() throws JSONException {
		JSONObject output = super.getExportJson();
		
		output.put(JsonNames.USE_PAGE_NUMBERING, Convert.nullIfSame(isUsePageNumbering(), true));
		output.put(JsonNames.USE_QUESTION_NUMBERING, isUseQuestionNumbering());
		output.put(JsonNames.LOGO_URL, getLogoUrl());
		output.put(JsonNames.IS_ENABLE_START_MESSAGE, null != getStartMessage());
		output.put(JsonNames.REDIRECT_URL, getRedirectUrl());
		
		output.put(JsonNames.MESSAGES, getExportMessagesJson());
		output.put(JsonNames.SHEETS, getExportJsonSheets());
		
		if(getLabelPlacement() != LabelPlacement.Top){ // default
			output.put(JsonNames.LABEL_PLACEMENT_ID, getLabelPlacement().getValue());
		}
		output.put(JsonNames.SHOW_PAGING, isShowPaging());
		
		return output;
	}

	private JSONArray getExportJsonSheets() throws JSONException {
		JSONArray output = null;
		OperationResult<List<ISheet>> sheetsResult = SheetEntity.getSheets(getId(), getTranslationId(), null);
		if(sheetsResult.hasValue()){
			List<ISheet> sheets = sheetsResult.getValue();
			output = new JSONArray();
			for (ISheet sheet : sheets) {
				output.put(sheet.getExportJson());
			}
		}
		
		return output;
	}

	public LabelPlacement getLabelPlacement() {
		return labelPlacement;
	}

	public void setLabelPlacement(LabelPlacement labelPlacement) {
		this.labelPlacement = labelPlacement;
	}

	public Boolean isShowPaging() {
		return showPaging;
	}

	public void setShowPaging(Boolean showPaging) {
		this.showPaging = showPaging;
	}

	@Override
	public String getAlreadyCompletedMessage() {
		return alreadyCompletedMessage;
	}

	public void setAlreadyCompletedMessage(String alreadyCompletedMessage) {
		this.alreadyCompletedMessage = alreadyCompletedMessage;
	}
}
