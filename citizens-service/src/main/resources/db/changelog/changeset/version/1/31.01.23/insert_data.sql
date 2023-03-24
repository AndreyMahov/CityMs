--liquibase formatted sql
--changeset author:Andrey_Mahov

INSERT INTO citizens(name, surname,place_of_work, gender,status)
VALUES ('name', 'surname','ANOTHER' ,'MALE','ACTIVE');

--rollback truncate table citizens;