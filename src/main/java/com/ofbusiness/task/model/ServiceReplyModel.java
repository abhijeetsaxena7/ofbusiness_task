package com.ofbusiness.task.model;

import com.ofbusiness.task.config.Constants;

public class ServiceReplyModel {
	private Object data;
	private boolean isError;
	private Enum<Constants.Messages> error;

	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public boolean isError() {
		return isError;
	}
	public void setError(boolean isError) {
		this.isError = isError;
	}
	public Enum<Constants.Messages> getError() {
		return error;
	}
	
	public void setError(Enum<Constants.Messages> error) {
		this.error = error;
		this.isError =true;
	}
}
