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
-- Table structure for table `productvariants`
--

DROP TABLE IF EXISTS `productvariants`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `productvariants` (
  `variant_id` int NOT NULL AUTO_INCREMENT,
  `product_id` int NOT NULL,
  `color` varchar(50) DEFAULT NULL,
  `size` varchar(50) DEFAULT NULL,
  `price` decimal(10,2) NOT NULL,
  `quantity` int NOT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`variant_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `productvariants_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productvariants`
--

LOCK TABLES `productvariants` WRITE;
/*!40000 ALTER TABLE `productvariants` DISABLE KEYS */;
INSERT INTO `productvariants` VALUES (1,1,'Đen',NULL,28000000.00,10,'img/iphone15_den.jpg'),(2,1,'Trắng',NULL,28500000.00,5,'img/iphone15_trang.jpg'),(3,1,'Xanh',NULL,29000000.00,8,'img/iphone15_xanh.jpg'),(4,2,'Đen',NULL,22000000.00,12,'img/s23ultra_den.jpg'),(5,2,'Xanh',NULL,22500000.00,7,'img/s23ultra_xanh.jpg'),(6,2,'Tím',NULL,23000000.00,3,'img/s23ultra_tim.jpg'),(7,2,'Kem',NULL,22800000.00,6,'img/s23ultra_kem.jpg'),(8,3,'Xám',NULL,5000000.00,20,'img/redmi12_xam.jpg'),(9,3,'Xanh',NULL,5200000.00,15,'img/redmi12_xanh.jpg'),(10,4,'Bạc',NULL,35000000.00,5,'img/macbook14_bac.jpg'),(11,4,'Xám',NULL,36000000.00,3,'img/macbook14_xam.jpg'),(12,5,'Bạc',NULL,28000000.00,8,'img/dellxps_bac.jpg'),(13,5,'Đen',NULL,29000000.00,6,'img/dellxps_den.jpg'),(14,5,'Trắng',NULL,28500000.00,4,'img/dellxps_trang.jpg'),(15,6,'Đen',NULL,22000000.00,10,'img/asustuf_den.jpg'),(16,6,'Xám',NULL,22500000.00,7,'img/asustuf_xam.jpg'),(17,7,'Trắng','M',200000.00,20,'img/aothun_trang_m.jpg'),(18,7,'Trắng','L',200000.00,15,'img/aothun_trang_l.jpg'),(19,7,'Đen','M',200000.00,18,'img/aothun_den_m.jpg'),(20,7,'Đen','L',200000.00,12,'img/aothun_den_l.jpg'),(21,8,'Xanh đậm','26',800000.00,5,'img/jeans_xanhdam_26.jpg'),(22,8,'Xanh đậm','28',800000.00,8,'img/jeans_xanhdam_28.jpg'),(23,8,'Xanh nhạt','26',820000.00,3,'img/jeans_xanhnhat_26.jpg'),(24,8,'Xanh nhạt','28',820000.00,6,'img/jeans_xanhnhat_28.jpg'),(25,9,'Đen','M',350000.00,10,'img/aokhoac_den_m.jpg'),(26,9,'Đen','L',350000.00,8,'img/aokhoac_den_l.jpg'),(27,9,'Xanh rêu','M',360000.00,5,'img/aokhoac_xanhreu_m.jpg'),(28,9,'Xanh rêu','L',360000.00,3,'img/aokhoac_xanhreu_l.jpg'),(29,10,'Đỏ cam',NULL,150000.00,30,'img/sonmaybelline_do.jpg'),(30,10,'Hồng đất',NULL,150000.00,25,'img/sonmaybelline_hong.jpg'),(31,10,'Cam đào',NULL,155000.00,20,'img/sonmaybelline_cam.jpg'),(32,11,NULL,NULL,450000.00,15,'img/anessa_1.jpg'),(33,12,NULL,NULL,300000.00,10,'img/laneige_1.jpg'),(34,13,NULL,NULL,1200000.00,5,'img/noichien_1.jpg'),(35,14,NULL,NULL,600000.00,8,'img/mayxay_1.jpg'),(36,15,NULL,NULL,900000.00,3,'img/bonoinox_1.jpg'),(37,16,NULL,NULL,80000.00,20,'img/dacnhantam_1.jpg'),(38,17,NULL,NULL,120000.00,15,'img/nhagiakim_1.jpg'),(39,18,NULL,NULL,150000.00,10,'img/tuduy_1.jpg'),(40,19,NULL,NULL,90000.00,12,'img/cafetony_1.jpg');
/*!40000 ALTER TABLE `productvariants` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-14 22:02:44
