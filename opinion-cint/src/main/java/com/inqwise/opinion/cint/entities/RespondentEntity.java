package com.inqwise.opinion.cint.entities;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;

import com.inqwise.opinion.cint.common.IDemographic;
import com.inqwise.opinion.cint.common.IRespondent;
import com.inqwise.opinion.cint.common.IVariable;
import com.inqwise.opinion.cint.common.IXmlOwnerCallback;

public class RespondentEntity implements IRespondent, IXmlOwnerCallback {
	private String id;
    private Integer age;
    private DemographicEntity gender;
    private DemographicEntity occupationStatus;
    private DemographicEntity educationLevel;
    private DemographicEntity country;
    private DemographicEntity region;
    private String status;
    private List<IVariable> variables;
    
    public RespondentEntity(Element element) {
		XmlHelper.parse(element, this);
	}

	@Override
	public String getId() {
        return id;
    }

    public void setId(String value) {
        this.id = value;
    }

    @Override
	public Integer getAge() {
        return age;
    }

    public void setAge(Integer value) {
        this.age = value;
    }

    @Override
	public IDemographic getGender() {
        return gender;
    }

    public void setGender(DemographicEntity value) {
        this.gender = value;
    }

    @Override
	public IDemographic getOccupationStatus() {
        return occupationStatus;
    }

    public void setOccupationStatus(DemographicEntity value) {
        this.occupationStatus = value;
    }

    @Override
	public IDemographic getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(DemographicEntity value) {
        this.educationLevel = value;
    }

    @Override
	public IDemographic getCountry() {
        return country;
    }

    public void setCountry(DemographicEntity value) {
        this.country = value;
    }

    @Override
	public IDemographic getRegion() {
        return region;
    }

    public void setRegion(DemographicEntity value) {
        this.region = value;
    }

    @Override
	public String getStatus() {
        return status;
    }

    public void setStatus(String value) {
        this.status = value;
    }

    @Override
	public List<IVariable> getVariables() {
        if (variables == null) {
            variables = new ArrayList<IVariable>();
        }
        return this.variables;
    } 
    
    public void onElementFound(Element element) {
    	switch(element.getTagName()){
    	case "id":
    		setId(element.getTextContent());
    		break;
    	case "age":
    		setAge(Integer.valueOf(element.getTextContent()));
    		break;
    	case "gender":
    		setGender(new DemographicEntity(element));
    		break;
    	case "occupation-status":
    		setOccupationStatus(new DemographicEntity(element));
    		break;
    	case "education-level":
    		setEducationLevel(new DemographicEntity(element));
    		break;
    	case "country":
    		setCountry(new DemographicEntity(element));
    		break;
    	case "region":
    		setRegion(new DemographicEntity(element));
    		break;
    	case "status":
    		setStatus(element.getTextContent());
    		break;
    	case "variable":
    		getVariables().add(new VariableEntity(element));
    		break;
    	}
    }
}
