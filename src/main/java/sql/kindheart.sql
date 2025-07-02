-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 20, 2025 at 03:11 PM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `kindheart`
--
CREATE DATABASE IF NOT EXISTS `kindheart` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `kindheart`;

-- --------------------------------------------------------

--
-- Table structure for table `donations`
--

CREATE TABLE `donations` (
  `donationId` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `itemName` varchar(100) NOT NULL,
  `description` text DEFAULT NULL,
  `quantity` int(11) NOT NULL,
  `category` varchar(50) NOT NULL,
  `image` varchar(255) DEFAULT 'default.png',
  `status` int(1) DEFAULT 1 COMMENT '1-available,2-requested,3-collected',
  `datePosted` date DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `donations`
--

INSERT INTO `donations` (`donationId`, `userId`, `itemName`, `description`, `quantity`, `category`, `image`, `status`, `datePosted`) VALUES
(1, 1, 'Canned Beans', '12 cans of beans - Best before:20 Dec 2025', 12, 'Food', 'default.png', 1, '2025-02-10'),
(2, 1, 'Blankets', 'New single size warm blankets', 5, 'Blanket', 'default.png', 1, '2025-03-17'),
(3, 3, 'Instant noodles', 'Unopened Koka Chicken Noodle - 10 Pack X 850G - Best before: 18 September 2025', 3, 'Food', 'default.png', 1, '2025-02-19'),
(4, 5, 'Kid Toy', 'Used 2 times Ocean Buddies Animal Band, suitable for 3-24 month', 1, 'Toy', 'default.png', 1, '2025-03-05'),
(5, 5, 'Wooden Xylophone Toy', 'Fully new Wooden Xylophone for kid, Musical Instrument Toy', 1, 'Toy', 'default.png', 1, '2025-04-02'),
(6, 14, 'Oreo Cereal', 'best before: 25 Aug 2025', 3, 'Food', 'Oreo cereal.jpg', 1, '2025-05-09'),
(7, 14, 'Oatmeal', 'Unopen simple granola oatmeal,\r\nBest before: 19 July 2025', 2, 'Food', 'oatmeal.png', 1, '2025-05-09'),
(9, 14, 'Black T-shirt', 'Woman Black color T-shirt,\r\nWear 2 times, still very new and good condition,\r\nM-size ', 1, 'Clothing', 'black-tshirt.jpg', 3, '2025-05-09');

-- --------------------------------------------------------

--
-- Table structure for table `itemrequests`
--

CREATE TABLE `itemrequests` (
  `itemRequestId` int(11) NOT NULL,
  `recipientId` int(11) NOT NULL,
  `itemName` varchar(100) NOT NULL,
  `description` text DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `category` varchar(100) DEFAULT NULL,
  `itemRequestDate` date DEFAULT current_timestamp(),
  `fulfilled` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `itemrequests`
--

INSERT INTO `itemrequests` (`itemRequestId`, `recipientId`, `itemName`, `description`, `quantity`, `category`, `itemRequestDate`, `fulfilled`) VALUES
(1, 2, 'Toothpaste', 'Need toothpaste for hygiene kits', 2, 'Daily Use', '2025-02-16', 0),
(2, 4, 'Rice', 'Dry rice needed, any size of dry rice', 10, 'Food', '2025-03-24', 0),
(3, 4, 'Canned sweetcorn', 'canned sweetcorn needed', 10, 'Food', '2025-03-19', 0),
(4, 5, 'Colour pencil Set', 'kid charity need colour pencil set for kid', 5, 'Stationery', '2025-04-04', 0),
(9, 13, 'Cookies', 'Any flavor cookies,\r\n5 packs', 5, 'Food', '2025-05-16', 0);

-- --------------------------------------------------------

--
-- Table structure for table `notifications`
--

CREATE TABLE `notifications` (
  `notificationId` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `message` text NOT NULL,
  `isRead` tinyint(1) DEFAULT 0,
  `createdAt` date DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `notifications`
--

INSERT INTO `notifications` (`notificationId`, `userId`, `message`, `isRead`, `createdAt`) VALUES
(1, 2, 'Your request for Instant noodles has been approved.', 0, '2025-03-05'),
(2, 6, 'Your request for Blankets has been approved.', 0, '2025-04-01'),
(3, 6, 'Your request for Kid Toy has been received.', 0, '2025-04-10'),
(4, 4, 'Your request for Canned Beans has been received.', 0, '2025-03-23'),
(5, 14, 'You have a new request for your item: Black T-shirt', 0, '2025-05-15');

-- --------------------------------------------------------

--
-- Table structure for table `requests`
--

CREATE TABLE `requests` (
  `requestId` int(11) NOT NULL,
  `donationId` int(11) NOT NULL,
  `recipientId` int(11) NOT NULL,
  `status` int(1) DEFAULT 1 COMMENT '1-pending,2-approved,3-rejected,4-collected',
  `requestedDate` date DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `requests`
--

INSERT INTO `requests` (`requestId`, `donationId`, `recipientId`, `status`, `requestedDate`) VALUES
(1, 1, 4, 1, '2025-03-23'),
(2, 2, 6, 2, '2025-03-29'),
(3, 3, 2, 2, '2025-03-03'),
(4, 4, 6, 1, '2025-04-10'),
(10, 9, 13, 4, '2025-05-15');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `userId` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `profilePic` varchar(255) NOT NULL DEFAULT 'default.png',
  `role` int(1) NOT NULL COMMENT '1-Donor,2-Recipient'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`userId`, `username`, `email`, `password`, `profilePic`, `role`) VALUES
(1, 'donor', 'donor@example.com', '$2a$10$JwKTznMJap4Os/x9RrOcgudAYLb.HjW85jf8pmbf9dmJ9ltxnBEl6', 'default.png	', 1),
(2, 'recipient', 'recipient@example.com', '$2a$10$JwKTznMJap4Os/x9RrOcgudAYLb.HjW85jf8pmbf9dmJ9ltxnBEl6', 'default.png', 2),
(3, 'Alen', 'alen@example.com', '$2a$10$eOKgo48SjURDIww9N9EpQOQ9nytFJXXZO1JGyR2VOfHO.Gua6Caz.', 'default.png', 1),
(4, 'Dundalk Food Bank', 'dundalkfoodbank@example.com', '$2a$10$eOKgo48SjURDIww9N9EpQOQ9nytFJXXZO1JGyR2VOfHO.Gua6Caz.', 'default.png', 2),
(5, 'Hannah', 'hannah@example.com', '$2a$10$eOKgo48SjURDIww9N9EpQOQ9nytFJXXZO1JGyR2VOfHO.Gua6Caz.', 'default.png', 1),
(6, 'Charity House', 'Charityhouse@example.com', '$2a$10$eOKgo48SjURDIww9N9EpQOQ9nytFJXXZO1JGyR2VOfHO.Gua6Caz.', 'default.png', 2),
(13, 'testRecipient', 'testRecipient@gmail.com', '$2a$10$LdZyn55grv70TVFYOYUOCulDHnNfgRoRUAvZC.c359yO9nAJXnCU2', 'default.png', 2),
(14, 'testDonor', 'testdonor@gmail.com', '$2a$10$0tDP7danjk177zz109.ir.L8QqTQpxzJd1Nb7DeThMiLQ52shpdOS', 'default.png', 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `donations`
--
ALTER TABLE `donations`
  ADD PRIMARY KEY (`donationId`),
  ADD KEY `userId` (`userId`);

--
-- Indexes for table `itemrequests`
--
ALTER TABLE `itemrequests`
  ADD PRIMARY KEY (`itemRequestId`),
  ADD KEY `recipientId` (`recipientId`);

--
-- Indexes for table `notifications`
--
ALTER TABLE `notifications`
  ADD PRIMARY KEY (`notificationId`),
  ADD KEY `userId` (`userId`);

--
-- Indexes for table `requests`
--
ALTER TABLE `requests`
  ADD PRIMARY KEY (`requestId`),
  ADD KEY `donationId` (`donationId`),
  ADD KEY `recipientId` (`recipientId`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`userId`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `donations`
--
ALTER TABLE `donations`
  MODIFY `donationId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `itemrequests`
--
ALTER TABLE `itemrequests`
  MODIFY `itemRequestId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `notifications`
--
ALTER TABLE `notifications`
  MODIFY `notificationId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `requests`
--
ALTER TABLE `requests`
  MODIFY `requestId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `userId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `donations`
--
ALTER TABLE `donations`
  ADD CONSTRAINT `donations_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `users` (`userId`);

--
-- Constraints for table `itemrequests`
--
ALTER TABLE `itemrequests`
  ADD CONSTRAINT `itemrequests_ibfk_1` FOREIGN KEY (`recipientId`) REFERENCES `users` (`userId`);

--
-- Constraints for table `notifications`
--
ALTER TABLE `notifications`
  ADD CONSTRAINT `notifications_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `users` (`userId`);

--
-- Constraints for table `requests`
--
ALTER TABLE `requests`
  ADD CONSTRAINT `requests_ibfk_1` FOREIGN KEY (`donationId`) REFERENCES `donations` (`donationId`),
  ADD CONSTRAINT `requests_ibfk_2` FOREIGN KEY (`recipientId`) REFERENCES `users` (`userId`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
