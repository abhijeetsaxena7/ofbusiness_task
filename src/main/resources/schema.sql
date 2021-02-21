CREATE TABLE message_tbl(
	message_id int auto_increment,
	user_id varchar(16) not null,
	message varchar(250) not null,
	timestamp timestamp default CURRENT_TIMESTAMP,
	is_sent bit(1) default false,
	PRIMARY KEY(message_id)	
);