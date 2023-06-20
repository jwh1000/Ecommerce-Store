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
        //setup
        Product product = new Product("Not Updated Product", 10, 6);
        //when updateProduct is called, return true simulating successful update and save
        when(mockProductDAO.updateProduct(product)).thenReturn(product);
        ResponseEntity<Product> response = productController.updateProduct(product);
        product.setName("Updated Product");
        //invoke
        response = productController.updateProduct(product);
        //analysis
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody());
    }

    @Test
    public void testUpdateProductFailed() throws IOException {
        //setup
        Product product = new Product("A Product", 15, 6);
        //when updateProduct is called, return true simulating successful update and save
        when(mockProductDAO.updateProduct(product)).thenReturn(null);
        //invoke
        ResponseEntity<Product> response = productController.updateProduct(product);
        //analysis
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdateProductHandleException() throws IOException {
        //setup
        Product product = new Product("A Product", 15, 6);
        //when updateProduct is called on the mock Product DAO, throw an IOException
        doThrow(new IOException()).when(mockProductDAO).updateProduct(product);
        //invoke
        ResponseEntity<Product> response = productController.updateProduct(product);
        //analysis
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    //TODO: test is failing and I don't know why
    @Test
    public void testDeleteProduct() throws IOException {
        //setup
        //when deleteProduct is called, return true simulating successful delete and save
        when(mockProductDAO.deleteProduct(6)).thenReturn(true);

        //invoke
        ResponseEntity<Product> response = productController.deleteProduct(6);

        //analysis
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteProductNotFound() throws IOException {
        //setup
        //when deleteProduct is called, return false simulating failed delete and save
        when(mockProductDAO.deleteProduct(1)).thenReturn(false);

        //invoke
        ResponseEntity<Product> response = productController.deleteProduct(1);

        //analysis
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    //TODO: test is failing and I don't know why
    @Test
    public void testDeleteProductHandleException() throws IOException {
        //setup
        //when deleteProduct is called, throw IOException
        doThrow(new IOException()).when(mockProductDAO).deleteProduct(1);

        //invoke
        ResponseEntity<Product> response = productController.deleteProduct(1);

        //analysis
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
