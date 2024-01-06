package com.inqwise.opinion.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.inqwise.opinion.infrastructure.dao.SqlProc;
import com.inqwise.opinion.infrastructure.dao.SqlProcParameter;

@SqlProc
public interface IInsertOpinionParams {
	void insertOpinionCompleted(ResultSet reader) throws SQLException;
	
	@SqlProcParameter(name=OpinionsDataAccess.ACCOUNT_ID_PARAM)
	public Long getAccountId();
	
	@SqlProcParameter(name=OpinionsDataAccess.ACTION_GUID_PARAM)
	public String getActionGuid();
	
	@SqlProcParameter(name=OpinionsDataAccess.TRANSLATION_ID_PARAM)
	public Long getTranslationId();
	
	@SqlProcParameter(name=OpinionsDataAccess.USER_ID_PARAM)
	public long getUserId();
	
	@SqlProcParameter(name=OpinionsDataAccess.NAME_PARAM)
	public String getName();
	
	@SqlProcParameter(name=OpinionsDataAccess.THEME_ID_PARAM)
	public Integer getThemeId();
	
	@SqlProcParameter(name=OpinionsDataAccess.TRANSLATION_NAME_PARAM)
	public String getTranslationName();
	
	@SqlProcParameter(name=OpinionsDataAccess.CULTURE_PARAM)
	public String getCulture();
	
	@SqlProcParameter(name=OpinionsDataAccess.TITLE_PARAM)
	public String getTitle();
	
	@SqlProcParameter(name=OpinionsDataAccess.DESCRIPTION_PARAM)
	public String getDescription();
	
	@SqlProcParameter(name=OpinionsDataAccess.IS_RTL_PARAM)
	public boolean isRtl();
	
	@SqlProcParameter(name=OpinionsDataAccess.IS_HIGHLIGHT_REQUIRED_QUESTIONS_PARAM)
	public Boolean getIsHighlightRequestedQuestions();
	
	@SqlProcParameter(name=OpinionsDataAccess.FINISH_MESSAGE_PARAM)
	public String getFinishMessage();
	
	@SqlProcParameter(name=OpinionsDataAccess.START_MESSAGE_PARAM)
	public String getStartMessage();
	
	@SqlProcParameter(name=OpinionsDataAccess.START_BUTTON_TITLE_PARAM)
	public String getStartButtonTitle();
	
	@SqlProcParameter(name=OpinionsDataAccess.FINISH_BUTTON_TITLE_PARAM)
	public String getFinishButtonTitle();
	
	@SqlProcParameter(name=OpinionsDataAccess.PREVIOUS_BUTTON_TITLE_PARAM)
	public String getPreviousButtonTitle();
	
	@SqlProcParameter(name=OpinionsDataAccess.NEXT_BUTTON_TITLE_PARAM)
	public String getNextButtonTitle();
	
	@SqlProcParameter(name=OpinionsDataAccess.USE_QUESTION_NUMBERING_PARAM)
	public Boolean isUseQuestionNumbering();
	
	@SqlProcParameter(name=OpinionsDataAccess.USE_PAGE_NUMBERING_PARAM)
	public Boolean isUsePageNumbering();
	
	@SqlProcParameter(name=OpinionsDataAccess.LOGO_URL_PARAM)
	public String getLogoUrl();
	
	@SqlProcParameter(name=OpinionsDataAccess.OPINION_TYPE_ID_PARAM)
	public int getOpinionTypeId();
	
	@SqlProcParameter(name=OpinionsDataAccess.REDIRECT_URL_PARAM)
	public String getRedirectUrl();
	
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
}
