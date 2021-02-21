package com.ofbusiness.task.model;

import java.util.Date;

public class MessageModel {
	private int messageId;
	private String userId;
	private String message;
	private Date timestamp;
	private boolean isSent;
	public int getMessageId() {
		return messageId;
	}
	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public boolean getIsSent() {
		return isSent;
	}
	public void setIsSent(boolean isSent) {
		this.isSent = isSent;
	}
}
