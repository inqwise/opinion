package com.cint.entities;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;

import com.cint.common.ICountry;
import com.cint.common.IEducationLevel;
import com.cint.common.IGender;
import com.cint.common.IOccupationStatus;
import com.cint.common.IQuote;
import com.cint.common.IVariable;
import com.cint.common.IXmlOwnerCallback;

public class QuoteEntity implements IQuote, IXmlOwnerCallback {
	
	private Integer numberOfQuestions;
    private Integer numberOfCompletes;
    private CountryEntity country;
    private Integer minAge;
    private Integer maxAge;
    private GenderEntity gender;
    private List<IEducationLevel> educationLevels;
    private List<IOccupationStatus> occupationStatuses;
    private List<IVariable> variables;
    private String probability;
    private String fieldPeriod;
    private Integer availableAnswers;
    private double amount;
    private String formattedAmount;
    private String currency;
    
    @Override
	public Integer getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(Integer value) {
        this.numberOfQuestions = value;
    }

    @Override
	public Integer getNumberOfCompletes() {
        return numberOfCompletes;
    }

    public void setNumberOfCompletes(Integer value) {
        this.numberOfCompletes = value;
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
	public String getProbability() {
        return probability;
    }

    public void setProbability(String value) {
        this.probability = value;
    }

    @Override
	public String getFieldPeriod() {
        return fieldPeriod;
    }

    public void setFieldPeriod(String value) {
        this.fieldPeriod = value;
    }

    @Override
	public Integer getAvailableAnswers() {
        return availableAnswers;
    }

    public void setAvailableAnswers(Integer value) {
        this.availableAnswers = value;
    }
    
    public QuoteEntity(Element element) {
    	XmlHelper.parse(element, this);
	}
    
    public void onElementFound(Element element) {
    	switch(element.getTagName()){
    	case "target-group":
    	case "feasibility":
    		XmlHelper.parse(element, this);
    		break;
    	case "amount":
    		setAmount(Double.parseDouble(element.getTextContent()));
    		break;
    	case "formatted":
    		setFormattedAmount(element.getTextContent());
    		break;
    	case "currency":
    		setCurrency(element.getTextContent());
    		break;
    	case "number-of-questions":
    		setNumberOfQuestions(Integer.valueOf(element.getTextContent()));
    		break;
    	case "number-of-completes":
    		setNumberOfCompletes(Integer.valueOf(element.getTextContent()));
    		break;
    	case "country":
    		setCountry(new CountryEntity(element));
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
    	case "probability":
    		setProbability(element.getTextContent());
    		break;
    	case "field-period":
    		setFieldPeriod(element.getTextContent());
    		break;
    	case "available-answers":
    		setAvailableAnswers(Integer.valueOf(element.getTextContent()));
    		break;
    	}
    }

	@Override
	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public String getFormattedAmount() {
		return formattedAmount;
	}

	public void setFormattedAmount(String formattedAmount) {
		this.formattedAmount = formattedAmount;
	}

	@Override
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
}
