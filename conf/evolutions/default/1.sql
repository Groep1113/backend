# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table role (
  id                            integer auto_increment not null,
  name                          varchar(255),
  constraint uq_role_name unique (name),
  constraint pk_role primary key (id)
);

create table role_user (
  role_id                       integer not null,
  user_id                       integer not null,
  constraint pk_role_user primary key (role_id,user_id)
);

create table user (
  id                            integer auto_increment not null,
  first_name                    varchar(255),
  last_name                     varchar(255),
  email                         varchar(255),
  password                      varchar(255),
  date_of_birth                 date,
  constraint uq_user_email unique (email),
  constraint pk_user primary key (id)
);

create index ix_role_user_role on role_user (role_id);
alter table role_user add constraint fk_role_user_role foreign key (role_id) references role (id) on delete restrict on update restrict;

create index ix_role_user_user on role_user (user_id);
alter table role_user add constraint fk_role_user_user foreign key (user_id) references user (id) on delete restrict on update restrict;


# --- !Downs

alter table role_user drop foreign key fk_role_user_role;
drop index ix_role_user_role on role_user;

alter table role_user drop foreign key fk_role_user_user;
drop index ix_role_user_user on role_user;

drop table if exists role;

drop table if exists role_user;

drop table if exists user;

