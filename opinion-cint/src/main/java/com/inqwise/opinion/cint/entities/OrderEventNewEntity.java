package com.inqwise.opinion.cint.entities;

import org.w3c.dom.Element;

import com.inqwise.opinion.cint.common.IGlobalVariable;
import com.inqwise.opinion.cint.common.IOrderEventNew;
import com.inqwise.opinion.cint.common.OrderEventType;

public class OrderEventNewEntity extends OrderEventEntity implements
		IOrderEventNew {
	private String currency;
	private String surveyTitle;
    private String surveyUrl;
    private double quote;
    private String contactEmail;
    private Integer numberOfQuestions;
    private int numberOfCompletes;
    private String contactCompany;
    private String contactName;
    private String country;
    private String region;
    private String gender;
    private String education;
    private String occupationStatus;
    private String ageRange;
    private IGlobalVariable globalVariable;
    
	protected OrderEventNewEntity(Element element) {
		super(element);
	}

	@Override
	public OrderEventType getEventType() {
		return OrderEventType.New;
	}
	
	@Override
	public void onElementFound(Element element) {
		switch(element.getTagName()){
		case "currency":
			setCurrency(element.getTextContent());
			break;
        case "survey-title":
        	setSurveyTitle(element.getTextContent());
        	break;
        case "survey-url":
        	setSurveyUrl(element.getTextContent());
        	break;
        case "quote":
        	setQuote(Double.parseDouble(element.getTextContent()));
        	break;
        case "contact-email":
        	setContactEmail(element.getTextContent());
        	break;
        case "number-of-questions":
        	setNumberOfQuestions(Integer.valueOf(element.getTextContent()));
        	break;
        case "number-of-completes":
        	setNumberOfCompletes(Integer.parseInt(element.getTextContent()));
        	break;
        case "target-group":
        	XmlHelper.parse(element, this);
        	break;
        case "contact-company":
        	setContactCompany(element.getTextContent());
        	break;
        case "contact-name":
        	setContactName(element.getTextContent());
        	break;
        case "country":
        	setCountry(element.getTextContent());
        	break;
        case "region":
        	setRegion(element.getTextContent());
        	break;
        case "gender":
        	setGender(element.getTextContent());
        	break;
        case "education":
        	setEducation(element.getTextContent());
        	break;
        case "occupation-status":
        	setOccupationStatus(element.getTextContent());
        	break;
        case "age-range":
        	setAgeRange(element.getTextContent());
        	break;
        case "global-variable":
        	setGlobalVariable(new GlobalVariableEntity(element));
        	break;
		default:
			super.onElementFound(element);
			break;
		}
	}

	@Override
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Override
	public String getSurveyTitle() {
		return surveyTitle;
	}

	public void setSurveyTitle(String surveyTitle) {
		this.surveyTitle = surveyTitle;
	}

	@Override
	public String getSurveyUrl() {
		return surveyUrl;
	}

	public void setSurveyUrl(String surveyUrl) {
		this.surveyUrl = surveyUrl;
	}

	@Override
	public double getQuote() {
		return quote;
	}

	public void setQuote(double quote) {
		this.quote = quote;
	}

	@Override
	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	@Override
	public Integer getNumberOfQuestions() {
		return numberOfQuestions;
	}

	public void setNumberOfQuestions(Integer numberOfQuestions) {
		this.numberOfQuestions = numberOfQuestions;
	}

	@Override
	public int getNumberOfCompletes() {
		return numberOfCompletes;
	}

	public void setNumberOfCompletes(int numberOfCompletes) {
		this.numberOfCompletes = numberOfCompletes;
	}

	@Override
	public String getContactCompany() {
		return contactCompany;
	}

	public void setContactCompany(String contactCompany) {
		this.contactCompany = contactCompany;
	}

	@Override
	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	@Override
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	@Override
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Override
	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	@Override
	public String getOccupationStatus() {
		return occupationStatus;
	}

	public void setOccupationStatus(String occupationStatus) {
		this.occupationStatus = occupationStatus;
	}

	@Override
	public String getAgeRange() {
		return ageRange;
	}

	public void setAgeRange(String ageRange) {
		this.ageRange = ageRange;
	}

	@Override
	public IGlobalVariable getGlobalVariable() {
		return globalVariable;
	}

	public void setGlobalVariable(IGlobalVariable globalVariable) {
		this.globalVariable = globalVariable;
	}

}
