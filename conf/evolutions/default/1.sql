# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table role (
  id                            integer auto_increment not null,
  name                          varchar(255),
  constraint uq_role_name unique (name),
  constraint pk_role primary key (id)
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

create table user_role (
  user_id                       integer not null,
  role_id                       integer not null,
  constraint pk_user_role primary key (user_id,role_id)
);

create index ix_user_role_user on user_role (user_id);
alter table user_role add constraint fk_user_role_user foreign key (user_id) references user (id) on delete restrict on update restrict;

create index ix_user_role_role on user_role (role_id);
alter table user_role add constraint fk_user_role_role foreign key (role_id) references role (id) on delete restrict on update restrict;


# --- !Downs

alter table user_role drop foreign key fk_user_role_user;
drop index ix_user_role_user on user_role;

alter table user_role drop foreign key fk_user_role_role;
drop index ix_user_role_role on user_role;

drop table if exists role;

drop table if exists user;

drop table if exists user_role;

