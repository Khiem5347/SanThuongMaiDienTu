-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: shopdb
-- ------------------------------------------------------
-- Server version	9.3.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `shippinglinks`
--

DROP TABLE IF EXISTS `shippinglinks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shippinglinks` (
  `link_id` int NOT NULL AUTO_INCREMENT,
  `provider_id` int NOT NULL,
  `product_id` int NOT NULL,
  PRIMARY KEY (`link_id`),
  KEY `provider_id` (`provider_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `shippinglinks_ibfk_1` FOREIGN KEY (`provider_id`) REFERENCES `shippingproviders` (`provider_id`),
  CONSTRAINT `shippinglinks_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shippinglinks`
--

LOCK TABLES `shippinglinks` WRITE;
/*!40000 ALTER TABLE `shippinglinks` DISABLE KEYS */;
INSERT INTO `shippinglinks` VALUES (1,1,1),(2,2,1),(3,3,1),(4,2,2),(5,3,2),(6,3,3),(7,1,4),(8,2,4),(9,2,5),(10,3,5),(11,3,6),(12,1,6),(13,1,7),(14,2,7),(15,2,8),(16,3,8),(17,1,9),(18,3,9),(19,1,10),(20,2,10),(21,2,11),(22,3,11),(23,1,12),(24,3,12),(25,1,13),(26,2,13),(27,2,14),(28,3,14),(29,1,15),(30,3,15),(31,1,16),(32,2,17),(33,3,17),(34,1,18),(35,3,18),(36,1,19),(37,2,19);
/*!40000 ALTER TABLE `shippinglinks` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-14 22:02:46
