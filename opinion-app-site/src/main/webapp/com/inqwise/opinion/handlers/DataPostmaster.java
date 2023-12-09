/**
 * 
 */
package com.inqwise.opinion.handlers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.inqwise.opinion.handlers.descriptors.DataPostmasterDescryptor;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;

/**
 * @author basil
 * 
 */
public class DataPostmaster extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5120518131918902703L;
	static ApplicationLog logger = ApplicationLog
	.getLogger(DataPostmaster.class);

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/json;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

		PrintWriter out = response.getWriter();
		ServletContext context = getServletContext();
		
		if(request.getParameter("rq") != null) {

			String values = request.getParameter("rq").trim();
			
			try {

				JSONObject source = new JSONObject(values);
				JSONArray methodNames = source.names();
				DataPostmasterDescryptor descryptor = new DataPostmasterDescryptor(
						out, request, response, context);
				
				JSONObject output = descryptor.invokeMethods(source, methodNames, null);
				if (output.length() > 0) {
					// Output
					out.println(output.toString());
				}

			} catch (Throwable t) {
				logger.error(t, "doGet() : Unexpected error occured.");
			}
			
		} else {
			logger.warn("doGet() : Not provided the mandatory parameter 'rq'");
		}

	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
	}

	public static void trace(StackTraceElement e[]) {
		boolean doNext = false;
		for (StackTraceElement s : e) {
			if (doNext) {
				
				return;
			}
			doNext = s.getMethodName().equals("getStackTrace");
		}
	}

}
