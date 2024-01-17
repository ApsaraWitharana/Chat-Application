DROP DATABASE IF EXISTS chatApp;

CREATE DATABASE IF NOT EXISTS  chatApp;

SHOW DATABASES;

USE chatApp;

CREATE TABLE users(
                      user_name VARCHAR(35) PRIMARY KEY,
                      password VARCHAR(155) NOT NULL



);