package com.inqwise.opinion.opinion.entities.analizeResults;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import net.casper.data.model.CDataCacheContainer;

import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.dao.IDataFillable;
import com.inqwise.opinion.infrastructure.dao.IResultSetCallback;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;
import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.entities.BaseEntity;
import com.inqwise.opinion.opinion.common.GuidType;
import com.inqwise.opinion.opinion.common.IAnswererSession;
import com.inqwise.opinion.opinion.dao.AnswerersSessionsDataAccess;

public class AnswererSessionEntity extends BaseEntity implements IAnswererSession {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1925895204612820715L;
	private static ApplicationLog logger = ApplicationLog
	.getLogger(AnswererSessionEntity.class);
	private static Format formatter = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");
	
	private long id;
	private Long opinionId;        
	private Date insertDate;       
	private String clientIp;         
	private String comment;           
	private Long userId;        
	private Long collectorId;      
	private String guid;              
	private GuidType guidType;      
	private String osName;           
	private String osPlatform;       
	private String osLanguage;       
	private String osTimeZone;      
	private Integer screenWidth;      
	private Integer screenHeight;     
	private Integer screenColorDepth;      
	private String browserAppName;  
	private String browserName;      
	private String browserVersion;   
	private boolean isCookieEnabled; 
	private boolean isJavaInstalled; 
	private String flashVersion;     
	private String answerSessionId; 
	private Date finishDate;  
	private Long modifyUserId;
	private Integer grade;
	private boolean starred;
	private String collectorName;
	private String respondentId;
	private Integer countryId;
	private String countryName;
	private boolean collectorExist;
	private Integer timeTakenSec;
	private boolean unplanned;
	private String targetUrl;
	
	public AnswererSessionEntity(ResultSet reader) throws SQLException {
		opinionId = ResultSetHelper.optLong(reader, ResultSetNames.OPINION_ID);        
		setInsertDate(ResultSetHelper.optDate(reader, ResultSetNames.INSERT_DATE));      
		setClientIp(ResultSetHelper.optString(reader, ResultSetNames.CLIENT_IP));         
		setComment(ResultSetHelper.optString(reader, ResultSetNames.COMMENT));           
		userId = ResultSetHelper.optLong(reader, ResultSetNames.USER_ID);        
		setCollectorId(ResultSetHelper.optLong(reader, ResultSetNames.COLLECTOR_ID));      
		guid = ResultSetHelper.optString(reader, ResultSetNames.GUID);
		int guidTypeId = reader.getInt(ResultSetNames.GUID_TYPE_ID);
		guidType = GuidType.fromInt(guidTypeId);
		setOsName(ResultSetHelper.optString(reader, ResultSetNames.OS_NAME));
		osPlatform = ResultSetHelper.optString(reader, ResultSetNames.OS_PLATFORM);
		osLanguage = ResultSetHelper.optString(reader, ResultSetNames.OS_LANGUAGE);
		osTimeZone = ResultSetHelper.optString(reader, ResultSetNames.OS_TIME_ZONE);
		screenWidth = ResultSetHelper.optInt(reader, ResultSetNames.SCREEN_WIDTH);
		screenHeight = ResultSetHelper.optInt(reader, ResultSetNames.SCREEN_HEIGHT);
		screenColorDepth = ResultSetHelper.optInt(reader, ResultSetNames.SCREEN_COLOR);
		browserAppName = ResultSetHelper.optString(reader, ResultSetNames.BROWSER_APP_NAME);
		setBrowserName(ResultSetHelper.optString(reader, ResultSetNames.BROWSER_NAME));
		browserVersion = ResultSetHelper.optString(reader, ResultSetNames.BROWSER_VERSION);
		isCookieEnabled = reader.getBoolean(ResultSetNames.IS_COOKIE_ENABLED);
		isJavaInstalled = reader.getBoolean(ResultSetNames.IS_JAVA_INSTALLED); 
		flashVersion = ResultSetHelper.optString(reader, ResultSetNames.FLASH_VERSION);
		setAnswerSessionId(ResultSetHelper.optString(reader, ResultSetNames.ANSWER_SESSION_ID));
		setFinishDate(ResultSetHelper.optDate(reader, ResultSetNames.FINISH_DATE));
		modifyUserId = ResultSetHelper.optLong(reader, ResultSetNames.MODIFY_USER_ID);
		setGrade(ResultSetHelper.optInt(reader, ResultSetNames.GARADE));
		setId(reader.getLong(ResultSetNames.ID));
		setStarred(reader.getBoolean(ResultSetNames.IS_STARRED));
		setCollectorName(ResultSetHelper.optString(reader, ResultSetNames.COLLECTOR_NAME));
		respondentId = ResultSetHelper.optString(reader, ResultSetNames.RESPONDENT_ID);
		setCountryId(ResultSetHelper.optInt(reader, ResultSetNames.COUNTRY_ID));
		setCountryName(ResultSetHelper.optString(reader, ResultSetNames.COUNTRY_NAME));
		setCollectorExist(reader.getBoolean(ResultSetNames.IS_COLLECTOR_EXIST));
		setTimeTakenSec(ResultSetHelper.optInt(reader, ResultSetNames.TIME_TAKEN_SEC));
		setUnplanned(reader.getBoolean(ResultSetNames.IS_UNPLAINED));
		setTargetUrl(ResultSetHelper.optString(reader, ResultSetNames.TARGET_URL));
	}
	
