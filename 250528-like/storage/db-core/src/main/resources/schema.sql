create table posts
(
    id         bigint        not null auto_increment,
    title      varchar(255)  not null,
    content    varchar(4000) not null,
    created_at timestamp(6)  not null default CURRENT_TIMESTAMP(6),
    updated_at timestamp(6)  not null default CURRENT_TIMESTAMP(6) on update CURRENT_TIMESTAMP(6),
    deleted    boolean       not null default false,
    primary key (id)
) engine = innodb;

create table post_meta
(
    id         bigint       not null auto_increment,
    post_id    bigint       not null,
    like_count int          not null default 0,
    created_at timestamp(6) not null default CURRENT_TIMESTAMP(6),
    updated_at timestamp(6) not null default CURRENT_TIMESTAMP(6) on update CURRENT_TIMESTAMP(6),
    deleted    boolean      not null default false,
    primary key (id),
    unique key uk_post_meta_1 (post_id)
) engine = innodb;

create index ix_post_meta_1 on post_meta (like_count);

create table likes
(
    id         bigint       not null auto_increment,
    user_id    bigint       not null,
    post_id    bigint       not null,
    created_at timestamp(6) not null default CURRENT_TIMESTAMP(6),
    updated_at timestamp(6) not null default CURRENT_TIMESTAMP(6) on update CURRENT_TIMESTAMP(6),
    deleted    boolean      not null default false,
    primary key (id),
    unique key uk_like_1 (post_id, user_id)
) engine = innodb;

create index ix_likes_1 on likes (user_id);
