package com.inqwise.opinion.cms.managers;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.inqwise.opinion.library.common.basicTypes.EntityBox;
import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.dao.IDataFillable;
import com.inqwise.opinion.infrastructure.dao.IResultSetCallback;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.cms.common.ITag;
import com.inqwise.opinion.cms.common.blog.Blogs;
import com.inqwise.opinion.cms.dao.Tags;
import com.inqwise.opinion.cms.entities.TagEntity;

public class TagsManager {
	static ApplicationLog logger = ApplicationLog.getLogger(TagsManager.class);
	
	public static OperationResult<List<ITag>> getBlogTags(Blogs blog){
		OperationResult<List<ITag>> result = null;
		final EntityBox<Long> totalBox = new EntityBox<Long>();
		final List<ITag> tags = new ArrayList<ITag>();
		try{
			IResultSetCallback callback = new IResultSetCallback() {
				@Override
				public void call(ResultSet reader, int generationId) throws Exception {
					if(generationId == 1){
						if(reader.next()){
							totalBox.setValue(reader.getLong("total_items"));
						}
					} else {
						while(reader.next()){
							tags.add(new TagEntity(reader, totalBox.getValue())); 
						}
					}
				}
			};
			
			Tags.getBlogTagsRecordSet(callback, blog.getId());
			
			if(null == tags){
				result = new OperationResult<List<ITag>>(ErrorCode.NoResults);
			} else {
				result = new OperationResult<List<ITag>>(tags);
			}
			
		} catch(Exception ex) {
			UUID errorTicket = logger.error(ex, "getBlogTags : Unexpected error occured");
			result = new OperationResult<List<ITag>>(ErrorCode.GeneralError, errorTicket);
		}
		
		return result;
	}
	
	public static BaseOperationResult deleteTag(int id){
		BaseOperationResult result = new BaseOperationResult();
		try{
			Tags.delete(id);
		} catch (DAOException e) {
			UUID errorId = logger.error(e, "deleteTag : Unexpected error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		return result;
	}

	public static BaseOperationResult updateTag(int id, String name){
		BaseOperationResult result = new BaseOperationResult();
		try{
			Tags.set(id, name);
		} catch (DAOException e) {
			UUID errorId = logger.error(e, "updateTag : Unexpected error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		return result;
	}

	public static OperationResult<Integer> createTag(String name){
		
		System.out.println(name);
		
		OperationResult<Integer> result = new OperationResult<Integer>();
		try{
			result.setValue(Tags.set(null, name));
		} catch (DAOException e) {
			UUID errorId = logger.error(e, "createTag : Unexpected error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		return result;
	}
}
