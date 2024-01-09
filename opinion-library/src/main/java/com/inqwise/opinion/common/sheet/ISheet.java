package com.inqwise.opinion.common.sheet;
import java.util.Collection;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.library.common.accounts.IAccount;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.common.IControl;
import com.inqwise.opinion.common.IControlRequest;

public interface ISheet {
	
	final class JsonNames {
		public static final String CONTROLS = "controls";
		public static final String DESCRIPTION = "description";
		public static final String TITLE = "title";
		public static final String PAGE_NUMBER = "pageNumber";
		public static final String SHEET_ID = "sheetId";
	}
	
	public OperationResult<List<IControl>> getItems();

	public Integer getPageNumber();
	public Long getId();
	BaseOperationResult createControl(IControlRequest request, IAccount account);

	public JSONObject toJson() throws JSONException ;

	public JSONObject getExportJson() throws JSONException;

	public abstract OperationResult<Long> copyTo(final Long opinionId, final Long translationId, final Integer pageNumber,
			final String title, final String description, final String actionGuid, final long userId, final long accountId);
}
