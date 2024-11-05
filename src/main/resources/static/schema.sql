####################################################################
###                                                             ####
### Author: Dave AKN                                             ####
### Date: November 04th, 2024                                   ####
### Version: 1.0                                                ####
###                                                             ####
####################################################################

/*
 * --- General Rules ---
 * Use underscore_names instead of camelCase
 * Table names should be plural
 * Spell out id fields (item_id instead of id)
 * Refrain from using ambiguous column names
 * Foreign key columns should be named the same as their reference columns
 * Use caps for all SQL queries
 */

CREATE SCHEMA IF NOT EXISTS billpal;

SET NAMES 'UTF8MB4';
SET TIME_ZONE = 'Europe/Berlin';
SET TIME_ZONE = '+02:00';

USE billpal;

DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    id           BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    first_name   VARCHAR(50) NOT NULL,
    last_name    VARCHAR(50) NOT NULL,
    email        VARCHAR(100) NOT NULL,
    password     VARCHAR(255) DEFAULT NULL,
    address      VARCHAR(255) DEFAULT NULL,
    phone        VARCHAR(30) DEFAULT NULL,
    title        VARCHAR(50) DEFAULT NULL,
    bio          VARCHAR(255) DEFAULT NULL,
    enabled      BOOLEAN DEFAULT FALSE,
    non_locked   BOOLEAN DEFAULT TRUE,
    using_mfa    BOOLEAN DEFAULT FALSE,
    created_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    image_url    VARCHAR(50) DEFAULT 'https://cdn-icons-png.flaticon.com/512/149/149071.png',
    CONSTRAINT UQ_User_Email UNIQUE (email)
);

DROP TABLE IF EXISTS roles;

CREATE TABLE roles
(
    id         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(50) NOT NULL,
    permission VARCHAR(255) NOT NULL,
    CONSTRAINT UQ_Roles_Name UNIQUE (name)
);

DROP TABLE IF EXISTS user_roles;

CREATE TABLE user_roles
(
    id      BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT UNSIGNED NOT NULL,
    role_id BIGINT UNSIGNED NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT UQ_UserRoles_User_Id UNIQUE (user_id)
);

DROP TABLE IF EXISTS events;

CREATE TABLE events
(
    id          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    type        VARCHAR(50) NOT NULL CHECK (type IN
                    (
                        'LOGIN_ATTEMPT',
                        'LOGIN_ATTEMPT_FAILURE',
                        'LOGIN_ATTEMPT_SUCCESS',
                        'PROFILE_UPDATE',
                        'PROFILE_PICTURE_UPDATE',
                        'ROLE_UPDATE',
                        'ACCOUNT_SETTINGS_UPDATE',
                        'PASSWORD_UPDATE',
                        'MFA_UPDATE'
                    )
                ),
    description VARCHAR(255) NOT NULL,
    CONSTRAINT UQ_Events_Type UNIQUE (type)
);

DROP TABLE IF EXISTS user_events;

CREATE TABLE user_events
(
    id         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id    BIGINT UNSIGNED NOT NULL,
    event_id   BIGINT UNSIGNED NOT NULL,
    device     VARCHAR(100) NOT NULL,
    ip_address VARCHAR(100) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (event_id) REFERENCES events (id) ON DELETE RESTRICT ON UPDATE CASCADE
);

DROP TABLE IF EXISTS account_verifications;

CREATE TABLE account_verifications
(
    id      BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT UNSIGNED NOT NULL,
    url     VARCHAR(255) NOT NULL,
    -- date DATETIME NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT UQ_AccountVerifications_User_Id UNIQUE (user_id),
    CONSTRAINT UQ_AccountVerifications_Url UNIQUE (url)
);