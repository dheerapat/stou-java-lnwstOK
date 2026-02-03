package org.example.service;

import org.example.dao.ProductDAO;
import org.example.model.Product;

import java.sql.SQLException;
import java.util.List;

public class ProductService {
    private final ProductDAO productDAO = new ProductDAO();

    public Product addProduct(String productName, String unit) throws SQLException {
        validateProduct(productName, unit);

        productDAO.addProduct(productName, unit);

        Product product = new Product();
        product.setProductName(productName);
        product.setUnit(unit);
        return product;
    }

    public Product getProductById(int productId) throws SQLException {
        validateProductId(productId);

        Product product = productDAO.getProductById(productId);
        if (product == null) {
            throw new IllegalArgumentException("Product not found with ID: " + productId);
        }
        return product;
    }

    public List<Product> getAllProducts() throws SQLException {
        return productDAO.getAllProducts();
    }

    public Product updateProduct(int productId, String productName, String unit) throws SQLException {
        validateProductId(productId);
        validateProduct(productName, unit);

        Product existingProduct = productDAO.getProductById(productId);
        if (existingProduct == null) {
            throw new IllegalArgumentException("Product not found with ID: " + productId);
        }

        Product product = new Product();
        product.setProductId(productId);
        product.setProductName(productName);
        product.setUnit(unit);

        productDAO.updateProduct(product);
        return product;
    }

    public void deleteProduct(int productId) throws SQLException {
        validateProductId(productId);

        Product existingProduct = productDAO.getProductById(productId);
        if (existingProduct == null) {
            throw new IllegalArgumentException("Product not found with ID: " + productId);
        }

        productDAO.deleteProduct(productId);
    }

    private void validateProduct(String productName, String unit) {
        if (productName == null || productName.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name is required");
        }
        if (unit == null || unit.trim().isEmpty()) {
            throw new IllegalArgumentException("Unit is required");
        }
    }

    private void validateProductId(int productId) {
        if (productId <= 0) {
            throw new IllegalArgumentException("Invalid product ID");
        }
    }
}
