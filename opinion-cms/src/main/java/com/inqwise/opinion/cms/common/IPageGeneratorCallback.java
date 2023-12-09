package com.inqwise.opinion.cms.common;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;

public interface IPageGeneratorCallback {
	public void create(ResultSet reader) throws SQLException, ServletException, IOException;
	public void fillResources(ResultSet reader) throws SQLException;
}
