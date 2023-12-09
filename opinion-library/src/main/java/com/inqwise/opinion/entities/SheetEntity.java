package com.inqwise.opinion.opinion.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.dao.IDataFillable;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;
import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;
import com.inqwise.opinion.library.common.accounts.IAccount;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.opinion.common.IControl;
import com.inqwise.opinion.opinion.common.IControlRequest;
import com.inqwise.opinion.opinion.common.ParentType;
import com.inqwise.opinion.opinion.common.opinions.IOpinion;
import com.inqwise.opinion.opinion.common.sheet.ISheet;
import com.inqwise.opinion.opinion.common.sheet.ISheetRequest;
import com.inqwise.opinion.opinion.dao.Sheets;
import com.inqwise.opinion.opinion.entities.controls.ControlEntity;
import com.inqwise.opinion.opinion.managers.ControlsManager;

public class SheetEntity implements ISheet {

	public static ApplicationLog logger = ApplicationLog.getLogger(SheetEntity.class);
	
	private Integer pageNumber;
	private Long id;
	private Long opinionId;
	private String title;
	private String description;
	private Long translationId;
	
	public SheetEntity(ResultSet reader) throws SQLException {
		setPageNumber(Integer.valueOf(reader.getInt("page_number")));
		setOpinionId(Long.valueOf(reader.getLong("opinion_id")));
		setId(Long.valueOf(reader.getLong("sheet_id")));
		setTitle(ResultSetHelper.optString(reader, "title"));
		setDescription(ResultSetHelper.optString(reader, "description"));
		setTranslationId(ResultSetHelper.optLong(reader, "translation_id"));
	}

	public SheetEntity(){}
	
