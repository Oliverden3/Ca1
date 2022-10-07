-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema ca1_db
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `ca1_db` ;

-- -----------------------------------------------------
-- Schema ca1_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ca1_db` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `ca1_db` ;

-- -----------------------------------------------------
-- Table `ca1_db`.`cityinfo`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ca1_db`.`cityinfo` ;

CREATE TABLE IF NOT EXISTS `ca1_db`.`cityinfo` (
                                                   `cityinfo_id` INT NOT NULL AUTO_INCREMENT,
                                                   `zipCode` INT NOT NULL,
                                                   `city` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`cityinfo_id`))
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ca1_db`.`address`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ca1_db`.`address` ;

CREATE TABLE IF NOT EXISTS `ca1_db`.`address` (
                                                  `address_id` INT NOT NULL AUTO_INCREMENT,
                                                  `address` VARCHAR(45) NOT NULL,
    `additionalInfo` VARCHAR(45) NULL,
    `cityinfo_id` INT NOT NULL,
    PRIMARY KEY (`address_id`),
    INDEX `fk_address_cityinfo1_idx` (`cityinfo_id` ASC) VISIBLE,
    CONSTRAINT `fk_address_cityinfo1`
    FOREIGN KEY (`cityinfo_id`)
    REFERENCES `ca1_db`.`cityinfo` (`cityinfo_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ca1_db`.`person`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ca1_db`.`person` ;

CREATE TABLE IF NOT EXISTS `ca1_db`.`person` (
                                                 `person_id` INT NOT NULL AUTO_INCREMENT,
                                                 `email` VARCHAR(45) NOT NULL,
    `firstName` VARCHAR(45) NOT NULL,
    `lastName` VARCHAR(45) NOT NULL,
    `address_id` INT NOT NULL,
    PRIMARY KEY (`person_id`),
    INDEX `fk_person_address1_idx` (`address_id` ASC) VISIBLE,
    CONSTRAINT `fk_person_address1`
    FOREIGN KEY (`address_id`)
    REFERENCES `ca1_db`.`address` (`address_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ca1_db`.`hobby`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ca1_db`.`hobby` ;

CREATE TABLE IF NOT EXISTS `ca1_db`.`hobby` (
                                                `hobby_id` INT NOT NULL AUTO_INCREMENT,
                                                `description` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`hobby_id`))
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ca1_db`.`phone`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ca1_db`.`phone` ;

CREATE TABLE IF NOT EXISTS `ca1_db`.`phone` (
                                                `phone_id` INT NOT NULL AUTO_INCREMENT,
                                                `number` INT NOT NULL,
                                                `description` VARCHAR(45) NOT NULL,
    `person_id` INT NOT NULL,
    PRIMARY KEY (`phone_id`),
    INDEX `fk_phone_person1_idx` (`person_id` ASC) VISIBLE,
    CONSTRAINT `fk_phone_person1`
    FOREIGN KEY (`person_id`)
    REFERENCES `ca1_db`.`person` (`person_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ca1_db`.`persons_hobbies`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ca1_db`.`persons_hobbies` ;

CREATE TABLE IF NOT EXISTS `ca1_db`.`persons_hobbies` (
                                                          `hobby_id` INT NOT NULL,
                                                          `person_id` INT NOT NULL,
                                                          PRIMARY KEY (`hobby_id`, `person_id`),
    INDEX `fk_hobby_has_person_person1_idx` (`person_id` ASC) VISIBLE,
    INDEX `fk_hobby_has_person_hobby_idx` (`hobby_id` ASC) VISIBLE,
    CONSTRAINT `fk_hobby_has_person_hobby`
    FOREIGN KEY (`hobby_id`)
    REFERENCES `ca1_db`.`hobby` (`hobby_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_hobby_has_person_person1`
    FOREIGN KEY (`person_id`)
    REFERENCES `ca1_db`.`person` (`person_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
