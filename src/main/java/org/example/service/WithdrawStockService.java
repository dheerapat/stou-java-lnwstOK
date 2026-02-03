package org.example.service;

import org.example.dao.WithdrawStockDAO;
import org.example.model.WithdrawStock;

import java.sql.SQLException;
import java.util.List;

public class WithdrawStockService {
    private final WithdrawStockDAO withdrawStockDAO = new WithdrawStockDAO();

    public WithdrawStock addWithdrawStock(WithdrawStock withdrawStock) throws SQLException {
        validateWithdrawStock(withdrawStock);
        withdrawStockDAO.addWithdrawStock(withdrawStock);
        return withdrawStock;
    }

    public WithdrawStock getWithdrawStockById(int withdrawStockId) throws SQLException {
        validateWithdrawStockId(withdrawStockId);
        WithdrawStock withdrawStock = withdrawStockDAO.getWithdrawStockById(withdrawStockId);
        if (withdrawStock == null) {
            throw new IllegalArgumentException("Withdraw stock not found with ID: " + withdrawStockId);
        }
        return withdrawStock;
    }

    public List<WithdrawStock> getWithdrawStockByStockId(int stockId) throws SQLException {
        validateStockId(stockId);
        return withdrawStockDAO.getWithdrawStockByStockId(stockId);
    }

    public List<WithdrawStock> getAllWithdrawStock() throws SQLException {
        return withdrawStockDAO.getAllWithdrawStock();
    }

    private void validateWithdrawStockId(int withdrawStockId) {
        if (withdrawStockId <= 0) {
            throw new IllegalArgumentException("Invalid withdraw stock ID");
        }
    }

    private void validateStockId(int stockId) {
        if (stockId <= 0) {
            throw new IllegalArgumentException("Invalid stock ID");
        }
    }

    private void validateWithdrawStock(WithdrawStock withdrawStock) {
        if (withdrawStock == null) {
            throw new IllegalArgumentException("Withdraw stock cannot be null");
        }
        if (withdrawStock.getStockId() <= 0) {
            throw new IllegalArgumentException("Invalid stock ID");
        }
        if (withdrawStock.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
    }
}