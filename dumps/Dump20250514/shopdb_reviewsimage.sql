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
-- Table structure for table `reviewsimage`
--

DROP TABLE IF EXISTS `reviewsimage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reviewsimage` (
  `image_id` int NOT NULL AUTO_INCREMENT,
  `review_id` int NOT NULL,
  `image_url` varchar(255) NOT NULL,
  PRIMARY KEY (`image_id`),
  KEY `review_id` (`review_id`),
  CONSTRAINT `reviewsimage_ibfk_1` FOREIGN KEY (`review_id`) REFERENCES `productreviews` (`review_id`)
) ENGINE=InnoDB AUTO_INCREMENT=132 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reviewsimage`
--

LOCK TABLES `reviewsimage` WRITE;
/*!40000 ALTER TABLE `reviewsimage` DISABLE KEYS */;
INSERT INTO `reviewsimage` VALUES (1,1,'img/iphone15_review1_1.jpg'),(2,1,'img/iphone15_review1_2.jpg'),(3,2,'img/iphone15_review2_1.jpg'),(4,2,'img/iphone15_review2_2.jpg'),(5,2,'img/iphone15_review2_3.jpg'),(6,3,'img/iphone15_review3_1.jpg'),(7,3,'img/iphone15_review3_2.jpg'),(8,4,'img/iphone15_review4_1.jpg'),(9,4,'img/iphone15_review4_2.jpg'),(10,5,'img/iphone15_review5_1.jpg'),(11,5,'img/iphone15_review5_2.jpg'),(12,5,'img/iphone15_review5_3.jpg'),(13,6,'img/iphone15_review6_1.jpg'),(14,6,'img/iphone15_review6_2.jpg'),(15,7,'img/s23ultra_review1_1.jpg'),(16,7,'img/s23ultra_review1_2.jpg'),(17,8,'img/s23ultra_review2_1.jpg'),(18,8,'img/s23ultra_review2_2.jpg'),(19,8,'img/s23ultra_review2_3.jpg'),(20,9,'img/s23ultra_review3_1.jpg'),(21,9,'img/s23ultra_review3_2.jpg'),(22,10,'img/s23ultra_review4_1.jpg'),(23,10,'img/s23ultra_review4_2.jpg'),(24,11,'img/s23ultra_review5_1.jpg'),(25,11,'img/s23ultra_review5_2.jpg'),(26,11,'img/s23ultra_review5_3.jpg'),(27,12,'img/s23ultra_review6_1.jpg'),(28,12,'img/s23ultra_review6_2.jpg'),(29,13,'img/redmi12_review1_1.jpg'),(30,13,'img/redmi12_review1_2.jpg'),(31,14,'img/redmi12_review2_1.jpg'),(32,14,'img/redmi12_review2_2.jpg'),(33,15,'img/redmi12_review3_1.jpg'),(34,15,'img/redmi12_review3_2.jpg'),(35,16,'img/redmi12_review4_1.jpg'),(36,16,'img/redmi12_review4_2.jpg'),(37,17,'img/redmi12_review5_1.jpg'),(38,17,'img/redmi12_review5_2.jpg'),(39,18,'img/macbook14_review1_1.jpg'),(40,18,'img/macbook14_review1_2.jpg'),(41,19,'img/macbook14_review2_1.jpg'),(42,19,'img/macbook14_review2_2.jpg'),(43,20,'img/macbook14_review3_1.jpg'),(44,20,'img/macbook14_review3_2.jpg'),(45,21,'img/macbook14_review4_1.jpg'),(46,21,'img/macbook14_review4_2.jpg'),(47,22,'img/macbook14_review5_1.jpg'),(48,22,'img/macbook14_review5_2.jpg'),(49,23,'img/dellxps_review1_1.jpg'),(50,23,'img/dellxps_review1_2.jpg'),(51,24,'img/dellxps_review2_1.jpg'),(52,24,'img/dellxps_review2_2.jpg'),(53,25,'img/dellxps_review3_1.jpg'),(54,25,'img/dellxps_review3_2.jpg'),(55,26,'img/dellxps_review4_1.jpg'),(56,26,'img/dellxps_review4_2.jpg'),(57,27,'img/dellxps_review5_1.jpg'),(58,27,'img/dellxps_review5_2.jpg'),(59,28,'img/asustuf_review1_1.jpg'),(60,28,'img/asustuf_review1_2.jpg'),(61,29,'img/asustuf_review2_1.jpg'),(62,29,'img/asustuf_review2_2.jpg'),(63,30,'img/asustuf_review3_1.jpg'),(64,30,'img/asustuf_review3_2.jpg'),(65,31,'img/asustuf_review4_1.jpg'),(66,31,'img/asustuf_review4_2.jpg'),(67,32,'img/asustuf_review5_1.jpg'),(68,32,'img/asustuf_review5_2.jpg'),(69,33,'img/aothun_review1_1.jpg'),(70,33,'img/aothun_review1_2.jpg'),(71,34,'img/aothun_review2_1.jpg'),(72,34,'img/aothun_review2_2.jpg'),(73,35,'img/aothun_review3_1.jpg'),(74,35,'img/aothun_review3_2.jpg'),(75,36,'img/jeans_review1_1.jpg'),(76,36,'img/jeans_review1_2.jpg'),(77,37,'img/jeans_review2_1.jpg'),(78,37,'img/jeans_review2_2.jpg'),(79,38,'img/jeans_review3_1.jpg'),(80,38,'img/jeans_review3_2.jpg'),(81,39,'img/aokhoac_review1_1.jpg'),(82,39,'img/aokhoac_review1_2.jpg'),(83,40,'img/aokhoac_review2_1.jpg'),(84,40,'img/aokhoac_review2_2.jpg'),(85,41,'img/aokhoac_review3_1.jpg'),(86,41,'img/aokhoac_review3_2.jpg'),(87,42,'img/sonmaybelline_review1_1.jpg'),(88,42,'img/sonmaybelline_review1_2.jpg'),(89,43,'img/sonmaybelline_review2_1.jpg'),(90,43,'img/sonmaybelline_review2_2.jpg'),(91,44,'img/sonmaybelline_review3_1.jpg'),(92,44,'img/sonmaybelline_review3_2.jpg'),(93,45,'img/anessa_review1_1.jpg'),(94,45,'img/anessa_review1_2.jpg'),(95,46,'img/anessa_review2_1.jpg'),(96,46,'img/anessa_review2_2.jpg'),(97,47,'img/anessa_review3_1.jpg'),(98,47,'img/anessa_review3_2.jpg'),(99,48,'img/laneige_review1_1.jpg'),(100,48,'img/laneige_review1_2.jpg'),(101,49,'img/laneige_review2_1.jpg'),(102,49,'img/laneige_review2_2.jpg'),(103,50,'img/laneige_review3_1.jpg'),(104,50,'img/laneige_review3_2.jpg'),(105,51,'img/noichien_review1_1.jpg'),(106,51,'img/noichien_review1_2.jpg'),(107,52,'img/noichien_review2_1.jpg'),(108,52,'img/noichien_review2_2.jpg'),(109,53,'img/noichien_review3_1.jpg'),(110,53,'img/noichien_review3_2.jpg'),(111,54,'img/mayxay_review1_1.jpg'),(112,54,'img/mayxay_review1_2.jpg'),(113,55,'img/mayxay_review2_1.jpg'),(114,55,'img/mayxay_review2_2.jpg'),(115,56,'img/mayxay_review3_1.jpg'),(116,56,'img/mayxay_review3_2.jpg'),(117,57,'img/bonoinox_review1_1.jpg'),(118,57,'img/bonoinox_review1_2.jpg'),(119,58,'img/bonoinox_review2_1.jpg'),(120,58,'img/bonoinox_review2_2.jpg'),(121,59,'img/bonoinox_review3_1.jpg'),(122,59,'img/bonoinox_review3_2.jpg'),(123,60,'img/dacnhantam_review1_1.jpg'),(124,60,'img/dacnhantam_review1_2.jpg'),(125,61,'img/dacnhantam_review2_1.jpg'),(126,61,'img/dacnhantam_review2_2.jpg'),(127,62,'img/dacnhantam_review3_1.jpg'),(128,62,'img/dacnhantam_review3_2.jpg'),(129,63,'img/nhagiakim_review1_1.jpg'),(130,63,'img/nhagiakim_review1_2.jpg'),(131,64,'img/nhagiakim_review2_1.jpg');
/*!40000 ALTER TABLE `reviewsimage` ENABLE KEYS */;
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
