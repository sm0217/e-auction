drop table if exists users CASCADE;
create table users (email_address varchar(255) not null, address varchar(255), city varchar(255), first_name varchar(30), phone_number varchar(255), pin varchar(255), state varchar(255), surname varchar(30), role varchar(255), password varchar(255), primary key (email_address));
