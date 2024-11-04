-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema billpal
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema billpal
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `billpal` DEFAULT CHARACTER SET utf8 ;
USE `billpal` ;

-- -----------------------------------------------------
-- Table `billpal`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `billpal`.`user` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `first_name` VARCHAR(50) NOT NULL,
    `last_name` VARCHAR(50) NOT NULL,
    `email` VARCHAR(100) NOT NULL,
    `phone` VARCHAR(20) NULL,
    PRIMARY KEY (`id`))
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `billpal`.`role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `billpal`.`role` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    `permissions` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`))
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `billpal`.`userrole`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `billpal`.`userrole` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `user_id` BIGINT NOT NULL,
    `role_id` BIGINT NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_UserRoles_Users_idx` (`user_id` ASC) VISIBLE,
    INDEX `fk_UserRole_Role1_idx` (`role_id` ASC) VISIBLE,
    CONSTRAINT `fk_UserRole_User`
    FOREIGN KEY (`user_id`)
    REFERENCES `billpal`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    CONSTRAINT `fk_UserRole_Role`
    FOREIGN KEY (`role_id`)
    REFERENCES `billpal`.`role` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
    ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
