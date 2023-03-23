-- phpMyAdmin SQL Dump
-- version 4.4.15.10
-- https://www.phpmyadmin.net
--
-- Host: d30.itc.rwth-aachen.de:3330
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

INSERT INTO `person` (`person_id`, `vorname`, `nachname`, `betrieb`, `email`,`rolle_id`) VALUES 
(0, 'testvorname','testnachname', null, 'test@mail.de', 0),
(1, 'peter', 'müller', 'testbetrieb', 'peter@mail.de', 2),
(2, 'max', 'mustermann', 'testbetrieb', 'max.mustermann@mail.de', 1);
(3, 'Patrick', 'schmidt', 'rwth', 'patrick@rwth.de', 2);
(4, 'michael', 'blume', 'rwth', 'mb@rwth.de',1);
