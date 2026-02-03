package org.example.servlet;

import org.example.model.Product;
import org.example.service.LocationService;
import org.example.service.ProductService;
import org.example.service.StockService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class InventoryServlet extends HttpServlet {
    private final StockService stockService = new StockService();
    private final ProductService productService = new ProductService();
    private final LocationService locationService = new LocationService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String productIdParam = request.getParameter("productId");
            String locationIdParam = request.getParameter("locationId");

            List<org.example.model.Stock> stockList;

            if (productIdParam != null && !productIdParam.isEmpty()) {
                int productId = Integer.parseInt(productIdParam);
                stockList = stockService.getStockByProductId(productId);
                request.setAttribute("selectedProductId", productId);
            } else if (locationIdParam != null && !locationIdParam.isEmpty()) {
                int locationId = Integer.parseInt(locationIdParam);
                stockList = stockService.getStockByLocationId(locationId);
                request.setAttribute("selectedLocationId", locationId);
            } else {
                stockList = stockService.getAllStockInfo();
            }

            List<Product> products = productService.getAllProducts();
            List<org.example.model.Location> locations = locationService.getAllLocations();

            request.setAttribute("stockList", stockList);
            request.setAttribute("products", products);
            request.setAttribute("locations", locations);

            request.getRequestDispatcher("/inventory.jsp").forward(request, response);

        } catch (SQLException e) {
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/inventory.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid parameter format");
            request.getRequestDispatcher("/inventory.jsp").forward(request, response);
        }
    }
}
