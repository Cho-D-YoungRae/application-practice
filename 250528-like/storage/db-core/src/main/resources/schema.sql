create table example
(
    id         bigint       not null auto_increment,
    val        varchar(255),
    created_at timestamp(6) not null default CURRENT_TIMESTAMP(6),
    updated_at timestamp(6) not null default CURRENT_TIMESTAMP(6) on update CURRENT_TIMESTAMP(6),
    deleted    boolean      not null default false,
    primary key (id)
) engine = innodb;
