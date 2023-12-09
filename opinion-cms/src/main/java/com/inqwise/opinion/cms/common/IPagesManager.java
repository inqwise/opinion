package com.inqwise.opinion.cms.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.inqwise.opinion.library.common.errorHandle.OperationResult;

public interface IPagesManager {

	public abstract OperationResult<IPage> getPage(String pageUri, IPagesEnvironment environment);

	public abstract OperationResult<IPage> getPage(int pageId, IPagesEnvironment environment);

	public abstract OperationResult<IPage> getPage(String pageUri, final int languageId, final HttpServletRequest request,
			final HttpServletResponse response);

	public abstract OperationResult<IPage> getPage(int pageId, final int languageId, final HttpServletRequest request,
			final HttpServletResponse response);
	
}
