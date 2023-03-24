--liquibase formatted sql
--changeset author:Andrey_Mahov

INSERT INTO houses(street, number)
VALUES ('street', 1);

INSERT INTO owners(citizen_id,status)
VALUES (1,'ACTIVE');

--rollback truncate table houses;
--rollback truncate table owners;