package com.inqwise.opinion.handlers;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.handlers.descriptors.DataPostmasterDescryptorBase;
import com.inqwise.opinion.infrastructure.common.IOperationResult;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.Convert;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.opinion.common.IPostmasterContext;

public abstract class HandlerBase extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6780760792768045531L;
	protected static ApplicationLog logger = ApplicationLog.getLogger(HandlerBase.class);
	
	protected void doRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//response.setContentType("application/json;charset=UTF-8");
		//response.setContentType(getContentType());
		response.setCharacterEncoding("UTF-8");

		String values = request.getParameter("rq");

		if (values == null) {
			logger.warn("doGet() : Not provided the mandatory parameter 'rq'");
			response.setContentType("application/json;charset=UTF-8");
			try {
				response.getWriter().write(BaseOperationResult.toJsonError(ErrorCode.ArgumentNull).toString());
				response.flushBuffer();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			values = values.trim();
			IPostmasterContext context = null;
			try {
				ServletOutputStream out = response.getOutputStream();
				IOperationResult result = null;
				context = new DataPostmasterDescryptorBase(request, response, getServletContext());
				
				if(getSignInType() != SignInType.BeforeLogin){
					IOperationResult validateResult = context.validateSignIn();
					if(null != validateResult && getSignInType() == SignInType.AfterLogin){
							result = validateResult;
					}
				}
				
				if( null == result) {
					try {
						JSONObject input = new JSONObject(values);
						byte[] output = process(input, context);
						if(null != output){
							out.write(output);
						}
					} catch(Throwable t){
						UUID errorId = logger.error(t, "Unexpected error occured, session: '%s', rq: '%s'", context.getSession().toJson(), values);
						result = new BaseOperationResult(ErrorCode.GeneralError, errorId);
					}
				} 
				
				if (null != result) {
					out.println(result.toJson().toString());
				}
				
				out.close();
			} catch (Throwable t) {
				Long userId = null;
				if(null != context){
					userId = context.getSession().getUserId();
				}
				logger.error(t, "doGet() : Unexpected error occured. userId: '%s'", userId);
			}
		}
	}
	
	
	
	protected abstract String getContentType(); 
	
	protected enum SignInType { AfterLogin, BeforeLogin, Mixed }
	
	protected abstract SignInType getSignInType();
	
	protected abstract byte[] process(JSONObject input, IPostmasterContext context) throws Exception;
	
	protected byte[] convertToBytsArray(String text){
		return text.getBytes();
	}
	
	protected byte[] convertToBytsArray(JSONObject jo){
		return jo.toString().getBytes();
	}
	
	protected enum NotSignInBehaviour { Error, RedirectLoginPage }
	
	protected abstract NotSignInBehaviour getNotSignInBehaviour();
	
	private Long optLong(String s){
		return Convert.toOptLong(s);
	}
}
