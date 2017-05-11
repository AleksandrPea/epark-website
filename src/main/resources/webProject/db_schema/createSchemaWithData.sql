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

-- -----------------------------------------------------
-- Data for table `parkdb`.`user`
-- -----------------------------------------------------
START TRANSACTION;
USE `parkdb`;
INSERT INTO `parkdb`.`user` (`id`, `firstName`, `lastName`, `email`, `role`, `info`, `superiorId`) VALUES (1, 'Aleks', 'Horoh', 'apea@park.net', 'OWNER', 'Owner of the park', NULL);
INSERT INTO `parkdb`.`user` (`id`, `firstName`, `lastName`, `email`, `role`, `info`, `superiorId`) VALUES (2, 'Igor', 'Ivor', 'ivgr@park.net', 'TASKMASTER', 'Main taskmaster', 1);
INSERT INTO `parkdb`.`user` (`id`, `firstName`, `lastName`, `email`, `role`, `info`, `superiorId`) VALUES (3, 'Ivan', 'Idan', 'idn@park.net', 'TASKMASTER', '', 1);
INSERT INTO `parkdb`.`user` (`id`, `firstName`, `lastName`, `email`, `role`, `info`, `superiorId`) VALUES (4, 'Roman', 'Rom', 'rom@park.net', 'FORESTER', 'Igors forester. Good person.', 2);
INSERT INTO `parkdb`.`user` (`id`, `firstName`, `lastName`, `email`, `role`, `info`, `superiorId`) VALUES (5, 'Evhenii', 'Hen', 'evhen@park.net', 'FORESTER', '', 2);
INSERT INTO `parkdb`.`user` (`id`, `firstName`, `lastName`, `email`, `role`, `info`, `superiorId`) VALUES (6, 'Mykola', 'Myk', 'myk@park.net', 'FORESTER', 'Ivans forester.', 3);

COMMIT;


-- -----------------------------------------------------
-- Data for table `parkdb`.`task`
-- -----------------------------------------------------
START TRANSACTION;
USE `parkdb`;
INSERT INTO `parkdb`.`task` (`id`, `state`, `title`, `comment`, `creationDate`, `senderId`, `receiverId`) VALUES (1, 'NEW', 'My first task', 'Please make me some report', NOW(), 1, 2);
INSERT INTO `parkdb`.`task` (`id`, `state`, `title`, `comment`, `creationDate`, `senderId`, `receiverId`) VALUES (2, 'NEW', 'Don\'t give up!', 'Phone me..', NOW(), 2, 4);
INSERT INTO `parkdb`.`task` (`id`, `state`, `title`, `comment`, `creationDate`, `senderId`, `receiverId`) VALUES (3, 'NEW', 'Fertilizers', 'Our park needs fertilizers. Go and buy it.', NOW(), 3, 6);

COMMIT;


-- -----------------------------------------------------
-- Data for table `parkdb`.`area`
-- -----------------------------------------------------
START TRANSACTION;
USE `parkdb`;
INSERT INTO `parkdb`.`area` (`id`, `name`, `description`, `taskmasterId`) VALUES (1, 'High trees', 'This park area has hight trees', 2);
INSERT INTO `parkdb`.`area` (`id`, `name`, `description`, `taskmasterId`) VALUES (2, 'Big flowers', 'This park area has big flowers', 3);

COMMIT;


