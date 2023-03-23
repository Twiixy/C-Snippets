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
-- Tabellenstruktur für Tabelle `Personen`
--

CREATE TABLE IF NOT EXISTS person (
  person_id int primary key,
  vorname varchar(255),
  nachname varchar(255),
  betrieb varchar(255),
  email varchar(255),
  rolle_id int NOT NULL,
  INDEX rolle_id (rolle_id ASC),
  CONSTRAINT fk_rolle FOREIGN KEY (rolle_id) REFERENCES rolle (rolle_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

