drop table pcr_purchase_request_item;
ALTER TABLE pcr_purchase_request DROP name;

ALTER TABLE pcr_purchase_request ADD item_id binary(16);
ALTER TABLE pcr_purchase_request ADD item_spec_id binary(16);
ALTER TABLE pcr_purchase_request ADD item_spec_code varchar(20);
ALTER TABLE pcr_purchase_request ADD quantity decimal(19,2);