	@Override
	public OperationResult<List<IControl>> getItems() {
		return ControlEntity.getControls(getId(), ParentType.Sheet.getValue(), getOpinionId(), null, getTranslationId(), null, null);		
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	@Override
	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setOpinionId(Long opinionId) {
		this.opinionId = opinionId;
	}

	public Long getOpinionId() {
		return opinionId;
	}

	@Override
	public BaseOperationResult createControl(IControlRequest request, IAccount account) {
		return ControlEntity.createControl(request);
	}

	public static OperationResult<ISheet> getSheet(Long sheetId, Long translationId, Long accountId) {
		OperationResult<ISheet> result = null;
		
		try{
			IDataFillable<ISheet> data = new IDataFillable<ISheet>()
			{
				public ISheet fill(ResultSet reader) throws Exception
				{
					return new SheetEntity(reader);
				}
			};
			
			result = Sheets.getSheet(sheetId, translationId, accountId, data);
		}
		catch(Exception ex){
			UUID errorId = logger.error(ex, "getSheet() : Error occured.");
			result = new OperationResult<ISheet>(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}
	
	public static OperationResult<List<ISheet>> getSheets(Long opinionId, Long translationId, Long accountId) {
		OperationResult<List<ISheet>> result = null;
		
		try{
			IDataFillable<ISheet> data = new IDataFillable<ISheet>()
			{
				public ISheet fill(ResultSet reader) throws Exception
				{
					return new SheetEntity(reader);
				}
			};
			
			result = Sheets.getSheets(opinionId, translationId, accountId, data);
		}
		catch(Exception ex){
			UUID errorId = logger.error(ex, "getSheets() : Error occured.");
			result = new OperationResult<List<ISheet>>(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}
	
	public static OperationResult<Long> createSheet(ISheetRequest request) {

		OperationResult<Long> result;
		try {
			result = Sheets.setSheet(request);
		} catch (DAOException e) {
			UUID errorId = logger.error(e, "createSheet() : Unexpected eror occured");
			result = new OperationResult<Long>(ErrorCode.GeneralError, errorId);
		}
				
		return result;
	}
	
	public static BaseOperationResult deleteSheet(Long id, Long accountId, long userId) {
		BaseOperationResult result;
		try {
			result = Sheets.deleteSheet(id, accountId, userId);
		} catch (DAOException e) {
			UUID errorId = logger.error(e, "deleteSheet() : Unexpected eror occured");
			result = new OperationResult<ISheet>(ErrorCode.GeneralError, errorId);
		}
				
		return result;
	}
	
	public static BaseOperationResult updateSheet(Long sheetId, Long accountId, Long translationId,
			String title, String description, long userId) {
		BaseOperationResult result;
		try {
			result = Sheets.updateSheet(sheetId, accountId, translationId, title, description, userId);
		} catch (DAOException e) {
			UUID errorId = logger.error(e, "updateSheet() : Unexpected eror occured");
			result = new OperationResult<ISheet>(ErrorCode.GeneralError, errorId);
		}
				
		return result;
	}
	
	@Override
	public OperationResult<Long> copyTo(final Long opinionId, final Long translationId,
									final Integer pageNumber, final String title,
									final String description, final String actionGuid,
									final long userId, final long accountId){
		
		OperationResult<Long> result = null;
		final SheetEntity s = this;
		ISheetRequest request = new ISheetRequest() {
			
			@Override
			public Long getTranslationId() {
				return translationId;
			}
			
			@Override
			public String getTitle() {
				return null == title ? s.getTitle() : title;
			}
			
			@Override
			public Integer getPageNumber() {
				return null == pageNumber ? s.getPageNumber() : pageNumber;
			}
			
			@Override
			public Long getOpinionId() {
				return null == opinionId ? s.getOpinionId() : opinionId;
			}
			
			@Override
			public String getDescription() {
				return null == description ? s.getDescription() : description;
			}
			
			@Override
			public String getActionGuid() {
				return actionGuid;
			}
			
			@Override
			public Long getAccountId() {
				return accountId;
			}

			@Override
			public long getUserId() {
				return userId;
			}
		};
		
		result = createSheet(request);
		Long copySheetId = null;
		if(result.hasValue()){
			copySheetId = result.getValue();
		}
		
		OperationResult<List<IControl>> controlsResult = null;
		if(!result.hasError()){
			controlsResult = getItems();
			if(controlsResult.hasError() && controlsResult.getError() != ErrorCode.NoResults){
				result = controlsResult.toErrorResult();
			}
		}
		
		List<IControl> controls = null;
		if(!result.hasError() && !controlsResult.hasError() /*NoResults*/){
			controls = controlsResult.getValue();
		}
		
		if(!result.hasError() && null != controls){
			for(IControl controlInterface : controls){
				ControlEntity control = (ControlEntity) controlInterface;
				OperationResult<Long> controlResult = control.copyTo(request.getOpinionId(), copySheetId,
															ParentType.Sheet.getValue(),
															null == translationId ? IOpinion.DEFAULT_TRANSLATION_ID : translationId, null,
															null, actionGuid, userId, accountId);
				if(controlResult.hasError()){
					result = controlResult.toErrorResult();
					break;
				}
			}
		}
		
		return result;
	}

	@Override
	public JSONObject toJson() throws JSONException {
		return new JSONObject()
		.put(JsonNames.PAGE_NUMBER, getPageNumber())
		.put(JsonNames.SHEET_ID, getId())
		.put(JsonNames.TITLE, getTitle())
		.put(JsonNames.DESCRIPTION, getDescription());
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

	public void setTranslationId(Long translationId) {
		this.translationId = translationId;
	}

	public Long getTranslationId() {
		return translationId;
	}

	@Override
	public JSONObject getExportJson() throws JSONException {
		JSONObject output = new JSONObject();
		output.put(JsonNames.TITLE, getTitle());
		output.put(JsonNames.DESCRIPTION, getDescription());
		output.put(JsonNames.CONTROLS, getControlsExportJson());
		
		return output;
	}

	private JSONArray getControlsExportJson() throws JSONException {
		JSONArray output = null;
		OperationResult<List<IControl>> controlsResult = ControlsManager.getControls(getId(), ParentType.Sheet, getTranslationId(), null, null);
		if(controlsResult.hasValue()){
			output = new JSONArray();
			List<IControl> controls = controlsResult.getValue();
			for (IControl control : controls) {
				output.put(control.getExportJson());
			}
		}
		
		return output;
	}
}
