--CREATE DATABASE  IF NOT EXISTS `pruebita` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
--USE `pruebita`;
-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: pruebita
-- ------------------------------------------------------
-- Server version	8.0.19

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `analisis`
--

DROP TABLE IF EXISTS `analisis`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `analisis` (
  `id_analisis` int NOT NULL AUTO_INCREMENT,
  `id_diagnostico` int NOT NULL,
  `doc_paciente` varchar(45) NOT NULL,
  `tipo_analisis` varchar(255) NOT NULL,
  `fecha_solicitud` datetime DEFAULT NULL,
  `fecha_realizacion` datetime DEFAULT NULL,
  PRIMARY KEY (`id_analisis`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `analisis`
--

INSERT  IGNORE INTO `analisis` VALUES (1,11,'12345678','prueba covid','2023-01-21 00:00:00','2023-01-21 00:00:00'),(2,15,'12345678','prueba molecular covid','2021-01-25 00:33:43','2021-01-26 02:07:54'),(4,15,'12345678','test de sangre','2021-01-25 00:34:28','2021-01-26 20:17:21'),(6,16,'12345678','test de sangre','2021-01-26 17:58:14','2021-01-26 17:59:47');

--
-- Table structure for table `analisis_resultados`
--

DROP TABLE IF EXISTS `analisis_resultados`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `analisis_resultados` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_analisis` int NOT NULL,
  `campo` varchar(255) NOT NULL,
  `valor` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `analisis_resultados`
--

INSERT  IGNORE INTO `analisis_resultados` VALUES (1,1,'test serologico','positivo'),(4,2,'Prueba molecular','Negativo'),(5,6,'Glucosa','250'),(6,4,'hemoglobina','420'),(7,0,'glucosa','322'),(8,0,'oxigenacion','69');

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
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `diagnostico`
--

INSERT  IGNORE INTO `diagnostico` VALUES (11,0,'12345678','covid','tos, fiebre','positivo para covid','ivermectina',0,'2020-07-04 00:00:00',NULL),(11,1,'12345678','ss','asd','sad','sad',0,'2020-07-04 00:00:00',NULL),(11,2,'12345678','COVID-18','ninguno','positivont','pa que',1,'2018-11-30 00:00:00',NULL),(13,0,'12345678','asdf','asdf','asdf','asdf',1,'2021-01-17 00:00:00',NULL),(14,0,'12345678','sadf','asdf','asdf','asdf',0,'2021-01-17 00:00:00','123456'),(15,0,'98765432','probanding','asdf','asdf','asdf',0,'2021-01-24 00:00:00',NULL),(16,0,'12345678','Sospecha de diabetes','mareos, dolor de cabeza','Sospecha de diabetes melitus tipo 2','No necesario',0,'2021-01-26 00:00:00','123456');

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

INSERT  IGNORE INTO `login` VALUES (1,'user','$2a$10$lV2jDxbtAYi4CuieewFmSu4MXG3nm8Vy0WQdEratUVrqifCpXoyTK','PACIENTE'),(2,'magico','$2a$10$X8r3h5kNd5qvUCIXMAGXg.A9jYX4E3XR.vDnm/tc31mD4sBpBW/Q.','MEDICO'),(3,'admin','$2a$10$aMeRdwWocUFjR0SAcp/tBOMtsn1qosicjoGQLIPKjriJZtgbMNzhe','ADMIN');

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

INSERT  IGNORE INTO `medico` VALUES ('123456','Garcia Gamboa Martin','Cardiologia',2),('24321','asdf','safd',NULL),('654321','Juan Gonzales','Cardiologia',NULL);

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

INSERT  IGNORE INTO `pacientep` VALUES ('12345678','Martin garcia',NULL),('98765432','Jimmy Kochi',1);

--
-- Table structure for table `sesion_llamada`
--

DROP TABLE IF EXISTS `sesion_llamada`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sesion_llamada` (
  `id` int NOT NULL AUTO_INCREMENT,
  `sessionid` varchar(255) NOT NULL,
  `idmedico` varchar(45) NOT NULL,
  `idpaciente` varchar(45) NOT NULL,
  `finalizado` tinyint(1) NOT NULL DEFAULT '0',
  `fecha` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sesion_llamada`
--

INSERT  IGNORE INTO `sesion_llamada` VALUES (1,'1_MX40Njg4OTc2NH5-MTYxMjE2MDk2MjU4MH43Y3JKRGlESWtnb1BDZWJINWZzOU9LMm5-UH4','123456','12345678',0,'2021-02-17 09:59:00'),(2,'2_MX40Njg4OTc2NH5-MTYxMjIxMjUyMDc0MX5SRmd2RDlISW9FaUVjYkk2WEM5d1Z6Vzl-UH4','123456','98765432',0,'2021-02-17 15:00:00');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed
