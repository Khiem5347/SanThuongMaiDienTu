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
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `product_id` int NOT NULL AUTO_INCREMENT,
  `category_id` int NOT NULL,
  `shop_id` int NOT NULL,
  `product_name` varchar(200) NOT NULL,
  `product_description` text,
  `product_star` int DEFAULT '0',
  `product_review` int DEFAULT '0',
  `product_sold` int DEFAULT '0',
  `product_main_image_url` varchar(255) NOT NULL,
  `product_min_price` int DEFAULT NULL,
  `product_max_price` int DEFAULT NULL,
  `number_of_likes` int DEFAULT '0',
  PRIMARY KEY (`product_id`),
  KEY `category_id` (`category_id`),
  KEY `shop_id` (`shop_id`),
  CONSTRAINT `products_ibfk_2` FOREIGN KEY (`category_id`) REFERENCES `categories` (`category_id`),
  CONSTRAINT `products_ibfk_3` FOREIGN KEY (`shop_id`) REFERENCES `shops` (`shop_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,1,1,'iPhone 15 Pro Max','Màn hình 6.7 inch, chip A17 Pro, camera 48MP',0,0,0,'img/iphone15.jpg',28000000,28000000,0),(2,1,1,'Samsung Galaxy S23 Ultra','Bút S Pen, camera 200MP, pin 5000mAh',0,0,0,'img/s23ultra.jpg',22000000,22000000,0),(3,1,1,'Xiaomi Redmi Note 12','Màn hình AMOLED 120Hz, chip Snapdragon 685',0,0,0,'img/redmi12.jpg',5000000,5000000,0),(4,2,1,'MacBook Pro M2 14inch','Chip Apple M2, 16GB RAM, SSD 512GB',0,0,0,'img/macbook14.jpg',35000000,35000000,0),(5,2,1,'Dell XPS 15','Core i7, RTX 3050, màn hình 4K OLED',0,0,0,'img/dellxps.jpg',28000000,28000000,0),(6,2,1,'Asus TUF Gaming F15','Core i5, RTX 4050, 144Hz',0,0,0,'img/asustuf.jpg',22000000,22000000,0),(7,3,1,'Áo thun nam Uniqlo','Chất liệu cotton 100%, co giãn tốt',0,0,0,'img/aothun.jpg',200000,200000,0),(8,3,1,'Quần jeans nữ Levi\'s','Dáng slim fit, màu xanh đậm',0,0,0,'img/jeans.jpg',800000,800000,0),(9,3,1,'Áo khoác dù nam','Chống nước, nhiều ngăn tiện lợi',0,0,0,'img/aokhoac.jpg',350000,350000,0),(10,4,1,'Son kem lì Maybelline','Màu đỏ cam, lâu trôi',0,0,0,'img/sonmaybelline.jpg',150000,150000,0),(11,4,1,'Kem chống nắng Anessa','SPF50+ PA++++, chống thấm nước',0,0,0,'img/anessa.jpg',450000,450000,0),(12,4,1,'Nước hoa hồng Laneige','Dưỡng ẩm, làm sáng da',0,0,0,'img/laneige.jpg',300000,300000,0),(13,5,1,'Nồi chiên không dầu','Dung tích 5L, công nghệ Rapid Air',0,0,0,'img/noichien.jpg',1200000,1200000,0),(14,5,1,'Máy xay sinh tố','Công suất 1000W, lưỡi dao inox',0,0,0,'img/mayxay.jpg',600000,600000,0),(15,5,1,'Bộ nồi inox 3 lớp','Chống dính, dẫn nhiệt đều',0,0,0,'img/bonoinox.jpg',900000,900000,0),(16,6,1,'Đắc Nhân Tâm','Sách kỹ năng sống bán chạy nhất',0,0,0,'img/dacnhantam.jpg',80000,80000,0),(17,6,1,'Nhà Giả Kim','Tiểu thuyết kinh điển của Paulo Coelho',0,0,0,'img/nhagiakim.jpg',120000,120000,0),(18,6,1,'Tư Duy Nhanh và Chậm','Sách về tâm lý học hành vi',0,0,0,'img/tuduy.jpg',150000,150000,0),(19,6,1,'Cafe Cùng Tony','Tập hợp những bài viết truyền cảm hứng',0,0,0,'img/cafetony.jpg',90000,90000,0);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
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
