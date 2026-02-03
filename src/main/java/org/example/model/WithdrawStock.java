package org.example.model;

import java.sql.Timestamp;

public class WithdrawStock {
    private int withdrawStockId;
    private int stockId;
    private String lotNumber;
    private int quantity;
    private Timestamp withdrawDate;
    private Stock stock;

    public int getWithdrawStockId() {
        return withdrawStockId;
    }

    public void setWithdrawStockId(int withdrawStockId) {
        this.withdrawStockId = withdrawStockId;
    }

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Timestamp getWithdrawDate() {
        return withdrawDate;
    }

    public void setWithdrawDate(Timestamp withdrawDate) {
        this.withdrawDate = withdrawDate;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }
}
