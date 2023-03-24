--liquibase formatted sql
--changeset author:Andrey_Mahov

CREATE TABLE CARS
(
    ID             BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    FEDERAL_NUMBER CHARACTER(100),
    OWNER_ID       BIGINT,
    STATUS         CHARACTER(100) default 'ACTIVE'
);

--rollback drop table CARS;