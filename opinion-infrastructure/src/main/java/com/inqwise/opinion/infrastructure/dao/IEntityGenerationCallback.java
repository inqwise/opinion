package com.inqwise.opinion.infrastructure.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IEntityGenerationCallback<T,TId> {
	void create(ResultSet reader) throws SQLException;
	T get(TId id);
}
