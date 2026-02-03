package org.example.servlet;

import org.example.dao.StockDAO;
import org.example.model.Location;
import org.example.model.Product;
import org.example.model.Stock;
import org.example.service.LocationService;
import org.example.service.ProductService;
import org.example.service.StockService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class WithdrawServlet extends HttpServlet {
    private final StockService stockService = new StockService();
    private final ProductService productService = new ProductService();
    private final LocationService locationService = new LocationService();
    private final StockDAO stockDAO = new StockDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("products", productService.getAllProducts());
            request.setAttribute("locations", locationService.getAllLocations());
            request.getRequestDispatcher("/withdraw.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/withdraw.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String confirmed = request.getParameter("confirmed");

            if (confirmed == null) {
                int productId = Integer.parseInt(request.getParameter("productId"));
                int locationId = Integer.parseInt(request.getParameter("locationId"));
                int quantity = Integer.parseInt(request.getParameter("quantity"));

                Stock oldestStock = stockDAO.getOldestStockByProductAndLocation(productId, locationId);

                if (oldestStock == null) {
                    request.setAttribute("error", "No stock found for this product in the selected location");
                    doGet(request, response);
                    return;
                }

                if (oldestStock.getQuantity() < quantity) {
                    request.setAttribute("error", "Insufficient stock in oldest lot. Available: " + oldestStock.getQuantity() + " " + oldestStock.getProduct().getUnit() + ", Requested: " + quantity);
                    doGet(request, response);
                    return;
                }

                request.setAttribute("selectedStock", oldestStock);
                request.setAttribute("quantity", quantity);
                request.setAttribute("productId", productId);
                request.setAttribute("locationId", locationId);
                request.getRequestDispatcher("/withdraw.jsp").forward(request, response);

            } else {
                int productId = Integer.parseInt(request.getParameter("productId"));
                int locationId = Integer.parseInt(request.getParameter("locationId"));
                int quantity = Integer.parseInt(request.getParameter("quantity"));

                stockService.withdrawFromOldestLot(productId, locationId, quantity);

                response.sendRedirect("inventory?success=Stock withdrawn successfully");
            }

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
