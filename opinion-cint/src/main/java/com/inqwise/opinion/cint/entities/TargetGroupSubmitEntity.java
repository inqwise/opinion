package com.inqwise.opinion.cint.entities;

import java.util.ArrayList;
import java.util.List;

import com.inqwise.opinion.cint.common.ITargetGroupSubmit;
import com.inqwise.opinion.cint.common.errorHandle.CintBaseOperationResult;
import com.inqwise.opinion.cint.common.errorHandle.CintErrorCode;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;

class TargetGroupSubmitEntity implements ITargetGroupSubmit {
	
	static ApplicationLog logger = ApplicationLog.getLogger(TargetGroupSubmitEntity.class);
	public String toXml(){
	    StringBuilder sb = new StringBuilder();
	    sb.append("<country>");appendCountryXml(sb).append("</country>");
	    if(null != minAge){
	    	sb.append("<min-age>").append(getMinAge()).append("</min-age>");
	    }
	    if(null != maxAge){
	    	sb.append("<max-age>").append(getMaxAge()).append("</max-age>");
	    }
	    if(null != genderId){
	    	sb.append("<gender><id>").append(getGenderId()).append("</id></gender>");
	    }
	    appendEducationLevelsXml(sb);
	    appendOccupationStatusesXml(sb);
	    appendVariablesXml(sb);
		return sb.toString();
	}
	
	private StringBuilder appendVariablesXml(StringBuilder sb) {
		if(null != variables){
			for (String variableId : variables) {
				sb.append("<variable><id>").append(variableId).append("</id></variable>");
			}
		}
		return sb;
	}

	private StringBuilder appendOccupationStatusesXml(StringBuilder sb) {
		if(null != occupationStatuses){
			for (String occupationStatusId : occupationStatuses) {
				sb.append("<occupation-status><id>").append(occupationStatusId).append("</id></occupation-status>");
			}
		}
		return sb;
	}

	private StringBuilder appendCountryXml(StringBuilder sb) {
		sb.append("<id>").append(getCountryId()).append("</id>");
		if(null != regions){
			for (String regionId : regions) {
				sb.append("<region><id>").append(regionId).append("</id></region>");
			}
		}
		return sb;
	}

	private StringBuilder appendEducationLevelsXml(StringBuilder sb) {
		if(null != educationLevels){
			for (String educationLevelId : educationLevels) {
				sb.append("<education-level><id>").append(educationLevelId).append("</id></education-level>");
			}
		}
		return sb;
	}

	private String countryId;
    private Integer minAge;
    private Integer maxAge;
    private String genderId;
    private List<String> regions;
    private List<String> educationLevels;
    private List<String> occupationStatuses;
    private List<String> variables;

    @Override
	public String getCountryId() {
        return countryId;
    }

    @Override
	public void setCountryId(String value) {
        this.countryId = value;
    }

    @Override
	public Integer getMinAge() {
        return minAge;
    }

    @Override
	public void setMinAge(Integer value) {
        this.minAge = value;
    }

    @Override
	public Integer getMaxAge() {
        return maxAge;
    }

    @Override
	public void setMaxAge(Integer value) {
        this.maxAge = value;
    }

    @Override
	public String getGenderId() {
        return genderId;
    }

    @Override
	public void setGenderId(String value) {
        this.genderId = value;
    }

    @Override
	public List<String> getEducationLevels() {
    	if (educationLevels == null) {
            educationLevels = new ArrayList<String>();
        }
        return this.educationLevels;
    }

    @Override
	public List<String> getOccupationStatuses() {
        if (occupationStatuses == null) {
            occupationStatuses = new ArrayList<String>();
        }
        return this.occupationStatuses;
    }

    @Override
	public List<String> getVariables() {
        if (variables == null) {
            variables = new ArrayList<String>();
        }
        return this.variables;
    }

	@Override
	public List<String> getRegions() {
		if (regions == null) {
			regions = new ArrayList<String>();
        }
        return this.regions;
	}

	public CintBaseOperationResult validate() {
		CintBaseOperationResult result = null;
		
		if(null == countryId){
			logger.warn("validate : countryId is mandatory");
			result = new CintBaseOperationResult(CintErrorCode.CountryIsMandatory);
		}
		
		if(null == result && null != minAge && (minAge < 14 || minAge > 80)){
			logger.warn("validate : minAge is outOfRange");
			result = new CintBaseOperationResult(CintErrorCode.AgeIsOutOfRange);
		}
		
		if(null == result && null != maxAge && (maxAge < 14 || maxAge > 80)){
			logger.warn("validate : maxAge is outOfRange");
			result = new CintBaseOperationResult(CintErrorCode.AgeIsOutOfRange);
		}
		
		return result;
	}

	@Override
	public void setEducationLevels(List<String> educationLevels) {
		this.educationLevels = educationLevels;
		
	}

	@Override
	public void setOccupationStatuses(List<String> occupationStatuses) {
		this.occupationStatuses = occupationStatuses;
		
	}

	public String getTargetGroupDetails() {
		StringBuilder sb = new StringBuilder();
	    sb.append("country: ").append(getCountryId()).append("<br/>");
	    
		if(null != regions){
			for (String regionId : regions) {
				sb.append("region: ").append(regionId).append("<br/>");
			}
		}
		
	    if(null != minAge){
	    	sb.append("min-age: ").append(getMinAge()).append("<br/>");
	    }
	    if(null != maxAge){
	    	sb.append("max-age: ").append(getMaxAge()).append("<br/>");
	    }
	    if(null != genderId){
	    	sb.append("gender :").append(getGenderId()).append("<br/>");
	    }

	    if(null != educationLevels){
			for (String educationLevelId : educationLevels) {
				sb.append("education-level: ").append(educationLevelId).append("<br/>");
			}
		}
	    
	    if(null != occupationStatuses){
			for (String occupationStatusId : occupationStatuses) {
				sb.append("occupation-status: ").append(occupationStatusId).append("<br/>");
			}
		}
	    
	    if(null != variables){
			for (String variableId : variables) {
				sb.append("variable: ").append(variableId).append("<br/>");
			}
		}
	    
		return sb.toString();
	}
}
