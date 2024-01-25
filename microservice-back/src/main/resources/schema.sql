CREATE TABLE patient
(
    id              INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    first_name      VARCHAR(255) NOT NULL,
    last_name       VARCHAR(255) NOT NULL,
    birthdate       DATE NOT NULL,
    gender          VARCHAR(1) NOT NULL,
    address         VARCHAR(255),
    phone_number    VARCHAR(15)
);