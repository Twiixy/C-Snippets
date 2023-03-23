-- phpMyAdmin SQL Dump
-- version 4.4.15.10
-- https://www.phpmyadmin.net
--
-- Host: d30.itc.rwth-aachen.de:3330
-- Erstellungszeit: 06. Jan 2023 um 13:15
-- Server-Version: 8.0.31-commercial
-- PHP-Version: 5.4.45

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `30_0018`
--

-- --------------------------------------------------------

INSERT INTO `rolle` (`rolle_id`,`beschreibung`) VALUES (0, 'admin'),
 (1, 'betreuer'),
 (2, 'auszubildener');

