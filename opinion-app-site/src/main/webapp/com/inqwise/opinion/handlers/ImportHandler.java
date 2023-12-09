package com.inqwise.opinion.handlers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONArray;
import org.json.JSONObject;

import com.inqwise.opinion.handlers.descriptors.DataPostmasterDescryptorBase;
import com.inqwise.opinion.infrastructure.common.IOperationResult;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.library.common.accounts.IAccount;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.common.users.IUser;
import com.inqwise.opinion.opinion.common.IPostmasterContext;
import com.inqwise.opinion.opinion.common.opinions.IOpinion;
import com.inqwise.opinion.opinion.managers.OpinionsManager;

public class ImportHandler extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8875350364228001134L;
	protected static ApplicationLog logger = ApplicationLog.getLogger(ImportHandler.class);

	//@SuppressWarnings("unchecked")
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (!ServletFileUpload.isMultipartContent(request)) {
            throw new IllegalArgumentException("Request is not multipart, please 'multipart/form-data' enctype for your form.");
        }

        int maxFileSize = 100000;
        ServletContext servletContext = this.getServletConfig().getServletContext();
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        DiskFileItemFactory factory = new DiskFileItemFactory(maxFileSize, repository);
        
        ServletFileUpload uploadHandler = new ServletFileUpload(factory);
        uploadHandler.setSizeMax(maxFileSize);
        PrintWriter writer = response.getWriter();
        response.setContentType("application/json");
        JSONArray json = new JSONArray();
        JSONObject jo = new JSONObject();
        try {
        	
        	IPostmasterContext context = new DataPostmasterDescryptorBase(request, response, getServletContext());
			
			IOperationResult result = context.validateSignIn();
			IAccount account = null;
			IUser user = null;
			if(null == result) {
				user = context.getLoggedInUser().getValue();
				OperationResult<IAccount> accountResult = context.getAccount();
				if(accountResult.hasError()){
					result = accountResult;
				} else {
					account = accountResult.getValue();
				}
			}
			
			if(null != result){
				jo = result.toJson();
			} else {
				
				for (FileItemIterator iterator = uploadHandler.getItemIterator(request); iterator.hasNext();) {
					FileItemStream item = iterator.next();
					if (!item.isFormField()) {
						InputStream uploadedStream = item.openStream();
						
						JSONObject jsono = new JSONObject();
						BaseOperationResult processResult = processStream(uploadedStream, jsono, account.getId(), user.getUserId());
						if(processResult.hasError()){
							jsono = processResult.toJson();
						} else {
							jsono.put("name", JSONObject.NULL);
		                    jsono.put("size", JSONObject.NULL);
		                    //jsono.put("url", JSONObject.NULL);
		                    //jsono.put("thumbnail_url", JSONObject.NULL);
		                    //jsono.put("delete_url", JSONObject.NULL);
		                    //jsono.put("delete_type", JSONObject.NULL);
						}
	                    json.put(jsono);
	                    
	                    //...
	                    uploadedStream.close();
					}
					
				}
	        	
	        	jo.put("files", json);
			}
        } catch (FileUploadException e) {
    		logger.error(e, "doPost: Failed to upload file");
            throw new RuntimeException(e);
        } catch (Exception e) {
    		logger.error(e, "doPost: Unexpected error occured");
            throw new RuntimeException(e);
        } finally {
            writer.write(jo.toString());
            writer.close();
        }
    }

	private BaseOperationResult processStream(InputStream stream, JSONObject jsono, long accountId, Long userId) {
		
		BaseOperationResult result = new BaseOperationResult(); 
		try {
			String str = convertStreamToString(stream);
			JSONObject input = new JSONObject(str);
			IOpinion opinion;
			
			jsono.put(IOpinion.JsonNames.OPINION_ID, 1);
			jsono.put(IOpinion.JsonNames.OPINION_TYPE_ID, 1);
			return result;
			
			/*
			OperationResult<IOpinion> opinionResult = importOpinion(input, accountId, userId);
			if(opinionResult.hasError()){
				result.setError(opinionResult);
			} else {
				opinion = opinionResult.getValue();
				jsono.put(IOpinion.JsonNames.OPINION_ID, opinion.getId());
				jsono.put(IOpinion.JsonNames.OPINION_TYPE_ID, opinion.getOpinionType().getValue());
			}
			*/
			
		} catch (Throwable t){
			UUID errorId = logger.error(t, "processStream: Unexpected error occured");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}

	private OperationResult<IOpinion> importOpinion(JSONObject input, long accountId, Long userId) {
		
		OperationResult<IOpinion> result = new OperationResult<>();
		OperationResult<Long> importResult = OpinionsManager.importOpinion(input, accountId, userId);
		Long opinionId = null;
		if(importResult.hasError()){
			result.setError(importResult);
		} else {
			opinionId = importResult.getValue();
		}
		
		if(!result.hasError()){
			result = OpinionsManager.getOpinion(opinionId, null);
		}
		
		return result;
	}

	static String convertStreamToString(java.io.InputStream is) {
	    java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
	    String result = s.hasNext() ? s.next() : "";
	    s.close();
	    
	    return result;
	}
}