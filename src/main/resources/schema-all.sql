DROP TABLE user IF EXISTS;

CREATE TABLE user  (
    msisdn varchar(30) NOT NULL PRIMARY KEY,
    sim_type varchar(8) NOT NULL,
    name varchar(255) NOT NULL,
    date_of_birth DATE NOT NULL,
    gender char(1) NOT NULL,
    address varchar(255) NOT NULL,
    id_number varchar(20) NOT NULL,
    first_name VARCHAR(20) NOT NULL,
    last_name VARCHAR(20) NOT NULL
);