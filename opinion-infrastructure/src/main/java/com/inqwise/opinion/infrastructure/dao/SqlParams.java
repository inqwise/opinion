package com.inqwise.opinion.infrastructure.dao;

import java.util.HashMap;
import java.util.Iterator;

public class SqlParams implements Iterable<SqlParam> {

	private HashMap<String, SqlParam> paramsSet;
	public SqlParams() {
		paramsSet = new HashMap<String, SqlParam>();
	}
	
	public SqlParams(SqlParam... defaultParams){
		this();
		putAll(defaultParams);
	}

	public void putAll(SqlParam... params){
		for (SqlParam sqlParam : params) {
			paramsSet.put(sqlParam.getName(), sqlParam);
		}
	}
	
	public SqlParam put(String name, Object value){
		SqlParam param = paramsSet.get(name);
		if(null == param){
			paramsSet.put(name, new SqlParam(name, value));
		} else {
			param.setValue(value);
		}
		return param;
	}
	
	public SqlParam put(SqlParam param){
		return paramsSet.put(param.getName() ,param);
	}
	
	public SqlParam[] toArray(){
		return paramsSet.values().toArray(new SqlParam[paramsSet.size()]);
	}

	@Override
	public Iterator<SqlParam> iterator() {
		return paramsSet.values().iterator();
	}
}
