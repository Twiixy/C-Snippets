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

--
-- Tabellenstruktur für Tabelle `betreut`
--

CREATE TABLE IF NOT EXISTS betreut (
  ausbilder_id int NOT NULL,
  azubi_id int NOT NULL ,
  primary key (ausbilder_id, azubi_id),
  INDEX person_id_betreut_1 (ausbilder_id ASC),
  CONSTRAINT fk_ausbilder_azubi FOREIGN KEY (ausbilder_id) REFERENCES person (person_id),
  INDEX person_id_betreut_2 (azubi_id ASC),
  CONSTRAINT fk_azubi_person FOREIGN KEY (azubi_id) REFERENCES person (person_id)
  
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

