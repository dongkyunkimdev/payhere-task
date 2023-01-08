create table ledgers (
    id varchar(255) not null,
    created_at datetime(6) not null,
    updated_at datetime(6) not null,
    is_deleted bit not null,
    memo varchar(255) not null,
    price bigint not null,
    user_id varchar(255) not null,
    primary key (id)
) engine=InnoDB;

create table users (
    id varchar(255) not null,
    created_at datetime(6) not null,
    updated_at datetime(6) not null,
    password varchar(255) not null,
    username varchar(255) not null,
    primary key (id)
) engine=InnoDB;

alter table users
   add constraint user_unique_username unique (username);

alter table ledgers
   add constraint ledger_fk_user_id
   foreign key (user_id)
   references users (id);