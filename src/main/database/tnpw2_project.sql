-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Počítač: 127.0.0.1
-- Vytvořeno: Ned 02. dub 2023, 11:17
-- Verze serveru: 10.4.18-MariaDB
-- Verze PHP: 7.3.27

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Databáze: `tnpw2_project`
--
CREATE DATABASE IF NOT EXISTS `tnpw2_project` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `tnpw2_project`;

-- --------------------------------------------------------

--
-- Struktura tabulky `posts`
--

DROP TABLE IF EXISTS `posts`;
CREATE TABLE IF NOT EXISTS `posts` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `header` varchar(30) NOT NULL,
  `text` mediumtext NOT NULL,
  `user_id` int(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id` (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;

--
-- Vypisuji data pro tabulku `posts`
--

INSERT INTO `posts` (`id`, `header`, `text`, `user_id`) VALUES
(1, 'Super novy header', 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Integer tempor. Duis risus. Etiam posuere lacus quis dolor. Proin in tellus sit amet nibh dignissim sagittis. Nullam feugiat, turpis at pulvinar vulputate, erat libero tristique tellus, nec bibendum odio risus sit amet ante. Etiam dui sem, fermentum vitae, sagittis id, malesuada in, quam. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. In sem justo, commodo ut, suscipit at, pharetra vitae, orci. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Nulla non arcu lacinia neque faucibus fringilla. Integer rutrum, orci vestibulum ullamcorper ultricies, lacus quam ultricies odio, vitae placerat pede sem sit amet enim. Curabitur bibendum justo non orci. Mauris tincidunt sem sed arcu. Proin pede metus, vulputate nec, fermentum fringilla, vehicula vitae, justo. Aliquam erat volutpat. Curabitur sagittis hendrerit ante.', 1),
(2, 'another test post', 'another test post', 1),
(3, 'nevim nejak se to jmenuje', 'toto je novy updatovany text tohoto blog postu', 1),
(4, 'testing', 'post', 1),
(5, 'myNewSuperPost', 'Totally not testing post', 4),
(6, 'asdf', 'asdf', 1),
(7, 'adf', 'asdf', 1),
(8, 'asdf', 'asfdasdfasdfasdfafToadflakjdfůalsdhfůocihxvyadgfdafgdsfg', 1);

-- --------------------------------------------------------

--
-- Struktura tabulky `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(60) NOT NULL,
  `surname` varchar(60) NOT NULL,
  `username` varchar(60) NOT NULL,
  `email` varchar(60) NOT NULL,
  `password` varchar(255) NOT NULL,
  `type` varchar(60) NOT NULL,
  `enabled` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

--
-- Vypisuji data pro tabulku `users`
--

INSERT INTO `users` (`id`, `name`, `surname`, `username`, `email`, `password`, `type`, `enabled`) VALUES
(1, 'Martin', 'Malíř', 'malirma', 'martin.malir@gmail.com', '$2a$10$F.1Ecni5eIjNsvOZtWhqn.CbXGgwgxnsGgDp9zN75p/xAlGzVaQV6', '0', 1),
(3, 'test', 'user', 'username', 'mail@mail.cz', 'password', '1', 0),
(4, 'te', 'test', 'tteesstt', 'mail@mail.cz', '$2a$10$F.1Ecni5eIjNsvOZtWhqn.CbXGgwgxnsGgDp9zN75p/xAlGzVaQV6', '1', 0);

--
-- Omezení pro exportované tabulky
--

--
-- Omezení pro tabulku `posts`
--
ALTER TABLE `posts`
  ADD CONSTRAINT `posts_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
