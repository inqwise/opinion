package com.inqwise.opinion.infrastructure.dao;

public interface IDataAccessBridge extends IResultSetCallback {
	void fillParams(SqlParams params);
}
