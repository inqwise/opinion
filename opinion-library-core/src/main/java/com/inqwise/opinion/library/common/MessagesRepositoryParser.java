package com.inqwise.opinion.library.common;

import java.util.Date;

import org.json.JSONObject;

public class MessagesRepositoryParser {

	public MessagesModel parse(JSONObject json) {
		return MessagesModel.builder()
				.withMessageId(json.optLongObject("message_id"))
				.withMessageName(json.optString("message_name"))
				.withMessageContent(json.optString("message_content"))
				.withActivateDate((Date) json.opt("activate_date"))
				.build();
	}
}