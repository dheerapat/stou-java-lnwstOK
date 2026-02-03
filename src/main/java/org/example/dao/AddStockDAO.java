package org.example.dao;

import org.example.model.AddStock;
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

public class AddStockDAO {

    public void addAddStock(AddStock addStock) throws SQLException {
        String sql = "INSERT INTO add_stock (stock_id, lot_number, quantity) VALUES (?, ?, ?)";

        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, addStock.getStockId());
            stmt.setString(2, addStock.getLotNumber());
            stmt.setInt(3, addStock.getQuantity());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    addStock.setAddStockId(generatedKeys.getInt(1));
                }
            }
        }
    }

    public AddStock getAddStockById(int addStockId) throws SQLException {
        String sql = """
            SELECT
                a.add_stock_id,
                a.stock_id,
                a.lot_number,
                a.quantity,
                a.add_date,
                i.stock_id,
                i.lot_number,
                i.quantity,
                p.product_id AS p_product_id,
                p.product_name AS p_product_name,
                p.unit AS p_unit,
                l.location_id AS l_location_id,
                l.location_name AS l_location_name
            FROM add_stock a
            JOIN inventory i ON a.stock_id = i.stock_id
            JOIN products p ON i.product_id = p.product_id
            JOIN locations l ON i.location_id = l.location_id
            WHERE a.add_stock_id = ?
            """;

        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, addStockId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToAddStock(rs);
                }
            }
        }

        return null;
    }

    public List<AddStock> getAddStockByStockId(int stockId) throws SQLException {
        String sql = """
            SELECT
                a.add_stock_id,
                a.stock_id,
                a.lot_number,
                a.quantity,
                a.add_date,
                i.stock_id,
                i.lot_number,
                i.quantity,
                p.product_id AS p_product_id,
                p.product_name AS p_product_name,
                p.unit AS p_unit,
                l.location_id AS l_location_id,
                l.location_name AS l_location_name
            FROM add_stock a
            JOIN inventory i ON a.stock_id = i.stock_id
            JOIN products p ON i.product_id = p.product_id
            JOIN locations l ON i.location_id = l.location_id
            WHERE a.stock_id = ?
            ORDER BY a.add_date DESC
            """;

        List<AddStock> addStockList = new ArrayList<>();

        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, stockId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    addStockList.add(mapResultSetToAddStock(rs));
                }
            }
        }

        return addStockList;
    }

    public List<AddStock> getAllAddStock() throws SQLException {
        String sql = """
            SELECT
                a.add_stock_id,
                a.stock_id,
                a.lot_number,
                a.quantity,
                a.add_date,
                i.stock_id,
                i.lot_number,
                i.quantity,
                p.product_id AS p_product_id,
                p.product_name AS p_product_name,
                p.unit AS p_unit,
                l.location_id AS l_location_id,
                l.location_name AS l_location_name
            FROM add_stock a
            JOIN inventory i ON a.stock_id = i.stock_id
            JOIN products p ON i.product_id = p.product_id
            JOIN locations l ON i.location_id = l.location_id
            ORDER BY a.add_date DESC
            """;

        List<AddStock> addStockList = new ArrayList<>();

        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                addStockList.add(mapResultSetToAddStock(rs));
            }
        }

        return addStockList;
    }

    private AddStock mapResultSetToAddStock(ResultSet rs) throws SQLException {
        AddStock addStock = new AddStock();
        addStock.setAddStockId(rs.getInt("add_stock_id"));
        addStock.setStockId(rs.getInt("stock_id"));
        addStock.setLotNumber(rs.getString("lot_number"));
        addStock.setQuantity(rs.getInt("quantity"));
        addStock.setAddDate(rs.getTimestamp("add_date"));

        Stock stock = new Stock();
        stock.setStockId(rs.getInt("stock_id"));
        stock.setLotNumber(rs.getString("lot_number"));
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

        addStock.setStock(stock);

        return addStock;
    }
}