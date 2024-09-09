CREATE DATABASE authdb;

\c authdb

-- Drop users table if it exists
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS user_roles CASCADE;

-- Create users' table (email-based authentication only)
CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    email    VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255)        NOT NULL,
    CONSTRAINT email_unique UNIQUE (email)
);

-- Create indexes
CREATE INDEX idx_email ON users (email);

-- Create user_roles table for storing the roles of users
CREATE TABLE user_roles
(
    user_id BIGINT NOT NULL,
    role    VARCHAR(255),
    CONSTRAINT fk_user_roles FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
