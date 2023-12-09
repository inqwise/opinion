package com.inqwise.opinion.cms.dao.blog;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

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
import com.inqwise.opinion.cms.common.blog.IPost;

public class Posts {

	private static final String POST_URL_TITLE_PARAM = "$url_title";
	private static final String TAG_NAME_PARAM = "$tag_name";
	private static final String CATEGORY_NAME_PARAM = "$category_name";
	private static final String ARCHIVE_MONTHES_PARAM = "$monthes";
	private static final String SEARCH_WORD_PARAM = "$search_word";
	private static final String TOP_PARAM = "$top";
	private static final String BLOG_ID_PARAM = "$blog_id";
	private static final String POST_ID_PARAM = "$post_id";
	private static final String POST_TITLE_PARAM = "$post_title";
	private static final String POST_DATE_PARAM = "$post_date";
	private static final String ORDER_ID_PARAM = "$order_id";
	private static final String IS_ACTIVE_PARAM = "$is_active";
	private static final String TAGS_PARAM = "$tags";
	private static final String CATEGORIES_PARAM = "$categories";
	private static final String POST_CONTENT_PARAM = "$post_content";
	
	public static OperationResult<List<IPost>> getPosts(
			IDataFillable<IPost> data, String tagName, String categoryName,
			Integer archiveMonthes, String searchWord, Integer top, Integer blogId) throws DAOException {
		List<IPost> posts = new ArrayList<IPost>();
		BaseOperationResult result = fillPosts(data, posts, null, tagName,
				categoryName, archiveMonthes, searchWord, top, blogId, null);
		if(result.hasError()){
			return result.toErrorResult();
		} else {
			return new OperationResult<List<IPost>>(posts);
		}
	}
	
	public static OperationResult<IPost> getPost(
			IDataFillable<IPost> data, String urlTitle, Integer totalMonth, Integer postId) throws DAOException {
		return fillPosts(data, null, urlTitle, null, null, totalMonth, null, 1, null, postId);
	}
	
	private static OperationResult<IPost> fillPosts(IDataFillable<IPost> postData,
			List<IPost> posts, String urlTitle, String tagName, String categoryName,
			Integer archiveMonthes, String searchWord, Integer top, Integer blogId, Integer postId) throws DAOException {
		
		CallableStatement call = null;
		ResultSet resultSet = null;
		OperationResult<IPost> result = null;
		Connection connection = null;
		IPost post = null;
		Dictionary<Long, IPost> postsSet = null; 
		
		SqlParam[] params =  
		{
				new SqlParam(POST_URL_TITLE_PARAM, urlTitle),
				new SqlParam(TAG_NAME_PARAM, tagName),
				new SqlParam(CATEGORY_NAME_PARAM, categoryName),
				new SqlParam(ARCHIVE_MONTHES_PARAM, archiveMonthes),
				new SqlParam(SEARCH_WORD_PARAM, searchWord),
				new SqlParam(TOP_PARAM, top),
				new SqlParam(BLOG_ID_PARAM, blogId),
				new SqlParam(POST_ID_PARAM, postId)
        };
        
		
		try {
			
			Database factory = DAOFactory.getInstance(CmsConfiguration.getDatabaseName());
        	call = factory.GetProcedureCall("getBlogPosts", params);
        	connection = call.getConnection();
            resultSet = call.executeQuery();
            while (resultSet.next()) {
            	post = postData.fill(resultSet);
            	if(null != posts){
            		posts.add(post);
            	}
            	
            	if(null == postsSet){
        			postsSet = new Hashtable<Long, IPost>();
        		}
        		postsSet.put(post.getId(), post);
            } 
            
            resultSet.close();
            
            if(call.getMoreResults()){
            	resultSet = call.getResultSet();
				while (resultSet.next()) {
					Long actualPostId = ResultSetHelper.optLong(resultSet, "post_id");
					String tag = resultSet.getString("tag_name");
					post = postsSet.get(actualPostId);
					post.getTags().add(tag);
				}
            }
            
            resultSet.close();
            
            if(call.getMoreResults()){
            	resultSet = call.getResultSet();
				while (resultSet.next()) {
					Long faqId = ResultSetHelper.optLong(resultSet, "post_id");
					String actualCategoryName = resultSet.getString("category_name");
					post = postsSet.get(faqId);
					post.getCategories().add(actualCategoryName);
				}
            }
            
            if(null == post) {
            	result = new OperationResult<IPost>(ErrorCode.NoResults);
            } else 
            {
            	result = new OperationResult<IPost>(post);
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

	public static int set(Integer postId, Integer blogId, String postTitle, String postUrlTitle, String postContent, Date postDate, Boolean isActive, Integer[] tags, Integer[] categories) throws DAOException {
		Connection connection = null;	
    	CallableStatement call = null;
    	ResultSet resultSet = null;
		
		SqlParam[] params = {
				
				new SqlParam(BLOG_ID_PARAM, blogId),
				new SqlParam(POST_ID_PARAM, postId),
				new SqlParam(POST_TITLE_PARAM, postTitle),
				new SqlParam(POST_URL_TITLE_PARAM, postUrlTitle),
				new SqlParam(POST_CONTENT_PARAM, postContent),
				new SqlParam(POST_DATE_PARAM, postDate),
				new SqlParam(ORDER_ID_PARAM, null),
				new SqlParam(IS_ACTIVE_PARAM, isActive),
				new SqlParam(TAGS_PARAM, (null == tags ? null : StringUtils.join(tags, ","))),
				new SqlParam(CATEGORIES_PARAM, (null == categories ? null : StringUtils.join(categories, ","))),
        };
		
		try {
			
			Database factory = DAOFactory.getInstance(CmsConfiguration.getDatabaseName());
			call = factory.GetProcedureCall("setBlogPost", params);
            connection = call.getConnection();
            resultSet = call.executeQuery();
            
            if(resultSet.next()){
            	postId = resultSet.getInt("post_id");
            } else {
            	throw new Exception("No Results");
            }
            
            resultSet.close();
            return postId;
            
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}

}
