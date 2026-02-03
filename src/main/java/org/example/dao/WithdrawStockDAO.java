package org.example.dao;

import org.example.model.Location;
import org.example.model.Product;
import org.example.model.Stock;
import org.example.model.WithdrawStock;
import org.example.util.MySQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WithdrawStockDAO {

    public void addWithdrawStock(WithdrawStock withdrawStock) throws SQLException {
        String sql = "INSERT INTO withdraw_stock (stock_id, lot_number, quantity) VALUES (?, ?, ?)";

        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, withdrawStock.getStockId());
            stmt.setString(2, withdrawStock.getLotNumber());
            stmt.setInt(3, withdrawStock.getQuantity());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    withdrawStock.setWithdrawStockId(generatedKeys.getInt(1));
                }
            }
        }
    }

    public WithdrawStock getWithdrawStockById(int withdrawStockId) throws SQLException {
        String sql = """
            SELECT
                w.withdraw_stock_id,
                w.stock_id,
                w.lot_number,
                w.quantity,
                w.withdraw_date,
                i.stock_id,
                i.lot_number,
                i.quantity,
                p.product_id AS p_product_id,
                p.product_name AS p_product_name,
                p.unit AS p_unit,
                l.location_id AS l_location_id,
                l.location_name AS l_location_name
            FROM withdraw_stock w
            JOIN inventory i ON w.stock_id = i.stock_id
            JOIN products p ON i.product_id = p.product_id
            JOIN locations l ON i.location_id = l.location_id
            WHERE w.withdraw_stock_id = ?
            """;

        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, withdrawStockId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToWithdrawStock(rs);
                }
            }
        }

        return null;
    }

    public List<WithdrawStock> getWithdrawStockByStockId(int stockId) throws SQLException {
        String sql = """
            SELECT
                w.withdraw_stock_id,
                w.stock_id,
                w.lot_number,
                w.quantity,
                w.withdraw_date,
                i.stock_id,
                i.lot_number,
                i.quantity,
                p.product_id AS p_product_id,
                p.product_name AS p_product_name,
                p.unit AS p_unit,
                l.location_id AS l_location_id,
                l.location_name AS l_location_name
            FROM withdraw_stock w
            JOIN inventory i ON w.stock_id = i.stock_id
            JOIN products p ON i.product_id = p.product_id
            JOIN locations l ON i.location_id = l.location_id
            WHERE w.stock_id = ?
            ORDER BY w.withdraw_date DESC
            """;

        List<WithdrawStock> withdrawStockList = new ArrayList<>();

        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, stockId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    withdrawStockList.add(mapResultSetToWithdrawStock(rs));
                }
            }
        }

        return withdrawStockList;
    }

    public List<WithdrawStock> getAllWithdrawStock() throws SQLException {
        String sql = """
            SELECT
                w.withdraw_stock_id,
                w.stock_id,
                w.lot_number,
                w.quantity,
                w.withdraw_date,
                i.stock_id,
                i.lot_number,
                i.quantity,
                p.product_id AS p_product_id,
                p.product_name AS p_product_name,
                p.unit AS p_unit,
                l.location_id AS l_location_id,
                l.location_name AS l_location_name
            FROM withdraw_stock w
            JOIN inventory i ON w.stock_id = i.stock_id
            JOIN products p ON i.product_id = p.product_id
            JOIN locations l ON i.location_id = l.location_id
            ORDER BY w.withdraw_date DESC
            """;

        List<WithdrawStock> withdrawStockList = new ArrayList<>();

        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                withdrawStockList.add(mapResultSetToWithdrawStock(rs));
            }
        }

        return withdrawStockList;
    }

    private WithdrawStock mapResultSetToWithdrawStock(ResultSet rs) throws SQLException {
        WithdrawStock withdrawStock = new WithdrawStock();
        withdrawStock.setWithdrawStockId(rs.getInt("withdraw_stock_id"));
        withdrawStock.setStockId(rs.getInt("stock_id"));
        withdrawStock.setLotNumber(rs.getString("lot_number"));
        withdrawStock.setQuantity(rs.getInt("quantity"));
        withdrawStock.setWithdrawDate(rs.getTimestamp("withdraw_date"));

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

        withdrawStock.setStock(stock);

        return withdrawStock;
    }
}