-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: localhost    Database: martial_arts_dev
-- ------------------------------------------------------
-- Server version	8.0.31

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

--
-- Table structure for table `character_quest_detail`
--

DROP TABLE IF EXISTS `character_quest_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `character_quest_detail` (
  `ID` bigint NOT NULL AUTO_INCREMENT,
  `IS_ACCEPT` bit(1) NOT NULL,
  `IS_COMPLETED` bit(1) NOT NULL,
  `IS_FINISHED` bit(1) NOT NULL,
  `MAX_PERFORMED` int NOT NULL,
  `PERFORMED_COUNT` int NOT NULL,
  `CHARACTER_ID` bigint NOT NULL,
  `QUEST_ID` bigint NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKeb4qt3f09sh7mo1lowv2piyx8` (`CHARACTER_ID`),
  KEY `FK226asj1nbd620lhb6dodtwm5l` (`QUEST_ID`),
  CONSTRAINT `FK226asj1nbd620lhb6dodtwm5l` FOREIGN KEY (`QUEST_ID`) REFERENCES `quest_info` (`QUEST_ID`),
  CONSTRAINT `FKeb4qt3f09sh7mo1lowv2piyx8` FOREIGN KEY (`CHARACTER_ID`) REFERENCES `user_character` (`CHARACTER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `character_quest_detail`
--

LOCK TABLES `character_quest_detail` WRITE;
/*!40000 ALTER TABLE `character_quest_detail` DISABLE KEYS */;
INSERT INTO `character_quest_detail` VALUES (2,_binary '\0',_binary '\0',_binary '\0',1,0,2,1),(3,_binary '\0',_binary '\0',_binary '\0',1,0,11,1),(4,_binary '\0',_binary '\0',_binary '\0',1,0,12,1),(5,_binary '\0',_binary '\0',_binary '\0',1,0,13,1),(6,_binary '\0',_binary '\0',_binary '\0',1,0,14,1),(7,_binary '\0',_binary '\0',_binary '\0',1,0,15,1),(8,_binary '\0',_binary '\0',_binary '\0',1,0,16,1),(9,_binary '\0',_binary '\0',_binary '\0',1,0,17,1),(10,_binary '\0',_binary '\0',_binary '\0',1,0,2,2),(11,_binary '\0',_binary '\0',_binary '\0',1,0,11,2),(12,_binary '\0',_binary '\0',_binary '\0',1,0,12,2),(13,_binary '\0',_binary '\0',_binary '\0',1,0,13,2),(14,_binary '\0',_binary '\0',_binary '\0',1,0,14,2),(15,_binary '\0',_binary '\0',_binary '\0',1,0,15,2),(16,_binary '\0',_binary '\0',_binary '\0',1,0,16,2),(17,_binary '\0',_binary '\0',_binary '\0',1,0,17,2);
/*!40000 ALTER TABLE `character_quest_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `costume`
--

DROP TABLE IF EXISTS `costume`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `costume` (
  `COSTUME_ID` bigint NOT NULL AUTO_INCREMENT,
  `COSTUME_NAME` varchar(255) NOT NULL,
  `COSTUME_PRICE` bigint NOT NULL,
  `COSTUME_TYPE` smallint DEFAULT NULL,
  PRIMARY KEY (`COSTUME_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `costume`
--

LOCK TABLES `costume` WRITE;
/*!40000 ALTER TABLE `costume` DISABLE KEYS */;
/*!40000 ALTER TABLE `costume` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `finisher_drawner`
--

DROP TABLE IF EXISTS `finisher_drawner`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `finisher_drawner` (
  `ITEM_ID` bigint NOT NULL,
  `DRAWER_ID` bigint NOT NULL,
  KEY `FK6oqf47fs9lmnsrpvi4u4w4lnf` (`DRAWER_ID`),
  KEY `FKprep9e1fx2b1054rhmgctkdxl` (`ITEM_ID`),
  CONSTRAINT `FK6oqf47fs9lmnsrpvi4u4w4lnf` FOREIGN KEY (`DRAWER_ID`) REFERENCES `room_drawer` (`DRAWER_ID`),
  CONSTRAINT `FKprep9e1fx2b1054rhmgctkdxl` FOREIGN KEY (`ITEM_ID`) REFERENCES `finisher_item` (`ITEM_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `finisher_drawner`
--

LOCK TABLES `finisher_drawner` WRITE;
/*!40000 ALTER TABLE `finisher_drawner` DISABLE KEYS */;
/*!40000 ALTER TABLE `finisher_drawner` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `finisher_item`
--

DROP TABLE IF EXISTS `finisher_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `finisher_item` (
  `ITEM_ID` bigint NOT NULL AUTO_INCREMENT,
  `HEIGHT` int DEFAULT NULL,
  `ITEM_NAME` varchar(255) NOT NULL,
  `ITEM_PRICE` varchar(255) NOT NULL,
  `LENGTH` int DEFAULT NULL,
  `WIDTH` int DEFAULT NULL,
  `ITEM_TYPE` smallint DEFAULT NULL,
  PRIMARY KEY (`ITEM_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `finisher_item`
--

LOCK TABLES `finisher_item` WRITE;
/*!40000 ALTER TABLE `finisher_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `finisher_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `furniture_drawner`
--

DROP TABLE IF EXISTS `furniture_drawner`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `furniture_drawner` (
  `ITEM_ID` bigint NOT NULL,
  `DRAWER_ID` bigint NOT NULL,
  KEY `FKr1ckaneqn2menkraxdd488pgd` (`DRAWER_ID`),
  KEY `FKj3ot6l91i673a9yb4eijdrkfi` (`ITEM_ID`),
  CONSTRAINT `FKj3ot6l91i673a9yb4eijdrkfi` FOREIGN KEY (`ITEM_ID`) REFERENCES `furniture_item` (`ITEM_ID`),
  CONSTRAINT `FKr1ckaneqn2menkraxdd488pgd` FOREIGN KEY (`DRAWER_ID`) REFERENCES `room_drawer` (`DRAWER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `furniture_drawner`
--

LOCK TABLES `furniture_drawner` WRITE;
/*!40000 ALTER TABLE `furniture_drawner` DISABLE KEYS */;
/*!40000 ALTER TABLE `furniture_drawner` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `furniture_item`
--

DROP TABLE IF EXISTS `furniture_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `furniture_item` (
  `ITEM_ID` bigint NOT NULL AUTO_INCREMENT,
  `HEIGHT` int DEFAULT NULL,
  `ITEM_NAME` varchar(255) NOT NULL,
  `ITEM_PRICE` varchar(255) NOT NULL,
  `LENGTH` int DEFAULT NULL,
  `WIDTH` int DEFAULT NULL,
  PRIMARY KEY (`ITEM_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `furniture_item`
--

LOCK TABLES `furniture_item` WRITE;
/*!40000 ALTER TABLE `furniture_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `furniture_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `my_room_item_place`
--

DROP TABLE IF EXISTS `my_room_item_place`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `my_room_item_place` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `HEIGHT_END` int DEFAULT NULL,
  `HEIGHT_START` int DEFAULT NULL,
  `isFloorItem` bit(1) DEFAULT NULL,
  `itemId` bigint DEFAULT NULL,
  `LENGTH_END` int DEFAULT NULL,
  `LENGTH_START` int DEFAULT NULL,
  `rotateNumber` int DEFAULT NULL,
  `ITEM_TYPE` smallint DEFAULT NULL,
  `WIDTH_END` int DEFAULT NULL,
  `WIDTH_START` int DEFAULT NULL,
  `ROOM_ID` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKem8p5pidk8i2hcmbtiott2vu6` (`ROOM_ID`),
  CONSTRAINT `FKem8p5pidk8i2hcmbtiott2vu6` FOREIGN KEY (`ROOM_ID`) REFERENCES `myroom` (`ROOM_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `my_room_item_place`
--

LOCK TABLES `my_room_item_place` WRITE;
/*!40000 ALTER TABLE `my_room_item_place` DISABLE KEYS */;
/*!40000 ALTER TABLE `my_room_item_place` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `myroom`
--

DROP TABLE IF EXISTS `myroom`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `myroom` (
  `ROOM_ID` bigint NOT NULL AUTO_INCREMENT,
  `LEVEL` int DEFAULT NULL,
  `HEIGHT` int DEFAULT '0',
  `LENGTH` int DEFAULT '0',
  `WIDTH` int DEFAULT '0',
  `FLOORING_ID` bigint DEFAULT NULL,
  `WALLPAPER_ID` bigint DEFAULT NULL,
  PRIMARY KEY (`ROOM_ID`),
  KEY `FKhgq0c62wstx2gw9ok33qjcwq3` (`FLOORING_ID`),
  KEY `FK588bu51no7aybpamrdrhwvqe6` (`WALLPAPER_ID`),
  CONSTRAINT `FK588bu51no7aybpamrdrhwvqe6` FOREIGN KEY (`WALLPAPER_ID`) REFERENCES `finisher_item` (`ITEM_ID`),
  CONSTRAINT `FKhgq0c62wstx2gw9ok33qjcwq3` FOREIGN KEY (`FLOORING_ID`) REFERENCES `finisher_item` (`ITEM_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `myroom`
--

LOCK TABLES `myroom` WRITE;
/*!40000 ALTER TABLE `myroom` DISABLE KEYS */;
INSERT INTO `myroom` VALUES (1,0,0,0,0,NULL,NULL),(2,0,0,0,0,NULL,NULL),(3,0,0,0,0,NULL,NULL),(4,4,0,0,0,NULL,NULL),(5,0,0,0,0,NULL,NULL),(6,0,0,0,0,NULL,NULL),(7,0,0,0,0,NULL,NULL),(8,0,0,0,0,NULL,NULL),(9,0,0,0,0,NULL,NULL);
/*!40000 ALTER TABLE `myroom` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prop_drawner`
--

DROP TABLE IF EXISTS `prop_drawner`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prop_drawner` (
  `ITEM_ID` bigint NOT NULL,
  `DRAWER_ID` bigint NOT NULL,
  KEY `FK56gshgggos7sd5xrdtm2ycrc5` (`DRAWER_ID`),
  KEY `FK912i8islqlmrtk008pott2aty` (`ITEM_ID`),
  CONSTRAINT `FK56gshgggos7sd5xrdtm2ycrc5` FOREIGN KEY (`DRAWER_ID`) REFERENCES `room_drawer` (`DRAWER_ID`),
  CONSTRAINT `FK912i8islqlmrtk008pott2aty` FOREIGN KEY (`ITEM_ID`) REFERENCES `prop_item` (`ITEM_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prop_drawner`
--

LOCK TABLES `prop_drawner` WRITE;
/*!40000 ALTER TABLE `prop_drawner` DISABLE KEYS */;
/*!40000 ALTER TABLE `prop_drawner` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prop_item`
--

DROP TABLE IF EXISTS `prop_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prop_item` (
  `ITEM_ID` bigint NOT NULL AUTO_INCREMENT,
  `HEIGHT` int DEFAULT NULL,
  `ITEM_NAME` varchar(255) NOT NULL,
  `ITEM_PRICE` varchar(255) NOT NULL,
  `LENGTH` int DEFAULT NULL,
  `WIDTH` int DEFAULT NULL,
  `ITEM_TYPE` smallint DEFAULT NULL,
  PRIMARY KEY (`ITEM_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prop_item`
--

LOCK TABLES `prop_item` WRITE;
/*!40000 ALTER TABLE `prop_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `prop_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quest_info`
--

DROP TABLE IF EXISTS `quest_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quest_info` (
  `QUEST_ID` bigint NOT NULL AUTO_INCREMENT,
  `QUEST_DESCRIPTION` varchar(255) NOT NULL,
  `QUEST_NAME` varchar(255) NOT NULL,
  `REWARD` int NOT NULL,
  PRIMARY KEY (`QUEST_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quest_info`
--

LOCK TABLES `quest_info` WRITE;
/*!40000 ALTER TABLE `quest_info` DISABLE KEYS */;
INSERT INTO `quest_info` VALUES (1,'QUEST TEST 1 DES','QUEST TEST 1',100),(2,'QUEST TEST 2 DES','QUEST TEST 2',200);
/*!40000 ALTER TABLE `quest_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `ID` int NOT NULL,
  `CREATED_DATE` datetime(6) DEFAULT NULL,
  `CREATED_USER` varchar(20) DEFAULT NULL,
  `ROLE_DESCRIPTION` varchar(2000) DEFAULT NULL,
  `ROLE_NAME` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'2023-04-14 03:08:10.000000','ADMIN','SYSTEM ADMIN','ADMIN');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room_drawer`
--

DROP TABLE IF EXISTS `room_drawer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room_drawer` (
  `DRAWER_ID` bigint NOT NULL AUTO_INCREMENT,
  `ROOM_ID` bigint DEFAULT NULL,
  PRIMARY KEY (`DRAWER_ID`),
  KEY `FK5jabb21cgm9nmdp6s8bpcwtbu` (`ROOM_ID`),
  CONSTRAINT `FK5jabb21cgm9nmdp6s8bpcwtbu` FOREIGN KEY (`ROOM_ID`) REFERENCES `myroom` (`ROOM_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_drawer`
--

LOCK TABLES `room_drawer` WRITE;
/*!40000 ALTER TABLE `room_drawer` DISABLE KEYS */;
INSERT INTO `room_drawer` VALUES (1,1);
/*!40000 ALTER TABLE `room_drawer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room_scrap_book`
--

DROP TABLE IF EXISTS `room_scrap_book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room_scrap_book` (
  `SCRAP_BOOK_ID` bigint NOT NULL AUTO_INCREMENT,
  `ROOM_ID` bigint DEFAULT NULL,
  PRIMARY KEY (`SCRAP_BOOK_ID`),
  KEY `FKfx6n5t9nem8os2wh3ri87g0ay` (`ROOM_ID`),
  CONSTRAINT `FKfx6n5t9nem8os2wh3ri87g0ay` FOREIGN KEY (`ROOM_ID`) REFERENCES `myroom` (`ROOM_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_scrap_book`
--

LOCK TABLES `room_scrap_book` WRITE;
/*!40000 ALTER TABLE `room_scrap_book` DISABLE KEYS */;
/*!40000 ALTER TABLE `room_scrap_book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room_wardrobe`
--

DROP TABLE IF EXISTS `room_wardrobe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room_wardrobe` (
  `WARDROBE_ID` bigint NOT NULL AUTO_INCREMENT,
  `ROOM_ID` bigint DEFAULT NULL,
  PRIMARY KEY (`WARDROBE_ID`),
  KEY `FKiit9u0pyftv0qhcyi0759bl54` (`ROOM_ID`),
  CONSTRAINT `FKiit9u0pyftv0qhcyi0759bl54` FOREIGN KEY (`ROOM_ID`) REFERENCES `myroom` (`ROOM_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_wardrobe`
--

LOCK TABLES `room_wardrobe` WRITE;
/*!40000 ALTER TABLE `room_wardrobe` DISABLE KEYS */;
INSERT INTO `room_wardrobe` VALUES (1,1);
/*!40000 ALTER TABLE `room_wardrobe` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shop`
--

DROP TABLE IF EXISTS `shop`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shop` (
  `ID` bigint NOT NULL AUTO_INCREMENT,
  `SHOP_TYPE` smallint DEFAULT NULL,
  `CHARACTER_ID` bigint DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK97yxw3b6eog68ofe7co7yuxk` (`CHARACTER_ID`),
  CONSTRAINT `FK97yxw3b6eog68ofe7co7yuxk` FOREIGN KEY (`CHARACTER_ID`) REFERENCES `user_character` (`CHARACTER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shop`
--

LOCK TABLES `shop` WRITE;
/*!40000 ALTER TABLE `shop` DISABLE KEYS */;
/*!40000 ALTER TABLE `shop` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_character`
--

DROP TABLE IF EXISTS `user_character`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_character` (
  `CHARACTER_ID` bigint NOT NULL AUTO_INCREMENT,
  `GOLD` decimal(38,0) DEFAULT NULL,
  `ROOM_ID` bigint DEFAULT NULL,
  `USER_ID` bigint DEFAULT NULL,
  `COSTUME_ACCESSORY` bigint DEFAULT NULL,
  `COSTUME_BOTTOM` bigint DEFAULT NULL,
  `COSTUME_HAIR` bigint DEFAULT NULL,
  `COSTUME_SHOE` bigint DEFAULT NULL,
  `COSTUME_TOP` bigint DEFAULT NULL,
  PRIMARY KEY (`CHARACTER_ID`),
  KEY `FKs8ihjsdbvvjturb426rhegngq` (`ROOM_ID`),
  KEY `FKlhf8vsk0eoykpakj0jd9fd5d4` (`USER_ID`),
  KEY `FK4mswli1g2ptaasfogg5oenal0` (`COSTUME_ACCESSORY`),
  KEY `FKt0xodg2djwf501frhcbt3du32` (`COSTUME_BOTTOM`),
  KEY `FK2rvuveem4j2o37j4kloquthus` (`COSTUME_HAIR`),
  KEY `FK73stdmtpup0i2txy31pyto4wx` (`COSTUME_SHOE`),
  KEY `FK8l2xpt47tflq11up753wg77kx` (`COSTUME_TOP`),
  CONSTRAINT `FK2rvuveem4j2o37j4kloquthus` FOREIGN KEY (`COSTUME_HAIR`) REFERENCES `costume` (`COSTUME_ID`),
  CONSTRAINT `FK4mswli1g2ptaasfogg5oenal0` FOREIGN KEY (`COSTUME_ACCESSORY`) REFERENCES `costume` (`COSTUME_ID`),
  CONSTRAINT `FK73stdmtpup0i2txy31pyto4wx` FOREIGN KEY (`COSTUME_SHOE`) REFERENCES `costume` (`COSTUME_ID`),
  CONSTRAINT `FK8l2xpt47tflq11up753wg77kx` FOREIGN KEY (`COSTUME_TOP`) REFERENCES `costume` (`COSTUME_ID`),
  CONSTRAINT `FKlhf8vsk0eoykpakj0jd9fd5d4` FOREIGN KEY (`USER_ID`) REFERENCES `users` (`ID`),
  CONSTRAINT `FKs8ihjsdbvvjturb426rhegngq` FOREIGN KEY (`ROOM_ID`) REFERENCES `myroom` (`ROOM_ID`),
  CONSTRAINT `FKt0xodg2djwf501frhcbt3du32` FOREIGN KEY (`COSTUME_BOTTOM`) REFERENCES `costume` (`COSTUME_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_character`
--

LOCK TABLES `user_character` WRITE;
/*!40000 ALTER TABLE `user_character` DISABLE KEYS */;
INSERT INTO `user_character` VALUES (2,100,1,127,NULL,NULL,NULL,NULL,NULL),(11,100,3,129,NULL,NULL,NULL,NULL,NULL),(12,100,4,130,NULL,NULL,NULL,NULL,NULL),(13,100,5,131,NULL,NULL,NULL,NULL,NULL),(14,100,6,132,NULL,NULL,NULL,NULL,NULL),(15,100,7,133,NULL,NULL,NULL,NULL,NULL),(16,100,8,134,NULL,NULL,NULL,NULL,NULL),(17,100,9,135,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `user_character` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_role` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `ROLE_ID` int DEFAULT NULL,
  `USER_ID` bigint DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (1,1,127),(2,1,128),(3,1,129),(4,1,130),(5,1,131),(6,1,132),(7,1,133),(8,1,134),(9,1,135);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `ID` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `EMAIL` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'User email',
  `NAME` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'User name',
  `NICKNAME` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'User nickname',
  `PASSWORD` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'Password',
  `PHONE` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'Phone number',
  `FD_ACTVTY_CODE` varchar(6) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'Field of activity(code)',
  `SPC_AREA_CODE` varchar(6) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'Mentor''s special area',
  `ONE_LINE_INTRO` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'One line introduction',
  `LONG_INTRO` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin COMMENT 'Long Introduction',
  `LC_ACTVTY_CODE` varchar(4) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'Location of activity(code)',
  `RECORD` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin COMMENT 'Personal history',
  `PROFILE_IMAGE` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'Profile photo image',
  `USER_TAGS` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin COMMENT 'Tags',
  `LC_RESIDENCE` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'Location of residence',
  `GENDER` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT 'M' COMMENT 'Gender(Men & Women)',
  `AGE_RANGE` varchar(5) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'Age range',
  `BANK_NAME` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'Bank account(bank name)',
  `BANK_OWNER` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'Bank account(owner name)',
  `BANK_ACC_NUM` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'Bank account number',
  `MEMBER_TYPE` varchar(6) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'Member type(code)',
  `IS_UPGRADING` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT '0' COMMENT 'Applying for upgrade',
  `FACEBOOK_ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'Facebook account ID',
  `GOOGLE_ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'Google account ID',
  `KAKAO_ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'Kakao account ID',
  `NAVER_ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'Naver account ID',
  `ANS_ADOPT_CNT` int DEFAULT '0' COMMENT 'Answer adopted count',
  `ANSWERED_CNT` int DEFAULT '0' COMMENT 'Answered count',
  `QUESTION_CNT` int DEFAULT '0' COMMENT 'Question count',
  `PSNLQ_CNT` int DEFAULT '0' COMMENT '1:1 Question count',
  `GET_PSNLQ_CNT` int DEFAULT '0' COMMENT 'Get 1:1 Question count',
  `TRIPNOTE_CNT` int DEFAULT '0' COMMENT 'Travel notebooks count',
  `ARTICLE_CNT` int DEFAULT '0' COMMENT 'Travel articles count',
  `MY_UPVOTE_CNT` int DEFAULT '0' COMMENT 'My total upvote count',
  `SCRAPED_CNT` int DEFAULT '0' COMMENT 'Scraped count(Mentor)',
  `CUMUL_POINT` int DEFAULT '0' COMMENT 'Total cumulative points',
  `CURRENT_POINT` int DEFAULT '0' COMMENT 'Total current points',
  `DELETED_AT` datetime DEFAULT NULL COMMENT 'When is this deleted?',
  `CREATED_AT` datetime NOT NULL COMMENT 'Created at Datetime',
  `UPDATED_AT` datetime DEFAULT NULL COMMENT 'Last modified time',
  `DELETE_REASON` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin COMMENT 'Delete Reason',
  `BAN_REASON` varchar(255) COLLATE utf8mb3_bin DEFAULT NULL,
  `IS_BANNED` bit(1) DEFAULT NULL,
  `USERNAME` varchar(255) COLLATE utf8mb3_bin DEFAULT NULL,
  `CONNECTION_STATUS` bit(1) DEFAULT NULL,
  `LAST_ACCESS` datetime(6) DEFAULT NULL,
  `REASON_SUSPENSION` varchar(255) COLLATE utf8mb3_bin DEFAULT NULL,
  `ROLE` varchar(20) COLLATE utf8mb3_bin DEFAULT NULL,
  `STATUS` smallint DEFAULT NULL,
  `SUSPENSION_END` datetime(6) DEFAULT NULL,
  `SUSPENSION_START` datetime(6) DEFAULT NULL,
  `SYS_CREATED_CHARACTER` datetime(6) DEFAULT NULL,
  `UUID` varchar(32) COLLATE utf8mb3_bin DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=137 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (127,'admin123@gmail.com','system admin','admin','$2a$10$1tBN/hTfY2e83idnhwIMVuiC5IzyNsHcZ.Ki9cI6Pa8UrhP9DeaKq','11111111111',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'M',NULL,NULL,NULL,NULL,NULL,'0',NULL,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,NULL,'2023-04-14 03:08:10',NULL,NULL,NULL,0,_binary '\0','admin',_binary '\0',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(129,'baolong123@gmail.com','baolong123',NULL,'$2a$10$1tBN/hTfY2e83idnhwIMVuiC5IzyNsHcZ.Ki9cI6Pa8UrhP9DeaKq','12345678912',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'M','30+',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2023-06-26 03:21:27',NULL,NULL,NULL,0,_binary '\0','baolong123',_binary '\0',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(130,'admin1234@gmail.com','admin1234',NULL,'$2a$10$sBweFqkh4Rrewipo3qJ2NuQXtAT7x8roIjPkKV7/NeXgRH70Iv/oi','1234567891',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'M','30+',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2023-06-27 08:45:12',NULL,NULL,NULL,0,_binary '\0','admin1234',_binary '\0',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(131,'devaccount1@gmail.com','devaccount1',NULL,'$2a$10$sBweFqkh4Rrewipo3qJ2NuQXtAT7x8roIjPkKV7/NeXgRH70Iv/oi','1234567891',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'M','30+',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2023-06-27 08:45:12',NULL,NULL,NULL,0,_binary '\0','devaccount1',_binary '\0',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(132,'devaccount2@gmail.com','devaccount2',NULL,'$2a$10$sBweFqkh4Rrewipo3qJ2NuQXtAT7x8roIjPkKV7/NeXgRH70Iv/oi','1234567891',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'M','30+',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2023-06-27 08:45:12',NULL,NULL,NULL,0,_binary '\0','devaccount2',_binary '\0',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(133,'devaccount3@gmail.com','devaccount3',NULL,'$2a$10$sBweFqkh4Rrewipo3qJ2NuQXtAT7x8roIjPkKV7/NeXgRH70Iv/oi','1234567891',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'M','30+',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2023-06-27 08:45:12',NULL,NULL,NULL,0,_binary '\0','devaccount3',_binary '\0',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(134,'devaccount4@gmail.com','devaccount4',NULL,'$2a$10$sBweFqkh4Rrewipo3qJ2NuQXtAT7x8roIjPkKV7/NeXgRH70Iv/oi','1234567891',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'M','30+',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2023-06-27 08:45:12',NULL,NULL,NULL,0,_binary '\0','devaccount4',_binary '\0',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(135,'devaccount5@gmail.com','devaccount5',NULL,'$2a$10$sBweFqkh4Rrewipo3qJ2NuQXtAT7x8roIjPkKV7/NeXgRH70Iv/oi','1234567891',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'M','30+',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2023-06-27 08:45:12',NULL,NULL,NULL,0,_binary '\0','devaccount5',_binary '\0',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(136,'123@gmail.com','123',NULL,'$2a$10$3RQnkOThRaVtA/0mX9x3cuXpx.U0gaXXfva8PhE.IVVks4EGbypfy','556677444',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'M',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2023-07-04 09:59:02',NULL,NULL,NULL,0,NULL,'admin321',_binary '\0',NULL,NULL,NULL,1,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `video_category`
--

DROP TABLE IF EXISTS `video_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `video_category` (
  `VIDEO_CATEGORY_ID` bigint NOT NULL,
  `CATEGORY_NAME` varchar(255) NOT NULL,
  PRIMARY KEY (`VIDEO_CATEGORY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `video_category`
--

LOCK TABLES `video_category` WRITE;
/*!40000 ALTER TABLE `video_category` DISABLE KEYS */;
/*!40000 ALTER TABLE `video_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `video_category_seq`
--

DROP TABLE IF EXISTS `video_category_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `video_category_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `video_category_seq`
--

LOCK TABLES `video_category_seq` WRITE;
/*!40000 ALTER TABLE `video_category_seq` DISABLE KEYS */;
INSERT INTO `video_category_seq` VALUES (1);
/*!40000 ALTER TABLE `video_category_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `videos`
--

DROP TABLE IF EXISTS `videos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `videos` (
  `VIDEO_ID` bigint NOT NULL AUTO_INCREMENT,
  `CREATED_DATE` datetime(6) DEFAULT NULL,
  `VIDEO_LINK` varchar(1000) DEFAULT NULL,
  `VIDEO_CATEGORY_ID` bigint NOT NULL,
  PRIMARY KEY (`VIDEO_ID`),
  KEY `FK276nwyfum54so81jh7lyoyqpa` (`VIDEO_CATEGORY_ID`),
  CONSTRAINT `FK276nwyfum54so81jh7lyoyqpa` FOREIGN KEY (`VIDEO_CATEGORY_ID`) REFERENCES `video_category` (`VIDEO_CATEGORY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `videos`
--

LOCK TABLES `videos` WRITE;
/*!40000 ALTER TABLE `videos` DISABLE KEYS */;
/*!40000 ALTER TABLE `videos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wardrobe_item`
--

DROP TABLE IF EXISTS `wardrobe_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wardrobe_item` (
  `ID` bigint NOT NULL AUTO_INCREMENT,
  `ITEM_ID` bigint NOT NULL,
  `WARDROBE_ID` bigint DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK8ooo6u8o2f5ig0t8x6b7gjhdu` (`WARDROBE_ID`),
  CONSTRAINT `FK8ooo6u8o2f5ig0t8x6b7gjhdu` FOREIGN KEY (`WARDROBE_ID`) REFERENCES `room_wardrobe` (`WARDROBE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wardrobe_item`
--

LOCK TABLES `wardrobe_item` WRITE;
/*!40000 ALTER TABLE `wardrobe_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `wardrobe_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'martial_arts_dev'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-07-04 17:25:30
