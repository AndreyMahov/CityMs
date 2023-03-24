--liquibase formatted sql
--changeset author:Andrey_Mahov

CREATE TABLE HOUSES
(
    ID     BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    STREET CHARACTER(100),
    NUMBER INTEGER
);

CREATE TABLE OWNERS
(
    ID         BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    CITIZEN_ID BIGINT,
    STATUS     CHARACTER(100) default 'ACTIVE'
);

CREATE TABLE OWNERS_HOUSES
(
    ID       BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    HOUSE_ID BIGINT REFERENCES HOUSES (ID),
    OWNER_ID BIGINT REFERENCES OWNERS (ID)
);

--rollback drop table CITIZENS_HOUSES;
--rollback drop table HOUSES;
