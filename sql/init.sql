drop table user;
create table user(id INTEGER GENERATED BY DEFAULT AS IDENTITY, name varchar(100) NOT NULL,  loginName varchar(100) NOT NULL, password varchar(100) NOT NULL, email varchar(50), status integer NOT NULL);
insert into user values (1,'管理员1','admin1','e10adc3949ba59abbe56e057f20f883e','a@b.c',1);
insert into user values (2,'管理员2','admin2','e10adc3949ba59abbe56e057f20f883e','a@b.c',1);

drop table role;
create table role(id INTEGER GENERATED BY DEFAULT AS IDENTITY, name varchar(100) NOT NULL, permission varchar(100), status integer NOT NULL);
insert into role values (1,'admin',null,1);

drop table r_user_role;
create table r_user_role(id INTEGER GENERATED BY DEFAULT AS IDENTITY, userid INTEGER NOT NULL, roleid  INTEGER NOT NULL, status integer NOT NULL);
insert into role values (1,1,1,1);
insert into role values (1,2,1,1);

drop table activity;
create table activity(id INTEGER GENERATED BY DEFAULT AS IDENTITY, sponsorId varchar(36) NOT NULL, title varchar(100) NOT NULL,  content varchar(1000), startdate date, status integer NOT NULL);
insert into activity values (1,1,'活动1','ejhrgr sdfa fejhrgra ejhrgr sdejhrg',null,1);
insert into activity values (2,1,'活动2','asd fas ejhrgr feiwdq dfasf asdf',null,1);