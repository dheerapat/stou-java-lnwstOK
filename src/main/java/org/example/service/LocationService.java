package org.example.service;

import org.example.dao.LocationDAO;
import org.example.model.Location;

import java.sql.SQLException;
import java.util.List;

public class LocationService {
    private final LocationDAO locationDAO = new LocationDAO();

    public Location addLocation(String locationName) throws SQLException {
        validateLocation(locationName);

        locationDAO.addLocation(locationName);

        Location location = new Location();
        location.setLocationName(locationName);
        return location;
    }

    public Location getLocationById(int locationId) throws SQLException {
        validateLocationId(locationId);

        Location location = locationDAO.getLocationById(locationId);
        if (location == null) {
            throw new IllegalArgumentException("Location not found with ID: " + locationId);
        }
        return location;
    }

    public List<Location> getAllLocations() throws SQLException {
        return locationDAO.getAllLocations();
    }

    public Location updateLocation(int locationId, String locationName) throws SQLException {
        validateLocationId(locationId);
        validateLocation(locationName);

        Location existingLocation = locationDAO.getLocationById(locationId);
        if (existingLocation == null) {
            throw new IllegalArgumentException("Location not found with ID: " + locationId);
        }

        Location location = new Location();
        location.setLocationId(locationId);
        location.setLocationName(locationName);

        locationDAO.updateLocation(location);
        return location;
    }

    public void deleteLocation(int locationId) throws SQLException {
        validateLocationId(locationId);

        Location existingLocation = locationDAO.getLocationById(locationId);
        if (existingLocation == null) {
            throw new IllegalArgumentException("Location not found with ID: " + locationId);
        }

        locationDAO.deleteLocation(locationId);
    }

    private void validateLocation(String locationName) {
        if (locationName == null || locationName.trim().isEmpty()) {
            throw new IllegalArgumentException("Location name is required");
        }
    }

    private void validateLocationId(int locationId) {
        if (locationId <= 0) {
            throw new IllegalArgumentException("Invalid location ID");
        }
    }
}
