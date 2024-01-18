package com.inqwise.opinion.library.common;

import java.util.Date;

import org.json.JSONObject;

import com.google.common.base.MoreObjects;

public class MessagesModel {

	private Long messageId;
	private String messageName;
	private String messageContent;
	private Date activateDate;
	private Long userId;
	private String userName;
	private Date modifyDate;
	private Date closeDate;
	private Date excludeDate;

	private MessagesModel(Builder builder) {
		this.messageId = builder.messageId;
		this.messageName = builder.messageName;
		this.messageContent = builder.messageContent;
		this.activateDate = builder.activateDate;
		this.userId = builder.userId;
		this.userName = builder.userName;
		this.modifyDate = builder.modifyDate;
		this.closeDate = builder.closeDate;
		this.excludeDate = builder.excludeDate;
	}

	public static final class Keys{

		public static final String MESSAGE_ID = "message_id";
		public static final String MESSAGE_NAME = "message_name";
		public static final String MESSAGE_CONTENT = "message_content";
		public static final String ACTIVATE_DATE = "activate_date";
		public static final String USER_ID = "user_id";
		public static final String USER_NAME = "user_name";
		public static final String MODIFY_DATE = "modify_date";
		public static final String CLOSE_DATE = "close_date";
		public static final String EXCLUDE_DATE = "exclude_date";
		
	}

	public MessagesModel(JSONObject json) {
		messageId = json.optLongObject(Keys.MESSAGE_ID);
		messageName = json.optString(Keys.MESSAGE_NAME);
		messageContent = json.optString(Keys.MESSAGE_CONTENT);
		
		var activateDateInMs = json.optLongObject(Keys.ACTIVATE_DATE);
		if(null != activateDateInMs) {
			activateDate = new Date(activateDateInMs);
		}
		
		userId = json.optLongObject(Keys.USER_ID);
		userName = json.optString(Keys.USER_NAME);
		
		var modifyDateInMs = json.optLongObject(Keys.MODIFY_DATE);
		if(null != modifyDateInMs) {
			modifyDate = new Date(modifyDateInMs);
		}
		
		var closeDateInMs = json.optLongObject(Keys.CLOSE_DATE);
		if(null != closeDateInMs) {
			closeDate = new Date(closeDateInMs);
		}
		
		var excludeDateInMs = json.optLongObject(Keys.EXCLUDE_DATE);
		if(null != excludeDateInMs) {
			excludeDate = new Date(excludeDateInMs);
		}
	
	}
	
	public Long getMessageId() {
		return messageId;
	}

	public String getMessageName() {
		return messageName;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public Date getActivateDate() {
		return activateDate;
	}

	public Long getUserId() {
		return userId;
	}

	public String getUserName() {
		return userName;
	}
	
	public Date getModifyDate() {
		return modifyDate;
	}
	
	public Date getCloseDate() {
		return closeDate;
	}
	
	public Date getExcludeDate() {
		return excludeDate;
	}
	
	public JSONObject toJson() {
		var json = new JSONObject();
		json.put(Keys.MESSAGE_ID, messageId);
		json.put(Keys.MESSAGE_NAME, messageName);
		json.put(Keys.MESSAGE_CONTENT, messageContent);
		json.put(Keys.ACTIVATE_DATE, activateDate);
		json.put(Keys.USER_ID, userId);
		json.put(Keys.USER_NAME, userName);
		json.put(Keys.MODIFY_DATE, modifyDate);
		json.put(Keys.CLOSE_DATE, closeDate);
		json.put(Keys.EXCLUDE_DATE, excludeDate);
		return json;
	}
	
	public static Builder builder() {
		return new Builder();
	}

	public static Builder builderFrom(MessagesModel messagesModel) {
		return new Builder(messagesModel);
	}

	public static final class Builder {
		private Long messageId;
		private String messageName;
		private String messageContent;
		private Date activateDate;
		private Long userId;
		private String userName;
		private Date modifyDate;
		private Date closeDate;
		private Date excludeDate;

		private Builder() {
		}

		private Builder(MessagesModel messagesModel) {
			this.messageId = messagesModel.messageId;
			this.messageName = messagesModel.messageName;
			this.messageContent = messagesModel.messageContent;
			this.activateDate = messagesModel.activateDate;
			this.userId = messagesModel.userId;
			this.userName = messagesModel.userName;
			this.modifyDate = messagesModel.modifyDate;
			this.closeDate = messagesModel.closeDate;
			this.excludeDate = messagesModel.excludeDate;
		}

		public Builder withMessageId(Long messageId) {
			this.messageId = messageId;
			return this;
		}

		public Builder withMessageName(String messageName) {
			this.messageName = messageName;
			return this;
		}

		public Builder withMessageContent(String messageContent) {
			this.messageContent = messageContent;
			return this;
		}

		public Builder withActivateDate(Date activateDate) {
			this.activateDate = activateDate;
			return this;
		}

		public Builder withUserId(Long userId) {
			this.userId = userId;
			return this;
		}

		public Builder withUserName(String userName) {
			this.userName = userName;
			return this;
		}

		public Builder withModifyDate(Date modifyDate) {
			this.modifyDate = modifyDate;
			return this;
		}

		public Builder withCloseDate(Date closeDate) {
			this.closeDate = closeDate;
			return this;
		}

		public Builder withExcludeDate(Date excludeDate) {
			this.excludeDate = excludeDate;
			return this;
		}

		public MessagesModel build() {
			return new MessagesModel(this);
		}
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("messageId", messageId).add("messageName", messageName)
				.add("messageContent", messageContent).add("activateDate", activateDate).add("userId", userId)
				.add("userName", userName).add("modifyDate", modifyDate).add("closeDate", closeDate)
				.add("excludeDate", excludeDate).toString();
	}

}
