package com.inqwise.opinion.entities.analizeResults;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.common.ControlType;

public class AnalizeMatrixSubControlEntity extends AnalizeControlEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static ApplicationLog logger = ApplicationLog
	.getLogger(AnalizeMatrixSubControlEntity.class);
	
	@Override
	protected void fill(ResultSet reader, ControlType controlType)
			throws SQLException, Exception {
		// TODO Auto-generated method stub
		super.fill(reader, controlType);
	}
}
