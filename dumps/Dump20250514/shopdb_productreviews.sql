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
-- Table structure for table `productreviews`
--

DROP TABLE IF EXISTS `productreviews`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `productreviews` (
  `review_id` int NOT NULL AUTO_INCREMENT,
  `product_id` int NOT NULL,
  `user_id` int NOT NULL,
  `rating` int DEFAULT NULL,
  `review_text` text,
  `review_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`review_id`),
  KEY `product_id` (`product_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `productreviews_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`),
  CONSTRAINT `productreviews_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `productreviews_chk_1` CHECK (((`rating` >= 1) and (`rating` <= 5)))
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productreviews`
--

LOCK TABLES `productreviews` WRITE;
/*!40000 ALTER TABLE `productreviews` DISABLE KEYS */;
INSERT INTO `productreviews` VALUES (1,1,1,5,'Sản phẩm tuyệt vời, camera đẹp, pin trâu.','2025-05-14 14:57:17'),(2,1,2,4,'Máy dùng ổn, nhưng giá hơi cao.','2025-05-14 14:57:17'),(3,1,3,5,'Rất hài lòng với sản phẩm và dịch vụ của shop.','2025-05-14 14:57:17'),(4,1,1,4,'Thiết kế đẹp, hiệu năng mạnh mẽ.','2025-05-14 14:57:17'),(5,1,2,5,'Đây là chiếc điện thoại tốt nhất tôi từng dùng.','2025-05-14 14:57:17'),(6,1,3,3,'Máy có vài lỗi nhỏ, nhưng không ảnh hưởng nhiều.','2025-05-14 14:57:17'),(7,2,1,5,'Sản phẩm rất tốt, camera zoom đỉnh.','2025-05-14 14:57:17'),(8,2,2,4,'Máy mượt, pin khỏe, thiết kế đẹp.','2025-05-14 14:57:17'),(9,2,3,5,'Tôi rất thích chiếc điện thoại này.','2025-05-14 14:57:17'),(10,2,1,4,'Màn hình đẹp, hiệu năng ổn định.','2025-05-14 14:57:17'),(11,2,2,5,'Camera chụp ảnh quá đẹp.','2025-05-14 14:57:17'),(12,2,3,3,'Giá hơi cao so với các đối thủ.','2025-05-14 14:57:17'),(13,3,1,4,'Máy tốt trong tầm giá, pin trâu.','2025-05-14 14:57:17'),(14,3,2,3,'Camera chấp nhận được, màn hình đẹp.','2025-05-14 14:57:17'),(15,3,3,4,'Hiệu năng ổn, chơi game tốt.','2025-05-14 14:57:17'),(16,3,1,3,'Thiết kế đẹp, nhưng hơi bám vân tay.','2025-05-14 14:57:17'),(17,3,2,4,'Giá rẻ, đáng mua.','2025-05-14 14:57:17'),(18,4,1,5,'Máy đẹp, chip M2 mạnh mẽ, màn hình xuất sắc.','2025-05-14 14:57:17'),(19,4,2,5,'Hiệu năng vượt trội, thời lượng pin dài.','2025-05-14 14:57:17'),(20,4,3,4,'Thiết kế sang trọng, bàn phím gõ êm.','2025-05-14 14:57:17'),(21,4,1,5,'Đây là chiếc máy tính tốt nhất tôi từng dùng.','2025-05-14 14:57:17'),(22,4,2,4,'Giá cao nhưng đáng đồng tiền.','2025-05-14 14:57:17'),(23,5,1,4,'Máy đẹp, màn hình 4K sắc nét, hiệu năng tốt.','2025-05-14 14:57:17'),(24,5,2,3,'Thiết kế mỏng nhẹ, dễ mang theo.','2025-05-14 14:57:17'),(25,5,3,4,'Cấu hình mạnh mẽ, phù hợp cho công việc và giải trí.','2025-05-14 14:57:17'),(26,5,1,4,'Màn hình đẹp, màu sắc sống động.','2025-05-14 14:57:17'),(27,5,2,3,'Giá hơi cao, tản nhiệt chưa tốt.','2025-05-14 14:57:17'),(28,6,1,4,'Máy chơi game tốt, tản nhiệt ổn định.','2025-05-14 14:57:17'),(29,6,2,4,'Thiết kế hầm hố, bàn phím RGB đẹp.','2025-05-14 14:57:17'),(30,6,3,3,'Hiệu năng khá, giá tốt.','2025-05-14 14:57:17'),(31,6,1,4,'Màn hình 144Hz mượt mà.','2025-05-14 14:57:17'),(32,6,2,3,'Loa chưa hay lắm.','2025-05-14 14:57:17'),(33,7,1,5,'Áo chất lượng tốt, mặc thoải mái.','2025-05-14 14:57:17'),(34,7,2,4,'Giá cả hợp lý, nhiều màu sắc để lựa chọn.','2025-05-14 14:57:17'),(35,7,3,5,'Tôi rất thích áo của Uniqlo.','2025-05-14 14:57:17'),(36,8,1,4,'Quần jeans đẹp, chất liệu tốt.','2025-05-14 14:57:17'),(37,8,2,3,'Dáng quần ôm vừa vặn, màu sắc đẹp.','2025-05-14 14:57:17'),(38,8,3,4,'Tôi hài lòng với sản phẩm này.','2025-05-14 14:57:17'),(39,9,1,4,'Áo khoác đẹp, chất liệu chống nước tốt.','2025-05-14 14:57:17'),(40,9,2,3,'Nhiều ngăn tiện lợi, màu sắc đẹp.','2025-05-14 14:57:17'),(41,9,3,4,'Tôi thích chiếc áo này.','2025-05-14 14:57:17'),(42,10,1,5,'Màu son đẹp, lì, lâu trôi.','2025-05-14 14:57:17'),(43,10,2,4,'Chất son mịn, không gây khô môi.','2025-05-14 14:57:17'),(44,10,3,5,'Tôi rất thích màu son này.','2025-05-14 14:57:17'),(45,11,1,5,'Kem chống nắng tốt, không gây nhờn dính.','2025-05-14 14:57:17'),(46,11,2,4,'Chống nắng hiệu quả, không kích ứng da.','2025-05-14 14:57:17'),(47,11,3,5,'Tôi tin dùng sản phẩm này.','2025-05-14 14:57:17'),(48,12,1,4,'Nước hoa hồng dịu nhẹ, dưỡng ẩm tốt.','2025-05-14 14:57:17'),(49,12,2,3,'Mùi thơm dễ chịu, thấm nhanh.','2025-05-14 14:57:17'),(50,12,3,4,'Tôi thấy da sáng hơn sau khi dùng.','2025-05-14 14:57:17'),(51,13,1,5,'Nồi chiên không dầu tiện lợi, nấu ăn nhanh.','2025-05-14 14:57:17'),(52,13,2,4,'Dễ vệ sinh, ít dầu mỡ.','2025-05-14 14:57:17'),(53,13,3,5,'Tôi rất thích nồi chiên này.','2025-05-14 14:57:17'),(54,14,1,4,'Máy xay sinh tố mạnh mẽ, xay nhuyễn.','2025-05-14 14:57:17'),(55,14,2,3,'Dễ sử dụng, dễ vệ sinh.','2025-05-14 14:57:17'),(56,14,3,4,'Tôi hài lòng với sản phẩm này.','2025-05-14 14:57:17'),(57,15,1,5,'Bộ nồi inox 3 lớp chất lượng tốt.','2025-05-14 14:57:17'),(58,15,2,4,'Chống dính, nấu ăn ngon.','2025-05-14 14:57:17'),(59,15,3,5,'Tôi rất thích bộ nồi này.','2025-05-14 14:57:17'),(60,16,1,4,'Sách hay, ý nghĩa.','2025-05-14 14:57:17'),(61,16,2,3,'Nội dung hấp dẫn, dễ đọc.','2025-05-14 14:57:17'),(62,16,3,4,'Tôi học được nhiều điều từ cuốn sách này.','2025-05-14 14:57:17'),(63,17,1,5,'Tiểu thuyết kinh điển, đáng đọc.','2025-05-14 14:57:17'),(64,17,2,4,'Cốt truyện hay, nhân vật ấn tượng.','2025-05-14 14:57:17'),(65,17,3,5,'Tôi rất thích cuốn sách này.','2025-05-14 14:57:17'),(66,18,1,4,'Sách về tâm lý học hay, hữu ích.','2025-05-14 14:57:17'),(67,18,2,3,'Nội dung sâu sắc, có tính ứng dụng cao.','2025-05-14 14:57:17'),(68,18,3,4,'Tôi thấy mình hiểu rõ hơn về bản thân sau khi đọc.','2025-05-14 14:57:17'),(69,19,1,5,'Sách truyền cảm hứng, động lực.','2025-05-14 14:57:17'),(70,19,2,4,'Bài viết hay, dễ đọc.','2025-05-14 14:57:17'),(71,19,3,5,'Tôi rất thích cuốn sách này.','2025-05-14 14:57:17');
/*!40000 ALTER TABLE `productreviews` ENABLE KEYS */;
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
