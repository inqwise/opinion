package com.inqwise.opinion.opinion.common.opinions;

import java.util.List;

import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.opinion.common.sheet.ISheet;

public interface ISheetsContainer {

	public abstract OperationResult<List<ISheet>> getSheets();

	public abstract BaseOperationResult createSheet(Integer pageNumber,
			Long accountId, String title, String description,
			String actionGuid, long userId);

	public abstract BaseOperationResult createSheetInTheEnd(Long accountId,
			String title, String description, long userId);

}