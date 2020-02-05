create table usr
(
	id int auto_increment
		primary key,
	account_id varchar(100) not null,
	name varchar(50) not null,
	token char(36) null,
	gmt_create bigint null,
	gmt_modified bigint null,
	bio varchar(256) null
);