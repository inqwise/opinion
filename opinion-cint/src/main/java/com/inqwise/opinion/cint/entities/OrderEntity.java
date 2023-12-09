package com.cint.entities;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;

import com.cint.common.ICountry;
import com.cint.common.IEducationLevel;
import com.cint.common.IGender;
import com.cint.common.ILink;
import com.cint.common.IOccupationStatus;
import com.cint.common.IOrder;
import com.cint.common.IVariable;
import com.cint.common.IXmlOwnerCallback;
import com.cint.common.OrderState;

public class OrderEntity implements IOrder, IXmlOwnerCallback {
	
	private List<ILink> links;
    private OrderState state;
    private String orderNumber;
    private String surveyTitle;
    private String surveyUrl;
    private Integer numberOfQuestions;
    private int numberOfCompletes;
    private String contactName;
    private String contactEmail;
    private String contactCompany;
    private CountryEntity country;
    private Integer minAge;
    private Integer maxAge;
    private IGender gender;
    private List<IEducationLevel> educationLevels;
    private List<IOccupationStatus> occupationStatuses;
    private List<IVariable> variables;
    private double quote;
    private String currency;
    private Integer totalNumberOfInvitations;
    private Integer totalNumberOfCompletes;
    private Integer totalScreenOuts;
    private Integer totalQuotaFulls;
    private Double actualPrice;
    private Double pricePerComplete;
    private Integer actualNumberOfCompletes;
    private String referenceNumber;
	
    public OrderEntity(Element element) {
    	 XmlHelper.parse(element, this);
	}
    
    public void onElementFound(Element element){
    	switch(element.getTagName()){
		case "link":
			getLinks().add(new LinkEntity(element));
			break;
		case "state":
			setState(OrderState.fromString(element.getTextContent()));
			break;
		case "order-number":
			setOrderNumber(element.getTextContent());
			break;
		case "survey-title":
			setSurveyTitle(element.getTextContent());
			break;
		case "survey-url":
			setSurveyUrl(element.getTextContent());
			break;
		case "number-of-questions":
			setNumberOfQuestions(Integer.valueOf(element.getTextContent()));
			break;
		case "number-of-completes":
			setNumberOfCompletes(Integer.parseInt(element.getTextContent()));
			break;
		case "contact-name":
			setContactName(element.getTextContent());
			break;
		case "contact-email":
			setContactEmail(element.getTextContent());
			break;
		case "contact-company":
			setContactCompany(element.getTextContent());
			break;
		case "country":
			setCountry(new CountryEntity(element));
			break;
		case "target-group":
			XmlHelper.parse(element, this);
			break;
		case "min-age":
			setMinAge(Integer.valueOf(element.getTextContent()));
			break;
		case "max-age":
			setMaxAge(Integer.valueOf(element.getTextContent()));
			break;
		case "gender":
			setGender(new GenderEntity(element));
			break;
		case "education-level":
			getEducationLevels().add(new EducationLevelEntity(element));
			break;
		case "occupation-status":
			getOccupationStatuses().add(new OccupationStatusEntity(element));
			break;
		case "variable":
			getVariables().add(new VariableEntity(element));
			break;
		case "quote":
			setQuote(Double.parseDouble(element.getTextContent()));
			break;
		case "total-number-of-invitations":
			setTotalNumberOfInvitations(Integer.valueOf(element.getTextContent()));
			break;
		case "total-number-of-completes":
			setTotalNumberOfCompletes(Integer.valueOf(element.getTextContent()));
			break;
		case "total-screen-outs":
			setTotalScreenOuts(Integer.valueOf(element.getTextContent()));
			break;
		case "total-quota-fulls":
			if(!StringUtils.isEmpty(element.getTextContent())){
				setTotalQuotaFulls(Integer.valueOf(element.getTextContent()));
			}
			break;
		case "actual-price":
			setActualPrice(Double.valueOf(element.getTextContent()));
			break;
		case "price-per-complete":
			setPricePerComplete(Double.valueOf(element.getTextContent()));
			break;
		case "actual-number-of-completes":
			setActualNumberOfCompletes(Integer.valueOf(element.getTextContent()));
			break;
		case "reference-number":
			setReferenceNumber(element.getTextContent());
			break;
		}
    }
    
