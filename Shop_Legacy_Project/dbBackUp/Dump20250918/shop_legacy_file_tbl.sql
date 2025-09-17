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
-- Table structure for table `file_tbl`
--

DROP TABLE IF EXISTS `file_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `file_tbl` (
  `fileId` bigint NOT NULL AUTO_INCREMENT COMMENT '파일 고유 번호',
  `refType` varchar(20) DEFAULT NULL COMMENT '참조 유형 (cart, order 등)',
  `refId` bigint DEFAULT NULL COMMENT '참조 ID (ex. cartNo, orderNo)',
  `filePath` varchar(1000) DEFAULT NULL COMMENT '파일 저장 경로',
  `fileName` varchar(255) DEFAULT NULL COMMENT '파일 이름',
  `uploadType` varchar(10) DEFAULT NULL COMMENT '업로드 방식 (DIRECT/URL)',
  `fileUrl` varchar(1000) DEFAULT NULL COMMENT 'URL 업로드일 경우 원본 URL',
  `uploadStatus` varchar(20) DEFAULT NULL COMMENT '업로드 상태 (SUCCESS/FAIL)',
  `delYn` char(1) DEFAULT 'N' COMMENT '삭제 여부',
  `crtDt` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
  `updDt` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일',
  `delDt` datetime DEFAULT NULL COMMENT '삭제일',
  PRIMARY KEY (`fileId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='공통 파일 관리 테이블';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `file_tbl`
--

LOCK TABLES `file_tbl` WRITE;
/*!40000 ALTER TABLE `file_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `file_tbl` ENABLE KEYS */;
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
