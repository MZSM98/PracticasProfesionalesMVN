-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: practicasprofesionales
-- ------------------------------------------------------
-- Server version	8.0.39

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
-- Table structure for table `academicoevaluador`
--

DROP TABLE IF EXISTS `academicoevaluador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `academicoevaluador` (
  `numeroDeTrabajador` varchar(9) NOT NULL,
  `nombreAcademico` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`numeroDeTrabajador`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `academicoevaluador`
--

LOCK TABLES `academicoevaluador` WRITE;
/*!40000 ALTER TABLE `academicoevaluador` DISABLE KEYS */;
INSERT INTO `academicoevaluador` VALUES ('M12345678','Martin');
/*!40000 ALTER TABLE `academicoevaluador` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `autoevaluacion`
--

DROP TABLE IF EXISTS `autoevaluacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `autoevaluacion` (
  `puntaje` int NOT NULL,
  `totalHoras` int NOT NULL,
  `ProfesorEE_numeroDeTrabajador` varchar(9) NOT NULL,
  `Estudiante_matricula` varchar(9) NOT NULL,
  KEY `fk_AutoEvaluacion_ProfesorEE1_idx` (`ProfesorEE_numeroDeTrabajador`),
  KEY `fk_AutoEvaluacion_Estudiante1_idx` (`Estudiante_matricula`),
  CONSTRAINT `fk_AutoEvaluacion_Estudiante1` FOREIGN KEY (`Estudiante_matricula`) REFERENCES `estudiante` (`matricula`),
  CONSTRAINT `fk_AutoEvaluacion_ProfesorEE1` FOREIGN KEY (`ProfesorEE_numeroDeTrabajador`) REFERENCES `profesoree` (`numeroDeTrabajador`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `autoevaluacion`
--

LOCK TABLES `autoevaluacion` WRITE;
/*!40000 ALTER TABLE `autoevaluacion` DISABLE KEYS */;
/*!40000 ALTER TABLE `autoevaluacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coordinador`
--

DROP TABLE IF EXISTS `coordinador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `coordinador` (
  `numeroDeTrabajador` varchar(9) NOT NULL,
  `nombreCoordinador` varchar(100) NOT NULL,
  `telefono` varchar(10) NOT NULL,
  PRIMARY KEY (`numeroDeTrabajador`),
  UNIQUE KEY `numeroDeTrabajador_UNIQUE` (`numeroDeTrabajador`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coordinador`
--

LOCK TABLES `coordinador` WRITE;
/*!40000 ALTER TABLE `coordinador` DISABLE KEYS */;
/*!40000 ALTER TABLE `coordinador` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estudiante`
--

DROP TABLE IF EXISTS `estudiante`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `estudiante` (
  `matricula` varchar(9) NOT NULL,
  `nombreEstudiante` varchar(100) NOT NULL,
  `periodoEscolar` varchar(45) NOT NULL,
  `seccionEstudiante` varchar(45) NOT NULL,
  `AvanceCrediticio` int NOT NULL,
  `promedio` double(2,2) NOT NULL,
  PRIMARY KEY (`matricula`),
  UNIQUE KEY `matricula_UNIQUE` (`matricula`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estudiante`
--

LOCK TABLES `estudiante` WRITE;
/*!40000 ALTER TABLE `estudiante` DISABLE KEYS */;
INSERT INTO `estudiante` VALUES ('S23014093','Marcos Zenon Sanchez','Febrero 2025 - Junio 2025','Febrero 2025 - Junio 2025',58,8.00);
/*!40000 ALTER TABLE `estudiante` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `organizacionvinculada`
--

DROP TABLE IF EXISTS `organizacionvinculada`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `organizacionvinculada` (
  `rfcMoral` varchar(12) NOT NULL,
  `nombreOV` varchar(100) DEFAULT NULL,
  `telefonoOV` varchar(10) NOT NULL,
  `direccionOV` varchar(200) NOT NULL,
  `estadoOV` enum('ACTIVO','INACTIVO') NOT NULL DEFAULT 'ACTIVO',
  PRIMARY KEY (`rfcMoral`),
  UNIQUE KEY `rfc_UNIQUE` (`rfcMoral`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `organizacionvinculada`
--

LOCK TABLES `organizacionvinculada` WRITE;
/*!40000 ALTER TABLE `organizacionvinculada` DISABLE KEYS */;
INSERT INTO `organizacionvinculada` VALUES ('SAM981231H54','Computación sin Fronteras y ni Señuelos','2282928271','Araucarias 89','ACTIVO'),('SAM981231H76','SiDoctom','2282929871','Ciruelos 2','INACTIVO'),('SAM981231H78','Marcos2','1234567890','Araucarias','INACTIVO'),('SRT981231U78','Marcos','1234567890','Ciruelos 15','ACTIVO');
/*!40000 ALTER TABLE `organizacionvinculada` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profesoree`
--

DROP TABLE IF EXISTS `profesoree`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `profesoree` (
  `numeroDeTrabajador` varchar(9) NOT NULL,
  `seccion` varchar(45) NOT NULL,
  `nombreProfesor` varchar(45) NOT NULL,
  PRIMARY KEY (`numeroDeTrabajador`),
  UNIQUE KEY `numeroDeTrabajador_UNIQUE` (`numeroDeTrabajador`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profesoree`
--

LOCK TABLES `profesoree` WRITE;
/*!40000 ALTER TABLE `profesoree` DISABLE KEYS */;
INSERT INTO `profesoree` VALUES ('GUI202020','2','Guillermo'),('VIC202020','3','Victor');
/*!40000 ALTER TABLE `profesoree` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `proyecto`
--

DROP TABLE IF EXISTS `proyecto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `proyecto` (
  `proyectoID` int NOT NULL AUTO_INCREMENT,
  `titulo` varchar(100) NOT NULL,
  `periodoEscolar` varchar(45) NOT NULL,
  `descripcion` varchar(255) NOT NULL,
  `rfcMoral` varchar(12) NOT NULL,
  `estadoProyecto` enum('ACTIVO','INACTIVO') NOT NULL DEFAULT 'ACTIVO',
  PRIMARY KEY (`proyectoID`),
  KEY `fk_proyecto_organizacion` (`rfcMoral`),
  CONSTRAINT `fk_proyecto_organizacion` FOREIGN KEY (`rfcMoral`) REFERENCES `organizacionvinculada` (`rfcMoral`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proyecto`
--

LOCK TABLES `proyecto` WRITE;
/*!40000 ALTER TABLE `proyecto` DISABLE KEYS */;
/*!40000 ALTER TABLE `proyecto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reporteparcial`
--

DROP TABLE IF EXISTS `reporteparcial`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reporteparcial` (
  `fechaDeReporte` date NOT NULL,
  `horasReportadas` int NOT NULL,
  `Estudiante_matricula` varchar(9) NOT NULL,
  `ProfesorEE_numeroDeTrabajador` varchar(9) NOT NULL,
  PRIMARY KEY (`fechaDeReporte`),
  UNIQUE KEY `fechaDeReporte_UNIQUE` (`fechaDeReporte`),
  KEY `fk_ReporteParcial_Estudiante1_idx` (`Estudiante_matricula`),
  KEY `fk_ReporteParcial_ProfesorEE1_idx` (`ProfesorEE_numeroDeTrabajador`),
  CONSTRAINT `fk_ReporteParcial_Estudiante1` FOREIGN KEY (`Estudiante_matricula`) REFERENCES `estudiante` (`matricula`),
  CONSTRAINT `fk_ReporteParcial_ProfesorEE1` FOREIGN KEY (`ProfesorEE_numeroDeTrabajador`) REFERENCES `profesoree` (`numeroDeTrabajador`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reporteparcial`
--

LOCK TABLES `reporteparcial` WRITE;
/*!40000 ALTER TABLE `reporteparcial` DISABLE KEYS */;
/*!40000 ALTER TABLE `reporteparcial` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `responsableorganizacionvinculada`
--

DROP TABLE IF EXISTS `responsableorganizacionvinculada`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `responsableorganizacionvinculada` (
  `rfc` varchar(13) NOT NULL,
  `nombreRepresentante` varchar(100) DEFAULT NULL,
  `cargo` varchar(45) NOT NULL,
  `correoRepresentante` varchar(100) NOT NULL,
  `rfcMoral_OV` varchar(12) NOT NULL,
  PRIMARY KEY (`rfc`),
  UNIQUE KEY `rfc_UNIQUE` (`rfc`),
  KEY `fk_representanteov_organizacionvinculada` (`rfcMoral_OV`),
  CONSTRAINT `fk_representanteov_organizacionvinculada` FOREIGN KEY (`rfcMoral_OV`) REFERENCES `organizacionvinculada` (`rfcMoral`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `responsableorganizacionvinculada`
--

LOCK TABLES `responsableorganizacionvinculada` WRITE;
/*!40000 ALTER TABLE `responsableorganizacionvinculada` DISABLE KEYS */;
/*!40000 ALTER TABLE `responsableorganizacionvinculada` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipousuario`
--

DROP TABLE IF EXISTS `tipousuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipousuario` (
  `idtipo` int NOT NULL,
  `tipo` varchar(45) NOT NULL,
  PRIMARY KEY (`idtipo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipousuario`
--

LOCK TABLES `tipousuario` WRITE;
/*!40000 ALTER TABLE `tipousuario` DISABLE KEYS */;
INSERT INTO `tipousuario` VALUES (1,'Coordinador'),(2,'Académico evaluador'),(3,'Profesor de experiencia educativa'),(4,'Estudiante');
/*!40000 ALTER TABLE `tipousuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `usuario` varchar(15) NOT NULL,
  `contrasena` varchar(64) NOT NULL,
  `tipousuario` int NOT NULL,
  `salt` varchar(24) NOT NULL,
  PRIMARY KEY (`usuario`),
  KEY `tipo_usuario_fk_idx` (`tipousuario`),
  CONSTRAINT `tipo_usuario_fk` FOREIGN KEY (`tipousuario`) REFERENCES `tipousuario` (`idtipo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES ('COR123456','69fa4d17ff66295f11b24a4d7bc8b2a9d7c9ac8317ab81e516c6e045bbf0e08f',1,'dHbZy4XvGCUjvP6R3bFdfg=='),('GUI202020','9a0ec7270ee7c828fddbea70e3c4bd905da8edc15d67c28fba889ef3032c8a90',3,'LlKp2UX8saTbB/VkVhTo2A=='),('VIC202020','ef54dd3da9570809d14015c8d43f02739a092db6ea7ccfad56eb977aa2c481ae',3,'XRRzwxNfJRRcKUh3+/lsMg==');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-22 19:34:18
