-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: shop_legacy
-- ------------------------------------------------------
-- Server version	9.2.0

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
-- Table structure for table `product_image`
--

DROP TABLE IF EXISTS `product_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_image` (
  `imageId` bigint NOT NULL AUTO_INCREMENT,
  `prodNo` bigint NOT NULL,
  `imageUrl` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `isMain` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT 'N',
  `crtDt` datetime DEFAULT CURRENT_TIMESTAMP,
  `sortOrder` int DEFAULT NULL,
  PRIMARY KEY (`imageId`),
  KEY `product_image_ibfk_1` (`prodNo`),
  CONSTRAINT `product_image_ibfk_1` FOREIGN KEY (`prodNo`) REFERENCES `product_master` (`prodNo`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_image`
--

LOCK TABLES `product_image` WRITE;
/*!40000 ALTER TABLE `product_image` DISABLE KEYS */;
INSERT INTO `product_image` VALUES (1,1,'https://d2gfz7wkiigkmv.cloudfront.net/pickin/2/1/2/HEL9pINJRqaLuwuYBFnXeA','Y','2025-05-25 17:09:39',1),(2,2,'https://intl.jlab.com/cdn/shop/products/GOChargeMouse3-min_2000x.jpg?v=1661468608','Y','2025-05-25 17:11:02',1),(3,3,'https://ae01.alicdn.com/kf/S09d0b4bb2444438c980476c0ad5ff9b1X.jpg?width=600&height=600&hash=1200','Y','2025-05-25 17:11:28',1),(6,1,'https://www.schezade.co.kr/goods/img/img_B5745.jpg','N','2025-05-29 21:19:45',2),(7,1,'https://cdn.imweb.me/thumbnail/20210729/fd3376f7e265c.jpg','N','2025-05-29 21:19:47',3),(8,1,'https://s3.cloud.cmctelecom.vn/tinhte2/2020/08/5104936_00000IMG_00000_BURST20200805143431000_COVER_Copy.jpg','N','2025-05-29 21:19:50',4);
/*!40000 ALTER TABLE `product_image` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-09-18  7:40:08
