package org.example.model;

public class Stock {
    private int stockId;
    private String dateLot;
    private int quantity;
    private Product product;
    private Location location;

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    public String getDateLot() {
        return dateLot;
    }

    public void setDateLot(String dateLot) {
        this.dateLot = dateLot;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
