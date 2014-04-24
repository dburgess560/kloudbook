USE `kloudbook`;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
-- Dumping data for table `additional_emails`
--

LOCK TABLES `additional_emails` WRITE;
/*!40000 ALTER TABLE `additional_emails` DISABLE KEYS */;
INSERT INTO `additional_emails` VALUES (1,4,'jimbob@gmail.com',1),(2,4,'JMarx@bobsfurniture.com',2);
/*!40000 ALTER TABLE `additional_emails` ENABLE KEYS */;
UNLOCK TABLES;

--

--
-- Dumping data for table `additional_emails_connections`
--

LOCK TABLES `additional_emails_connections` WRITE;
/*!40000 ALTER TABLE `additional_emails_connections` DISABLE KEYS */;
/*!40000 ALTER TABLE `additional_emails_connections` ENABLE KEYS */;
UNLOCK TABLES;


-- Dumping data for table `additional_numbers`
--

LOCK TABLES `additional_numbers` WRITE;
/*!40000 ALTER TABLE `additional_numbers` DISABLE KEYS */;
INSERT INTO `additional_numbers` VALUES (1,4,'4137745081','17','',1),(2,4,'4137745081','17','',2);
/*!40000 ALTER TABLE `additional_numbers` ENABLE KEYS */;
UNLOCK TABLES;

--

-- Dumping data for table `additional_numbers_connections`
--

LOCK TABLES `additional_numbers_connections` WRITE;
/*!40000 ALTER TABLE `additional_numbers_connections` DISABLE KEYS */;
/*!40000 ALTER TABLE `additional_numbers_connections` ENABLE KEYS */;
UNLOCK TABLES;

--

-- Dumping data for table `connections`
--

LOCK TABLES `connections` WRITE;
/*!40000 ALTER TABLE `connections` DISABLE KEYS */;
/*!40000 ALTER TABLE `connections` ENABLE KEYS */;
UNLOCK TABLES;

--

-- Dumping data for table `group_members`
--

LOCK TABLES `group_members` WRITE;
/*!40000 ALTER TABLE `group_members` DISABLE KEYS */;
/*!40000 ALTER TABLE `group_members` ENABLE KEYS */;
UNLOCK TABLES;

--

-- Dumping data for table `groups`
--

LOCK TABLES `groups` WRITE;
/*!40000 ALTER TABLE `groups` DISABLE KEYS */;
/*!40000 ALTER TABLE `groups` ENABLE KEYS */;
UNLOCK TABLES;

--

-- Dumping data for table `social_media`
--

LOCK TABLES `social_media` WRITE;
/*!40000 ALTER TABLE `social_media` DISABLE KEYS */;
INSERT INTO `social_media` VALUES (1,4,'http://www.google.com/',1),(2,4,'http://www.google.com/',2);
/*!40000 ALTER TABLE `social_media` ENABLE KEYS */;
UNLOCK TABLES;

--

-- Dumping data for table `social_media_connections`
--

LOCK TABLES `social_media_connections` WRITE;
/*!40000 ALTER TABLE `social_media_connections` DISABLE KEYS */;
/*!40000 ALTER TABLE `social_media_connections` ENABLE KEYS */;
UNLOCK TABLES;

--

--
-- Dumping data for table `user_businessinfo`
--

LOCK TABLES `user_businessinfo` WRITE;
/*!40000 ALTER TABLE `user_businessinfo` DISABLE KEYS */;
INSERT INTO `user_businessinfo` VALUES (1,2,'72 Bartlett Street','Room 416','Southboro','MA','01772','8005554444','71435','1','Cisco'),(2,3,'81 Arrrr Street\r',NULL,'Amherst','MA','01003','8003331000',NULL,'1','MS'),(3,4,'72 Bart Street','Apt 2','City','State','12345','4137745081','17','','Bobs Discount Furniture');
/*!40000 ALTER TABLE `user_businessinfo` ENABLE KEYS */;
UNLOCK TABLES;


-- Dumping data for table `user_businessinfo_connections`
--

LOCK TABLES `user_businessinfo_connections` WRITE;
/*!40000 ALTER TABLE `user_businessinfo_connections` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_businessinfo_connections` ENABLE KEYS */;
UNLOCK TABLES;

--

-- Dumping data for table `user_college`
--

LOCK TABLES `user_college` WRITE;
/*!40000 ALTER TABLE `user_college` DISABLE KEYS */;
INSERT INTO `user_college` VALUES (1,2,'UMass'),(2,3,'College'),(3,2,'MIT'),(4,4,'MIT'),(5,4,'Babson');
/*!40000 ALTER TABLE `user_college` ENABLE KEYS */;
UNLOCK TABLES;

--

-- Dumping data for table `user_college_connections`
--

LOCK TABLES `user_college_connections` WRITE;
/*!40000 ALTER TABLE `user_college_connections` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_college_connections` ENABLE KEYS */;
UNLOCK TABLES;

--

-- Dumping data for table `user_high_school`
--

LOCK TABLES `user_high_school` WRITE;
/*!40000 ALTER TABLE `user_high_school` DISABLE KEYS */;
INSERT INTO `user_high_school` VALUES (1,2,'ARHS'),(2,3,'BSCU'),(3,2,'High School'),(4,4,'ARHS'),(5,4,'Boston South');
/*!40000 ALTER TABLE `user_high_school` ENABLE KEYS */;
UNLOCK TABLES;

--

-- Dumping data for table `user_high_school_connections`
--

LOCK TABLES `user_high_school_connections` WRITE;
/*!40000 ALTER TABLE `user_high_school_connections` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_high_school_connections` ENABLE KEYS */;
UNLOCK TABLES;

--

-- Dumping data for table `user_homeinfo`
--

LOCK TABLES `user_homeinfo` WRITE;
/*!40000 ALTER TABLE `user_homeinfo` DISABLE KEYS */;
INSERT INTO `user_homeinfo` VALUES (1,2,'','','Hadley','MA','01004','1236547890','7','1','Amherst'),(2,3,'1 M Drive','Appt 72','Amherst','MA','01002','5086547890',NULL,'1','Hadley'),(3,4,'72 Bart Street','Apt 2','City','State','12345','4137745081','17','','Amherst');
/*!40000 ALTER TABLE `user_homeinfo` ENABLE KEYS */;
UNLOCK TABLES;

--

-- Dumping data for table `user_homeinfo_connections`
--

LOCK TABLES `user_homeinfo_connections` WRITE;
/*!40000 ALTER TABLE `user_homeinfo_connections` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_homeinfo_connections` ENABLE KEYS */;
UNLOCK TABLES;

--

-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'1','publicuser','publicuserpublicuserpublicuserpublicuserpublicuserpublicuser1234',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(2,'1','DaveBurgess@gmail.com','5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8','Dave','R','Burgess','M','Worcester','5556667777','700','1',NULL,'2014-03-28','1992-07-05'),(3,'1','jmarsh@gmail.com','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4','Julie','Sue','Marshal','F',NULL,NULL,NULL,NULL,NULL,'2014-04-01','2008-07-04'),(4,'1','jimbob@ymail.com','9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08','Jim','Bo','Marx','M','Quincy','4137745081','17','',NULL,'2014-04-13','2014-04-13');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--