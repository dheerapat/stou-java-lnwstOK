package org.example.model;

import java.sql.Timestamp;

public class AddStock {
    private int addStockId;
    private int stockId;
    private int quantity;
    private Timestamp addDate;
    private Stock stock;

    public int getAddStockId() {
        return addStockId;
    }

    public void setAddStockId(int addStockId) {
        this.addStockId = addStockId;
    }

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Timestamp getAddDate() {
        return addDate;
    }

    public void setAddDate(Timestamp addDate) {
        this.addDate = addDate;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }
}
