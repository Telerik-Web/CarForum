create table users
(
    user_id    int auto_increment primary key,
    first_name varchar(32) not null,
    last_name  varchar(32) not null,
    email      varchar(50) not null unique,
    username   varchar(32) not null,
    password   varchar(32) not null,
    isAdmin    boolean  not null,
    isBlocked  boolean  not null
);

create table phone_numbers
(
    phone_number_id int auto_increment primary key,
    phone_number    varchar(32) not null,
    user_id         int         not null,
    constraint phone_numbers_users_user_id_fk
        foreign key (user_id) references users (user_id)
);

create table posts
(
    post_id   int auto_increment primary key,
    title     varchar(128)  not null,
    content   varchar(8192) not null,
    user_id   int           not null,
    timestamp timestamp     default current_timestamp,
    constraint posts_users_user_id_fk
        foreign key (user_id) references users (user_id)
);

create table comments
(
    comment_id int auto_increment primary key,
    content    varchar(2000) not null,
    post_id    int           not null,
    user_id    int           not null,
    constraint comments_posts_post_id_fk
        foreign key (post_id) references posts (post_id),
    constraint comments_users_user_id_fk
        foreign key (user_id) references users (user_id)
);

create table likes
(
    like_id int not null primary key,
    post_id int not null,
    user_id int not null,
    constraint likes_posts_post_id_fk
        foreign key (post_id) references posts (post_id),
    constraint likes_users_user_id_fk
        foreign key (user_id) references users (user_id)
);

