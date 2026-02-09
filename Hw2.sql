CREATE DATABASE  IF NOT EXISTS `course_booking` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `course_booking`;
-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: localhost    Database: course_booking
-- ------------------------------------------------------
-- Server version	8.0.44

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
-- Table structure for table `bookings`
--

DROP TABLE IF EXISTS `bookings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bookings` (
  `id` int NOT NULL AUTO_INCREMENT,
  `s_id` int NOT NULL,
  `t_id` int NOT NULL,
  `slot_id` int NOT NULL,
  `status` varchar(45) DEFAULT NULL,
  `zoom_link` varchar(255) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `slot_id_UNIQUE` (`slot_id`),
  KEY `fk_booking_student_idx` (`s_id`),
  KEY `fk_booking_teacher_idx` (`t_id`),
  CONSTRAINT `fk_booking_slot` FOREIGN KEY (`slot_id`) REFERENCES `teacher_slots` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_booking_student` FOREIGN KEY (`s_id`) REFERENCES `student` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_booking_teacher` FOREIGN KEY (`t_id`) REFERENCES `teacher` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bookings`
--

LOCK TABLES `bookings` WRITE;
/*!40000 ALTER TABLE `bookings` DISABLE KEYS */;
INSERT INTO `bookings` VALUES (1,1,1,1,'已完成','https://zoom.us/testlink','2026-02-05 02:56:19'),(3,1,1,2,'已預約','https://zoom.us/test','2026-02-07 18:18:59'),(4,1,1,12,'已預約','https://zoom.us/test','2026-02-08 06:22:27'),(5,1,4,25,'已預約','https://zoom.us/test','2026-02-08 06:22:55'),(6,1,4,26,'已預約','https://zoom.us/test','2026-02-08 06:22:55'),(7,3,4,33,'已預約','https://zoom.us/test','2026-02-08 14:07:42'),(8,3,1,13,'已預約','','2026-02-08 14:34:23'),(9,1,1,9,'已預約','','2026-02-08 15:43:10'),(10,3,3,19,'已預約','','2026-02-08 15:48:19'),(11,3,3,21,'已預約','','2026-02-08 15:48:19'),(12,4,3,17,'已預約','','2026-02-08 16:16:15'),(13,4,3,20,'已預約','','2026-02-08 16:41:59'),(14,4,3,24,'已預約','','2026-02-08 16:41:59');
/*!40000 ALTER TABLE `bookings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feedback`
--

DROP TABLE IF EXISTS `feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feedback` (
  `id` int NOT NULL AUTO_INCREMENT,
  `b_id` int NOT NULL,
  `rating` int DEFAULT NULL,
  `comment` text,
  PRIMARY KEY (`id`),
  UNIQUE KEY `b_id_UNIQUE` (`b_id`),
  CONSTRAINT `fk_feedback_booking` FOREIGN KEY (`b_id`) REFERENCES `bookings` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feedback`
--

LOCK TABLES `feedback` WRITE;
/*!40000 ALTER TABLE `feedback` DISABLE KEYS */;
/*!40000 ALTER TABLE `feedback` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `point_transactions`
--

DROP TABLE IF EXISTS `point_transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `point_transactions` (
  `id` int NOT NULL AUTO_INCREMENT,
  `s_id` int NOT NULL,
  `amount_paid` int NOT NULL,
  `points_added` int NOT NULL,
  `tx_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_tx_student_idx` (`s_id`),
  CONSTRAINT `fk_tx_student` FOREIGN KEY (`s_id`) REFERENCES `student` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `point_transactions`
--

LOCK TABLES `point_transactions` WRITE;
/*!40000 ALTER TABLE `point_transactions` DISABLE KEYS */;
INSERT INTO `point_transactions` VALUES (1,1,500,50,'2026-02-05 02:55:40'),(2,1,500,50,'2026-02-05 02:55:43'),(3,1,500,50,'2026-02-07 15:49:55'),(4,1,500,50,'2026-02-07 15:56:06'),(5,1,500,50,'2026-02-07 18:09:09'),(6,1,500,50,'2026-02-07 18:30:09'),(7,1,500,50,'2026-02-07 18:37:27'),(8,3,900,90,'2026-02-08 11:55:56'),(9,3,1000,100,'2026-02-08 14:11:23'),(10,1,500,50,'2026-02-08 14:55:50'),(11,1,0,-100,'2026-02-08 15:43:10'),(12,3,10000,1000,'2026-02-08 15:47:21'),(13,3,0,-90,'2026-02-08 15:48:19'),(14,3,0,-90,'2026-02-08 15:48:19'),(15,4,100000,10000,'2026-02-08 16:15:53'),(16,4,0,-90,'2026-02-08 16:16:15'),(17,4,0,-90,'2026-02-08 16:41:59'),(18,4,0,-90,'2026-02-08 16:41:59'),(19,4,5000,500,'2026-02-08 16:42:38');
/*!40000 ALTER TABLE `point_transactions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student` (
  `id` int NOT NULL AUTO_INCREMENT,
  `account` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `student_name` varchar(45) NOT NULL,
  `student_points` int DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `student_account_UNIQUE` (`account`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES (1,'stu01','123456','Amy',940),(2,'stu02','1234','Ben',1000),(3,'qoo','123','qoo',830),(4,'QQQ','123','QQQ',10230);
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teacher`
--

DROP TABLE IF EXISTS `teacher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `teacher` (
  `id` int NOT NULL AUTO_INCREMENT,
  `account` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `teacher_name` varchar(45) NOT NULL,
  `point_per_lesson` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `teacher_account_UNIQUE` (`account`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teacher`
--

LOCK TABLES `teacher` WRITE;
/*!40000 ALTER TABLE `teacher` DISABLE KEYS */;
INSERT INTO `teacher` VALUES (1,'tea01','123456','T_Katja',100),(3,'tea02','1234','T_Jurin',90),(4,'tea03','123','T_Peter',80);
/*!40000 ALTER TABLE `teacher` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teacher_slots`
--

DROP TABLE IF EXISTS `teacher_slots`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `teacher_slots` (
  `id` int NOT NULL AUTO_INCREMENT,
  `t_id` int NOT NULL,
  `slot_datetime` datetime NOT NULL,
  `is_available` tinyint DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `fk_slots_teacher_idx` (`t_id`),
  CONSTRAINT `fk_slots_teacher` FOREIGN KEY (`t_id`) REFERENCES `teacher` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teacher_slots`
--

LOCK TABLES `teacher_slots` WRITE;
/*!40000 ALTER TABLE `teacher_slots` DISABLE KEYS */;
INSERT INTO `teacher_slots` VALUES (1,1,'2026-02-01 10:00:00',0),(2,1,'2026-02-10 15:00:00',0),(7,1,'2026-02-10 09:00:00',1),(8,1,'2026-02-08 10:00:00',1),(9,1,'2026-02-11 09:00:00',0),(10,1,'2026-02-11 10:00:00',1),(11,1,'2026-02-12 09:00:00',1),(12,1,'2026-02-12 10:00:00',0),(13,1,'2026-02-13 09:00:00',0),(14,1,'2026-02-13 10:00:00',1),(15,3,'2026-02-09 13:00:00',1),(16,3,'2026-02-09 14:00:00',1),(17,3,'2026-02-10 13:00:00',0),(18,3,'2026-02-10 14:00:00',1),(19,3,'2026-02-11 13:00:00',0),(20,3,'2026-02-11 14:00:00',0),(21,3,'2026-02-12 13:00:00',0),(22,3,'2026-02-12 14:00:00',1),(23,3,'2026-02-13 13:00:00',1),(24,3,'2026-02-13 14:00:00',0),(25,4,'2026-02-09 19:00:00',0),(26,4,'2026-02-09 20:00:00',0),(27,4,'2026-02-10 19:00:00',1),(28,4,'2026-02-10 20:00:00',1),(29,4,'2026-02-11 19:00:00',1),(30,4,'2026-02-11 20:00:00',1),(31,4,'2026-02-12 19:00:00',1),(32,4,'2026-02-12 20:00:00',1),(33,4,'2026-02-13 19:00:00',0),(34,4,'2026-02-13 20:00:00',1);
/*!40000 ALTER TABLE `teacher_slots` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `view_available_slots`
--

DROP TABLE IF EXISTS `view_available_slots`;
/*!50001 DROP VIEW IF EXISTS `view_available_slots`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `view_available_slots` AS SELECT 
 1 AS `slot_id`,
 1 AS `teacher_name`,
 1 AS `cost`,
 1 AS `start_time`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_student_feedback`
--

DROP TABLE IF EXISTS `view_student_feedback`;
/*!50001 DROP VIEW IF EXISTS `view_student_feedback`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `view_student_feedback` AS SELECT 
 1 AS `booking_id`,
 1 AS `student_id`,
 1 AS `teacher_id`,
 1 AS `teacher_name`,
 1 AS `lesson_time`,
 1 AS `booking_status`,
 1 AS `feedback_id`,
 1 AS `rating`,
 1 AS `comment`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_student_schedule`
--

DROP TABLE IF EXISTS `view_student_schedule`;
/*!50001 DROP VIEW IF EXISTS `view_student_schedule`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `view_student_schedule` AS SELECT 
 1 AS `booking_id`,
 1 AS `student_id`,
 1 AS `student_name`,
 1 AS `teacher_id`,
 1 AS `teacher_name`,
 1 AS `lesson_time`,
 1 AS `booking_status`,
 1 AS `zoom_link`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_teacher_revenue`
--

DROP TABLE IF EXISTS `view_teacher_revenue`;
/*!50001 DROP VIEW IF EXISTS `view_teacher_revenue`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `view_teacher_revenue` AS SELECT 
 1 AS `teacher_id`,
 1 AS `teacher_name`,
 1 AS `report_month`,
 1 AS `total_lessons`,
 1 AS `total_points_earned`*/;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `view_available_slots`
--

/*!50001 DROP VIEW IF EXISTS `view_available_slots`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `view_available_slots` AS select `ts`.`id` AS `slot_id`,`t`.`teacher_name` AS `teacher_name`,`t`.`point_per_lesson` AS `cost`,`ts`.`slot_datetime` AS `start_time` from (`teacher_slots` `ts` join `teacher` `t` on((`ts`.`t_id` = `t`.`id`))) where ((`ts`.`is_available` = 1) and (`ts`.`slot_datetime` > now())) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_student_feedback`
--

/*!50001 DROP VIEW IF EXISTS `view_student_feedback`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `view_student_feedback` AS select `b`.`id` AS `booking_id`,`b`.`s_id` AS `student_id`,`b`.`t_id` AS `teacher_id`,`t`.`teacher_name` AS `teacher_name`,`ts`.`slot_datetime` AS `lesson_time`,`b`.`status` AS `booking_status`,`f`.`id` AS `feedback_id`,`f`.`rating` AS `rating`,`f`.`comment` AS `comment` from (((`bookings` `b` join `teacher` `t` on((`b`.`t_id` = `t`.`id`))) join `teacher_slots` `ts` on((`b`.`slot_id` = `ts`.`id`))) left join `feedback` `f` on((`f`.`b_id` = `b`.`id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_student_schedule`
--

/*!50001 DROP VIEW IF EXISTS `view_student_schedule`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `view_student_schedule` AS select `b`.`id` AS `booking_id`,`b`.`s_id` AS `student_id`,`s`.`student_name` AS `student_name`,`b`.`t_id` AS `teacher_id`,`t`.`teacher_name` AS `teacher_name`,`ts`.`slot_datetime` AS `lesson_time`,`b`.`status` AS `booking_status`,`b`.`zoom_link` AS `zoom_link` from (((`bookings` `b` join `student` `s` on((`b`.`s_id` = `s`.`id`))) join `teacher` `t` on((`b`.`t_id` = `t`.`id`))) join `teacher_slots` `ts` on((`b`.`slot_id` = `ts`.`id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_teacher_revenue`
--

/*!50001 DROP VIEW IF EXISTS `view_teacher_revenue`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `view_teacher_revenue` AS select `t`.`id` AS `teacher_id`,`t`.`teacher_name` AS `teacher_name`,date_format(`ts`.`slot_datetime`,'%Y-%m') AS `report_month`,count(`b`.`id`) AS `total_lessons`,sum(`t`.`point_per_lesson`) AS `total_points_earned` from ((`teacher` `t` join `bookings` `b` on((`t`.`id` = `b`.`t_id`))) join `teacher_slots` `ts` on((`b`.`slot_id` = `ts`.`id`))) where (`b`.`status` = '已完成') group by `t`.`id`,`report_month` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-02-09  1:32:15
