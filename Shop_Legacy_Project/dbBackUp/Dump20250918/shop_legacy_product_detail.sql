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
-- Table structure for table `product_detail`
--

DROP TABLE IF EXISTS `product_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_detail` (
  `prodNo` bigint NOT NULL,
  `detailDesc` text COLLATE utf8mb4_unicode_ci,
  `notice` text COLLATE utf8mb4_unicode_ci,
  `shippingInfo` text COLLATE utf8mb4_unicode_ci,
  `additionalInfo` text COLLATE utf8mb4_unicode_ci,
  PRIMARY KEY (`prodNo`),
  CONSTRAINT `fk_product_detail_prodNo` FOREIGN KEY (`prodNo`) REFERENCES `product_master` (`prodNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_detail`
--

LOCK TABLES `product_detail` WRITE;
/*!40000 ALTER TABLE `product_detail` DISABLE KEYS */;
INSERT INTO `product_detail` VALUES (1,'<p><strong>선명한 음질</strong>과 <em>긴 배터리 수명</em>을 자랑하는 무선 이어폰입니다. <br>Bluetooth 5.0을 지원하며, 충전 케이스 포함으로 편리한 사용이 가능합니다.</p>','습기나 물에 닿지 않도록 주의하세요. 충전 시에는 정품 케이블을 사용하세요.','<p>평균 배송일: <strong>2~3일</strong><br>배송비: <strong>3,000원</strong> (3만원 이상 무료배송)</p>','제조사: XYZ전자<br>제조국: 중국<br>품질보증기간: 6개월'),(2,'<p>부드러운 곡선 디자인과 <strong>정밀한 센서</strong>를 갖춘 무선 마우스입니다. <br>Windows, Mac 모두 호환 가능하며, USB 수신기를 통한 빠른 연결을 지원합니다.</p>','전자기기 근처에서 수신 간섭이 있을 수 있습니다. 사용하지 않을 땐 전원을 꺼두세요.','<p>배송기간: <strong>1~2일</strong><br>배송비: <strong>무료</strong></p>','제조사: 에이비씨 컴퍼니<br>제조국: 대한민국<br>보증기간: 1년'),(3,'<p>12가지 컬러의 젤 잉크 펜 세트입니다. <br>부드러운 필기감과 <strong>선명한 색상</strong>으로 필기 및 꾸미기 모두에 적합합니다.</p>','만 3세 미만 아동은 사용을 금합니다. 뚜껑을 꼭 닫아 보관하세요.','<p>배송일: <strong>1~3일</strong><br>배송비: <strong>2,500원</strong> (2만원 이상 무료배송)</p>','제조사: 펜코리아<br>제조국: 대한민국<br>인증번호: KC-1234567');
/*!40000 ALTER TABLE `product_detail` ENABLE KEYS */;
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
