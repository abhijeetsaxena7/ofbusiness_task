package com.ofbusiness.task.config;

public interface Constants {
	public enum Messages{
		RECORD_NOT_FOUND(1,"Record not found"),
		UPDATED_SUCCESSFULLY(2,"Updated Successfully"),
		EMPTY_USERID(3,"UserId cannot be empty"),
		EMPTY_MESSAGE_CONTENT(3,"Empty Message Content Not Allowed"),
		DELETED_SUCCESSFULLY(4,"Deleted Successfully");
		int id;
		String message;
		
		Messages(int id,String message){
			this.id = id;
			this.message = message;
		}
		
		@Override
		public String toString() {
			return this.message;
		}		
	}

}
