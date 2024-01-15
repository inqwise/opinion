package com.inqwise.opinion.library.common;

import java.util.Date;

import org.json.JSONObject;

import com.google.common.base.MoreObjects;

public class MessagesModel {

	private Long messageId;
	private String messageName;
	private String messageContent;
	private Date activateDate;

	private MessagesModel(Builder builder) {
		this.messageId = builder.messageId;
		this.messageName = builder.messageName;
		this.messageContent = builder.messageContent;
		this.activateDate = builder.activateDate;
	}
	
	public static final class Keys{

		public static final String MESSAGE_ID = "message_id";
		public static final String MESSAGE_NAME = "message_name";
		public static final String MESSAGE_CONTENT = "message_content";
		public static final String ACTIVATE_DATE = "activate_date";
		
	}

	public MessagesModel(JSONObject json) {
		messageId = json.optLongObject(Keys.MESSAGE_ID);
		messageName = json.optString(Keys.MESSAGE_NAME);
		messageContent = json.optString(Keys.MESSAGE_CONTENT);
		activateDate = (Date) json.opt(Keys.ACTIVATE_DATE);
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

	public JSONObject toJson() {
		var json = new JSONObject();
		json.put(Keys.MESSAGE_ID, messageId);
		json.put(Keys.MESSAGE_NAME, messageName);
		json.put(Keys.MESSAGE_CONTENT, messageContent);
		json.put(Keys.ACTIVATE_DATE, activateDate);
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

		private Builder() {
		}

		private Builder(MessagesModel messagesModel) {
			this.messageId = messagesModel.messageId;
			this.messageName = messagesModel.messageName;
			this.messageContent = messagesModel.messageContent;
			this.activateDate = messagesModel.activateDate;
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

		public MessagesModel build() {
			return new MessagesModel(this);
		}
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("messageId", messageId).add("messageName", messageName)
				.add("messageContent", messageContent).add("activateDate", activateDate).toString();
	}

}
