package org.example.service;

import org.example.dao.AddStockDAO;
import org.example.dao.StockDAO;
import org.example.dao.WithdrawStockDAO;
import org.example.model.AddStock;
import org.example.model.Location;
import org.example.model.Product;
import org.example.model.Stock;
import org.example.model.WithdrawStock;
import org.example.util.MySQLConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class StockService {
    private final StockDAO stockDAO = new StockDAO();
    private final AddStockDAO addStockDAO = new AddStockDAO();
    private final WithdrawStockDAO withdrawStockDAO = new WithdrawStockDAO();

    public void addStock(Stock stock) throws SQLException {
        validateStock(stock);
        stockDAO.addStock(stock);
    }

    public List<Stock> getAllStockInfo() throws SQLException {
        return stockDAO.getAllStockInfo();
    }

    public Stock getStockById(int stockId) throws SQLException {
        validateStockId(stockId);
        return stockDAO.getStockById(stockId);
    }

    public List<Stock> getStockByProductId(int productId) throws SQLException {
        validateStockId(productId);
        return stockDAO.getStockByProductId(productId);
    }

    public List<Stock> getStockByLocationId(int locationId) throws SQLException {
        validateStockId(locationId);
        return stockDAO.getStockByLocationId(locationId);
    }

    public Stock addStockTransaction(int productId, int locationId, String lotNumber, int quantity) throws SQLException {
        validateProductLocation(productId, locationId);
        validateQuantity(quantity, "Add");
        validateLotNumber(lotNumber);

        try (Connection conn = MySQLConnection.getConnection()) {
            conn.setAutoCommit(false);

            try {
                Stock stock = stockDAO.getStockByProductAndLocation(productId, locationId);
                if (stock == null) {
                    stock = createNewStock(conn, productId, locationId, lotNumber, quantity);
                } else {
                    int newQuantity = stock.getQuantity() + quantity;
                    stockDAO.updateStockQuantity(stock.getStockId(), newQuantity);
                    stock.setQuantity(newQuantity);
                }

                AddStock addStock = new AddStock();
                addStock.setStockId(stock.getStockId());
                addStock.setLotNumber(lotNumber);
                addStock.setQuantity(quantity);
                addStockDAO.addAddStock(addStock);

                conn.commit();
                return stock;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    public Stock withdrawStockTransaction(int stockId, String lotNumber, int quantity) throws SQLException {
        validateStockId(stockId);
        validateQuantity(quantity, "Withdraw");
        validateLotNumber(lotNumber);

        try (Connection conn = MySQLConnection.getConnection()) {
            conn.setAutoCommit(false);

            try {
                Stock stock = stockDAO.getStockById(stockId);
                if (stock == null) {
                    throw new IllegalArgumentException("Stock not found with ID: " + stockId);
                }

                if (stock.getQuantity() < quantity) {
                    throw new IllegalArgumentException("Insufficient stock. Current quantity: " + stock.getQuantity() + ", Requested: " + quantity);
                }

                int newQuantity = stock.getQuantity() - quantity;
                stockDAO.updateStockQuantity(stockId, newQuantity);
                stock.setQuantity(newQuantity);

                WithdrawStock withdrawStock = new WithdrawStock();
                withdrawStock.setStockId(stockId);
                withdrawStock.setLotNumber(lotNumber);
                withdrawStock.setQuantity(quantity);
                withdrawStockDAO.addWithdrawStock(withdrawStock);

                conn.commit();
                return stock;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } catch (IllegalArgumentException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    private Stock createNewStock(Connection conn, int productId, int locationId, String lotNumber, int quantity) throws SQLException {
        Stock stock = new Stock();
        stock.setLotNumber(lotNumber);
        stock.setQuantity(quantity);

        Product product = new Product();
        product.setProductId(productId);
        stock.setProduct(product);

        Location location = new Location();
        location.setLocationId(locationId);
        stock.setLocation(location);

        stockDAO.addStock(stock);
        return stock;
    }

    private void validateStockId(int stockId) {
        if (stockId <= 0) {
            throw new IllegalArgumentException("Invalid stock ID");
        }
    }

    private void validateStock(Stock stock) {
        if (stock.getProduct() == null || stock.getProduct().getProductId() <= 0) {
            throw new IllegalArgumentException("Invalid product");
        }
        if (stock.getLocation() == null || stock.getLocation().getLocationId() <= 0) {
            throw new IllegalArgumentException("Invalid location");
        }
        if (stock.getQuantity() < 0) {
            throw new IllegalArgumentException("Quantity must be 0 or greater");
        }
    }

    private void validateProductLocation(int productId, int locationId) {
        if (productId <= 0) {
            throw new IllegalArgumentException("Invalid product ID");
        }
        if (locationId <= 0) {
            throw new IllegalArgumentException("Invalid location ID");
        }
    }

    private void validateQuantity(int quantity, String operation) {
        if (quantity <= 0) {
            throw new IllegalArgumentException(operation + " quantity must be greater than 0");
        }
    }

    private void validateLotNumber(String lotNumber) {
        if (lotNumber == null || lotNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Lot number is required");
        }
    }
}