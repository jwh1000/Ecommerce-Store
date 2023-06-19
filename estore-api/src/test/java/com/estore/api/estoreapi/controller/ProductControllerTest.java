package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.persistence.ProductDAO;


/**
 * Tests for the Product Controller Class
 * 
 * @author Cole DenBleyker (add your name if you add tests)
 */
@Tag("Controller-tier")
public class ProductControllerTest {
    private ProductController productController;
    private ProductDAO mockProductDAO;

    /**
     * Before each test, create a new ProductController object and inject a mock Product DAO
     */
    @BeforeEach
    public void setupProductController() {
        mockProductDAO = mock(ProductDAO.class);
        productController = new ProductController(mockProductDAO);
    }

    @Test
    public void testUpdateProduct() throws IOException {
        // Setup
        Product product = new Product("Not Updated Product", 10, 6);
        // when updateProduct is called, return true simulating successful update and save
        when(mockProductDAO.updateProduct(product)).thenReturn(product);
        ResponseEntity<Product> response = productController.updateProduct(product);
        product.setName("Updated Product");
        // Invoke
        response = productController.updateProduct(product);
        // Analysis
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody());
    }

    @Test
    public void testUpdateProductFailed() throws IOException {
        // Setup
        Product product = new Product("A Product", 15, 6);
        // when updateProduct is called, return true simulating successful update and save
        when(mockProductDAO.updateProduct(product)).thenReturn(null);
        // Invoke
        ResponseEntity<Product> response = productController.updateProduct(product);
        // Analysis
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdateProductHandleException() throws IOException {
        // Setup
        Product product = new Product("A Product", 15, 6);
        // When updateProduct is called on the mock Product DAO, throw an IOException
        doThrow(new IOException()).when(mockProductDAO).updateProduct(product);
        // Invoke
        ResponseEntity<Product> response = productController.updateProduct(product);
        // Analysis
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
