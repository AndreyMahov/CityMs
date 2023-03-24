--liquibase formatted sql
--changeset author:Andrey_Mahov

INSERT INTO cars(federal_number, owner_id,status)
VALUES ('AA123A', 1,'ACTIVE');

--rollback truncate table cars;