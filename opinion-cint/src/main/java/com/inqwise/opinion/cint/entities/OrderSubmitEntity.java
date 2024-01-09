package com.inqwise.opinion.cint.entities;

import com.inqwise.opinion.cint.common.IOrderSubmit;
import com.inqwise.opinion.cint.common.ITargetGroupSubmit;
import com.inqwise.opinion.cint.common.errorHandle.CintBaseOperationResult;
import com.inqwise.opinion.cint.common.errorHandle.CintErrorCode;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;

public class OrderSubmitEntity implements IOrderSubmit {
	
	static ApplicationLog logger = ApplicationLog.getLogger(OrderSubmitEntity.class);
	private String surveyTitle;
    private String surveyUrl;
    private int numberOfQuestions;
    private Integer numberOfCompletes;
    private String contactName;
    private String contactEmail;
    private String contactCompany;
    private TargetGroupSubmitEntity targetGroup;

    public OrderSubmitEntity() {
    	targetGroup = new TargetGroupSubmitEntity();
	}
    
    @Override
	public String getSurveyTitle() {
        return surveyTitle;
    }

    @Override
	public void setSurveyTitle(String value) {
        this.surveyTitle = value;
    }

    @Override
	public String getSurveyUrl() {
        return surveyUrl;
    }

    @Override
	public void setSurveyUrl(String value) {
        this.surveyUrl = value;
    }

    @Override
	public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    @Override
	public void setNumberOfQuestions(int value) {
        this.numberOfQuestions = value;
    }

    @Override
	public Integer getNumberOfCompletes() {
        return numberOfCompletes;
    }

    @Override
	public void setNumberOfCompletes(Integer value) {
        this.numberOfCompletes = value;
    }

    @Override
	public String getContactName() {
        return contactName;
    }

    @Override
	public void setContactName(String value) {
        this.contactName = value;
    }

    @Override
	public String getContactEmail() {
        return contactEmail;
    }

    @Override
	public void setContactEmail(String value) {
        this.contactEmail = value;
    }

    @Override
	public String getContactCompany() {
        return contactCompany;
    }

    @Override
	public void setContactCompany(String value) {
        this.contactCompany = value;
    }

    @Override
	public ITargetGroupSubmit getTargetGroup() {
    	return targetGroup;
    }

	public String toXml() {
		StringBuilder sb = new StringBuilder();
		sb.append("<order>");
		sb.append("<survey-title>").append(getSurveyTitle()).append("</survey-title>");
		sb.append("<survey-url>").append(getSurveyUrl()).append("</survey-url>");
		sb.append("<number-of-questions>").append(getNumberOfQuestions()).append("</number-of-questions>");
		sb.append("<number-of-completes>").append(getNumberOfCompletes()).append("</number-of-completes>");
		
		if(null != getContactName()){
			sb.append("<contact-name>").append(getContactName()).append("</contact-name>");
		}
		if(null != getContactEmail()){
			sb.append("<contact-email>").append(getContactEmail()).append("</contact-email>");
		}
		if(null != getContactCompany()){
			sb.append("<contact-company>").append(getContactCompany()).append("</contact-company>");
		}
		sb.append("<target-group>").append(targetGroup.toXml()).append("</target-group>");
		sb.append("</order>");
		return sb.toString();
	}

	public CintBaseOperationResult validate() {
		CintBaseOperationResult result = targetGroup.validate();
		if(null == result && null == surveyTitle){
			logger.warn("validate : surveyTitle is mandatory");
			result = new CintBaseOperationResult(CintErrorCode.SurveyTitleIsMandatory);
		}
		
		if(null == result && null == surveyUrl){
			logger.warn("validate : surveyUrl is mandatory");
			result = new CintBaseOperationResult(CintErrorCode.SurveyUrlIsMandatory);
		}
		
		if(null == result && numberOfQuestions < 1){
			logger.warn("validate : numberOfQuestions is out of range '%s'", numberOfQuestions);
			result = new CintBaseOperationResult(CintErrorCode.NumberOfQuestionsIsOutOfRange);
		}
		
		if(null == result && null == numberOfCompletes){
			logger.warn("validate : numberOfCompletes is mandatory");
			result = new CintBaseOperationResult(CintErrorCode.NumberOfCompletesIsMandatory);
		}
		
		if(null == result && (numberOfCompletes < 10)){
			logger.warn("validate : numberOfCompletes is out of range '%s'", numberOfCompletes);
			result = new CintBaseOperationResult(CintErrorCode.NumberOfCompletesIsOutOfRange);
		}
		
		if(null == result && null == targetGroup){
			logger.warn("validate : targetGroup is mandatory");
			result = new CintBaseOperationResult(CintErrorCode.TargetGroupIsMandatory);
		}
		
		return result;
	}

	public String getOrderDetails() {
		StringBuilder sb = new StringBuilder();
		sb.append("survey-title: ").append(getSurveyTitle()).append("<br/>");
		sb.append("number-of-questions: ").append(getNumberOfQuestions()).append("<br/>");
		sb.append("number-of-completes: ").append(getNumberOfCompletes()).append("<br/>");
		sb.append(targetGroup.getTargetGroupDetails());
		return sb.toString();
	}
}