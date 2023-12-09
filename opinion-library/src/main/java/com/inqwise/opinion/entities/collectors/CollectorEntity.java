package com.inqwise.opinion.opinion.entities.collectors;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.dao.IResultSetCallback;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;
import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;
import com.inqwise.opinion.library.common.accounts.IAccount;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.entities.BaseEntity;
import com.inqwise.opinion.library.entities.accounts.AccountEntity;
import com.inqwise.opinion.library.systemFramework.ApplicationConfiguration;
import com.inqwise.opinion.opinion.common.collectors.CollectorSourceType;
import com.inqwise.opinion.opinion.common.collectors.CollectorStatus;
import com.inqwise.opinion.opinion.common.collectors.ICollector;
import com.inqwise.opinion.opinion.common.collectors.IDeletedCollectorDetails;
import com.inqwise.opinion.opinion.common.opinions.IOpinion;
import com.inqwise.opinion.opinion.common.opinions.OpinionType;
import com.inqwise.opinion.opinion.dao.CollectorsDataAccess;
import com.inqwise.opinion.opinion.managers.OpinionsManager;

public abstract class CollectorEntity extends BaseEntity implements ICollector {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8551751940803807475L;

	private static final ApplicationLog logger = ApplicationLog.getLogger(CollectorEntity.class);
	
	public static final String COLLECT_URL_FORMAT = "%s/c/%s/%s/1";

	private Long id;
	private String name;
	private Long opinionId;
	private Date endDate;
	private Boolean allowMultiplyResponses;
	private Long responseQuota;
	private Long translationId;
	private String uuid;
	private CollectorStatus collectorStatus;
	private Long countOfFinishedOpinions;
	private Long countOfStartedOpinions;
	private Boolean enableFinishedSessionEmailNotification;
	private OpinionType opinionType;
	private String password;
	private Boolean hidePassword;
	private String closeMessage;
	private Boolean enableRssUpdates;
	private Integer collectorSourceId;
	private CollectorSourceType collectorSourceType;
	private Date dateOfLastStartedOpinion;
	private Date dateOfLastFinishedOpinion;
	private boolean shortStatisticsHasBeenLoaded;
	
	public CollectorEntity(ResultSet reader)
			throws SQLException {
		setId(ResultSetHelper.optLong(reader, ResultSetNames.COLLECTOR_ID));
		setName(ResultSetHelper.optString(reader, ResultSetNames.COLLECTOR_NAME));
		setOpinionId(ResultSetHelper.optLong(reader, ResultSetNames.OPINION_ID));
		setEndDate(ResultSetHelper.optDate(reader, ResultSetNames.END_DATE));
		setAllowMultiplyResponses(ResultSetHelper.optBool(reader, ResultSetNames.IS_ALLOW_MULTIPLY_RESPONSES, false));
		setResponseQuota(ResultSetHelper.optLong(reader, ResultSetNames.RESPONSE_QUOTA));
		setTranslationId(ResultSetHelper.optLong(reader, ResultSetNames.TRANSLATION_ID));
		setUuid(ResultSetHelper.optString(reader, ResultSetNames.COLLECTOR_UUID));
		setCollectorStatus(CollectorStatus.fromInt(ResultSetHelper.optInt(reader, ResultSetNames.COLLECTOR_STATUS_ID)));
		setEnableFinishedSessionEmailNotification(ResultSetHelper.optBool(reader, ResultSetNames.IS_ENABLE_FINISHED_SESSION_EMAIL_NOTIFICATION));
		setOpinionType(OpinionType.fromInt(reader.getInt(ResultSetNames.OPINION_TYPE_ID)));
		setPassword(ResultSetHelper.optString(reader, ResultSetNames.PASSWORD));
		setIsHidePassword(ResultSetHelper.optBool(reader, ResultSetNames.IS_HIDE_PASSWORD, false));
		setCloseMessage(ResultSetHelper.optString(reader, ResultSetNames.CLOSE_MESSAGE));
		setEnableRssUpdates(ResultSetHelper.optBool(reader, ResultSetNames.IS_ENABLE_RSS_UPDATES));
		setCollectorSourceId(ResultSetHelper.optInt(reader, ResultSetNames.COLLECTOR_SOURCE_ID));
		setCollectorSourceType(CollectorSourceType.fromInt(ResultSetHelper.optInt(reader, ResultSetNames.COLLECTOR_SOURCE_TYPE_ID)));
	}
	
	public static OperationResult<IDeletedCollectorDetails> delete(Long id, Long accountId, long userId) {
		
		try {
			return CollectorsDataAccess.deleteCollector(id, accountId, userId);
		} catch (Exception e) {
			UUID errorId = logger.error(e, "delete() : Unexpected error occured");
			return new OperationResult<IDeletedCollectorDetails>(ErrorCode.GeneralError, errorId, e.toString());
		}
	}

	
	public BaseOperationResult delete(Long accountId, long userId) {
		return delete(getId(), accountId, userId);
	}

