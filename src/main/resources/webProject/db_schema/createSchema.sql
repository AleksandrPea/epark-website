-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema parkdb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema parkdb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `parkdb` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci ;
USE `parkdb` ;

-- -----------------------------------------------------
-- Table `parkdb`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `parkdb`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `firstName` VARCHAR(30) NOT NULL,
  `lastName` VARCHAR(30) NOT NULL,
  `email` VARCHAR(30) NOT NULL,
  `role` ENUM('OWNER', 'TASKMASTER', 'FORESTER') NOT NULL,
  `info` VARCHAR(150) NULL DEFAULT '',
  `superiorId` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_user_user1_idx` (`superiorId` ASC),
  CONSTRAINT `fk_user_user1`
    FOREIGN KEY (`superiorId`)
    REFERENCES `parkdb`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `parkdb`.`task`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `parkdb`.`task` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `state` ENUM('NEW', 'RUNNING', 'FINISHED', 'INCOMPLETED') NOT NULL,
  `title` VARCHAR(30) NOT NULL,
  `comment` TEXT NULL,
  `creationDate` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `senderId` INT NOT NULL,
  `receiverId` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_task_user2_idx` (`senderId` ASC),
  INDEX `fk_task_user1_idx` (`receiverId` ASC),
  CONSTRAINT `fk_task_user2`
    FOREIGN KEY (`senderId`)
    REFERENCES `parkdb`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_task_user1`
    FOREIGN KEY (`receiverId`)
    REFERENCES `parkdb`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `parkdb`.`report`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `parkdb`.`report` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `comment` TEXT NULL,
  `imgPath` TEXT NULL,
  `creationDate` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `taskId` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_report_task1_idx` (`taskId` ASC),
  CONSTRAINT `fk_report_task1`
    FOREIGN KEY (`taskId`)
    REFERENCES `parkdb`.`task` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `parkdb`.`area`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `parkdb`.`area` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(30) NOT NULL,
  `description` TEXT NULL,
  `taskmasterId` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_area_user1_idx` (`taskmasterId` ASC),
  CONSTRAINT `fk_area_user1`
    FOREIGN KEY (`taskmasterId`)
    REFERENCES `parkdb`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `parkdb`.`plant`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `parkdb`.`plant` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(30) NOT NULL,
  `state` ENUM('SEEDLING', 'NORMAL', 'SICK', 'EXTRACTED') NOT NULL,
  `imgPath` TEXT NULL,
  `description` TEXT NULL,
  `areaId` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_plant_area1_idx` (`areaId` ASC),
  CONSTRAINT `fk_plant_area1`
    FOREIGN KEY (`areaId`)
    REFERENCES `parkdb`.`area` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `parkdb`.`plant_task`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `parkdb`.`plant_task` (
  `taskId` INT NOT NULL,
  `plantId` INT NOT NULL,
  INDEX `fk_plant_task_task_idx` (`taskId` ASC),
  INDEX `fk_plant_task_Plant1_idx` (`plantId` ASC),
  PRIMARY KEY (`taskId`, `plantId`),
  CONSTRAINT `fk_plant_task_task`
    FOREIGN KEY (`taskId`)
    REFERENCES `parkdb`.`task` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_plant_task_Plant1`
    FOREIGN KEY (`plantId`)
    REFERENCES `parkdb`.`plant` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `parkdb`.`credential`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `parkdb`.`credential` (
  `userId` INT NOT NULL,
  `login` VARCHAR(30) NOT NULL,
  `password` VARCHAR(12) NOT NULL,
  INDEX `fk_credentials_user1_idx` (`userId` ASC),
  PRIMARY KEY (`userId`),
  UNIQUE INDEX `login_UNIQUE` (`login` ASC),
  CONSTRAINT `fk_credentials_user1`
    FOREIGN KEY (`userId`)
    REFERENCES `parkdb`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
