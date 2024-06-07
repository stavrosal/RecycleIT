-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Εξυπηρετητής: localhost
-- Χρόνος δημιουργίας: 07 Ιουν 2024 στις 15:12:05
-- Έκδοση διακομιστή: 10.4.28-MariaDB
-- Έκδοση PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Βάση δεδομένων: `users`
--

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `users`
--

CREATE TABLE `users` (
  `username` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  `isAdmin` tinyint(1) DEFAULT NULL,
  `points` int(11) DEFAULT 0,
  `paper` int(11) DEFAULT 0,
  `glass` int(11) DEFAULT 0,
  `metal` int(11) DEFAULT 0,
  `plastic` int(11) DEFAULT 0,
  `current_paper` int(11) DEFAULT 0,
  `current_glass` int(11) DEFAULT 0,
  `current_metal` int(11) DEFAULT 0,
  `current_plastic` int(11) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Άδειασμα δεδομένων του πίνακα `users`
--

INSERT INTO `users` (`username`, `password`, `isAdmin`, `points`, `paper`, `glass`, `metal`, `plastic`, `current_paper`, `current_glass`, `current_metal`, `current_plastic`) VALUES
('Afro', 'test', 0, 5, 428, 17, 98, 0, 0, 0, 0, 0),
('Rico', 'rico', 0, 91, 0, 0, 0, 0, 0, 0, 0, 0),
('admin', 'admin', 1, 0, 0, 0, 0, 0, 0, 0, 0, 0);

--
-- Ευρετήρια για άχρηστους πίνακες
--

--
-- Ευρετήρια για πίνακα `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`username`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
