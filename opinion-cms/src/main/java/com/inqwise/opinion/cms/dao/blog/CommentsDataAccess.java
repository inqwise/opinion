package com.inqwise.opinion.cms.dao.blog;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import com.inqwise.opinion.cms.CmsConfiguration;
import com.inqwise.opinion.cms.common.blog.IComment;
import com.inqwise.opinion.cms.common.blog.ICommentRequest;
import com.inqwise.opinion.infrastructure.dao.Database;
import com.inqwise.opinion.infrastructure.dao.IDataFillable;
import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.library.dao.DAOFactory;
import com.inqwise.opinion.infrastructure.dao.DAOUtil;
import com.inqwise.opinion.library.dao.Databases;
import com.inqwise.opinion.infrastructure.dao.SqlParam;

public class CommentsDataAccess {
	private static final String BLOG_ID_PARAM = "$blog_id";
	private static final String AUTOR_URL_PARAM = "$autor_url";
	private static final String AUTOR_EMAIL_PARAM = "$autor_email";
	private static final String COMMENT_CONTENT_PARAM = "$comment_content";
	private static final String AUTOR_NAME_PARAM = "$autor_name";
	private static final String AUTOR_ID_PARAM = "$autor_id";
	private static final String PARENT_ID_PARAM = "$parent_id";
	private static final String POST_ID_PARAM = "$post_id";

	public static void getCommentsReader(IDataFillable<IComment> callback, long postId) throws DAOException{
		Connection connection = null;	
    	CallableStatement call = null;
		ResultSet resultSet = null;
		
		SqlParam[] params = {
				new SqlParam(POST_ID_PARAM, postId),
        };
		
		try {
			
			Database factory = DAOFactory.getInstance(CmsConfiguration.getDatabaseName());
			call = factory.GetProcedureCall("getBlogComments", params);
            connection = call.getConnection();
            
            // comments
            resultSet = call.executeQuery();
            callback.fill(resultSet);
            resultSet.close();
            
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}
	
	public static long insertComment(ICommentRequest request) throws DAOException{
		Connection connection = null;	
    	CallableStatement call = null;
    	ResultSet resultSet = null;
		
		SqlParam[] params = {
				new SqlParam(POST_ID_PARAM, request.getPostId()),
				new SqlParam(PARENT_ID_PARAM, request.getParentId()),
				new SqlParam(AUTOR_ID_PARAM, request.getAutorId()),
				new SqlParam(AUTOR_NAME_PARAM, request.getAutorName()),
				new SqlParam(COMMENT_CONTENT_PARAM, request.getContent()),
				new SqlParam(AUTOR_EMAIL_PARAM, request.getAutorEmail()),
				new SqlParam(AUTOR_URL_PARAM, request.getAutorUrl()),
				new SqlParam(BLOG_ID_PARAM, request.getBlogId()),
        };
		
		try {
			
			Database factory = DAOFactory.getInstance(CmsConfiguration.getDatabaseName());
			call = factory.GetProcedureCall("setBlogComment", params);
            connection = call.getConnection();
            
            resultSet = call.executeQuery();
            long commentId = 0;
            if(resultSet.next()){
            	commentId = resultSet.getLong("comment_id");
            } else {
            	throw new Exception("No Results");
            }
            resultSet.close();
            return commentId;
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}
}
