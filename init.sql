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
    date_lot VARCHAR(50),
    product_id INT NOT NULL,
    location_id INT NOT NULL,
    quantity INT NOT NULL DEFAULT 0,
    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE,
    FOREIGN KEY (location_id) REFERENCES locations(location_id) ON DELETE CASCADE
);

INSERT INTO locations (location_name) VALUES
('Warehouse A'),
('Warehouse B'),
('Store Front');

INSERT INTO products (product_name, unit) VALUES
('Widget A', 'pcs'),
('Widget B', 'kg'),
('Widget C', 'boxes');
