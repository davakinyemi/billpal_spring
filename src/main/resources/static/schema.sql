####################################################################
###                                                             ####
### Author: Dave AK                                             ####
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

DROP TABLE IF EXISTS user;

CREATE TABLE user
(
    id              BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    first_name      VARCHAR(50) NOT NULL,
    last_name       VARCHAR(50) NOT NULL,
    email           VARCHAR(100) NOT NULL,
    password        VARCHAR(255) DEFAULT NULL,
    address         VARCHAR(255) DEFAULT NULL,
    phone           VARCHAR(30) DEFAULT NULL,
    title           VARCHAR(50) DEFAULT NULL,
    bio             VARCHAR(255) DEFAULT NULL,
    enabled         BOOLEAN DEFAULT FALSE,
    non_locked      BOOLEAN DEFAULT TRUE,
    using_mfa       BOOLEAN DEFAULT FALSE,
    created_date    DATETIME DEFAULT CURRENT_TIMESTAMP,
    image_url       VARCHAR(50) DEFAULT 'https://cdn-icons-png.flaticon.com/512/149/149071.png',
    CONSTRAINT UQ_User_Email UNIQUE (email)
);