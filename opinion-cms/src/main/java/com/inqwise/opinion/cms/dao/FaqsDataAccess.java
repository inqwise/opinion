package com.inqwise.opinion.cms.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import com.inqwise.opinion.infrastructure.dao.Database;
import com.inqwise.opinion.infrastructure.dao.IDataFillable;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.library.dao.DAOFactory;
import com.inqwise.opinion.infrastructure.dao.DAOUtil;
import com.inqwise.opinion.library.dao.Databases;
import com.inqwise.opinion.infrastructure.dao.SqlParam;
import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;
import com.inqwise.opinion.cms.CmsConfiguration;
import com.inqwise.opinion.cms.common.ICategory;
import com.inqwise.opinion.cms.common.faq.IFaq;

public class FaqsDataAccess {
	private static final String TOP_PARAM = "$top";
	private static final String CATEGORY_NAME_PARAM = "$category_name";
	private static final String SEARCH_WORD_PARAM = "$search_word";

	public static OperationResult<List<IFaq>> getFaqs(
			IDataFillable<IFaq> data, String searchWord, String categoryName, Integer top) throws DAOException {
		List<IFaq> faqs = new ArrayList<IFaq>();
		BaseOperationResult result = fillFaqs(data, faqs, searchWord, categoryName, top);
		if(result.hasError()){
			return result.toErrorResult();
		} else {
			return new OperationResult<List<IFaq>>(faqs);
		}
	}
	
	private static OperationResult<IFaq> fillFaqs(IDataFillable<IFaq> data,
			List<IFaq> faqs, String searchWord, String categoryName, Integer top) throws DAOException {
		CallableStatement call = null;
		ResultSet resultSet = null;
		OperationResult<IFaq> result = null;
		Connection connection = null;
		IFaq faq = null;
		Dictionary<Long, IFaq> faqsSet = null; 
		
		SqlParam[] params = 
		{
				new SqlParam(SEARCH_WORD_PARAM, searchWord),
				new SqlParam(CATEGORY_NAME_PARAM, categoryName),
				new SqlParam(TOP_PARAM, top),
        };        
		
		try {
			
			Database factory = DAOFactory.getInstance(CmsConfiguration.getDatabaseName());
        	call = factory.GetProcedureCall("getFaqs", params);
        	connection = call.getConnection();
            resultSet = call.executeQuery();
            while (resultSet.next()) {
            	faq = data.fill(resultSet);
            	if(null != faqs){
            		faqs.add(faq);
            	}
            	
            	if(null == faqsSet){
            		faqsSet = new Hashtable<Long, IFaq>();
        		}
            	faqsSet.put(faq.getId(), faq);
            } 
            
            resultSet.close();
            
            if(call.getMoreResults()){
            	resultSet = call.getResultSet();
				while (resultSet.next()) {
					Long faqId = ResultSetHelper.optLong(resultSet, "faq_id");
					String actualCategoryName = resultSet.getString("category_name");
					faq = faqsSet.get(faqId);
					faq.getCategories().add(actualCategoryName);
				}
            }
            
            if(null == faq) {
            	result = new OperationResult<IFaq>(ErrorCode.NoResults);
            } else 
            {
            	result = new OperationResult<IFaq>(faq);
            }
            
			return result;
		
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}
	
	public static OperationResult<List<ICategory>> getCategories(IDataFillable<OperationResult<List<ICategory>>> callback) throws DAOException{
		CallableStatement call = null;
		Connection connection = null;
		ResultSet resultSet = null;
		
		SqlParam[] params =  
		{
				//new SqlParam(CATEGORY_ID_PARAM, themeId),
        };
		
		Database factory = DAOFactory.getInstance(CmsConfiguration.getDatabaseName());
    	try {
			call = factory.GetProcedureCall("getFaqCategories", params);
			connection = call.getConnection();
			return callback.fill(call.executeQuery());
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}
}
