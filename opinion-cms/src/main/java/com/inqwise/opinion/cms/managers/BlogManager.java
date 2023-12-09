package com.inqwise.opinion.cms.managers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang3.StringUtils;

import com.inqwise.opinion.cms.common.EntityType;
import com.inqwise.opinion.cms.common.ICategory;
import com.inqwise.opinion.cms.common.blog.Blogs;
import com.inqwise.opinion.cms.common.blog.IBlog;
import com.inqwise.opinion.cms.common.blog.IComment;
import com.inqwise.opinion.cms.common.blog.ICommentRequest;
import com.inqwise.opinion.cms.common.blog.INewCommentNotificationEmailData;
import com.inqwise.opinion.cms.common.blog.IPost;
import com.inqwise.opinion.cms.dao.Categories;
import com.inqwise.opinion.cms.dao.Tags;
import com.inqwise.opinion.cms.dao.blog.BlogsDataAccess;
import com.inqwise.opinion.cms.dao.blog.CommentsDataAccess;
import com.inqwise.opinion.cms.dao.blog.Posts;
import com.inqwise.opinion.cms.entities.CategoryEntity;
import com.inqwise.opinion.cms.entities.blog.BlogEntity;
import com.inqwise.opinion.cms.entities.blog.CommentEntity;
import com.inqwise.opinion.cms.entities.blog.PostEntity;
import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.dao.IDataFillable;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.library.common.IProduct;
import com.inqwise.opinion.library.common.basicTypes.EntityBox;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.common.users.IUser;
import com.inqwise.opinion.library.managers.ProductsManager;
import com.inqwise.opinion.library.managers.UsersManager;

public class BlogManager {
	
	private static ApplicationLog logger = ApplicationLog.getLogger(BlogManager.class);
	
	public static OperationResult<List<IPost>> getPosts(Integer top, Blogs blog){
		return PostEntity.getPosts(null, null, null, null, top, blog.getId());
	}
	
	public static OperationResult<List<IPost>> searchPosts(String tagName, String categoryName, 
													Integer archiveTotalMonth, String searchWord, Integer top,
													Blogs blog){
		return PostEntity.getPosts(tagName, categoryName, archiveTotalMonth, searchWord, top, blog.getId());
	}
	
	public static OperationResult<IPost> getPostByUrlTitle(String urlTitle, Integer totalMonth){
		return PostEntity.getPost(null, urlTitle, totalMonth);
	}
	
	private static void createComment(
			final Hashtable<Long, CommentEntity> commentsSet,
			final Hashtable<Long, CommentEntity> unprocessedCommentsSet,
			final List<IComment> comments, ResultSet reader)
			throws SQLException {
		CommentEntity comment = new CommentEntity(reader);
		if(null == comment.getParentId()){
			comments.add(comment);
		} else {
			// Search in processed set
			CommentEntity parentComment = commentsSet.get(comment.getParentId());
			if(null == parentComment){
				// Search in unprocessed set
				parentComment = unprocessedCommentsSet.get(comment.getParentId());
			}
			
			if(null == parentComment){
				unprocessedCommentsSet.put(comment.getId(), comment);
			} else {
				parentComment.addSubComment(comment);
			}
		}
		commentsSet.put(comment.getId(), comment);
	}
	
