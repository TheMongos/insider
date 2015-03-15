-- MySQL dump 10.13  Distrib 5.6.22, for osx10.10 (x86_64)
--
-- Host: localhost    Database: insider
-- ------------------------------------------------------
-- Server version	5.6.22

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
-- Table structure for table `Category`
--

DROP TABLE IF EXISTS `Category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Category` (
  `category_id` tinyint(4) NOT NULL,
  `category_name` char(30) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Category`
--

LOCK TABLES `Category` WRITE;
/*!40000 ALTER TABLE `Category` DISABLE KEYS */;
INSERT INTO `Category` VALUES (1,'Movies'),(2,'TV');
/*!40000 ALTER TABLE `Category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Item`
--

DROP TABLE IF EXISTS `Item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Item` (
  `item_id` int(11) NOT NULL AUTO_INCREMENT,
  `category_id` tinyint(4) DEFAULT NULL,
  `title` varchar(500) COLLATE utf8_bin NOT NULL,
  `year` char(15) COLLATE utf8_bin NOT NULL,
  `description` varchar(1200) COLLATE utf8_bin NOT NULL,
  `other_data` varchar(1000) COLLATE utf8_bin NOT NULL,
  `created_ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`item_id`),
  KEY `category_id` (`category_id`),
  CONSTRAINT `Item_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `Category` (`category_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Item`
--

LOCK TABLES `Item` WRITE;
/*!40000 ALTER TABLE `Item` DISABLE KEYS */;
INSERT INTO `Item` VALUES (1,1,'Spider-Man','2002','When bitten by a genetically modified spider, a nerdy, shy, and awkward high school student gains spider-like abilities that he eventually must use to fight evil as a superhero after tragedy befalls his family.','{\'Director\': \'Sam Raimi\'}','2015-01-01 19:06:32','2015-01-01 19:06:32'),(2,1,'Guardians of the Galaxy','2014','A group of intergalactic criminals are forced to work together to stop a fanatical warrior from taking control of the universe.','{\'Director\': \'James Gunn\'}','2015-01-01 19:09:45','2015-01-01 19:09:45'),(3,1,'The Shawshank Redemption','1994','Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.','{\'Director\': \'Frank Darabont\'}','2015-01-01 19:10:48','2015-01-01 19:10:48'),(4,1,'The Godfather','1972','The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.','{\'Director\': \'Francis Ford Coppola\'}','2015-01-01 19:11:18','2015-01-01 19:11:18'),(5,1,'Pulp Fiction','1994','The lives of two mob hit men, a boxer, a gangster\'s wife, and a pair of diner bandits intertwine in four tales of violence and redemption.','{\'Director\': \'Quentin Tarantino\'}','2015-01-01 19:14:05','2015-01-01 19:14:05'),(6,1,'The Dark Knight','2008','When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, the caped crusader must come to terms with one of the greatest psychological tests of his ability to fight injustice.','{\'Director\': \'Christopher Nolan\'}','2015-01-01 19:14:42','2015-01-01 19:14:42'),(7,2,'Friends','1994-2004','When Monica\'s high school friend (Rachel) re-enters her life, she sets off on a series of humorous and entertaining events involving Monica\'s brother (Ross), her ex-roommate (Phoebe), and her next door neighbors (Chandler & Joey)','{\'Genre\': \'Comedy\'}','2015-01-01 19:17:24','2015-01-01 19:17:24'),(8,2,'The Big Bang Theory','2007–','A woman who moves into an apartment across the hall from two brilliant but socially awkward physicists shows them how little they know about life outside of the laboratory.','{\'Genre\': \'Comedy\'}','2015-01-01 19:18:08','2015-01-01 19:18:08'),(9,2,'Game of Thrones','2011–','Several noble families fight for control of the mythical land of Westeros.','{\'Genre\': \'Adventure\'}','2015-01-01 19:18:48','2015-01-01 19:18:48'),(10,2,'House M.D.','2004–2012','An antisocial maverick doctor who specializes in diagnostic medicine does whatever it takes to solve puzzling cases that come his way using his crack team of doctors and his wits.','{\'Genre\': \'Drama\'}','2015-01-01 19:19:34','2015-01-01 19:19:34'),(11,2,'How I Met Your Mother','2005–2014','A father recounts to his children, through a series of flashbacks, the journey he and his four best friends took leading up to him meeting their mother.','{\'Genre\': \'Comedy\'}','2015-01-01 19:20:23','2015-01-01 19:20:23'),(12,2,'Grey\'s Anatomy','2005–','A drama centered on the personal and professional lives of five surgical interns and their supervisors.','{\'Genre\': \'Drama\'}','2015-01-01 19:21:56','2015-01-01 19:21:56');
/*!40000 ALTER TABLE `Item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Review`
--

DROP TABLE IF EXISTS `Review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Review` (
  `review_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `item_id` int(11) NOT NULL,
  `review_text` varchar(500) COLLATE utf8_bin NOT NULL,
  `created_ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`review_id`),
  KEY `user_id` (`user_id`),
  KEY `item_id` (`item_id`),
  CONSTRAINT `Review_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `User` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `Review_ibfk_2` FOREIGN KEY (`item_id`) REFERENCES `Item` (`item_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Review`
--

LOCK TABLES `Review` WRITE;
/*!40000 ALTER TABLE `Review` DISABLE KEYS */;
INSERT INTO `Review` VALUES (7,2,6,'The best movie of the century!!!','2015-03-14 19:36:58','2015-03-14 19:36:58');
/*!40000 ALTER TABLE `Review` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `User` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` char(35) COLLATE utf8_bin NOT NULL,
  `last_name` char(35) COLLATE utf8_bin NOT NULL,
  `username` char(30) COLLATE utf8_bin NOT NULL,
  `email` char(255) COLLATE utf8_bin NOT NULL,
  `password` char(60) COLLATE utf8_bin NOT NULL,
  `created_ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES (1,'Elad','Birnboim','elad123','eladbirn@gmail.com','$2a$10$.PZuqsu/amDQcBXKT0lN4uxuPYax5zAlcJ2Cc4qpy7yEumNzXLClq','2015-01-01 18:49:32','2015-01-01 18:49:32'),(2,'David','Tzoor','david123','david.tzoor@gmail.com','$2a$10$tTKMmHSL1g0B6r1PIhpDv.V9GSrrzMOUaN44noRkqo6jGb3TRRYKi','2015-01-01 18:51:02','2015-01-01 18:51:02'),(3,'Uzi','Hadad','uzi','uzi@gmail.com','$2a$10$kiIOXGmthameAjMxtqQ6L.T6/eo3hTRGfuPXTPfbwPCRo6FjcHXNa','2015-01-01 18:51:30','2015-01-01 18:51:30'),(4,'Ariel','Shimon','ariel','ariel@gmail.com','$2a$10$PgKJDPj0Oib73iv0DKzY4eXE9SynqKXSkGLbERig49Y1sWBHURxay','2015-01-01 18:52:02','2015-01-01 18:52:02'),(5,'Bar','Ben Yaakov','bar123','bar@gmail.com','$2a$10$G3vO2WlqhdB/1GwrNbyZT.cRxd6MidYeyCf/IblxHYfaYs42qgatG','2015-03-08 19:17:43','2015-03-08 19:17:43');
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-03-15 17:56:03
