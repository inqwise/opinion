package com.inqwise.opinion.infrastructure.dao;

import java.sql.ResultSet;
import java.sql.SQLException;


public interface IDataFillable<T> {
	T fill(ResultSet reader) throws Exception;
}
