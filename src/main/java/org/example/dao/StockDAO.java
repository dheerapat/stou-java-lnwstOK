package org.example.dao;

import org.example.model.Location;
import org.example.model.Product;
import org.example.model.Stock;
import org.example.util.MySQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockDAO {
    public List<Stock> getAllStockInfo() throws SQLException {
        String sql = """
            SELECT
                i.stock_id AS stock_id,
                i.date_lot AS date_lot,
                i.quantity AS quantity,
                p.product_id AS p_product_id,
                p.product_name AS p_product_name,
                p.unit AS p_unit,
                l.location_id AS l_location_id,
                l.location_name AS l_location_name
            FROM inventory i
            JOIN products p ON i.product_id = p.product_id
            JOIN locations l ON i.location_id = l.location_id
            """;

        List<Stock> stockList = new ArrayList<>();

        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Stock stock = mapResultSetToStock(rs);
                stockList.add(stock);
            }
        }

        return stockList;
    }

    public Stock getStockById(int stockId) throws SQLException {
        String sql = """
            SELECT
                i.stock_id AS stock_id,
                i.date_lot AS date_lot,
                i.quantity AS quantity,
                p.product_id AS p_product_id,
                p.product_name AS p_product_name,
                p.unit AS p_unit,
                l.location_id AS l_location_id,
                l.location_name AS l_location_name
            FROM inventory i
            JOIN products p ON i.product_id = p.product_id
            JOIN locations l ON i.location_id = l.location_id
            WHERE i.stock_id = ?
            """;

        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, stockId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToStock(rs);
                }
            }
        }

        return null;
    }

    public List<Stock> getStockByProductId(int productId) throws SQLException {
        String sql = """
            SELECT
                i.stock_id AS stock_id,
                i.date_lot AS date_lot,
                i.quantity AS quantity,
                p.product_id AS p_product_id,
                p.product_name AS p_product_name,
                p.unit AS p_unit,
                l.location_id AS l_location_id,
                l.location_name AS l_location_name
            FROM inventory i
            JOIN products p ON i.product_id = p.product_id
            JOIN locations l ON i.location_id = l.location_id
            WHERE i.product_id = ?
            """;

        return getStockListByQuery(sql, productId);
    }

    public List<Stock> getStockByLocationId(int locationId) throws SQLException {
        String sql = """
            SELECT
                i.stock_id AS stock_id,
                i.date_lot AS date_lot,
                i.quantity AS quantity,
                p.product_id AS p_product_id,
                p.product_name AS p_product_name,
                p.unit AS p_unit,
                l.location_id AS l_location_id,
                l.location_name AS l_location_name
            FROM inventory i
            JOIN products p ON i.product_id = p.product_id
            JOIN locations l ON i.location_id = l.location_id
            WHERE i.location_id = ?
            """;

        return getStockListByQuery(sql, locationId);
    }

    private List<Stock> getStockListByQuery(String sql, int param) throws SQLException {
        List<Stock> stockList = new ArrayList<>();

        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, param);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    stockList.add(mapResultSetToStock(rs));
                }
            }
        }

        return stockList;
    }

    private Stock mapResultSetToStock(ResultSet rs) throws SQLException {
        Stock stock = new Stock();
        stock.setStockId(rs.getInt("stock_id"));
        stock.setDateLot(rs.getString("date_lot"));
        stock.setQuantity(rs.getInt("quantity"));

        Product product = new Product();
        product.setProductId(rs.getInt("p_product_id"));
        product.setProductName(rs.getString("p_product_name"));
        product.setUnit(rs.getString("p_unit"));
        stock.setProduct(product);

        Location location = new Location();
        location.setLocationId(rs.getInt("l_location_id"));
        location.setLocationName(rs.getString("l_location_name"));
        stock.setLocation(location);

        return stock;
    }
}
