package com.inqwise.opinion.opinion.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.inqwise.opinion.infrastructure.dao.SqlProc;
import com.inqwise.opinion.infrastructure.dao.SqlProcParameter;

@SqlProc
public interface IUpdateOpinionParams {
	
	public void updateOpinionCompleted(ResultSet reader) throws SQLException;
	
	@SqlProcParameter(name=OpinionsDataAccess.OPINION_ID_PARAM)
	public long getOpinionId();
	
	@SqlProcParameter(name=OpinionsDataAccess.START_MESSAGE_PARAM)
	public String getStartMessage();
	
	@SqlProcParameter(name=OpinionsDataAccess.FINISH_MESSAGE_PARAM)
	public String getFinishMessage();
	
	@SqlProcParameter(name=OpinionsDataAccess.REDIRECT_URL_PARAM)
	public String getRedirectUrl();
	
	@SqlProcParameter(name=OpinionsDataAccess.SHOW_PROGRESS_BAR_PARAM)
	public Boolean isShowProgressBar();
	
	@SqlProcParameter(name=OpinionsDataAccess.ACCOUNT_ID_PARAM)
	// Not required
	public Long getAccountId();
	
	@SqlProcParameter(name=OpinionsDataAccess.TRANSLATION_ID_PARAM)
	public Long getTranslationId();
	
	@SqlProcParameter(name=OpinionsDataAccess.START_BUTTON_TITLE_PARAM)
	public String getStartButtonTitle();
	
	@SqlProcParameter(name=OpinionsDataAccess.FINISH_BUTTON_TITLE_PARAM)
	public String getFinishButtonTitle();
	
	@SqlProcParameter(name=OpinionsDataAccess.PREVIOUS_BUTTON_TITLE_PARAM)
	public String getPreviousButtonTitle();
	
	@SqlProcParameter(name=OpinionsDataAccess.NEXT_BUTTON_TITLE_PARAM)
	public String getNextButtonTitle();
	
	@SqlProcParameter(name=OpinionsDataAccess.IS_HIGHLIGHT_REQUIRED_QUESTIONS_PARAM)
	public boolean isHighlightRequiredQuestions();
	
	@SqlProcParameter(name=OpinionsDataAccess.USE_PAGE_NUMBERING_PARAM)
	public Boolean isUsePageNumbering();
	
	@SqlProcParameter(name=OpinionsDataAccess.USE_QUESTION_NUMBERING_PARAM)
	public Boolean isUseQuestionNumbering();
	
	@SqlProcParameter(name=OpinionsDataAccess.IS_RTL_PARAM)
	public boolean isRtl();
	
	@SqlProcParameter(name=OpinionsDataAccess.LOGO_URL_PARAM)
	public String GetLogoUrl();
	
	@SqlProcParameter(name=OpinionsDataAccess.USER_ID_PARAM)
	public long getUserId();
	
	@SqlProcParameter(name=OpinionsDataAccess.FINISH_BEHAVIOUR_TYPE_ID_PARAM)
	public Integer getFinishBehaviourTypeId();
	
	@SqlProcParameter(name=OpinionsDataAccess.LABEL_PLACEMENT_ID_PARAM)
	public Integer getLabelPlacementId();
	
	@SqlProcParameter(name=OpinionsDataAccess.SHOW_PAGING_PARAM)
	public Boolean isShowPaging();
	
	@SqlProcParameter(name=OpinionsDataAccess.HIDE_POWERED_BY_PARAM)
	public Boolean isHidePoweredBy();
	
	@SqlProcParameter(name=OpinionsDataAccess.ALREADY_COMPLETED_MESSAGE_PARAM)
	public String getAlreadyCompletedMessage();
	
	@SqlProcParameter(name=OpinionsDataAccess.REQUIRED_QUESTION_ERROR_MESSAGE)
	public String getRequiredQuestionErrorMessage();
}
