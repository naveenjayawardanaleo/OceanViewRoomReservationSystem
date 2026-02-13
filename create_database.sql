-- Create database
CREATE DATABASE IF NOT EXISTS oceanviewresort;

-- Use the database
USE oceanviewresort;

-- Create rooms table
CREATE TABLE IF NOT EXISTS rooms (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    shortDescription VARCHAR(500),
    capacity INT NOT NULL,
    room_type INT NOT NULL,
    wifi INT DEFAULT 0,
    aircon INT DEFAULT 0,
    hotwater INT DEFAULT 0,
    pricePerNight DOUBLE NOT NULL
);

-- Create reservation table
CREATE TABLE IF NOT EXISTS reservation (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(500),
    telephone VARCHAR(20),
    roomType INT NOT NULL,
    checkIn DATE NOT NULL,
    checkOut DATE NOT NULL,
    status VARCHAR(50) DEFAULT 'Pending'
);

-- Create users table
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

-- Insert dummy data into rooms table
INSERT INTO rooms (name, shortDescription, capacity, room_type, wifi, aircon, hotwater, pricePerNight) VALUES
('Deluxe Suite', 'Spacious suite with ocean view', 2, 1, 1, 1, 1, 25000.00),
('Standard Room', 'Comfortable room for families', 4, 2, 1, 0, 1, 15000.00),
('Economy Room', 'Basic room with essential amenities', 2, 3, 0, 0, 0, 10000.00),
('Family Suite', 'Large suite for families', 6, 1, 1, 1, 1, 35000.00),
('Executive Room', 'Premium room with extra services', 2, 2, 1, 1, 1, 20000.00);

-- Insert dummy data into reservation table
INSERT INTO reservation (name, address, telephone, roomType, checkIn, checkOut, status) VALUES
('Nimal Fernando', '45 Galle Road, Colombo 03', '077-1234567', 1, '2026-02-10', '2026-02-15', 'Pending'),
('Kumari Perera', '12 Temple Road, Kandy', '081-2345678', 2, '2026-02-12', '2026-02-14', 'Pending'),
('Rohan Silva', '78 Beach Avenue, Negombo', '031-3456789', 1, '2026-02-20', '2026-02-25', 'Pending'),
('Anura Jayasinghe', '23 Hill Street, Nuwara Eliya', '052-4567890', 3, '2026-02-18', '2026-02-20', 'Pending'),
('Dilini Ratnayake', '56 Lake Drive, Anuradhapura', '025-5678901', 2, '2026-02-22', '2026-02-24', 'Pending');

-- Insert admin user
INSERT INTO users (username, password) VALUES
('admin', '123456@A');
