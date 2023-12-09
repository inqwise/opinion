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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;

/**
 * @author basil
 * 
 */
public class DataPostmaster extends HttpServlet {

	static ApplicationLog logger = ApplicationLog.getLogger(DataPostmaster.class);
		
	public void init() {
		
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/json;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

		PrintWriter out = response.getWriter();
		ServletContext context = getServletContext();
		
		String values = org.apache.commons.lang3.StringUtils.trimToNull(request.getParameter("rq"));
		String callbackName = org.apache.commons.lang3.StringUtils.trimToNull(request.getParameter("callback"));
		
		if (values == null) {
			logger.warn("doGet() : Not provided the mandatory parameter 'rq'");
		} else {
			try {

				JSONObject source = new JSONObject(values);
				JSONArray methodNames = source.names();
				JSONObject output = new JSONObject();
				DataPostmasterDescryptor descryptor = new DataPostmasterDescryptor(out, request, response, context);
				descryptor.invokeMethods(source, methodNames, null, output);
				if(output.length() > 0){
					// Output
					if(null == callbackName){
						out.println(output.toString());
					} else {
						out.println(callbackName + "(" + output + ")");
					}
				}
			} catch (JSONException ex){
				logger.error(ex, "doGet() : Unexpected error occured. rq:'%s'", values);
			} catch (Throwable t) {
				logger.error(t, "doGet() : Unexpected error occured.");
			}
		}

	}

	/*
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
	}*/
}
