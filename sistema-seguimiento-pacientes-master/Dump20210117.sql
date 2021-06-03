CREATE DATABASE  IF NOT EXISTS `pruebita` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `pruebita`;
-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: pruebita
-- ------------------------------------------------------
-- Server version	8.0.19

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
-- Table structure for table `diagnostico`
--

DROP TABLE IF EXISTS `diagnostico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `diagnostico` (
  `id` int NOT NULL AUTO_INCREMENT,
  `numEdiciones` int NOT NULL DEFAULT '0',
  `docPaciente` varchar(15) NOT NULL,
  `resumen` varchar(256) NOT NULL,
  `sintomas` varchar(512) NOT NULL,
  `diagnostico` varchar(1024) NOT NULL,
  `receta` varchar(512) NOT NULL,
  `altoriesgo` tinyint(1) NOT NULL,
  `fecha` datetime NOT NULL,
  `idmedico` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`,`numEdiciones`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `diagnostico`
--

/*!40000 ALTER TABLE `diagnostico` DISABLE KEYS */;
INSERT  IGNORE INTO `diagnostico` VALUES (11,0,'12345678','covid','tos, fiebre','positivo para covid','ivermectina',0,'2020-07-04 00:00:00',NULL),(11,1,'12345678','ss','asd','sad','sad',0,'2020-07-04 00:00:00',NULL),(11,2,'12345678','COVID-18','ninguno','positivont','pa que',1,'2018-11-30 00:00:00',NULL),(13,0,'12345678','asdf','asdf','asdf','asdf',1,'2021-01-17 00:00:00',NULL),(14,0,'12345678','sadf','asdf','asdf','asdf',0,'2021-01-17 00:00:00','123456');
/*!40000 ALTER TABLE `diagnostico` ENABLE KEYS */;

--
-- Table structure for table `login`
--

DROP TABLE IF EXISTS `login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `login` (
  `idlogin` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(60) NOT NULL,
  `tipouser` varchar(15) NOT NULL,
  PRIMARY KEY (`idlogin`),
  UNIQUE KEY `unique_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `login`
--

/*!40000 ALTER TABLE `login` DISABLE KEYS */;
INSERT  IGNORE INTO `login` VALUES (1,'user','$2a$10$lV2jDxbtAYi4CuieewFmSu4MXG3nm8Vy0WQdEratUVrqifCpXoyTK','MEDICO'),(2,'magico','$2a$10$X8r3h5kNd5qvUCIXMAGXg.A9jYX4E3XR.vDnm/tc31mD4sBpBW/Q.','MEDICO'),(3,'admin','$2a$10$aMeRdwWocUFjR0SAcp/tBOMtsn1qosicjoGQLIPKjriJZtgbMNzhe','ADMIN');
/*!40000 ALTER TABLE `login` ENABLE KEYS */;

--
-- Table structure for table `medico`
--

DROP TABLE IF EXISTS `medico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `medico` (
  `idmedico` varchar(8) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `especialidad` varchar(45) NOT NULL,
  `id_login` int DEFAULT NULL,
  PRIMARY KEY (`idmedico`),
  KEY `fk_loginm` (`id_login`),
  CONSTRAINT `fk_loginm` FOREIGN KEY (`id_login`) REFERENCES `login` (`idlogin`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medico`
--

/*!40000 ALTER TABLE `medico` DISABLE KEYS */;
INSERT  IGNORE INTO `medico` VALUES ('123456','Garcia Gamboa Martin','Cardiologia',2),('24321','asdf','safd',NULL),('654321','Juan Gonzales','Cardiologia',NULL);
/*!40000 ALTER TABLE `medico` ENABLE KEYS */;

--
-- Table structure for table `pacientep`
--

DROP TABLE IF EXISTS `pacientep`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pacientep` (
  `dni` varchar(8) NOT NULL,
  `nombres` varchar(45) NOT NULL,
  `id_login` int DEFAULT NULL,
  PRIMARY KEY (`dni`),
  KEY `fk_loginpa` (`id_login`),
  CONSTRAINT `fk_loginpa` FOREIGN KEY (`id_login`) REFERENCES `login` (`idlogin`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pacientep`
--

/*!40000 ALTER TABLE `pacientep` DISABLE KEYS */;
INSERT  IGNORE INTO `pacientep` VALUES ('12345678','sdafas',NULL),('98765432','Jimmy Kochi',NULL);
/*!40000 ALTER TABLE `pacientep` ENABLE KEYS */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed
