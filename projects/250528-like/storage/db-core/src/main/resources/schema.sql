drop table if exists posts;
drop table if exists post_meta;
drop table if exists post_like;

create table posts
(
    id         bigint       not null auto_increment,
    title      varchar(255) not null,
    content    text         not null,
    created_at timestamp(6) not null default CURRENT_TIMESTAMP(6),
    updated_at timestamp(6) not null default CURRENT_TIMESTAMP(6) on update CURRENT_TIMESTAMP(6),
    primary key (id)
) engine = innodb;

create index ix_posts__1 on posts (created_at desc);

create table post_meta
(
    id         bigint       not null auto_increment,
    post_id    bigint       not null,
    like_count int          not null default 0,
    version    int          not null default 0,
    created_at timestamp(6) not null default CURRENT_TIMESTAMP(6),
    updated_at timestamp(6) not null default CURRENT_TIMESTAMP(6) on update CURRENT_TIMESTAMP(6),
    primary key (id),
    unique key uk_post_meta__1 (post_id)
) engine = innodb;

create index ix_post_meta__1 on post_meta (like_count, post_id);

create table post_like
(
    id         bigint       not null auto_increment,
    post_id    bigint       not null,
    user_id    bigint       not null,
    created_at timestamp(6) not null default CURRENT_TIMESTAMP(6),
    updated_at timestamp(6) not null default CURRENT_TIMESTAMP(6) on update CURRENT_TIMESTAMP(6),
    primary key (id),
    unique key uk_post_like__1 (post_id, user_id)
) engine = innodb;

create index ix_post_like__1 on post_like (user_id);
