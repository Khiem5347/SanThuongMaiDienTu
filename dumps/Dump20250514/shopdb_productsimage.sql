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
-- Table structure for table `productsimage`
--

DROP TABLE IF EXISTS `productsimage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `productsimage` (
  `image_id` int NOT NULL AUTO_INCREMENT,
  `product_url` varchar(255) NOT NULL,
  `product_id` int DEFAULT NULL,
  PRIMARY KEY (`image_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `productsimage_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productsimage`
--

LOCK TABLES `productsimage` WRITE;
/*!40000 ALTER TABLE `productsimage` DISABLE KEYS */;
INSERT INTO `productsimage` VALUES (1,'img/iphone15_1.jpg',1),(2,'img/iphone15_2.jpg',1),(3,'img/s23ultra_1.jpg',2),(4,'video/s23ultra_unbox.mp4',2),(5,'img/redmi12_1.jpg',3),(6,'img/macbook14_1.jpg',4),(7,'img/macbook14_2.jpg',4),(8,'img/dellxps_1.jpg',5),(9,'img/dellxps_2.jpg',5),(10,'img/asustuf_1.jpg',6),(11,'img/aothun_1.jpg',7),(12,'img/jeans_1.jpg',8),(13,'img/jeans_2.jpg',8),(14,'img/aokhoac_1.jpg',9),(15,'img/sonmaybelline_1.jpg',10),(16,'img/anessa_1.jpg',11),(17,'img/laneige_1.jpg',12),(18,'img/laneige_2.jpg',12),(19,'img/noichien_1.jpg',13),(20,'img/mayxay_1.jpg',14),(21,'img/bonoinox_1.jpg',15),(22,'img/dacnhantam_1.jpg',16),(23,'img/nhagiakim_1.jpg',17),(24,'img/tuduy_1.jpg',18),(25,'img/cafetony_1.jpg',19),(26,'img/cafetony_2.jpg',19),(27,'img/redmi12_2.jpg',3),(28,'img/asustuf_2.jpg',6),(29,'img/aothun_2.jpg',7),(30,'img/aokhoac_2.jpg',9),(31,'img/anessa_2.jpg',11),(32,'img/noichien_2.jpg',13),(33,'img/mayxay_2.jpg',14),(34,'img/bonoinox_2.jpg',15),(35,'img/dacnhantam_2.jpg',16),(36,'img/nhagiakim_2.jpg',17),(37,'img/tuduy_2.jpg',18);
/*!40000 ALTER TABLE `productsimage` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-14 22:02:47
