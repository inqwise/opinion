package com.inqwise.opinion.managers;

import java.util.List;
import java.util.UUID;

import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.common.opinions.ISurvey;
import com.inqwise.opinion.common.sheet.ISheet;
import com.inqwise.opinion.common.sheet.ISheetRequest;
import com.inqwise.opinion.common.sheet.StartSheetIndexData;
import com.inqwise.opinion.dao.Sheets;
import com.inqwise.opinion.entities.SheetEntity;

public class SheetsManager {
	private static ApplicationLog logger = ApplicationLog.getLogger(SheetsManager.class);
	public static BaseOperationResult orderSheets(String sheetIds, Long accountId, long userId){
	
		BaseOperationResult result;
		try {
			result = Sheets.order(sheetIds, accountId, userId);
		} catch (Exception ex) {
			UUID errorId = logger.error(ex, "orderSheets() : Error occured.");
			result = new OperationResult<ISurvey>(ErrorCode.GeneralError, errorId);
		}
		return result;
	}
	
	public static OperationResult<List<ISheet>> getSheets(long opinionId, Long translationId, Long accountId){
		return SheetEntity.getSheets(opinionId, translationId, accountId);
	}
	
	public static OperationResult<ISheet> getSheet(long sheetId, Long translationId, Long accountId){
		return SheetEntity.getSheet(sheetId, translationId, accountId);
	}
	
	public static OperationResult<Long> createSheet(ISheetRequest request){
		return SheetEntity.createSheet(request);
	}
	
	public static BaseOperationResult deleteSheet(Long sheetId, Long accountId, long userId){
		return SheetEntity.deleteSheet(sheetId, accountId, userId);
	}
	
	public static OperationResult<StartSheetIndexData> getStartIndexOfSheet(Long sheetId){
		OperationResult<StartSheetIndexData> result;
		try {
			result = Sheets.getStartIndexOfSheet(sheetId);
		} catch (DAOException e) {
			UUID errorId = logger.error(e, "getStartIndexOfSheet() : Unexpected eror occured");
			result = new OperationResult<StartSheetIndexData>(ErrorCode.GeneralError, errorId);
		}
				
		return result;
	}
	
	public static BaseOperationResult updateSheet(Long sheetId, Long accountId, Long translationId, String title,
			String description, long userId){
		return SheetEntity.updateSheet(sheetId, accountId, translationId, title, description, userId);
	}
	
	public static OperationResult<Long> createDefaultSheet(final Long accountId,
			final String actionGuid, final long userId, final long opinionId, final Long translationId){
		return createSheet(new ISheetRequest() {
			
			@Override
			public Long getTranslationId() {
				return translationId;
			}
			
			@Override
			public String getTitle() {
				return null;
			}
			
			@Override
			public Integer getPageNumber() {
				return null;
			}
			
			@Override
			public Long getOpinionId() {
				return opinionId;
			}
			
			@Override
			public String getDescription() {
				return null;
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
		});
	}
	
	public static OperationResult<Long> copyTo(Long sheetId, Long opinionId,
			Long translationId, Integer pageNumber, String title, String description, long userId, long accountId) throws Exception{
		
		OperationResult<Long> result = null;
		OperationResult<ISheet> getSheetResult = SheetEntity.getSheet(sheetId, translationId, accountId);
		if(getSheetResult.hasError()){
			result = getSheetResult.toErrorResult();
		}
		
		if (null == result){
			UUID actionGuid = UUID.randomUUID();
			ISheet sheet;
			sheet = getSheetResult.getValue();
			result = sheet.copyTo(opinionId, translationId, pageNumber, title, description, actionGuid.toString(), userId, accountId);
		}
		
		return result;
	}
}
