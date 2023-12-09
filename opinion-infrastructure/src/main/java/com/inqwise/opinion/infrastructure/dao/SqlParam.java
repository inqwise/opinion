package com.inqwise.opinion.infrastructure.dao;

import java.io.UnsupportedEncodingException;
import java.sql.Types;

public class SqlParam {
	/** The name of the parameter, if any */
	private String name;

	/** SQL type constant from <code>java.sql.Types */
	private final int sqlType;

	private Object value = null;

	/** The scale to apply in case of a NUMERIC or DECIMAL type, if any */
	private Integer scale = -1;
	
	/**
	 * Create a new SqlParameter, supplying name and SQL type.
	 * @param name name of the parameter, as used in input and output maps
	 * @param sqlType SQL type of the parameter according to <code>java.sql.Types
	 */
	public SqlParam(String name, Object value, int sqlType) {
		this.name = name;
		this.value = value;
		this.sqlType = sqlType;
	}

	/**
	 * Create a new SqlParameter, supplying name and SQL type.
	 * @param name name of the parameter, as used in input and output maps
	 * @param sqlType SQL type of the parameter according to <code>java.sql.Types
	 * @param scale the number of digits after the decimal point
	 * (for DECIMAL and NUMERIC types)
	 */
	public SqlParam(String name, Object value, int sqlType, int scale) {
		this.name = name;
		this.value = value;
		this.sqlType = sqlType;
		this.scale = new Integer(scale);
	}

	/**
	 * Copy constructor.
	 * @param otherParam the SqlParameter object to copy from
	 */
	public SqlParam(SqlParam otherParam) {
		//Assert.notNull(otherParam, "SqlParameter object must not be null");
		this.name = otherParam.name;
		this.sqlType = otherParam.sqlType;
		this.value = otherParam.value;
		this.scale = otherParam.scale;
	}


	public SqlParam(String name, Object value){
		this.name = name;
		this.sqlType = Types.OTHER;
		this.value = value;
		this.scale = -1;
	}

	
	/**
	 * Return the name of the parameter.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Return the SQL type of the parameter.
	 */
	public int getSqlType() {
		return this.sqlType;
	}

	/**
	 * Return the scale of the parameter, if any.
	 */
	public Integer getScale() {
		return this.scale;
	}


	/**
	 * Return whether this parameter holds input values that should be set
	 * before execution even if they are <code>null.
	 * <p>This implementation always returns true.
	 */
	public boolean isInputValueProvided() {
		return true;
	}

	/**
	 * Return whether this parameter is an implicit return parameter used during the
	 * results preocessing of the CallableStatement.getMoreResults/getUpdateCount.
	 * <p>This implementation always returns false.
	 */
	public boolean isResultsParameter() {
		return false;
	}

	public void setValue(Object value) {
		/*if(value instanceof String){
			try {
				this.value = new String(((String)value).getBytes("UTF-8"), "ISO-8859-1");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				this.value = value;
			}
		} else*/ {
			this.value = value;
		}
	}

	public Object getValue() {
		return value;
	}
}
