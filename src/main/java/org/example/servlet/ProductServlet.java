package org.example.servlet;

import org.example.model.Product;
import org.example.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class ProductServlet extends HttpServlet {
    private final ProductService productService = new ProductService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String action = request.getParameter("action");
            String idParam = request.getParameter("id");

            if ("delete".equals(action) && idParam != null) {
                int productId = Integer.parseInt(idParam);
                productService.deleteProduct(productId);
                response.sendRedirect("products");
                return;
            }

            if ("edit".equals(action) && idParam != null) {
                int productId = Integer.parseInt(idParam);
                Product product = productService.getProductById(productId);
                request.setAttribute("product", product);
            }

            request.setAttribute("products", productService.getAllProducts());
            request.getRequestDispatcher("/masterlist.jsp").forward(request, response);

        } catch (SQLException e) {
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/masterlist.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid product ID");
            response.sendRedirect("products");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String idParam = request.getParameter("productId");
            String productName = request.getParameter("productName");
            String unit = request.getParameter("unit");

            if (idParam != null && !idParam.isEmpty()) {
                int productId = Integer.parseInt(idParam);
                productService.updateProduct(productId, productName, unit);
            } else {
                productService.addProduct(productName, unit);
            }

            response.sendRedirect("products");

        } catch (SQLException e) {
            request.setAttribute("error", "Database error: " + e.getMessage());
            doGet(request, response);
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            doGet(request, response);
        }
    }
}
