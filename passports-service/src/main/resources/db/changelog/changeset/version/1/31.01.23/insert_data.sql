--liquibase formatted sql
--changeset author:Andrey_Mahov

INSERT INTO passports(number, owner_id)
VALUES (123456, 1);

--rollback truncate table passports;