	/* (non-Javadoc)
	 * @see com.inqwise.opinion.opinion.entities.analizeResults.IAnswererSession#toJson()
	 */
	@Override
	public JSONObject toJson() throws JSONException {
		JSONObject jo = new JSONObject();
		jo.put("participantSessionId", getAnswerSessionId());
		jo.put("ipAddress", getClientIp());
		jo.put("startDate", formatter.format(getInsertDate()));
		if(null != getFinishDate()){
			jo.put("finishDate", formatter.format(getFinishDate()));
		}
		jo.put("status", null == getFinishDate() ? 0 : 1);
		//jo.put("note", getComment());
		//jo.put("grade", getGrade());
		if(null != getTimeTakenSec()){
			jo.put("timeTaken", JSONHelper.getTimeSpanSec(new Long(getTimeTakenSec())));
		}
		jo.put("id", getId());
		jo.put("starred", isStarred());
		jo.put("collectorName", getCollectorName());
		jo.put("collectorId", getCollectorId());
		jo.put("osName", getOsName());
		jo.put("browserName", getBrowserName());
		jo.put("countryName", getCountryName());
		jo.put("respondentId", getRespondentId());
		jo.put("targetUrl", getTargetUrl());
		return jo;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	/* (non-Javadoc)
	 * @see com.inqwise.opinion.opinion.entities.analizeResults.IAnswererSession#getInsertDate()
	 */
	public Date getInsertDate() {
		return insertDate;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	/* (non-Javadoc)
	 * @see com.inqwise.opinion.opinion.entities.analizeResults.IAnswererSession#getClientIp()
	 */
	public String getClientIp() {
		return clientIp;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	/* (non-Javadoc)
	 * @see com.inqwise.opinion.opinion.entities.analizeResults.IAnswererSession#getFinishDate()
	 */
	public Date getFinishDate() {
		return finishDate;
	}

	public void setAnswerSessionId(String answerSessionId) {
		this.answerSessionId = answerSessionId;
	}

	/* (non-Javadoc)
	 * @see com.inqwise.opinion.opinion.entities.analizeResults.IAnswererSession#getAnswerSessionId()
	 */
	public String getAnswerSessionId() {
		return answerSessionId;
	}
	
	public static CDataCacheContainer getAnswerersSessions(Long opinionId, Long accountId, Long collectorId, String respondentId, boolean includeUnplanned, Long fromIndex, Integer top, AtomicLong total){
		try {
			return AnswerersSessionsDataAccess.getAnswerersSessions(opinionId, accountId, collectorId, respondentId, includeUnplanned, fromIndex, top, total);
		} catch (DAOException e) {
			throw new Error(e);
		}
	}
	
	public static OperationResult<IAnswererSession> getAnswererSession(Long id, String answererSessionId, Long accountId){
		
		
		final OperationResult<IAnswererSession> result = new OperationResult<IAnswererSession>();
		try{
			IResultSetCallback callback  = new IResultSetCallback() {
				
				@Override
				public void call(ResultSet reader, int generationId)
						throws Exception {
					result.setValue(new AnswererSessionEntity(reader));
				}
			};
			
			AnswerersSessionsDataAccess.getAnswererSessionResultSet(callback, id, answererSessionId, accountId);
			
			if(!result.hasError() && !result.hasValue()){
				result.setError(ErrorCode.NoResults);
			}
			
		} catch (Exception ex){
			UUID errorId = logger.error(ex, "getAnswererSession() : Unexpected eror occured");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}
	
	public static OperationResult<IAnswererSession> getLastAnswererSession(Long collectorId, String respondentId, boolean includeUnplanned){
		final OperationResult<IAnswererSession> result = new OperationResult<IAnswererSession>();
		try{
			IResultSetCallback callback  = new IResultSetCallback() {
				
				@Override
				public void call(ResultSet reader, int generationId)
						throws Exception {
					if(reader.next()){
						result.setValue(new AnswererSessionEntity(reader));
					}
				}
			};
			
			AnswerersSessionsDataAccess.getLastAnswererSessionResultSet(callback, collectorId, respondentId, includeUnplanned);
			
			if(!result.hasError() && !result.hasValue()){
				result.setError(ErrorCode.NoResults);
			}
			
		} catch (Exception ex){
			UUID errorId = logger.error(ex, "getLastAnswererSession() : Unexpected eror occured");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getComment() {
		return comment;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public Integer getGrade() {
		return grade;
	}

	public BaseOperationResult updateGrade(Long accountId, Integer grade,
			String comment, Long userId) {
		
		boolean hasChanges = false;
		BaseOperationResult result = null;
		try {
			if(grade != getGrade()){
				hasChanges = true;	
			}
			
			if(comment != getComment()){
				hasChanges = true;
			}
			
			if(hasChanges) {
				result = AnswerersSessionsDataAccess.updateGrade(getId(), accountId, grade, comment, userId);
				if(!result.hasError()){
					setGrade(grade);
					setComment(comment);
				}
			} else {
				logger.warn("updateGrade () : No chages occured for session: '%s', accountId: '%s'", getAnswerSessionId(), accountId);
				result = BaseOperationResult.Ok;
			}
		} catch (Exception ex){
			UUID errorId = logger.error(ex, "updateGrade() : Unexpected error occured.");
			result = new BaseOperationResult(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}

	public BaseOperationResult updateStarred(Long accountId, boolean starred, Long userId){
		boolean hasChanges = false;
		BaseOperationResult result = null;
		try {
			if(starred != isStarred()){
				hasChanges = true;
			}
			
			if(hasChanges) {
				result = AnswerersSessionsDataAccess.updateStarred(getId(), accountId, starred, userId);
				if(!result.hasError()){
					setStarred(starred);
				}
			} else {
				logger.warn("updateStarred () : No chages occured for session: '%s', accountId: '%s'", getAnswerSessionId(), accountId);
				result = BaseOperationResult.Ok;
			}
		} catch (Exception ex){
			UUID errorId = logger.error(ex, "updateStarred() : Unexpected error occured.");
			result = new BaseOperationResult(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setStarred(boolean starred) {
		this.starred = starred;
	}

	public boolean isStarred() {
		return starred;
	}

	public void setCollectorId(Long collectorId) {
		this.collectorId = collectorId;
	}

	public Long getCollectorId() {
		return collectorId;
	}

	public void setCollectorName(String collectorName) {
		this.collectorName = collectorName;
	}

	public String getCollectorName() {
		return collectorName;
	}

	public void setOsName(String osName) {
		this.osName = osName;
	}

	public String getOsName() {
		return osName;
	}

	public void setBrowserName(String browserName) {
		this.browserName = browserName;
	}

	public String getBrowserName() {
		return browserName;
	}
	
	public String getCountryName() {
		return countryName;
	}

	@Override
	public String getRespondentId() {
		return respondentId;
	}

	public Integer getCountryId() {
		return countryId;
	}

	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	@Override
	public boolean isCollectorExist() {
		return collectorExist;
	}

	public void setCollectorExist(boolean collectorExist) {
		this.collectorExist = collectorExist;
	}

	public Integer getTimeTakenSec() {
		return timeTakenSec;
	}

	public void setTimeTakenSec(Integer timeTakenSec) {
		this.timeTakenSec = timeTakenSec;
	}

	public boolean isUnplanned() {
		return unplanned;
	}

	public void setUnplanned(boolean unplanned) {
		this.unplanned = unplanned;
	}

	public String getTargetUrl() {
		return targetUrl;
	}

	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}
}