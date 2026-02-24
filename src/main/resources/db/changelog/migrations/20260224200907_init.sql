-- liquibase formatted sql

-- changeset Andrey Tikholoz:20260224200907
-- comment: init

create table test
(
    id bigserial primary key
);

-- rollback
-- TODO: write your rollback here
