SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `mydb` ;
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`calendars`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`calendars` ;

CREATE TABLE IF NOT EXISTS `mydb`.`calendars` (
  `calendar_id` INT(5) NOT NULL AUTO_INCREMENT,
  `calendarname` VARCHAR(45) NULL DEFAULT NULL,
  `background` BLOB NULL DEFAULT NULL,
  `begindate` DATE NULL DEFAULT NULL,
  `public` TINYINT(1) NULL DEFAULT NULL,
  PRIMARY KEY (`calendar_id`),
  UNIQUE INDEX `calender_id_UNIQUE` (`calendar_id` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`days`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`days` ;

CREATE TABLE IF NOT EXISTS `mydb`.`days` (
  `iddays` INT(10) NOT NULL AUTO_INCREMENT,
  `date` VARCHAR(45) NULL DEFAULT NULL,
  `data` VARCHAR(450) NULL DEFAULT NULL,
  `calendars_calendar_id` INT(5) NOT NULL,
  PRIMARY KEY (`iddays`),
  INDEX `fk_days_calendars1_idx` (`calendars_calendar_id` ASC),
  CONSTRAINT `fk_days_calendars1`
    FOREIGN KEY (`calendars_calendar_id`)
    REFERENCES `mydb`.`calendars` (`calendar_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`users` ;

CREATE TABLE IF NOT EXISTS `mydb`.`users` (
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `role` VARCHAR(45) NOT NULL DEFAULT 'User',
  PRIMARY KEY (`username`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`users_has_calendars`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`users_has_calendars` ;

CREATE TABLE IF NOT EXISTS `mydb`.`users_has_calendars` (
  `users_username` VARCHAR(45) NOT NULL,
  `calendars_calendar_id` INT(5) NOT NULL,
  PRIMARY KEY (`users_username`, `calendars_calendar_id`),
  INDEX `fk_users_has_calendars_calendars1_idx` (`calendars_calendar_id` ASC),
  INDEX `fk_users_has_calendars_users1_idx` (`users_username` ASC),
  CONSTRAINT `fk_users_has_calendars_calendars1`
    FOREIGN KEY (`calendars_calendar_id`)
    REFERENCES `mydb`.`calendars` (`calendar_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_has_calendars_users1`
    FOREIGN KEY (`users_username`)
    REFERENCES `mydb`.`users` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
