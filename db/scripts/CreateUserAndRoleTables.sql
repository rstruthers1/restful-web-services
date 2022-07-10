create table if not exists USER
(
    ID bigint auto_increment
        primary key,
    USERNAME varchar(100) not null,
    PASSWORD varchar(200) not null,
    ENABLED tinyint(1) null,
    FIRST_NAME varchar(100) not null,
    LAST_NAME varchar(100) not null,
    constraint USER_USERNAME_uindex
        unique (USERNAME)
);

create table if not exists ROLE
(
    ID bigint auto_increment
        primary key,
    NAME varchar(100) not null,
    constraint ROLE_NAME_uindex
        unique (NAME)
);

create table USER_ROLE
(
    USER_ID bigint not null,
    ROLE_ID bigint null,
    constraint USER_ROLE_ROLE_ID_fk
        foreign key (ROLE_ID) references ROLE (ID),
    constraint USER_ROLE_USER_ID_fk
        foreign key (USER_ID) references USER (ID)
);

INSERT INTO in28minutes.ROLE (NAME) VALUES ('ROLE_ADMIN');
INSERT INTO in28minutes.ROLE (NAME) VALUES ('ROLE_USER');

set @roleUserId = (SELECT LAST_INSERT_ID());

INSERT INTO in28minutes.USER (NAME, USERNAME, PASSWORD) 
VALUES ('Test User', 'in28minutes', 'password1');

set @in28MinutesUserId = (SELECT LAST_INSERT_ID());

INSERT INTO in28minutes.USER_ROLE (USER_ID, ROLE_ID) VALUES (@in28MinutesUserId, @roleUserId);





