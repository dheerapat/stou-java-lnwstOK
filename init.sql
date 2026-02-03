CREATE DATABASE IF NOT EXISTS inventory_db;
USE inventory_db;

CREATE TABLE IF NOT EXISTS locations (
    location_id INT AUTO_INCREMENT PRIMARY KEY,
    location_name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS products (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    product_name VARCHAR(255) NOT NULL,
    unit VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS inventory (
    stock_id INT AUTO_INCREMENT PRIMARY KEY,
    lot_number VARCHAR(50),
    product_id INT NOT NULL,
    location_id INT NOT NULL,
    quantity INT NOT NULL DEFAULT 0,
    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE,
    FOREIGN KEY (location_id) REFERENCES locations(location_id) ON DELETE CASCADE,
    UNIQUE KEY unique_product_location (product_id, location_id, lot_number)
);

CREATE TABLE IF NOT EXISTS add_stock (
    add_stock_id INT AUTO_INCREMENT PRIMARY KEY,
    stock_id INT NOT NULL,
    quantity INT NOT NULL,
    add_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (stock_id) REFERENCES inventory(stock_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS withdraw_stock (
    withdraw_stock_id INT AUTO_INCREMENT PRIMARY KEY,
    stock_id INT NOT NULL,
    quantity INT NOT NULL,
    withdraw_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (stock_id) REFERENCES inventory(stock_id) ON DELETE CASCADE
);

INSERT INTO locations (location_name) VALUES
('Warehouse A'),
('Warehouse B'),
('Store Front');

INSERT INTO products (product_name, unit) VALUES
('Widget A', 'pcs'),
('Widget B', 'kg'),
('Widget C', 'boxes');
