package com.inqwise.opinion.common.opinions;

public enum OpinionsOrderBy {
	InsertDate("insert_date"),
	ModifyDate("modify_date");
	
	private String value;
	
	public String getValue(){
		return value;
	}
	
	private OpinionsOrderBy(String value) {
		this.value = value;  
	}
	
}
