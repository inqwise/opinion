package com.inqwise.opinion.library.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FileSystemTemplate {
	Integer id;
	String name;
	String value;
	
	FileSystemTemplate(ResultSet reader) throws SQLException{
		id = reader.getInt("template_id");
		name = reader.getString("template_name");
		value = reader.getString("template_value");
	} 
	
	public Integer getId(){
		return id;
	}
	
	public String getName(){
		return name;
	}
	
	public String getValue(){
		return value;
	}
}
