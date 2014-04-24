CREATE DATABASE  IF NOT EXISTS `kloudbook` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `kloudbook`;
-- MySQL dump 10.13  Distrib 5.6.13, for Win32 (x86)
--
-- Host: localhost    Database: kloudbook
-- ------------------------------------------------------
-- Server version	5.6.16-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `additional_emails`
--

DROP TABLE IF EXISTS `additional_emails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `additional_emails` (
  `add_email_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) unsigned NOT NULL,
  `email` varchar(255) NOT NULL,
  `email_index` int(11) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`add_email_id`),
  UNIQUE KEY `add_emails_id_UNIQUE` (`add_email_id`),
  KEY `fk_ae_user_id_idx` (`user_id`),
  CONSTRAINT `fk_ae_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `additional_emails`
--

LOCK TABLES `additional_emails` WRITE;
/*!40000 ALTER TABLE `additional_emails` DISABLE KEYS */;
/*!40000 ALTER TABLE `additional_emails` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `additional_emails_index`
BEFORE INSERT ON `additional_emails`
FOR EACH ROW
BEGIN
	SET @new_index = (SELECT MAX(email_index) + 1 FROM additional_emails WHERE user_id = NEW.user_id);
	SET @new_index = IF(@new_index IS NULL, 1, @new_index);
	SET NEW.`email_index` = @new_index;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `additional_numbers`
--

DROP TABLE IF EXISTS `additional_numbers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `additional_numbers` (
  `add_number_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) unsigned NOT NULL,
  `number` varchar(10) NOT NULL,
  `number_extension` varchar(5) DEFAULT NULL,
  `number_country_code` varchar(4) DEFAULT NULL,
  `number_index` int(11) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`add_number_id`),
  UNIQUE KEY `add_number_id_UNIQUE` (`add_number_id`),
  KEY `fk_an_user_id_idx` (`user_id`),
  CONSTRAINT `fk_an_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `additional_numbers`
--

LOCK TABLES `additional_numbers` WRITE;
/*!40000 ALTER TABLE `additional_numbers` DISABLE KEYS */;
/*!40000 ALTER TABLE `additional_numbers` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `additional_numbers_index`
BEFORE INSERT ON `additional_numbers`
FOR EACH ROW
BEGIN
	SET @new_index = (SELECT MAX(number_index) + 1 FROM additional_numbers WHERE user_id = NEW.user_id);
	SET @new_index = IF(@new_index IS NULL, 1, @new_index);
	SET NEW.`number_index` = @new_index;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `connections`
--

DROP TABLE IF EXISTS `connections`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `connections` (
  `connections_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user1_id` int(11) unsigned NOT NULL,
  `user2_id` int(11) unsigned NOT NULL,
  `isActive` binary(1) NOT NULL DEFAULT '0',
  `primary_email_access` binary(1) DEFAULT '1',
  `first_name_access` binary(1) DEFAULT '1',
  `middle_name_access` binary(1) DEFAULT NULL,
  `last_name_access` binary(1) DEFAULT NULL,
  `gender_access` binary(1) DEFAULT '1',
  `hometown_access` binary(1) DEFAULT '1',
  `mobile_number_access` binary(1) DEFAULT '1',
  `picture_access` binary(1) DEFAULT '1',
  `registered_access` binary(1) DEFAULT '1',
  `birthday_access` binary(1) DEFAULT '1',
  `additional_emails_access` binary(1) DEFAULT '1',
  `additional_numbers_access` binary(1) DEFAULT '1',
  `social_media_access` binary(1) DEFAULT '1',
  PRIMARY KEY (`connections_id`),
  UNIQUE KEY `connections_id_UNIQUE` (`connections_id`),
  KEY `fk_cn_user1_id_idx` (`user1_id`),
  KEY `fk_cn_user2_id_idx` (`user2_id`),
  CONSTRAINT `fk_cn_user1_id` FOREIGN KEY (`user1_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_cn_user2_id` FOREIGN KEY (`user2_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `connections`
--

LOCK TABLES `connections` WRITE;
/*!40000 ALTER TABLE `connections` DISABLE KEYS */;
/*!40000 ALTER TABLE `connections` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_members`
--

DROP TABLE IF EXISTS `group_members`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group_members` (
  `group_members_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `group_id` int(11) unsigned NOT NULL,
  `user_id` int(11) unsigned NOT NULL,
  `access` int(11) DEFAULT '-1',
  PRIMARY KEY (`group_members_id`),
  UNIQUE KEY `group_members_id_UNIQUE` (`group_members_id`),
  KEY `fk_gm_group_id_idx` (`group_id`),
  KEY `fk_gm_user_id_idx` (`user_id`),
  CONSTRAINT `fk_gm_group_id` FOREIGN KEY (`group_id`) REFERENCES `groups` (`groups_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_gm_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_members`
--

LOCK TABLES `group_members` WRITE;
/*!40000 ALTER TABLE `group_members` DISABLE KEYS */;
/*!40000 ALTER TABLE `group_members` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `groups`
--

DROP TABLE IF EXISTS `groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `groups` (
  `groups_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `description` text,
  `shared_info` int(11) DEFAULT NULL,
  `isActive` int(1) DEFAULT '1',
  PRIMARY KEY (`groups_id`),
  UNIQUE KEY `groups_id_UNIQUE` (`groups_id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `groups`
--

LOCK TABLES `groups` WRITE;
/*!40000 ALTER TABLE `groups` DISABLE KEYS */;
/*!40000 ALTER TABLE `groups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notification` (
  `notification_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) unsigned NOT NULL,
  `connection_id` int(11) unsigned NOT NULL,
  `isGroup` binary(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`notification_id`),
  UNIQUE KEY `notification_id_UNIQUE` (`notification_id`),
  KEY `fk_nt_user_id_idx` (`user_id`),
  CONSTRAINT `fk_nt_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification`
--

LOCK TABLES `notification` WRITE;
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
/*!40000 ALTER TABLE `notification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `social_media`
--

DROP TABLE IF EXISTS `social_media`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `social_media` (
  `social_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) unsigned NOT NULL,
  `media_link` varchar(255) DEFAULT NULL,
  `media_index` int(11) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`social_id`),
  UNIQUE KEY `social_id_UNIQUE` (`social_id`),
  KEY `fk_sm_user_id_idx` (`user_id`),
  CONSTRAINT `fk_sm_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `social_media`
--

LOCK TABLES `social_media` WRITE;
/*!40000 ALTER TABLE `social_media` DISABLE KEYS */;
/*!40000 ALTER TABLE `social_media` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `social_media_index`
BEFORE INSERT ON `social_media`
FOR EACH ROW
BEGIN
	SET @new_index = (SELECT MAX(media_index) + 1 FROM social_media WHERE user_id = NEW.user_id);
	SET @new_index = IF(@new_index IS NULL, 1, @new_index);
	SET NEW.`media_index` = @new_index;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `user_businessinfo`
--

DROP TABLE IF EXISTS `user_businessinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_businessinfo` (
  `user_businessinfo` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) unsigned NOT NULL,
  `business_address1` varchar(255) DEFAULT NULL,
  `business_address2` varchar(255) DEFAULT NULL,
  `business_city` varchar(50) DEFAULT NULL,
  `business_state` varchar(50) DEFAULT NULL,
  `business_zip` varchar(5) DEFAULT NULL,
  `business_number` varchar(10) DEFAULT NULL,
  `business_number_ext` varchar(5) DEFAULT NULL,
  `business_country_code` varchar(4) DEFAULT '1',
  `company` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`user_businessinfo`),
  UNIQUE KEY `user_businessinfo_UNIQUE` (`user_businessinfo`),
  UNIQUE KEY `user_id_UNIQUE` (`user_id`),
  CONSTRAINT `fk_ub_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_businessinfo`
--

LOCK TABLES `user_businessinfo` WRITE;
/*!40000 ALTER TABLE `user_businessinfo` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_businessinfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_businessinfo_connections`
--

DROP TABLE IF EXISTS `user_businessinfo_connections`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_businessinfo_connections` (
  `connections_id` int(11) unsigned NOT NULL,
  `business_address1_access` binary(1) DEFAULT '1',
  `business_address2_access` binary(1) DEFAULT NULL,
  `business_city_access` binary(1) DEFAULT NULL,
  `business_state_access` binary(1) DEFAULT NULL,
  `business_zip_access` binary(1) DEFAULT NULL,
  `business_number_access` binary(1) DEFAULT '1',
  `company_access` binary(1) DEFAULT '1',
  KEY `fk_connections_id_idx` (`connections_id`),
  CONSTRAINT `fk_ub_connections_id` FOREIGN KEY (`connections_id`) REFERENCES `connections` (`connections_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_businessinfo_connections`
--

LOCK TABLES `user_businessinfo_connections` WRITE;
/*!40000 ALTER TABLE `user_businessinfo_connections` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_businessinfo_connections` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_college`
--

DROP TABLE IF EXISTS `user_college`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_college` (
  `user_college_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) unsigned NOT NULL,
  `college` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`user_college_id`),
  UNIQUE KEY `user_college_id_UNIQUE` (`user_college_id`),
  KEY `fk_uc_user_id_idx` (`user_id`),
  CONSTRAINT `fk_uc_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_college`
--

LOCK TABLES `user_college` WRITE;
/*!40000 ALTER TABLE `user_college` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_college` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `user_data`
--

DROP TABLE IF EXISTS `user_data`;
/*!50001 DROP VIEW IF EXISTS `user_data`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `user_data` (
  `user_id` tinyint NOT NULL,
  `isActive` tinyint NOT NULL,
  `primary_email` tinyint NOT NULL,
  `first_name` tinyint NOT NULL,
  `middle_name` tinyint NOT NULL,
  `last_name` tinyint NOT NULL,
  `gender` tinyint NOT NULL,
  `hometown` tinyint NOT NULL,
  `current_city` tinyint NOT NULL,
  `home_address1` tinyint NOT NULL,
  `home_address2` tinyint NOT NULL,
  `home_city` tinyint NOT NULL,
  `home_state` tinyint NOT NULL,
  `home_zip` tinyint NOT NULL,
  `home_number` tinyint NOT NULL,
  `home_number_ext` tinyint NOT NULL,
  `home_country_code` tinyint NOT NULL,
  `company` tinyint NOT NULL,
  `business_address1` tinyint NOT NULL,
  `business_address2` tinyint NOT NULL,
  `business_city` tinyint NOT NULL,
  `business_state` tinyint NOT NULL,
  `business_zip` tinyint NOT NULL,
  `business_number` tinyint NOT NULL,
  `business_number_ext` tinyint NOT NULL,
  `business_country_code` tinyint NOT NULL,
  `mobile_number` tinyint NOT NULL,
  `mobile_number_ext` tinyint NOT NULL,
  `mobile_country_code` tinyint NOT NULL,
  `picture` tinyint NOT NULL,
  `registered` tinyint NOT NULL,
  `birthday` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `user_high_school`
--

DROP TABLE IF EXISTS `user_high_school`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_high_school` (
  `user_hs_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) unsigned NOT NULL,
  `high_school` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`user_hs_id`),
  UNIQUE KEY `user_hs_id_UNIQUE` (`user_hs_id`),
  KEY `fk_uhs_user_id_idx` (`user_id`),
  CONSTRAINT `fk_uhs_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_high_school`
--

LOCK TABLES `user_high_school` WRITE;
/*!40000 ALTER TABLE `user_high_school` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_high_school` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_homeinfo`
--

DROP TABLE IF EXISTS `user_homeinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_homeinfo` (
  `user_homeinfo_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) unsigned NOT NULL,
  `home_address1` varchar(255) DEFAULT NULL,
  `home_address2` varchar(255) DEFAULT NULL,
  `home_city` varchar(50) DEFAULT NULL,
  `home_state` varchar(50) DEFAULT NULL,
  `home_zip` varchar(5) DEFAULT NULL,
  `home_number` varchar(10) DEFAULT NULL,
  `home_number_ext` varchar(5) DEFAULT NULL,
  `home_country_code` varchar(4) DEFAULT '1',
  `current_city` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`user_homeinfo_id`),
  UNIQUE KEY `user_homeinfo_id_UNIQUE` (`user_homeinfo_id`),
  UNIQUE KEY `user_id_UNIQUE` (`user_id`),
  CONSTRAINT `fk_uh_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_homeinfo`
--

LOCK TABLES `user_homeinfo` WRITE;
/*!40000 ALTER TABLE `user_homeinfo` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_homeinfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_homeinfo_connections`
--

DROP TABLE IF EXISTS `user_homeinfo_connections`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_homeinfo_connections` (
  `connections_id` int(11) unsigned NOT NULL,
  `home_address1_access` binary(1) DEFAULT '1',
  `home_address2_access` binary(1) DEFAULT NULL,
  `home_city_access` binary(1) DEFAULT NULL,
  `home_state_access` binary(1) DEFAULT NULL,
  `home_zip_access` binary(1) DEFAULT NULL,
  `home_number_access` binary(1) DEFAULT '1',
  `current_city_access` binary(1) DEFAULT '1',
  KEY `fk_uh_connections_id_idx` (`connections_id`),
  CONSTRAINT `fk_uh_connections_id` FOREIGN KEY (`connections_id`) REFERENCES `connections` (`connections_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_homeinfo_connections`
--

LOCK TABLES `user_homeinfo_connections` WRITE;
/*!40000 ALTER TABLE `user_homeinfo_connections` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_homeinfo_connections` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `user_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `isActive` binary(1) NOT NULL DEFAULT '1',
  `primary_email` varchar(255) NOT NULL,
  `password` varchar(64) NOT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `middle_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `gender` char(1) DEFAULT NULL,
  `hometown` varchar(50) DEFAULT NULL,
  `mobile_number` varchar(10) DEFAULT NULL,
  `mobile_number_ext` varchar(5) DEFAULT NULL,
  `mobile_country_code` varchar(4) DEFAULT NULL,
  `picture` varchar(20) DEFAULT NULL,
  `registered` date DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `id_UNIQUE` (`user_id`),
  UNIQUE KEY `primary_email_UNIQUE` (`primary_email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'1','publicuser','publicuserpublicuserpublicuserpublicuserpublicuserpublicuser1234',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `view_access`
--

DROP TABLE IF EXISTS `view_access`;
/*!50001 DROP VIEW IF EXISTS `view_access`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `view_access` (
  `user1_id` tinyint NOT NULL,
  `user2_id` tinyint NOT NULL,
  `primary_email` tinyint NOT NULL,
  `name` tinyint NOT NULL,
  `gender` tinyint NOT NULL,
  `mobile_number` tinyint NOT NULL,
  `picture` tinyint NOT NULL,
  `birthday` tinyint NOT NULL,
  `additional_emails` tinyint NOT NULL,
  `additional_numbers` tinyint NOT NULL,
  `social_media` tinyint NOT NULL,
  `business_address` tinyint NOT NULL,
  `business_number` tinyint NOT NULL,
  `home_address` tinyint NOT NULL,
  `home_number` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Dumping routines for database 'kloudbook'
--
/*!50003 DROP PROCEDURE IF EXISTS `add_college` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `add_college`(
IN user_id INT(11),
IN college VARCHAR(100)
)
BEGIN
	INSERT INTO `kloudbook`.`user_college`
	(`user_id`,
	`college`)
	VALUES
	(user_id,
	college);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `add_email` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `add_email`(
IN user_id INT(11),
IN email VARCHAR(255)
)
BEGIN
	INSERT INTO `kloudbook`.`additional_emails`
	(`user_id`,
	`email`)
	VALUES
	(user_id,
	email);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `add_hs` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `add_hs`(
IN user_id INT(11),
IN high_school VARCHAR(100)
)
BEGIN
	INSERT INTO `kloudbook`.`user_high_school`
	(`user_id`,
	`high_school`)
	VALUES
	(user_id,
	high_school);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `add_number` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `add_number`(
IN user_id INT(11),
IN num VARCHAR(10),
IN ext VARCHAR(5),
IN cc VARCHAR(4)
)
BEGIN
	INSERT INTO `kloudbook`.`additional_numbers`
	(`user_id`,
	`number`,
	`number_extension`,
	`number_country_code`)
	VALUES
	(user_id,
	num,
	ext,
	cc);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `add_social` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `add_social`(
IN user_id INT(11),
IN social_media VARCHAR(255)
)
BEGIN
	INSERT INTO `kloudbook`.`social_media`
	(`user_id`,
	`media_link`)
	VALUES
	(user_id,
	social_media);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `DROP_THE_BASS` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `DROP_THE_BASS`()
begin
    DECLARE truncate_table VARCHAR(255) DEFAULT '';
    DECLARE done INT DEFAULT 0;
    DECLARE cur1 CURSOR FOR SELECT CONCAT('TRUNCATE TABLE ', 'kloudbook', '.', info.table_name)
        FROM information_schema.tables AS info
        WHERE info.table_schema='kloudbook' AND info.table_type='BASE TABLE' AND info.table_name LIKE '%%';
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;
 
    OPEN cur1;
 
    read_loop: LOOP
        FETCH cur1 INTO truncate_table;
        SET @sql = truncate_table;
        IF done THEN LEAVE read_loop; END IF;
        PREPARE stmt FROM @sql;
        EXECUTE stmt;
        DROP PREPARE stmt;
    END LOOP;
    CLOSE cur1;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `edit_user` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `edit_user`(
IN user_id INT(11),
IN primary_email VARCHAR(255),
IN first_name VARCHAR(50),
IN middle_name VARCHAR(50),
IN last_name VARCHAR(50),
IN gender CHAR(1),
IN hometown VARCHAR(50),
IN mobile_number VARCHAR(10),
IN mobile_number_ext VARCHAR(5),
IN mobile_country_code VARCHAR(4),
IN picture BLOB,
IN birthday DATE,
IN home_address1 VARCHAR(255),
IN home_address2 VARCHAR(255),
IN home_city VARCHAR(50),
IN home_state VARCHAR(50),
IN home_zip VARCHAR(5),
IN home_number VARCHAR(10),
IN home_number_ext VARCHAR(5),
IN home_country_code VARCHAR(4),
IN current_city VARCHAR(50),
IN business_address1 VARCHAR(255),
IN business_address2 VARCHAR(255),
IN business_city VARCHAR(50),
IN business_state VARCHAR(50),
IN business_zip VARCHAR(5),
IN business_number VARCHAR(10),
IN business_number_ext VARCHAR(10),
IN business_country_code VARCHAR(4),
IN company VARCHAR(100)
)
BEGIN
UPDATE `kloudbook`.`users`
SET
`primary_email` = IF(primary_email IS NULL,`primary_email`, primary_email),
`first_name` = IF(first_name IS NULL, `first_name`, first_name),
`middle_name` = IF(middle_name IS NULL, `middle_name`, middle_name),
`last_name` = IF(last_name IS NULL, `last_name`, last_name),
`gender` = IF(gender IS NULL, `gender`, gender),
`hometown` = IF(hometown IS NULL, `hometown`, hometown),
`mobile_number` = IF(mobile_number IS NULL, `mobile_number`, mobile_number),
`mobile_number_ext` = IF(mobile_number_ext IS NULL, `mobile_number_ext`, mobile_number_ext),
`mobile_country_code` = IF(mobile_country_code IS NULL, `mobile_country_code`, mobile_country_code),
`picture` = IF(picture IS NULL, `picture`, picture),
`birthday` = IF(birthday IS NULL, `birthday`, birthday)
WHERE `user_id` = user_id;

UPDATE `kloudbook`.`user_homeinfo`
SET
`home_address1` = IF(home_address1 IS NULL, `home_address1`, home_address1),
`home_address2` = IF(home_address2 IS NULL, `home_address2`, home_address2),
`home_city` = IF(home_city IS NULL, `home_city`, home_city),
`home_state` = IF(home_state IS NULL, `home_state`, home_state),
`home_zip` = IF(home_zip IS NULL, `home_zip`, home_zip),
`home_number` = IF(home_number IS NULL, `home_number`, home_number),
`home_number_ext` = IF(home_number_ext IS NULL, `home_number_ext`, home_number_ext),
`home_country_code` = IF(home_country_code IS NULL, `home_country_code`, home_country_code),
`current_city` = IF(current_city IS NULL, `current_city`, current_city)
WHERE `user_id` = user_id;

UPDATE `kloudbook`.`user_businessinfo`
SET
`business_address1` = IF(business_address1 IS NULL, `business_address1`, business_address1),
`business_address2` = IF(business_address2 IS NULL, `business_address2`, business_address2),
`business_city` = IF(business_city IS NULL, `business_city`, business_city),
`business_state` = IF(business_state IS NULL, `business_state`, business_state),
`business_zip` = IF(business_zip IS NULL, `business_zip`, business_zip),
`business_number` = IF(business_number IS NULL, `business_number`, business_number),
`business_number_ext` = IF(business_number_ext IS NULL, `business_number_ext`, business_number_ext),
`business_country_code` = IF(business_country_code IS NULL, `business_country_code`, business_country_code),
`company` = IF(company IS NULL, `company`, company)
WHERE `user_id` = user_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `friend_del` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `friend_del`(
IN connection_id INT(11)
)
BEGIN
	DELETE FROM `kloudbook`.`connections` WHERE `connections_id` = connection_id;
	DELETE FROM `kloudbook`.`additional_emails_connections` WHERE `connections_id` = connection_id;
	DELETE FROM `kloudbook`.`additional_numbers_connections` WHERE `connections_id` = connection_id;
	DELETE FROM `kloudbook`.`social_media_connections` WHERE `connections_id` = connection_id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `friend_new` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `friend_new`(
IN user1_id INT(11),
IN user2_id INT(11),
IN primary_email BINARY(1),
IN first_name BINARY(1),
IN gender BINARY(1),
IN mobile_number BINARY(1),
IN picture BINARY(1),
IN registered BINARY(1),
IN birthday BINARY(1),
IN business_address BINARY(1),
IN business_number BINARY(1),
IN home_address BINARY(1),
IN home_number BINARY(1),
IN add_emails BINARY(1),
IN add_nums BINARY(1),
IN social_media BINARY(1)
)
BEGIN
	INSERT INTO `kloudbook`.`connections`
	(`user1_id`,
	`user2_id`,
	`primary_email_access`,
	`first_name_access`,
	`gender_access`,
	`hometown_access`,
	`mobile_number_access`,
	`picture_access`,
	`registered_access`,
	`birthday_access`,
	`additional_emails_access`,
	`additional_numbers_access`,
	`social_media_access`)
	VALUES
	(user1_id,
	user2_id,
	primary_email,
	first_name,
	gender,
	mobile_number,
	picture,
	registered,
	birthday,
	add_emails,
	add_nums,
	social_media);

	SET @connection_id = LAST_INSERT_ID();

	INSERT INTO `kloudbook`.`user_businessinfo_connections`
	(`connections_id`,
	`business_address1_access`,
	`business_number_access`)
	VALUES
	(@connection_id,
	business_address,
	business_number);

	INSERT INTO `kloudbook`.`user_homeinfo_connections`
	(`connections_id`,
	`home_address1_access`,
	`home_number_access`)
	VALUES
	(@connection_id,
	home_address,
	home_number);

	-- Only User2 receives notification because User1 initiated connection
	INSERT INTO `kloudbook`.`notification`
	(`user_id`,
	`connection_id`)
	VALUES
	(user2_id,
	@connection_id
	);

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `new_user` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `new_user`(
IN primary_email VARCHAR(255),
IN first_name VARCHAR(50),
IN middle_name VARCHAR(50),
IN last_name VARCHAR(50),
IN gender CHAR(1),
IN hometown VARCHAR(50),
IN mobile_number VARCHAR(10),
IN mobile_number_ext VARCHAR(5),
IN mobile_country_code VARCHAR(4),
IN picture BLOB,
IN registered DATE,
IN birthday DATE,
OUT user_id INT(11),
IN password1 VARCHAR(64),
IN home_address1 VARCHAR(255),
IN home_address2 VARCHAR(255),
IN home_city VARCHAR(50),
IN home_state VARCHAR(50),
IN home_zip VARCHAR(5),
IN home_number VARCHAR(10),
IN home_number_ext VARCHAR(5),
IN home_country_code VARCHAR(4),
IN current_city VARCHAR(50),
IN business_address1 VARCHAR(255),
IN business_address2 VARCHAR(255),
IN business_city VARCHAR(50),
IN business_state VARCHAR(50),
IN business_zip VARCHAR(5),
IN business_number VARCHAR(10),
IN business_number_ext VARCHAR(10),
IN business_country_code VARCHAR(4),
IN company VARCHAR(100)
)
BEGIN
	INSERT INTO `kloudbook`.`users`
	(`primary_email`,
	`password`,
	`first_name`,
	`middle_name`,
	`last_name`,
	`gender`,
	`hometown`,
	`mobile_number`,
	`mobile_number_ext`,
	`mobile_country_code`,
	`picture`,
	`registered`,
	`birthday`)
	VALUES
	(primary_email,
	password1,
	first_name,
	middle_name,
	last_name,
	gender,
	hometown,
	mobile_number,
	mobile_number_ext,
	mobile_country_code,
	picture,
	registered,
	birthday);

	SET @user_id = LAST_INSERT_ID();

	INSERT INTO `kloudbook`.`user_homeinfo`
	(`user_id`,
	`home_address1`,
	`home_address2`,
	`home_city`,
	`home_state`,
	`home_zip`,
	`home_number`,
	`home_number_ext`,
	`home_country_code`,
	`current_city`)
	VALUES
	(@user_id,
	home_address1,
	home_address2,
	home_city,
	home_state,
	home_zip,
	home_number,
	home_number_ext,
	home_country_code,
	current_city);

	INSERT INTO `kloudbook`.`user_businessinfo`
	(`user_id`,
	`business_address1`,
	`business_address2`,
	`business_city`,
	`business_state`,
	`business_zip`,
	`business_number`,
	`business_number_ext`,
	`business_country_code`,
	`company`)
	VALUES
	(@user_id,
	business_address1,
	business_address2,
	business_city,
	business_state,
	business_zip,
	business_number,
	business_number_ext,
	business_country_code,
	company);

	INSERT INTO `kloudbook`.`connections`
	(`user1_id`,
	`user2_id`,
	`isActive`)
	VALUES(@user_id,1,TRUE);

	SET @connection_id = LAST_INSERT_ID();

	INSERT INTO `kloudbook`.`user_businessinfo_connections`
	(`connections_id`)
	VALUES
	(@connection_id);

	INSERT INTO `kloudbook`.`user_homeinfo_connections`
	(`connections_id`)
	VALUES
	(@connection_id);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Final view structure for view `user_data`
--

/*!50001 DROP TABLE IF EXISTS `user_data`*/;
/*!50001 DROP VIEW IF EXISTS `user_data`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `user_data` AS select `users`.`user_id` AS `user_id`,`users`.`isActive` AS `isActive`,`users`.`primary_email` AS `primary_email`,`users`.`first_name` AS `first_name`,`users`.`middle_name` AS `middle_name`,`users`.`last_name` AS `last_name`,`users`.`gender` AS `gender`,`users`.`hometown` AS `hometown`,`user_homeinfo`.`current_city` AS `current_city`,`user_homeinfo`.`home_address1` AS `home_address1`,`user_homeinfo`.`home_address2` AS `home_address2`,`user_homeinfo`.`home_city` AS `home_city`,`user_homeinfo`.`home_state` AS `home_state`,`user_homeinfo`.`home_zip` AS `home_zip`,`user_homeinfo`.`home_number` AS `home_number`,`user_homeinfo`.`home_number_ext` AS `home_number_ext`,`user_homeinfo`.`home_country_code` AS `home_country_code`,`user_businessinfo`.`company` AS `company`,`user_businessinfo`.`business_address1` AS `business_address1`,`user_businessinfo`.`business_address2` AS `business_address2`,`user_businessinfo`.`business_city` AS `business_city`,`user_businessinfo`.`business_state` AS `business_state`,`user_businessinfo`.`business_zip` AS `business_zip`,`user_businessinfo`.`business_number` AS `business_number`,`user_businessinfo`.`business_number_ext` AS `business_number_ext`,`user_businessinfo`.`business_country_code` AS `business_country_code`,`users`.`mobile_number` AS `mobile_number`,`users`.`mobile_number_ext` AS `mobile_number_ext`,`users`.`mobile_country_code` AS `mobile_country_code`,`users`.`picture` AS `picture`,`users`.`registered` AS `registered`,`users`.`birthday` AS `birthday` from ((`users` join `user_homeinfo` on((`users`.`user_id` = `user_homeinfo`.`user_id`))) join `user_businessinfo` on((`users`.`user_id` = `user_businessinfo`.`user_id`))) where (`users`.`isActive` = 1) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_access`
--

/*!50001 DROP TABLE IF EXISTS `view_access`*/;
/*!50001 DROP VIEW IF EXISTS `view_access`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `view_access` AS select `connections`.`user1_id` AS `user1_id`,`connections`.`user2_id` AS `user2_id`,`connections`.`primary_email_access` AS `primary_email`,`connections`.`first_name_access` AS `name`,`connections`.`gender_access` AS `gender`,`connections`.`mobile_number_access` AS `mobile_number`,`connections`.`picture_access` AS `picture`,`connections`.`birthday_access` AS `birthday`,`connections`.`additional_emails_access` AS `additional_emails`,`connections`.`additional_numbers_access` AS `additional_numbers`,`connections`.`social_media_access` AS `social_media`,`user_businessinfo_connections`.`business_address1_access` AS `business_address`,`user_businessinfo_connections`.`business_number_access` AS `business_number`,`user_homeinfo_connections`.`home_address1_access` AS `home_address`,`user_homeinfo_connections`.`home_number_access` AS `home_number` from ((`connections` join `user_businessinfo_connections` on((`connections`.`connections_id` = `user_businessinfo_connections`.`connections_id`))) join `user_homeinfo_connections` on((`connections`.`connections_id` = `user_homeinfo_connections`.`connections_id`))) where (`connections`.`isActive` = 1) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-04-24 15:29:50
