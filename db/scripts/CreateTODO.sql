create table TODO
(
    ID bigint auto_increment
        primary key,
    DESCRIPTION varchar(500) not null,
    TARGET_DATE date not null,
    DONE tinyint(1) not null,
    USER_ID bigint not null,
    constraint TODO_USER_ID_fk
        foreign key (USER_ID) references USER (ID)
);


