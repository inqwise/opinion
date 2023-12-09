package com.inqwise.opinion.cms.entities.blog;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;
import com.inqwise.opinion.cms.common.blog.IComment;

public class CommentEntity implements IComment {
	private long id;
	private long postId;
	private String autorName;
	private Long autorId;
	private Date commentDate;
	private String content;
	private Long parentId;
	private ArrayList<IComment> comments;
	private String autorEmail;
	private String autorUrl;
	
	public CommentEntity(ResultSet reader) throws SQLException{
		setId(reader.getLong("comment_id"));
		setPostId(reader.getLong("post_id"));
		setAutorName(ResultSetHelper.optString(reader, "autor_name"));
		setAutorId(ResultSetHelper.optLong(reader, "autor_id"));
		setCommentDate(ResultSetHelper.optDate(reader, "comment_date"));
		setContent(ResultSetHelper.optString(reader, "comment_content"));
		setParentId(ResultSetHelper.optLong(reader, "parent_id"));
		setAutorEmail(ResultSetHelper.optString(reader, "autor_email"));
		setAutorUrl(ResultSetHelper.optString(reader, "autor_url"));
	}

	public void setPostId(long postId) {
		this.postId = postId;
	}

	@Override
	public long getPostId() {
		return postId;
	}

	public void setAutorName(String autorName) {
		this.autorName = autorName;
	}

	@Override
	public String getAutorName() {
		return autorName;
	}

	public void setAutorId(Long autorId) {
		this.autorId = autorId;
	}

	@Override
	public Long getAutorId() {
		return autorId;
	}

	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}

	@Override
	public Date getCommentDate() {
		return commentDate;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String getContent() {
		return content;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public long getId() {
		return id;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	@Override
	public Long getParentId() {
		return parentId;
	}

	public void addSubComment(IComment comment) {
		if(null == comments){
			comments = new ArrayList<IComment>();
			comments.add(comment);
		}
	}

	public void setAutorEmail(String autorEmail) {
		this.autorEmail = autorEmail;
	}

	@Override
	public String getAutorEmail() {
		return autorEmail;
	}

	public void setAutorUrl(String autorUrl) {
		this.autorUrl = autorUrl;
	}

	@Override
	public String getAutorUrl() {
		return autorUrl;
	}
	
	@Override
	public boolean HasComments(){
		return null != comments;
	}
	
	@Override
	public List<IComment> getComments(){
		return comments;
	}
}
