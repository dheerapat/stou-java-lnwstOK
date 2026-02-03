package org.example.service;

import org.example.dao.AddStockDAO;
import org.example.model.AddStock;

import java.sql.SQLException;
import java.util.List;

public class AddStockService {
    private final AddStockDAO addStockDAO = new AddStockDAO();

    public AddStock addAddStock(AddStock addStock) throws SQLException {
        validateAddStock(addStock);
        addStockDAO.addAddStock(addStock);
        return addStock;
    }

    public AddStock getAddStockById(int addStockId) throws SQLException {
        validateAddStockId(addStockId);
        AddStock addStock = addStockDAO.getAddStockById(addStockId);
        if (addStock == null) {
            throw new IllegalArgumentException("Add stock not found with ID: " + addStockId);
        }
        return addStock;
    }

    public List<AddStock> getAddStockByStockId(int stockId) throws SQLException {
        return addStockDAO.getAddStockByStockId(stockId);
    }

    public List<AddStock> getAllAddStock() throws SQLException {
        return addStockDAO.getAllAddStock();
    }

    private void validateAddStockId(int addStockId) {
        if (addStockId <= 0) {
            throw new IllegalArgumentException("Invalid add stock ID");
        }
    }

    private void validateAddStock(AddStock addStock) {
        if (addStock == null) {
            throw new IllegalArgumentException("Add stock cannot be null");
        }
        if (addStock.getStockId() <= 0) {
            throw new IllegalArgumentException("Invalid stock ID");
        }
        if (addStock.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
    }
}