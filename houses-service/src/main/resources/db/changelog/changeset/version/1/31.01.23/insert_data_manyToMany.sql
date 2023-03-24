--liquibase formatted sql
--changeset author:Andrey_Mahov

INSERT INTO owners_houses(house_id, owner_id)
VALUES (1, 1);

--rollback truncate table owners_houses;