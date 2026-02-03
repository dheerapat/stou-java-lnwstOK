package org.example.servlet;

import org.example.service.LocationService;
import org.example.service.ProductService;
import org.example.service.StockService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class ReceiverServlet extends HttpServlet {
    private final StockService stockService = new StockService();
    private final ProductService productService = new ProductService();
    private final LocationService locationService = new LocationService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("products", productService.getAllProducts());
            request.setAttribute("locations", locationService.getAllLocations());
            request.getRequestDispatcher("/receiver.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/receiver.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int productId = Integer.parseInt(request.getParameter("productId"));
            int locationId = Integer.parseInt(request.getParameter("locationId"));
            String lotNumber = request.getParameter("lotNumber");
            int quantity = Integer.parseInt(request.getParameter("quantity"));

            stockService.addStockTransaction(productId, locationId, lotNumber, quantity);

            response.sendRedirect("inventory?success=Stock added successfully");

        } catch (SQLException e) {
            request.setAttribute("error", "Database error: " + e.getMessage());
            doGet(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid number format");
            doGet(request, response);
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            doGet(request, response);
        }
    }
}
