--auto-increment from 1
--create table todo (id bigserial not null, activity_name varchar(255) not null, category varchar(255), label varchar(255),progress varchar(255) not null, primary key (id));

--if custom auto-increment start value
create sequence todo_id_seq start 10001;
create table todo (id bigint not null DEFAULT nextval('todo_id_seq'), activity_name varchar(255) not null, category varchar(255), label varchar(255),progress varchar(255) not null, primary key (id));

--if use @SequenceGenerator in Entity
--create table todo (id bigint not null , activity_name varchar(255) not null, category varchar(255), label varchar(255),progress varchar(255) not null, primary key (id));

--create sequence todo_id_seq start 10001;
