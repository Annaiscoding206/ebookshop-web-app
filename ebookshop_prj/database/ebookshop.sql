-- MySQL dump 10.13  Distrib 9.6.0, for Win64 (x86_64)
--
-- Host: localhost    Database: ebookshop
-- ------------------------------------------------------
-- Server version	9.6.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- GTID state at the beginning of the backup 
--

SET @@GLOBAL.GTID_PURGED=/*!80000 '+'*/ 'e051e6ae-0838-11f1-97e8-c475ab8a45a7:1-136';

--
-- Current Database: `ebookshop`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `ebookshop` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `ebookshop`;

--
-- Table structure for table `books`
--

DROP TABLE IF EXISTS `books`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `books` (
  `id` int NOT NULL,
  `title` varchar(50) DEFAULT NULL,
  `author` varchar(50) DEFAULT NULL,
  `price` float DEFAULT NULL,
  `qty` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `books`
--

LOCK TABLES `books` WRITE;
/*!40000 ALTER TABLE `books` DISABLE KEYS */;
INSERT INTO `books` VALUES (1001,'Java for dummies','Tan Ah Teck',11.8877,4),(1002,'More Java for dummies','Tan Ah Teck',22.22,20),(1003,'More Java for more dummies','Mohammad Ali',33.33,24),(1004,'A Cup of Java','Kumar',44.44,41),(1005,'A Teaspoon of Java','Kevin Jones',55.55,54),(2002,'Clean Architecture','Robert Martin',42.5,2),(2003,'Effective Java','Joshua Bloch',45,4),(2004,'Refactoring','Martin Fowler',50,3),(2005,'Java Concurrency in Practice','Brian Goetz',48,2);
/*!40000 ALTER TABLE `books` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customers` (
  `customer_id` int NOT NULL AUTO_INCREMENT,
  `cust_name` varchar(100) NOT NULL,
  `cust_email` varchar(100) NOT NULL,
  `cust_phone` varchar(15) DEFAULT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  PRIMARY KEY (`customer_id`),
  UNIQUE KEY `cust_email` (`cust_email`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` VALUES (1,'Anna','anna@gmail.com','0918665167','AnnaDo','annado'),(3,'Khang','khang@gmail.com','6536785','khangtin','tintin'),(4,'Jane','jane@gmail.com','23423423','jane','jane'),(5,'My','my@gmail.com','12312334','my','my'),(6,'Tin','tin@gmail.com','34563456','tintin','tintin');
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_records`
--

DROP TABLE IF EXISTS `order_records`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_records` (
  `id` int DEFAULT NULL,
  `qty_ordered` int DEFAULT NULL,
  `cust_name` varchar(30) DEFAULT NULL,
  `cust_email` varchar(30) DEFAULT NULL,
  `cust_phone` varchar(15) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_records`
--

LOCK TABLES `order_records` WRITE;
/*!40000 ALTER TABLE `order_records` DISABLE KEYS */;
INSERT INTO `order_records` VALUES (1003,1,'Anna','anna@gmail.com','0918665167'),(1001,1,'Anna','anna@gmail.com','0918665167'),(1001,1,'Anna','anna@gmail.com','0918665167'),(2003,1,'Khang','khang@gmail.com','456789'),(2003,1,'Jane','jane@gmail.com','23423423'),(1003,1,'Anna','anna@gmail.com','0918665167'),(1001,1,'Anna','anna@gmail.com','0918665167'),(1001,1,'Anna','anna@gmail.com','0918665167'),(1001,1,'Anna','anna@gmail.com','0918665167'),(1002,1,'Anna','anna@gmail.com','0918665167'),(1001,1,'Anna','anna@gmail.com','0918665167'),(1003,1,'Anna','anna@gmail.com','0918665167'),(2002,1,'Anna','anna@gmail.com','0918665167'),(1001,1,'Anna','anna@gmail.com','0918665167'),(1001,1,'Anna','anna@gmail.com','0918665167'),(1001,1,'Anna','anna@gmail.com','0918665167'),(1001,2,'Anna','anna@gmail.com','0918665167'),(1001,1,'Anna','anna@gmail.com','0918665167'),(2002,1,'Kate','kate@gmail.com','45645678'),(1001,1,'Anna','anna@gmail.com','90890898'),(1002,1,'Anna','anna@gmail.com','90890898');
/*!40000 ALTER TABLE `order_records` ENABLE KEYS */;
UNLOCK TABLES;
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-03-13 20:52:04
