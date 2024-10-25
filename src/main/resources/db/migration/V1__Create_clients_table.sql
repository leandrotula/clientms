-- V1__Create_clients_table.sql
CREATE TABLE clients (
                         id SERIAL PRIMARY KEY,
                         name VARCHAR(50) NOT NULL,
                         last_name VARCHAR(50) NOT NULL,
                         age INT CHECK (age >= 0),
                         birthdate DATE NOT NULL
);