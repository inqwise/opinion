package com.inqwise.opinion.entities;

import java.sql.ResultSet;
import java.util.List;
import java.util.UUID;

import com.inqwise.opinion.infrastructure.dao.IDataFillable;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.common.IControlType;
import com.inqwise.opinion.dao.ControlTypes;

public class ControlTypeEntity extends BaseOperationResult implements IControlType {

	public static ApplicationLog logger = ApplicationLog.getLogger(ControlTypeEntity.class);
	private Integer id;
	private String title;
	private String description;
	
	public ControlTypeEntity(ResultSet reader) throws Exception {
		setId(reader.getInt("control_type_id"));
		setTitle(reader.getString("control_type_title"));
		setDescription(reader.getString("control_type_description"));
	}
	
	public static OperationResult<List<IControlType>> getControlTypes() {
		OperationResult<List<IControlType>> result;
		try {
			IDataFillable<IControlType> data = new IDataFillable<IControlType>()
			{
				public ControlTypeEntity fill(ResultSet reader) throws Exception
				{
					return new ControlTypeEntity(reader);
				}
			};
			result = new OperationResult<List<IControlType>>(ControlTypes.getControlTypes(data));
			
		} catch (Exception e) {
			UUID errorId = logger.error(e, "getControlTypes() : unexpected error occured");
			result = new OperationResult<List<IControlType>>(ErrorCode.GeneralError, errorId, e.toString());
		}
		
		return result;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
	
}
