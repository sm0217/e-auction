CREATE TABLE IF NOT EXISTS PRODUCT_CATEGORY(id INT PRIMARY KEY, name VARCHAR(255));

--CREATE TABLE IF NOT EXISTS Products(
--id INT PRIMARY KEY AUTO_INCREMENT,
--name VARCHAR(255),
--short_description VARCHAR(255),
--detailed_description VARCHAR(255),
--category VARCHAR(255),
--starting_price VARCHAR(255),
--bid_end_date VARCHAR(255),
--seller VARCHAR(255),
--CONSTRAINT PROD_UNIQ UNIQUE (name)
--);
--
--CREATE TABLE IF NOT EXISTS SELLERS(
--id INT PRIMARY KEY AUTO_INCREMENT,
--first_name VARCHAR(255),
--surname VARCHAR(255),
--address VARCHAR(255),
--city VARCHAR(255),
--state VARCHAR(255),
--pin VARCHAR(255),
--phone_number VARCHAR(255),
--email_address VARCHAR(255),
--CONSTRAINT SELLER_UNIQ UNIQUE (email_address, phone_number)
--);

drop table if exists product CASCADE;
drop table if exists product_category CASCADE;
drop table if exists seller CASCADE;
drop sequence if exists hibernate_sequence;
create sequence hibernate_sequence start with 1 increment by 1;
create table BID (id bigint not null, email_address varchar(255) not null, product_id bigint, bid_amount INTEGER);