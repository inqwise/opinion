package com.inqwise.opinion.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.Convert;
import com.inqwise.opinion.infrastructure.systemFramework.DateConverter;
import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;
import com.inqwise.opinion.library.common.accounts.IAccount;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.common.parameters.AccessValue;
import com.inqwise.opinion.library.common.parameters.IAccess;
import com.inqwise.opinion.library.common.parameters.IVariableSet;
import com.inqwise.opinion.library.common.parameters.PermissionsKeys;
import com.inqwise.opinion.library.entities.BaseEntity;
import com.inqwise.opinion.library.entities.accounts.AccountEntity;
import com.inqwise.opinion.library.systemFramework.ApplicationConfiguration;
import com.inqwise.opinion.common.opinions.FinishBehaviourType;
import com.inqwise.opinion.common.opinions.IOpinion;
import com.inqwise.opinion.common.opinions.OpinionType;
import com.inqwise.opinion.dao.OpinionsDataAccess;

public abstract class OpinionEntity extends BaseEntity implements IOpinion {

	protected final static String DEFAULT_COPY_SUFFIX = " Copy";
	private Long id;
	private String name;
	private Long accountId;
	private Integer themeId;
	private String description;
	private String translationName;
	private Long translationId;
	private Date createDate;
	private Date modifyDate;
	private boolean isRtl;
	private String guid;
	private String culture;
	private Integer version;
	private Long lastModifyUserId;
	private Boolean isHighlightRequiredQuestions;
	private FinishBehaviourType finishBehaviourType;
	private HashMap<String, IVariableSet> permissions;
	private String title;
	private boolean hidePoweredBy;
	private String requiredQuestionErrorMessage;
	
	public HashMap<String, IVariableSet> getPermissions() {
		return permissions;
	}

	static ApplicationLog logger = ApplicationLog.getLogger(OpinionEntity.class);
	
	public OpinionEntity(ResultSet reader, HashMap<String, IVariableSet> permissions) throws SQLException {
		this.permissions = permissions;
		
		setId(Long.valueOf(reader.getLong(ResultSetNames.OPINION_ID)));
		setName(reader.getString(ResultSetNames.NAME));
		setTitle(reader.getString(ResultSetNames.TITLE));
		setThemeId(ResultSetHelper.optInt(reader, ResultSetNames.THEME_ID, 1));
		setDescription(reader.getString(ResultSetNames.DESCRIPTION));
		setTranslationName(reader.getString(ResultSetNames.TRANSLATION_NAME));
		setCreateDate(Convert.toDate(reader.getTimestamp(ResultSetNames.CREATE_DATE)));
		setModifyDate(Convert.toDate(reader.getTimestamp(ResultSetNames.MODIFY_DATE)));
		setTranslationId(reader.getLong(ResultSetNames.TRANSLATION_ID));
		setAccountId(ResultSetHelper.optLong(reader, ResultSetNames.ACCOUNT_ID));
		setGuid(reader.getString(ResultSetNames.GUID));
		setRtl(reader.getBoolean(ResultSetNames.IS_RTL));
		setCulture(ResultSetHelper.optString(reader, ResultSetNames.CULTURE));
		setLastModifyUserId(ResultSetHelper.optLong(reader, ResultSetNames.MODIFY_USER_ID));
		setVersion(ResultSetHelper.optInt(reader, ResultSetNames.VERSION));
		setIsHighlightRequiredQuestions(ResultSetHelper.optBool(reader, ResultSetNames.IS_HIGHLIGHT_REQUIRED_QUESTIONS, false));
		setFinishBehaviourType(FinishBehaviourType.fromInt(ResultSetHelper.optInt(reader, ResultSetNames.FINISH_BEHAVIOUR_TYPE_ID)));
		setHidePoweredBy(ResultSetHelper.optBool(reader, ResultSetNames.HIDE_POWERED_BY, false));
		setRequiredQuestionErrorMessage(ResultSetHelper.optString(reader, ResultSetNames.REQUIRED_QUESTION_ERROR_MESSAGE));
		
	}