	public Date getEndDate() {
		return endDate;
	}

	public Long getId() {
		return id;
	}

	public Boolean isAllowMultiplyResponses() {
		return allowMultiplyResponses;
	}

	public String getName() {
		return name;
	}

	public Long getOpinionId() {
		return opinionId;
	}

	public Long getResponseQuota() {
		return responseQuota;
	}

	public Long getTranslationId() {
		return translationId;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setAllowMultiplyResponses(Boolean isAllowMultiplyResponses) {
		this.allowMultiplyResponses = isAllowMultiplyResponses;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOpinionId(Long opinionId) {
		this.opinionId = opinionId;
	}

	public void setResponseQuota(Long responseQuota) {
		this.responseQuota = responseQuota;
	}

	public void setTranslationId(Long translationId) {
		this.translationId = translationId;
	}

	public Boolean isHidePassword() {
		return hidePassword;
	}

	public String getPassword() {
		return password;
	}

	public void setIsHidePassword(Boolean isEnablePassword) {
		this.hidePassword = isEnablePassword;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setCloseMessage(String closeMessage) {
		this.closeMessage = closeMessage;
	}
	
	public String getCloseMessage() {
		return closeMessage;
	}

	public JSONObject toJson(Integer timezoneOffset) throws JSONException {
		JSONObject jo = new JSONObject()
		.put(JsonNames.COLLECTOR_ID, JSONHelper.getNullable(getId()))
		.put(JsonNames.NAME, JSONHelper.getNullable(getName()))
		.put(JsonNames.OPINION_ID, JSONHelper.getNullable(getOpinionId()))
		.put(JsonNames.TRANSLATION_ID, JSONHelper.getNullable(getTranslationId()))
		.put(JsonNames.IS_ENABLE_FINISHED_SESSION_EMAIL_NOTIFICATION, JSONHelper.getNullable(isEnableFinishedSessionEmailNotification()))
		.put(JsonNames.STATUS_ID, getCollectorStatus().getValue())
		.put(JsonNames.COUNT_OF_COMPLETED, getCountOfFinishedOpinions())
		.put(JsonNames.COLLECT_URL, getCollectUrl());
		
		return jo;
	}

	public static BaseOperationResult updateStatus(Long collectorId, int statusId, Long accountId,
			String closeMessage, UUID newCollectorUuid, Long userId) {
				BaseOperationResult result;
				try {
					result = CollectorsDataAccess.updateCollectorStatus(collectorId, statusId, accountId, closeMessage, newCollectorUuid, userId);
				} catch (DAOException e) {
					UUID errorId = logger.error(e, "updateStatus() : Unexpected error occured");
					result = new BaseOperationResult(ErrorCode.GeneralError, errorId, e.getMessage());
				}
				
				return result;
			}

	public static BaseOperationResult changeOpinionName(Long collectorId, Long accountId,
			String collectorName, String actionGuid, long userId) {
				BaseOperationResult result;
				try {
					result = CollectorsDataAccess.updateCollectorName(collectorId, accountId, collectorName, actionGuid, userId);
				} catch (DAOException e) {
					UUID errorId = logger.error(e, "changeOpinionName() : Unexpected error occured");
					result = new BaseOperationResult(ErrorCode.GeneralError, errorId, e.getMessage());
				}
				
				return result;
			}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public void setCollectorStatus(CollectorStatus collectorStatus) {
		this.collectorStatus = collectorStatus;
	}

	public CollectorStatus getCollectorStatus() {
		return collectorStatus;
	}

	private void setCountOfFinishedOpinions(Long countOfFinishedOpinions) {
		this.countOfFinishedOpinions = countOfFinishedOpinions;
	}

	public Long getCountOfFinishedOpinions() {
		if(!shortStatisticsHasBeenLoaded) fillShortStatistics();
		return countOfFinishedOpinions;
	}

	private void setCountOfStartedOpinions(Long countOfStartedOpinions) {
		this.countOfStartedOpinions = countOfStartedOpinions;
	}

	public Long getCountOfStartedOpinions() {
		if(!shortStatisticsHasBeenLoaded) fillShortStatistics();
		return countOfStartedOpinions;
	}

	public CollectorEntity() {
		super();
	}

	public void setEnableFinishedSessionEmailNotification(Boolean enableFinishedSessionEmailNotification) {
		this.enableFinishedSessionEmailNotification = enableFinishedSessionEmailNotification;
	}

	public Boolean isEnableFinishedSessionEmailNotification() {
		return enableFinishedSessionEmailNotification;
	}

	public String getCollectUrl() {
		return String.format(COLLECT_URL_FORMAT, ApplicationConfiguration.Opinion.Collector.getUrl(), getOpinionType().getValue(), getUuid());
	}

	public OperationResult<IOpinion> getOpinion(Long accountId) {
		return OpinionsManager.getOpinion(getOpinionId(), getTransactionId(), accountId, null);
	}

	public BaseOperationResult changeStatus(CollectorStatus status, Long userId) {
		BaseOperationResult result = this;
		
		try{
			validate(null != status, ErrorCode.ArgumentMandatory, "status is mandatory");
			if(!hasError()){
				result = CollectorsDataAccess.updateCollectorStatus(getId(), status.getValue(), null, null, null, userId);
			}
			
			if(!result.hasError()){
				setCollectorStatus(status);
			}
		} catch (DAOException e) {
			UUID errorId = logger.error(e, "changeStatus() : Unexpected error occured");
			result = new BaseOperationResult(ErrorCode.GeneralError, errorId, e.getMessage());
		}
		
		return result;
	}
	
	public BaseOperationResult modify(JSONObject input, long userId, long accountId) {
		
		
		BaseOperationResult result = null;
		
		IAccount account = null;
		OperationResult<IAccount> accountResult = AccountEntity.getAccount(accountId);
		if(accountResult.hasError()){
			result = accountResult;
		} else {
			account = accountResult.getValue();
		}
		
		if(null == result){
			result = modify(input, userId, account); 
		}
		
		return result;
	}

	protected abstract BaseOperationResult modify(final JSONObject input,
			final long userId, final IAccount account);
	
	public OpinionType getOpinionType() {
		return opinionType;
	}

	public void setOpinionType(OpinionType opinionType) {
		this.opinionType = opinionType;
	}

	public Boolean isEnableRssUpdates() {
		return enableRssUpdates;
	}

	public void setEnableRssUpdates(Boolean enableRssUpdates) {
		this.enableRssUpdates = enableRssUpdates;
	}

	public Integer getCollectorSourceId() {
		return collectorSourceId;
	}

	public void setCollectorSourceId(Integer collectorSourceId) {
		this.collectorSourceId = collectorSourceId;
	}

	public CollectorSourceType getCollectorSourceType() {
		return collectorSourceType;
	}

	public void setCollectorSourceType(CollectorSourceType collectorSourceType) {
		this.collectorSourceType = collectorSourceType;
	}

	public static CollectorEntity identifyCollector(ResultSet reader) throws SQLException {
		CollectorEntity result;
		OpinionType opinionType = OpinionType.fromInt(reader.getInt(ResultSetNames.OPINION_TYPE_ID));
		switch (opinionType) {
		case Poll:
			result = new PollsCollectorEntity(reader);
			break;
		case Survey:
			CollectorSourceType sourceType = CollectorSourceType.fromInt(reader.getInt(ResultSetNames.COLLECTOR_SOURCE_TYPE_ID));
			if(sourceType == CollectorSourceType.BuyRespondents){
				result = new PanelSurveysCollectorEntity(reader);
			} else {
				result = new SurveysCollectorEntity(reader);
			}
			break;
		default:
			throw new UnsupportedClassVersionError("opinionType not supported #" + reader.getInt(ResultSetNames.OPINION_TYPE_ID));
		}
		return result;
	}
	
	private void fillShortStatistics(){
		try{
			IResultSetCallback callback = new IResultSetCallback() {
				
				@Override
				public void call(ResultSet reader, int generationId) throws Exception {
					if(reader.next()){
						setCountOfStartedOpinions(reader.getLong(ResultSetNames.CNT_STARTED_OPINIONS));
						setCountOfFinishedOpinions(reader.getLong(ResultSetNames.CNT_FINISHED_OPINIONS));
						setDateOfLastFinishedOpinion(reader.getDate(ResultSetNames.LAST_FINISH_DATE));
						setDateOfLastStartedOpinion(reader.getDate(ResultSetNames.LAST_START_DATE));
						shortStatisticsHasBeenLoaded = true;
					} else {
						throw new Error("No results");
					}
				}
			};
			
			CollectorsDataAccess.getShortStatisticsReader(getId(), callback);
		} catch (DAOException ex){
			throw new Error("fillShortStatistics: Unexpected error occured", ex);
		}
	}

	public Date getDateOfLastStartedOpinion() {
		if(!shortStatisticsHasBeenLoaded) fillShortStatistics();
		return dateOfLastStartedOpinion;
	}

	private void setDateOfLastStartedOpinion(Date dateOfLastStartedOpinion) {
		this.dateOfLastStartedOpinion = dateOfLastStartedOpinion;
	}

	public Date getDateOfLastFinishedOpinion() {
		if(!shortStatisticsHasBeenLoaded) fillShortStatistics();
		return dateOfLastFinishedOpinion;
	}

	private void setDateOfLastFinishedOpinion(Date dateOfLastFinishedOpinion) {
		this.dateOfLastFinishedOpinion = dateOfLastFinishedOpinion;
	}
}