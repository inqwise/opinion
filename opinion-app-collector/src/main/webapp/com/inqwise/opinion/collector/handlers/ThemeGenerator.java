/**
 * 
 */
package com.inqwise.opinion.collector.handlers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.opinion.common.ITheme;
import com.inqwise.opinion.opinion.common.OutputMode;
import com.inqwise.opinion.opinion.dao.ThemesDataAccess;
import com.inqwise.opinion.opinion.managers.ThemesManager;

/**
 * @author basil
 *
 */
public class ThemeGenerator extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8199435947067054516L;
	static ApplicationLog logger = ApplicationLog.getLogger(ThemeGenerator.class);
	
	public void init() {
		
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/css");
		String strThemeId = null;//request.getParameter("theme_id");
		String guid = request.getParameter("guid");
		String strModeId = request.getParameter("mode_id");
		try{
			int themeId = (null == strThemeId ? 0 : Integer.parseInt(strThemeId));
			OutputMode mode = (null == strModeId ? OutputMode.Answer : OutputMode.fromInt(Integer.parseInt(strModeId)));
			if (guid == null && null == strThemeId) {
				logger.warn("doGet() : guid or themeId is mandatoriy");
			} else {
				OperationResult<ITheme> themeResult = (null == strThemeId ? ThemesManager.get(guid, mode) : ThemesManager.get(themeId, null));
				if(!themeResult.hasError()){
					ITheme theme = themeResult.getValue();
					out.print(theme.getCssContent());
				} else if(ErrorCode.NoResults == themeResult.getError()) {
					logger.warn("No results for theme request. guid: '%s', themeId: '%s'", guid, themeId);
				} else {
					out.print(themeResult.toString());
				}
			}
		} catch (Throwable t) {
			logger.error(t, "doGet() : Failed to get CSS content for guid '%s' or themeId '%s'", guid, strThemeId);
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException {
	
	}
}
