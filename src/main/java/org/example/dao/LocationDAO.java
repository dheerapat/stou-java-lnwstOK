package org.example.dao;

import org.example.model.Location;
import org.example.util.MySQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LocationDAO {
    public void addLocation(String locationName) throws SQLException {
        String sql = "INSERT INTO locations (location_name) VALUES (?)";

        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, locationName);
            stmt.executeUpdate();
        }
    }

    public Location getLocationById(int locationId) throws SQLException {
        String sql = "SELECT location_id, location_name FROM locations WHERE location_id = ?";

        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, locationId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToLocation(rs);
                }
            }
        }

        return null;
    }

    public List<Location> getAllLocations() throws SQLException {
        String sql = "SELECT location_id, location_name FROM locations";

        List<Location> locationList = new ArrayList<>();

        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                locationList.add(mapResultSetToLocation(rs));
            }
        }

        return locationList;
    }

    public void updateLocation(Location location) throws SQLException {
        String sql = "UPDATE locations SET location_name = ? WHERE location_id = ?";

        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, location.getLocationName());
            stmt.setInt(2, location.getLocationId());
            stmt.executeUpdate();
        }
    }

    public void deleteLocation(int locationId) throws SQLException {
        String sql = "DELETE FROM locations WHERE location_id = ?";

        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, locationId);
            stmt.executeUpdate();
        }
    }

    private Location mapResultSetToLocation(ResultSet rs) throws SQLException {
        Location location = new Location();
        location.setLocationId(rs.getInt("location_id"));
        location.setLocationName(rs.getString("location_name"));
        return location;
    }
}