-- -----------------------------------------------------
-- Data for table `parkdb`.`plant`
-- -----------------------------------------------------
START TRANSACTION;
USE `parkdb`;
INSERT INTO `parkdb`.`plant` (`id`, `name`, `state`, `imgPath`, `description`, `areaId`) VALUES (1, 'Rain Tree', 'NORMAL', 'https://www.nparks.gov.sg/~/media/nparks-real-content/activities/family-time-with-nature/know-10-trees/1-rain-tree/yellow-rt.jpg?h=300&&w=490&la=en', 'The large and majestic Rain Tree can be seen growing by our roadsides. It provides plenty of shade with its big umbrella-shaped crown', 1);
INSERT INTO `parkdb`.`plant` (`id`, `name`, `state`, `imgPath`, `description`, `areaId`) VALUES (2, 'Angsana', 'NORMAL', 'https://www.nparks.gov.sg/~/media/nparks-real-content/activities/family-time-with-nature/know-10-trees/2-angsana/angsana-tree-main-pic.jpg?h=300&w=275&la=en', 'The Angsana is a large deciduous tree that grows up to 40m tall. It can be recognised by its drooping, dome-shaped crown. ', 1);
INSERT INTO `parkdb`.`plant` (`id`, `name`, `state`, `imgPath`, `description`, `areaId`) VALUES (3, 'Yellow Flame', 'NORMAL', 'https://www.nparks.gov.sg/~/media/nparks-real-content/activities/family-time-with-nature/know-10-trees/3-yellow-flame/overall-main-pic.jpg?h=300&&w=436&la=en', 'The Yellow Flame grows up to 20m tall. It is a popular tree for roadside planting. It is drought-resistant, which makes it well adapted to Singapore’s sunny urban conditions.', 1);
INSERT INTO `parkdb`.`plant` (`id`, `name`, `state`, `imgPath`, `description`, `areaId`) VALUES (4, 'Senegal Mahogany', 'NORMAL', 'https://www.nparks.gov.sg/~/media/nparks-real-content/activities/family-time-with-nature/know-10-trees/4-senegal-mahogany/overall-tree.jpg?h=300&&w=223&la=en', 'The Senegal Mahogany is a fast growing evergreen tree. It can grow to more than 30m in height, and has a girth of 1 – 2m. It has a straight, robust and cylindrical trunk, with buttresses at the base. ', 1);
INSERT INTO `parkdb`.`plant` (`id`, `name`, `state`, `imgPath`, `description`, `areaId`) VALUES (5, 'Broad-leafed Mahogany', 'NORMAL', 'https://www.nparks.gov.sg/~/media/nparks-real-content/activities/family-time-with-nature/know-10-trees/5-broad-leafed-mahogany/overall-tree.jpg?h=300&&w=314&la=en', 'The Broad-leafed Mahogany is a large hardwood tree that can grow up to 30m or more. It can be easily recognised by its crown, which is dense dark green and round to oblong in shape. ', 1);
INSERT INTO `parkdb`.`plant` (`id`, `name`, `state`, `imgPath`, `description`, `areaId`) VALUES (6, 'Tembusu', 'NORMAL', 'https://www.nparks.gov.sg/~/media/nparks-real-content/activities/family-time-with-nature/know-10-trees/6-tembusu/$5-tembusu-tree.jpg?h=300&&w=372&la=en', 'The Tembusu is one of Singapore’s most distinctive trees. This native of Singapore is a large, evergreen tree that grows up to 40m in height.', 1);
INSERT INTO `parkdb`.`plant` (`id`, `name`, `state`, `imgPath`, `description`, `areaId`) VALUES (7, 'Saga', 'NORMAL', 'https://www.nparks.gov.sg/~/media/nparks-real-content/activities/family-time-with-nature/know-10-trees/8-saga/overall.jpg?h=300&&w=322&la=en', 'The Saga Tree is a deciduous tree that grows up to 15-20m tall. It is hardy, fast-growing, and low-maintenance.', 1);
INSERT INTO `parkdb`.`plant` (`id`, `name`, `state`, `imgPath`, `description`, `areaId`) VALUES (8, 'Trumpet Tree', 'NORMAL', 'https://www.nparks.gov.sg/~/media/nparks-real-content/activities/family-time-with-nature/know-10-trees/9-trumpet-tree/overall-tree.jpg?h=300&&w=277&la=en', 'The Trumpet Tree grows up to 18-25m tall, and has a large, broadly conical and shady crown. The tree’s name comes from its large trumpet-shaped flowers, which range in colour from pink to white.', 1);
INSERT INTO `parkdb`.`plant` (`id`, `name`, `state`, `imgPath`, `description`, `areaId`) VALUES (9, 'Sea Almond', 'NORMAL', 'https://www.nparks.gov.sg/~/media/nparks-real-content/activities/family-time-with-nature/know-10-trees/10-sea-almond/overall.jpg?h=300&w=448&la=en', 'The Sea Almond or Ketapang is a large coastal tree which grows up to 25m tall. It can be recognised by its distinct pagoda shape, formed by its tiered branching pattern.', 1);
INSERT INTO `parkdb`.`plant` (`id`, `name`, `state`, `imgPath`, `description`, `areaId`) VALUES (10, 'Sea Apple', 'NORMAL', 'https://www.nparks.gov.sg/~/media/nparks-real-content/activities/family-time-with-nature/know-10-trees/7-sea-apple/overall-2.jpg?h=300&&w=207&la=en', 'The Sea Apple is a tall coastal tree of this region that grows up to 30m in height. It is a robust tree with large white flowers arranged in compact clusters with showy stamens. ', 1);
INSERT INTO `parkdb`.`plant` (`id`, `name`, `state`, `imgPath`, `description`, `areaId`) VALUES (11, 'Anemone', 'NORMAL', 'http://www.namesofflowers.net/images/anemones-flower-1.jpg', 'The Anemone genus is part of the Ranunculaceae (buttercup) family. There are a little over 120 species of anemones in a wide range of colors.\n\nAnemones are popular in gardens as individual species flower in the spring, summer, or fall, providing continual color. ', 2);
INSERT INTO `parkdb`.`plant` (`id`, `name`, `state`, `imgPath`, `description`, `areaId`) VALUES (12, 'The Aster', 'NORMAL', 'http://www.namesofflowers.net/images/aster-flower-1.jpg', 'The aster is a flower with a bit of a wild appearance, but it fits nicely in many garden settings. The aster flower is the birth flower for the month of September, and is often used to mark twenty years of marriage.\n\nIn gardens, asters continue to attract bees and butterflies long after most other flowers have disappeared. People have enjoyed the simple beauty of aster flowers for many generations, and it is likely that these flowers will continue to be celebrated for years to come. ', 2);
INSERT INTO `parkdb`.`plant` (`id`, `name`, `state`, `imgPath`, `description`, `areaId`) VALUES (13, 'Begonia', 'NORMAL', 'http://www.namesofflowers.net/images/begonia.jpg', '', 2);
INSERT INTO `parkdb`.`plant` (`id`, `name`, `state`, `imgPath`, `description`, `areaId`) VALUES (14, 'Bellflower', 'NORMAL', 'http://www.namesofflowers.net/images/bell-flower.jpg', '', 2);
INSERT INTO `parkdb`.`plant` (`id`, `name`, `state`, `imgPath`, `description`, `areaId`) VALUES (15, 'Bergamot', 'NORMAL', 'http://www.namesofflowers.net/images/bee-balm.jpg', '', 2);
INSERT INTO `parkdb`.`plant` (`id`, `name`, `state`, `imgPath`, `description`, `areaId`) VALUES (16, 'Bluebell', 'NORMAL', 'http://www.namesofflowers.net/images/bluebell.jpg', '', 2);
INSERT INTO `parkdb`.`plant` (`id`, `name`, `state`, `imgPath`, `description`, `areaId`) VALUES (17, 'Camellia', 'NORMAL', 'http://www.namesofflowers.net/images/camellias-flower-1.jpg', 'Glossy green leaves, lovely blossoms, oil, and the invigorating beverage that spurred the American revolution all come from plants in the Camellia family.\n\nBeautiful in a naturalized wooded setting, functional as a flowering formal hedge, and lovely as a focal centerpiece, camellias are evergreen shrubs and small trees that grow well in slightly acidic soils with lots of humus and good drainage.\n\nAlmost all camellia plants are happiest with plentiful water. ', 2);

COMMIT;


-- -----------------------------------------------------
-- Data for table `parkdb`.`credential`
-- -----------------------------------------------------
START TRANSACTION;
USE `parkdb`;
INSERT INTO `parkdb`.`credential` (`userId`, `login`, `password`) VALUES (1, 'admin', 'admin');
INSERT INTO `parkdb`.`credential` (`userId`, `login`, `password`) VALUES (2, 'igor', 'igor');
INSERT INTO `parkdb`.`credential` (`userId`, `login`, `password`) VALUES (3, 'ivan', 'ivan');
INSERT INTO `parkdb`.`credential` (`userId`, `login`, `password`) VALUES (4, 'roman', 'roman');
INSERT INTO `parkdb`.`credential` (`userId`, `login`, `password`) VALUES (5, 'evhen', 'evhen');
INSERT INTO `parkdb`.`credential` (`userId`, `login`, `password`) VALUES (6, 'mykola', 'mykola');

COMMIT;

