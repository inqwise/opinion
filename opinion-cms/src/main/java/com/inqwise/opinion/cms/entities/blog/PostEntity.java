package com.inqwise.opinion.cms.entities.blog;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.inqwise.opinion.infrastructure.dao.IDataFillable;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;
import com.inqwise.opinion.cms.common.blog.IPost;
import com.inqwise.opinion.cms.dao.blog.Posts;

public class PostEntity implements IPost {
	public static ApplicationLog logger = ApplicationLog.getLogger(PostEntity.class);
	
	private long id;
	private String title;
	private String urlTitle;
	private String content;
	private Date postDate;
	private List<String> tags;
	private List<String> categories;
	private int countOfComments;
	private Date modifyDate;
	
	public PostEntity(ResultSet reader) throws SQLException{
		setId(reader.getLong("post_id"));
		setTitle(ResultSetHelper.optString(reader, "post_title"));
		setUrlTitle(ResultSetHelper.optString(reader, "post_url_title"));
		setContent(ResultSetHelper.optString(reader, "post_content"));
		setPostDate(ResultSetHelper.optDate(reader, "post_date"));
		tags = new ArrayList<String>();
		categories = new ArrayList<String>();
		setCountOfComments(reader.getInt("count_of_comments"));
		setModifyDate(ResultSetHelper.optDate(reader, "post_date"));
	}

	public void setId(long id) {
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see com.inqwise.opinion.opinion.entities.IPost#getId()
	 */
	public long getId() {
		return id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/* (non-Javadoc)
	 * @see com.inqwise.opinion.opinion.entities.IPost#getTitle()
	 */
	public String getTitle() {
		return title;
	}

	public void setUrlTitle(String urlTitle) {
		this.urlTitle = urlTitle;
	}

	/* (non-Javadoc)
	 * @see com.inqwise.opinion.opinion.entities.IPost#getUrlTitle()
	 */
	public String getUrlTitle() {
		return urlTitle;
	}

	public void setContent(String content) {
		this.content = content;
	}

	/* (non-Javadoc)
	 * @see com.inqwise.opinion.opinion.entities.IPost#getContent()
	 */
	public String getContent() {
		return content;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}

	/* (non-Javadoc)
	 * @see com.inqwise.opinion.opinion.entities.IPost#getPostDate()
	 */
	public Date getPostDate() {
		return postDate;
	}
	
	public static OperationResult<List<IPost>> getPosts(String tagName, String categoryName,
			Integer archiveMonthes, String searchWord, Integer top, Integer blogId){
		OperationResult<List<IPost>> result;
		try{
			IDataFillable<IPost> data = new IDataFillable<IPost>()
			{
				public IPost fill(ResultSet reader) throws Exception
				{
					return new PostEntity(reader);
				}
			};
			
			result = Posts.getPosts(data, tagName, categoryName, archiveMonthes, searchWord, top, blogId);
		}
		catch(Throwable ex){
			UUID errorId = logger.error(ex, "getPosts() : Error occured.");
			result = new OperationResult<List<IPost>>(ErrorCode.GeneralError, errorId);
		}
		return result;
	}
	
	public List<String> getTags(){
		return tags;
	}
	
	public static OperationResult<IPost> getPost(Integer id, String urlTitle, Integer totalMonth){
		OperationResult<IPost> result = null;
			
		try{
			IDataFillable<IPost> data = new IDataFillable<IPost>()
			{
				public IPost fill(ResultSet reader) throws Exception
				{
					return new PostEntity(reader);
				}
			};
			
			result = Posts.getPost(data, urlTitle, totalMonth, id);
		}
		catch(Exception ex){
			UUID errorId = logger.error(ex, "getPost() : Error occured.");
			result = new OperationResult<IPost>(ErrorCode.GeneralError, errorId);
		}
		
		return result; 
	}

	public List<String> getCategories() {
		return this.categories;
	}

	public void setCountOfComments(int countOfComments) {
		this.countOfComments = countOfComments;
	}

	@Override
	public int getCountOfComments() {
		return countOfComments;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

}
