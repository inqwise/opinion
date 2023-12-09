package com.inqwise.opinion.cms.managers;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.inqwise.opinion.cms.common.EntityType;
import com.inqwise.opinion.cms.common.ICategory;
import com.inqwise.opinion.cms.common.blog.Blogs;
import com.inqwise.opinion.cms.dao.Categories;
import com.inqwise.opinion.cms.entities.CategoryEntity;
import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.dao.IDataFillable;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;

public class CategoriesManager {
	private static ApplicationLog logger = ApplicationLog.getLogger(CategoriesManager.class);
	
	public static OperationResult<Integer> createCategory(String name){
		OperationResult<Integer> result = new OperationResult<Integer>();
		try{
			result.setValue(Categories.set(null, name));
		} catch (DAOException e) {
			UUID errorId = logger.error(e, "createCategory : Unexpected error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		return result;
	}
	
	public static BaseOperationResult updateCategory(int id, String name){
		BaseOperationResult result = new BaseOperationResult();
		try{
			Categories.set(id, name);
		} catch (DAOException e) {
			UUID errorId = logger.error(e, "updateCategory : Unexpected error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		return result;
	}
	
	public static BaseOperationResult deleteCategory(int id){
		BaseOperationResult result = new BaseOperationResult();
		try{
			Categories.delete(id);
		} catch (DAOException e) {
			UUID errorId = logger.error(e, "deleteCategory : Unexpected error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		return result;
	}
	
	public static OperationResult<List<ICategory>> getCategories(Blogs blog){
		
		 OperationResult<List<ICategory>> result = null;
		try{
			logger.debug("getCategories");
			IDataFillable<OperationResult<List<ICategory>>> callback = new IDataFillable<OperationResult<List<ICategory>>>() {
				@Override
				public OperationResult<List<ICategory>> fill(ResultSet reader) throws Exception {
					OperationResult<List<ICategory>> result = null;
					List<ICategory> categories = null;
					while(reader.next()){
						if(null == categories){
							categories = new ArrayList<ICategory>();
						}
						categories.add(new CategoryEntity(reader)); 
					}
					
					if(null == categories){
						result = new OperationResult<List<ICategory>>(ErrorCode.NoResults);
					} else {
						result = new OperationResult<List<ICategory>>(categories);
					}
					
					return result;
				}
			};
			
			result = Categories.getCategories(callback, EntityType.Post.getValue(), blog.getId());
		} catch(Exception ex) {
			UUID errorTicket = logger.error(ex, "getCategories : Unexpected error occured");
			result = new OperationResult<List<ICategory>>(ErrorCode.GeneralError, errorTicket);
		}
		
		return result;
	}
	

}
