CREATE SCHEMA `htg_it_wms` ;
USE htg_it_wms;
CREATE TABLE `htg_it_wms`.`users` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `dob` DATE NOT NULL,
  PRIMARY KEY (`user_id`));