	protected OpinionEntity() {
		
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setThemeId(Integer themeId) {
		this.themeId = themeId;
	}

	public Integer getThemeId() {
		return themeId;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setTranslationName(String translationName) {
		this.translationName = translationName;
	}

	public String getTranslationName() {
		return translationName;
	}

	public void setCreateDate(Date insertDate) {
		this.createDate = insertDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setTranslationId(Long translationId) {
		this.translationId = translationId;
	}

	public Long getTranslationId() {
		return translationId;
	}
	
	@Override
	public abstract OperationResult<Long> copyTo(Long translationId, String translationName,
			long accountId, String name, String actionGuid, long userId);
	
	public boolean isRtl() {
		return isRtl;
	}

	public void setRtl(boolean isRtl) {
		this.isRtl = isRtl;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getGuid() {
		return guid;
	}
	
	public static BaseOperationResult delete(Long opinionId, Long accountId, long userId){
		BaseOperationResult result = null;
		try {
			result = OpinionsDataAccess.deleteOpinion(opinionId, accountId, userId);
		} catch (DAOException e) {
			UUID errorId = logger.error(e, "createSurvey() : Unexpected error occured");
			result = new BaseOperationResult(ErrorCode.GeneralError, errorId, e.toString());
		}
		return result;
	}
	
	public static BaseOperationResult changeOpinionName(Long opinionId,
			Long accountId, String name, String title, String description, String actionGuid, Long translationId, long userId) {
		BaseOperationResult result = null;
		try {
			result = OpinionsDataAccess.setOpinionName(opinionId, accountId, name, title, description, actionGuid, translationId, userId);
		} catch (DAOException e) {
			UUID errorId = logger.error(e, "changeOpinionName() : Unexpected error occured");
			result = new BaseOperationResult(ErrorCode.GeneralError, errorId, e.toString());
		}
		return result;
	}

	public static BaseOperationResult changeTheme(Long opinionId, int themeId, Long accountId, String actionGuid,
			long userId) {
		BaseOperationResult result = null;
		try {
			result = OpinionsDataAccess.setThemeId(opinionId, accountId, themeId, actionGuid, userId);
		} catch (DAOException e) {
			UUID errorId = logger.error(e, "changeTheme() : Unexpected error occured");
			result = new BaseOperationResult(ErrorCode.GeneralError, errorId, e.toString());
		}
		return result;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Long getAccountId() {
		return accountId;
	}
	
	public IAccount getAccount(){
		IAccount result = null;
		if(null == getAccountId()){
			logger.error("getAccount : no account id exist for opinion '%s'", getId());
		} else {
			
			OperationResult<IAccount> accountResult = AccountEntity.getAccount(getAccountId());
			if(accountResult.hasError()){
				throw new NullPointerException(accountResult.toString());
			} else {
				result = accountResult.getValue();
			}
		}
		return result;
	}
	
	public void setCulture(String culture) {
		this.culture = culture;
	}

	public String getCulture() {
		return culture;
	}

	public void setLastModifyUserId(Long lastModifyUserId) {
		this.lastModifyUserId = lastModifyUserId;
	}

	public Long getLastModifyUserId() {
		return lastModifyUserId;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getVersion() {
		return version;
	}

	@Override
	public abstract OpinionType getOpinionType();

	public static OpinionEntity identifyOpinion(ResultSet reader, HashMap<String, IVariableSet> permissions) throws SQLException {
		OpinionEntity result;
		OpinionType opinionType = OpinionType.fromInt(reader.getInt("opinion_type_id"));
		switch (opinionType) {
		case Poll:
			result = new PollEntity(reader, permissions);
			break;
		case Survey:
			result = new SurveyEntity(reader, permissions);
			break;
		default:
			throw new Error(String.format("identifyOpinion: opinion type '%s' not implemented", opinionType));
		}
		return result;
	}
	

	public void setIsHighlightRequiredQuestions(Boolean isHighlightRequiredQuestions) {
		this.isHighlightRequiredQuestions = isHighlightRequiredQuestions;
	}

	public Boolean getIsHighlightRequiredQuestions() {
		return isHighlightRequiredQuestions;
	}
	
	@Override
	public JSONObject getSettingsJson() throws JSONException{
		JSONObject result = new JSONObject();
		result.put(JsonNames.HIGHLIGHT_REQUIRED_QUESTIONS, getIsHighlightRequiredQuestions());
		result.put(JsonNames.IS_RTL, isRtl());
		result.put(JsonNames.FINISH_BEHAVIOUR_TYPE_ID, getFinishBehaviourType().getValue());
		result.put(JsonNames.HIDE_POWERED_BY, isHidePoweredBy());
		return result;
	}
	
	@Override
	public String getPreviewUrl(boolean secure) {
		return String.format(IOpinion.PREVIEW_URL_FORMAT, (secure ? ApplicationConfiguration.Opinion.Collector.getSecureUrl(): ApplicationConfiguration.Opinion.Collector.getUrl()), getOpinionType().getValue(), getGuid());
	}
	
	@Override
	public JSONObject getJson(Integer timezoneOffset) throws JSONException {
		DateFormat formatter = new SimpleDateFormat(
				"MMM dd, yyyy HH:mm:ss");
	
		JSONObject result = getShortDetailsJson();
		result
			.put(JsonNames.CREATE_DATE, formatter.format(DateConverter.addDateOffset(getCreateDate(), timezoneOffset)))
			.put(JsonNames.MODIFY_DATE, formatter.format(DateConverter.addDateOffset(getModifyDate(), timezoneOffset)))
			.put(JsonNames.GUID, getGuid())
			.put(JsonNames.THEME_ID, getThemeId())
			.put(JsonNames.HIGHLIGHT_REQUIRED_QUESTIONS, getIsHighlightRequiredQuestions())
			;

		return result;
	}
	
	@Override
	public JSONObject getShortDetailsJson() throws JSONException {
		
		JSONObject result = new JSONObject();
		result
			.put(JsonNames.TITLE, getTitle())
			.put(JsonNames.NAME, getName())
			.put(JsonNames.IS_RTL, isRtl())
			;

		return result;
	}
	
	protected abstract JSONObject getMessagesJson() throws JSONException;

	public FinishBehaviourType getFinishBehaviourType() {
		return finishBehaviourType;
	}

	public void setFinishBehaviourType(FinishBehaviourType finishBehaviourType) {
		
		if(null != permissions && finishBehaviourType != FinishBehaviourType.Void){
			IAccess finishBehaviourAccess = permissions.get(PermissionsKeys.CustomFinishBehaviour.getValue()).getActual();
			if(finishBehaviourAccess.getValue() == AccessValue.Denied){
				finishBehaviourType = FinishBehaviourType.Void;
			}
		}
		
		this.finishBehaviourType = finishBehaviourType;
	}
	
	public JSONObject getExportJson() throws JSONException{
		JSONObject output = new JSONObject();
		output.put(JsonNames.NAME, getName());
		output.put(JsonNames.TITLE, getTitle());
		output.put(JsonNames.IS_RTL, isRtl());
		output.put(JsonNames.DESCRIPTION, getDescription());
		//output.put(JsonNames.CULTURE, getCulture());
		output.put(JsonNames.HIGHLIGHT_REQUIRED_QUESTIONS, getIsHighlightRequiredQuestions());
		output.put(JsonNames.FINISH_BEHAVIOUR_TYPE_ID, getFinishBehaviourType().getValue());
		
		/*
		OperationResult<ITheme> themeResult = ThemesManager.get(getThemeId(), null);
		if(themeResult.hasValue()){
			ITheme theme = themeResult.getValue();
			if(theme.isTemplate()){
				output.put(JsonNames.THEME_ID, getThemeId());
			} else {
				output.put(JsonNames.THEME, theme.getExportJson());
			}
		}*/
		return output;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String getTitle() {
		return title;
	}

	public void setHidePoweredBy(boolean hidePoweredBy) {
		if(null != permissions && hidePoweredBy){
			IAccess finishBehaviourAccess = permissions.get(PermissionsKeys.CustomFinishBehaviour.getValue()).getActual();
			if(finishBehaviourAccess.getValue() == AccessValue.Denied){
				hidePoweredBy = false;
			}
		}
		this.hidePoweredBy = hidePoweredBy;
	}
	
	@Override
	public boolean isHidePoweredBy() {
		return hidePoweredBy;
	}
	
	public void setRequiredQuestionErrorMessage(String requiredQuestionErrorMessage) {
		this.requiredQuestionErrorMessage = requiredQuestionErrorMessage;
	}
	
	@Override
	public String getRequiredQuestionErrorMessage() {
		return requiredQuestionErrorMessage;
	}
	
}
