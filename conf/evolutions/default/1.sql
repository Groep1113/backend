-- noinspection SqlNoDataSourceInspectionForFile

-- # --- !Downs
--
-- alter table item_attribute drop foreign key fk_item_attribute_item_id;
-- drop index ix_item_attribute_item_id on item_attribute;
--
-- alter table role_user drop foreign key fk_role_user_role;
-- drop index ix_role_user_role on role_user;
--
-- alter table role_user drop foreign key fk_role_user_user;
-- drop index ix_role_user_user on role_user;
--
-- drop table if exists category;
--
-- drop table if exists item;
--
-- drop table if exists item_attribute;
--
-- drop table if exists location;
--
-- drop table if exists role;
--
-- drop table if exists role_user;
--
-- drop table if exists supplier;
--
-- drop table if exists user;

# --- !Ups

create table account (
  id                            integer auto_increment not null,
  created_at                    datetime,
  updated_at                    datetime,
  name                          varchar(255),
  constraint pk_account primary key (id)
);

create table category (
  id                            integer auto_increment not null,
  created_at                    datetime,
  updated_at                    datetime,
  name                          varchar(255),
  constraint pk_category primary key (id)
);

create table item (
  id                            integer auto_increment not null,
  supplier_id                   integer not null,
  category_id                   integer not null,
  created_at                    datetime,
  updated_at                    datetime,
  name                          varchar(255),
  code                          varchar(255),
  recommended_stock             integer not null,
  constraint pk_item primary key (id)
);

create table item_attribute (
  id                            integer auto_increment not null,
  created_at                    datetime,
  updated_at                    datetime,
  item_id                       integer,
  `key`                         varchar(255),
  `value`                       varchar(255),
  constraint pk_item_attribute primary key (id)
);

create table location (
  id                            integer auto_increment not null,
  created_at                    datetime,
  updated_at                    datetime,
  code                          varchar(255),
  depth                         varchar(255),
  width                         varchar(255),
  height                        varchar(255),
  constraint uq_location_width unique (width),
  constraint pk_location primary key (id)
);

create table role (
  id                            integer auto_increment not null,
  created_at                    datetime,
  updated_at                    datetime,
  name                          varchar(255),
  constraint uq_role_name unique (name),
  constraint pk_role primary key (id)
);

create table role_user (
  role_id                       integer not null,
  user_id                       integer not null,
  constraint pk_role_user primary key (role_id,user_id)
);

create table supplier (
  id                            integer auto_increment not null,
  created_at                    datetime,
  updated_at                    datetime,
  name                          varchar(255),
  constraint pk_supplier primary key (id)
);

create table user (
  id                            integer auto_increment not null,
  created_at                    datetime,
  updated_at                    datetime,
  first_name                    varchar(255),
  last_name                     varchar(255),
  email                         varchar(255),
  password                      varchar(255),
  date_of_birth                 date,
  constraint uq_user_email unique (email),
  constraint pk_user primary key (id)
);

create index ix_item_supplier_id on item (supplier_id);
alter table item add constraint fk_item_supplier_id foreign key (supplier_id) references supplier (id) on delete restrict on update restrict;
create index ix_item_category_id on item (category_id);
alter table item add constraint fk_item_category_id foreign key (category_id) references category (id) on delete restrict on update restrict;

create index ix_item_attribute_item_id on item_attribute (item_id);
alter table item_attribute add constraint fk_item_attribute_item_id foreign key (item_id) references item (id) on delete restrict on update restrict;

create index ix_role_user_role on role_user (role_id);
alter table role_user add constraint fk_role_user_role foreign key (role_id) references role (id) on delete restrict on update restrict;

create index ix_role_user_user on role_user (user_id);
alter table role_user add constraint fk_role_user_user foreign key (user_id) references user (id) on delete restrict on update restrict;
