package com.inqwise.opinion.infrastructure.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

import org.jooq.impl.DSL;
import org.json.JSONArray;
import org.json.JSONObject;

public class ResultSets {
	
	public static JSONArray parse(Connection connection, ResultSet resultSet){
		
		
		List<JSONObject> list = DSL.using(connection)
		.fetch(resultSet)
		.map(r -> {
			JSONObject obj = new JSONObject();
			
			for(var field : r.fields()) {
				obj.put(field.getName(), r.getValue(field));
			}
			return obj;
		});
		
		return new JSONArray(list);
		
	}
	
	
	//JSONArray result = ResultSets.parse(connection, resultSet);


}
