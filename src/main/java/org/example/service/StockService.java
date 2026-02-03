package org.example.service;

import org.example.dao.StockDAO;
import org.example.model.Stock;

import java.sql.SQLException;
import java.util.List;

public class StockService {
    private final StockDAO stockDAO = new StockDAO();

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

    private void validateStockId(int stockId) {
        if (stockId <= 0) {
            throw new IllegalArgumentException("Invalid stock ID");
        }
    }
}
