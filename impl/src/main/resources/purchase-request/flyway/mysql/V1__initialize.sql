create table pcr_purchase_request (
	id binary(16) not null,
	accepted_by_id varchar(50),
	accepted_by_name varchar(50),
	accepted_date datetime,
	canceled_date datetime,
	code varchar(20),
	committed_date datetime,
	completed_date datetime,
	created_by_id varchar(50),
	created_by_name varchar(50),
	created_date datetime,
	due_date datetime,
	last_modified_by_id varchar(50),
	last_modified_by_name varchar(50),
	last_modified_date datetime,
	project_id binary(16),
	receive_site_id binary(16),
	receive_station_id binary(16),
	receiver_id varchar(50),
	rejected_date datetime,
	rejected_reason varchar(50),
	remark varchar(50),
	requested_by_id varchar(50),
	requested_by_name varchar(50),
	status varchar(20),
	primary key (id)
) engine=InnoDB;

create table pcr_purchase_request_item (
	id binary(16) not null,
	created_by_id varchar(50),
	created_by_name varchar(50),
	created_date datetime,
	item_id binary(16),
	item_spec_id binary(16),
	last_modified_by_id varchar(50),
	last_modified_by_name varchar(50),
	last_modified_date datetime,
	quantity decimal(19,2),
	remark varchar(50),
	request_id binary(16),
	status varchar(20),
	primary key (id)
) engine=InnoDB;

create index IDXdxu050ydkak7gau4loa9fgi6c
	on pcr_purchase_request_item (request_id);