	public static OperationResult<List<IComment>> getComments(long postId){
		
		OperationResult<List<IComment>> result = null;
		try{
			final Hashtable<Long, CommentEntity> commentsSet = new Hashtable<Long, CommentEntity>();
			final Hashtable<Long, CommentEntity> unprocessedCommentsSet = new Hashtable<Long, CommentEntity>();
			final List<IComment> comments = new ArrayList<IComment>();
			IDataFillable<IComment> callback = new IDataFillable<IComment>() {
				
				@Override
				public IComment fill(ResultSet reader) throws Exception {
					while(reader.next()){
						createComment(commentsSet, unprocessedCommentsSet,
								comments, reader);
					}
					
					return null;
				}
			};
			
			// Data Access
			CommentsDataAccess.getCommentsReader(callback, postId);
			
			// Process unprocessed comments
			processUnprocessedComments(commentsSet, unprocessedCommentsSet);
			result = new OperationResult<List<IComment>>(comments);
		} catch(Exception ex){
			UUID errorId = logger.error(ex, "getComments() : Unexpected error occured.");
			result = new OperationResult<List<IComment>>(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}

	private static void processUnprocessedComments(
			final Hashtable<Long, CommentEntity> commentsSet,
			final Hashtable<Long, CommentEntity> unprocessedCommentsSet) {
		int previousSize = unprocessedCommentsSet.size();
		if(previousSize > 0){
			do{
				// Pull first unprocessed comment
				Long commentId = unprocessedCommentsSet.keys().nextElement();
				IComment comment = unprocessedCommentsSet.get(commentId);
				unprocessedCommentsSet.remove(commentId);
				
				// Search in processed set
				CommentEntity parentComment = commentsSet.get(comment.getParentId());
				if(null == parentComment){
					// Search in unprocessed set
					parentComment = unprocessedCommentsSet.get(comment.getParentId());
				}
				
				if(null != parentComment){
					parentComment.addSubComment(comment);
				}
			}while(unprocessedCommentsSet.size() > 0 && unprocessedCommentsSet.size() < previousSize);
		}
	}
	
	public static BaseOperationResult insertComment(final ICommentRequest request) throws ExecutionException{
		BaseOperationResult result = null;
		try {
			final long commentId = CommentsDataAccess.insertComment(request);
			result = new BaseOperationResult(commentId);
			
			// send email
			sendNewCommentNotification(request, commentId);
		} catch (DAOException e) {
			UUID errorId = logger.error(e, "insertComment() : Unexpected error occured.");
			result = new BaseOperationResult(ErrorCode.GeneralError, errorId);
		}
		return result;
	}

	private static void sendNewCommentNotification(
			final ICommentRequest request, final long commentId) throws ExecutionException {
		// collect data for notification
		BaseOperationResult collectResult = null;
		IProduct product = null;
		if(null == collectResult){
			product = ProductsManager.getCurrentProduct();
			if(null == product){
				collectResult = new BaseOperationResult(ErrorCode.NoResults);
			}
		}
		
		IBlog blog = null;
		if(null == collectResult){
			OperationResult<IBlog> blogResult = getBlog(request.getBlogId());
			if(blogResult.hasError()){
				collectResult = blogResult;
			} else {
				blog = blogResult.getValue();
				if(null == blog.getOwnerId()){
					logger.warn("No owner defined for blog: '%s'", request.getBlogId());
					collectResult = new BaseOperationResult(ErrorCode.GeneralError);
				}
			}
		}
		
		IUser user = null;
		if(null == collectResult){
			OperationResult<IUser> userResult = UsersManager.getUser(blog.getOwnerId(), product.getId());
			if(userResult.hasError()){
				collectResult = userResult;
				logger.warn("sendNewCommentNotification : getUser : NO results for userId: '%s', blogId: '%s'", blog.getOwnerId(), request.getBlogId());
			} else {
				user = userResult.getValue();
			}
		}
		
		if(null == collectResult){
			final IProduct finalProduct = product;
			final IUser owner = user;
			// send notification email
			INewCommentNotificationEmailData data = new INewCommentNotificationEmailData() {
				
				@Override
				public String getNoreplyEmail() {
					return finalProduct.getNoreplyEmail();
				}
				
				@Override
				public String getEmail() {
					return owner.getEmail();
				}
				
				@Override
				public String getContent() {
					return request.getContent();
				}
				
				@Override
				public long getCommentId() {
					return commentId;
				}
				
				@Override
				public String getAutorUrl() {
					return request.getAutorUrl();
				}
				
				@Override
				public String getAutorName() {
					return request.getAutorName();
				}
				
				@Override
				public String getAutorEmail() {
					return request.getAutorEmail();
				}

				@Override
				public String getFeedbackCaption() {
					return finalProduct.getFeedbackCaption();
				}
			};
			
			EmailsManager.sendNewCommentNotificationEmail(data);
		} else {
			
		}
	}
	
	public static OperationResult<IBlog> getBlog(int blogId){
		OperationResult<IBlog> result;
		try{
			final EntityBox<BlogEntity> blogBox = new EntityBox<BlogEntity>();
			IDataFillable<IBlog> callback = new IDataFillable<IBlog>() {
				
				@Override
				public IBlog fill(ResultSet reader) throws Exception {
					while(reader.next()){
						if(blogBox.hasValue()){
							throw new Exception("getBlog : Receaved more that one record;");
						}
						
						blogBox.setValue(new BlogEntity(reader));
					}
					return blogBox.getValue();
				}
			};
			
			BlogsDataAccess.getCommentsReader(callback, blogId);
			
			if(blogBox.hasValue()){
				result = new OperationResult<IBlog>(blogBox.getValue());
			} else {
				logger.warn("getBlog : No records received for blogId: '%s'", blogId);
				result = new OperationResult<IBlog>(ErrorCode.NoResults);
			}
		} catch (DAOException e) {
			UUID errorId = logger.error(e, "insertComment() : Unexpected error occured.");
			result = new OperationResult<IBlog>(ErrorCode.GeneralError, errorId);
		}
		return result;
	}
	
	public static OperationResult<Integer> createPost(int blogId, String title, String urlTitle, String content, Date postDate, Boolean isActive, Integer[] tags, Integer[] categories){
		OperationResult<Integer> result = new OperationResult<Integer>();
		try{
			
			
			/*
			System.out.println(blogId);
			System.out.println(title);
			System.out.println(urlTitle);
			System.out.println(content);
			System.out.println(postDate);
			System.out.println(isActive);
			System.out.println(StringUtils.join(tags, ","));
			System.out.println(categories.length);
			*/
			
			int postId = Posts.set(null, blogId, title, urlTitle, content, postDate, isActive, tags, categories);
			result.setValue(postId);
		} catch (DAOException e) {
			UUID errorId = logger.error(e, "insertPost : Unexpected error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		return result;
	}
	
	public static BaseOperationResult updatePost(int postId, String title, String urlTitle, String content, Date postDate, Boolean isActive, Integer[] tags, Integer[] categories){
		BaseOperationResult result = new BaseOperationResult();
		try{
			Posts.set(postId, null, title, urlTitle, content, postDate, isActive, tags, categories);
		} catch (DAOException e) {
			UUID errorId = logger.error(e, "updatePost : Unexpected error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		return result;
	}

	public static OperationResult<IPost> getPost(int id) {
		return PostEntity.getPost(id, null, null);
	}
	
	
}
