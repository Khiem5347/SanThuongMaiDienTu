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
-- Table structure for table `vouchers`
--

DROP TABLE IF EXISTS `vouchers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vouchers` (
  `voucher_id` int NOT NULL AUTO_INCREMENT,
  `voucher_code` varchar(50) NOT NULL,
  `voucher_description` text,
  `discount_type` enum('percentage','fixed') NOT NULL,
  `discount_value` decimal(10,2) NOT NULL,
  `min_price` int NOT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `max_usage` int DEFAULT NULL,
  `usage_count` int DEFAULT '0',
  `target_user_role` set('buyer','seller','all') DEFAULT 'all',
  PRIMARY KEY (`voucher_id`),
  UNIQUE KEY `voucher_code` (`voucher_code`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vouchers`
--

LOCK TABLES `vouchers` WRITE;
/*!40000 ALTER TABLE `vouchers` DISABLE KEYS */;
INSERT INTO `vouchers` VALUES (1,'GIAM10K','Giảm 10k cho đơn hàng đầu tiên','fixed',10000.00,0,'2024-01-01','2024-12-31',100,0,'buyer'),(2,'SALE20','Giảm 20% tối đa 50k','percentage',20.00,100000,'2024-01-15','2024-02-15',50,0,'buyer'),(3,'FREESHIP','Miễn phí vận chuyển đơn hàng > 200k','fixed',30000.00,200000,'2024-02-01','2024-03-31',30,0,'buyer'),(4,'VIP50','Giảm 50k cho thành viên VIP','fixed',50000.00,500000,'2024-03-01','2024-12-31',20,0,'buyer'),(5,'FLASH30','Giảm 30% trong khung giờ vàng','percentage',30.00,150000,'2024-03-10','2024-03-10',10,0,'buyer'),(6,'NEWYEAR24','Voucher Tết Nguyên Đán 2024','percentage',15.00,0,'2024-02-08','2024-02-14',100,0,'all'),(7,'BLACKFRIDAY','Voucher Black Friday','percentage',25.00,200000,'2024-11-29','2024-11-29',50,0,'all'),(8,'CHRISTMAS23','Voucher Giáng Sinh 2023','percentage',20.00,0,'2023-12-24','2023-12-25',80,0,'all'),(9,'SELLER10','Giảm 10% cho người bán mới','percentage',10.00,0,'2024-01-01','2024-03-31',20,0,'seller'),(10,'SHIP50K','Hỗ trợ 50k phí vận chuyển cho người bán','fixed',50000.00,300000,'2024-02-01','2024-04-30',15,0,'seller'),(11,'MUA1TANG1','Mua 1 tặng 1 (giảm giá sản phẩm rẻ nhất)','percentage',50.00,100000,'2024-05-01','2024-05-31',25,0,'buyer'),(12,'GIAM50KDON300K','Giảm 50k cho đơn hàng từ 300k','fixed',50000.00,300000,'2024-04-01','2024-04-30',40,0,'buyer'),(13,'SALEOFF15','Giảm giá 15% toàn bộ sản phẩm','percentage',15.00,0,'2024-06-01','2024-06-30',60,0,'all'),(14,'VOUCHERBACK','Hoàn tiền 10% cho đơn hàng','percentage',10.00,100000,'2024-07-01','2024-07-31',35,0,'buyer'),(15,'BIGSALE88','Siêu sale 8/8','percentage',30.00,0,'2024-08-08','2024-08-08',100,0,'all'),(16,'GIAITRIVUI','Voucher giải trí (phim, nhạc, game)','fixed',20000.00,50000,'2024-09-01','2024-09-30',15,0,'buyer'),(17,'SINNHATSHOP','Voucher sinh nhật shop','percentage',20.00,150000,'2024-10-01','2024-10-31',20,0,'all'),(18,'TETSUMVAY','Voucher sắm Tết','percentage',18.00,0,'2025-01-20','2025-02-05',120,0,'all'),(19,'HELLOSUMMER','Voucher chào hè','percentage',22.00,100000,'2024-05-15','2024-06-15',70,0,'all'),(20,'HAPPYWOMEN','Voucher ngày phụ nữ','percentage',20.00,0,'2025-03-08','2025-03-08',90,0,'all');
/*!40000 ALTER TABLE `vouchers` ENABLE KEYS */;
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