    @Override
	public List<ILink> getLinks() {
        if (links == null) {
            links = new ArrayList<ILink>();
        }
        return this.links;
    }

    @Override
	public OrderState getState() {
        return state;
    }

    public void setState(OrderState value) {
        this.state = value;
    }

    @Override
	public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String value) {
        this.orderNumber = value;
    }

    @Override
	public String getSurveyTitle() {
        return surveyTitle;
    }

    public void setSurveyTitle(String value) {
        this.surveyTitle = value;
    }

    @Override
	public String getSurveyUrl() {
        return surveyUrl;
    }

    public void setSurveyUrl(String value) {
        this.surveyUrl = value;
    }

    @Override
	public Integer getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(Integer value) {
        this.numberOfQuestions = value;
    }

    @Override
	public int getNumberOfCompletes() {
        return numberOfCompletes;
    }

    public void setNumberOfCompletes(int value) {
        this.numberOfCompletes = value;
    }

    @Override
	public String getContactName() {
        return contactName;
    }

    public void setContactName(String value) {
        this.contactName = value;
    }

    @Override
	public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String value) {
        this.contactEmail = value;
    }

    @Override
	public String getContactCompany() {
        return contactCompany;
    }

    public void setContactCompany(String value) {
        this.contactCompany = value;
    }

    @Override
	public ICountry getCountry() {
        return country;
    }

    public void setCountry(CountryEntity value) {
        this.country = value;
    }

    @Override
	public Integer getMinAge() {
        return minAge;
    }

    public void setMinAge(Integer value) {
        this.minAge = value;
    }

    @Override
	public Integer getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Integer value) {
        this.maxAge = value;
    }

    @Override
	public IGender getGender() {
        return gender;
    }

    public void setGender(GenderEntity value) {
        this.gender = value;
    }

    @Override
	public List<IEducationLevel> getEducationLevels() {
        if (educationLevels == null) {
            educationLevels = new ArrayList<IEducationLevel>();
        }
        return this.educationLevels;
    }

    @Override
	public List<IOccupationStatus> getOccupationStatuses() {
        if (occupationStatuses == null) {
            occupationStatuses = new ArrayList<IOccupationStatus>();
        }
        return this.occupationStatuses;
    }

    @Override
	public List<IVariable> getVariables() {
        if (variables == null) {
            variables = new ArrayList<IVariable>();
        }
        return this.variables;
    }

    @Override
	public double getQuote() {
        return quote;
    }

    public void setQuote(double value) {
        this.quote = value;
    }

    @Override
	public String getCurrency() {
        return currency;
    }

    public void setCurrency(String value) {
        this.currency = value;
    }

    @Override
	public Integer getTotalNumberOfInvitations() {
        return totalNumberOfInvitations;
    }

    public void setTotalNumberOfInvitations(Integer value) {
        this.totalNumberOfInvitations = value;
    }

    @Override
	public Integer getTotalNumberOfCompletes() {
        return totalNumberOfCompletes;
    }

    public void setTotalNumberOfCompletes(Integer value) {
        this.totalNumberOfCompletes = value;
    }

    @Override
	public Integer getTotalScreenOuts() {
        return totalScreenOuts;
    }

    public void setTotalScreenOuts(Integer value) {
        this.totalScreenOuts = value;
    }

    @Override
	public Integer getTotalQuotaFulls() {
        return totalQuotaFulls;
    }

    public void setTotalQuotaFulls(Integer value) {
        this.totalQuotaFulls = value;
    }

    @Override
	public Double getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(Double value) {
        this.actualPrice = value;
    }

    @Override
	public Double getPricePerComplete() {
        return pricePerComplete;
    }

    public void setPricePerComplete(Double value) {
        this.pricePerComplete = value;
    }

    @Override
	public Integer getActualNumberOfCompletes() {
        return actualNumberOfCompletes;
    }

    public void setActualNumberOfCompletes(Integer value) {
        this.actualNumberOfCompletes = value;
    }

    @Override
	public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String value) {
        this.referenceNumber = value;
    }
}
