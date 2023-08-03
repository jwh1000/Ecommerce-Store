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
import com.estore.api.estoreapi.persistence.PurchaseHistoryDAO;

/**
 * Tests for the PurchaseHistoryController class
 * 
 * @author Cole DenBleyker
 */
@Tag("Controller-tier")
public class PurchaseHistoryControllerTest {
    private PurchaseHistoryController purchaseController;
    private PurchaseHistoryDAO mockPurchaseDAO;
    private ProductDAO mockProductDAO;

    /**
     * Before each test, creates a new PurchaseHistoryController object
     * and inserts a mock ProductDAO
     */
    @BeforeEach
    public void setupPurchaseHistoryController() {
        mockPurchaseDAO = mock(PurchaseHistoryDAO.class);
        mockProductDAO = mock(ProductDAO.class);
        purchaseController = new PurchaseHistoryController(mockPurchaseDAO, mockProductDAO);
    }

    @Test
    public void testAddToPurchaseHistory() throws IOException {
        // setup
        Product product = new Product(1, "Book 1", 2132);
        Product product2 = new Product(2, "Book 2", 2);
        Product[] products = new Product[1];
        Product[] products2 = new Product[1];
        products[0] = product;
        products2[0] = product2;
        when(mockProductDAO.createProduct(product)).thenReturn(product);
        when(mockProductDAO.findProducts(product.getName())).thenReturn(products);
        when(mockPurchaseDAO.addToPurchased(product, "AUser")).thenReturn(product);
        when(mockPurchaseDAO.searchPurchases(product.getName(), "AUser")).thenReturn(products2);
        // invoke
        ResponseEntity<Product> response = purchaseController.addToPurchased(product, "AUser");
        // analysis
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(product, response.getBody());
    }

    @Test
    public void testAddToPurchaseHistoryNotFound() throws IOException {
        // setup
        Product product = new Product(1, "Book 1", 2132);
        Product product2 = new Product(2, "Book 2", 2);
        Product[] products = new Product[1];
        products[0] = product2;
        when(mockProductDAO.createProduct(product)).thenReturn(product);
        when(mockProductDAO.findProducts(product.getName())).thenReturn(products);
        when(mockPurchaseDAO.addToPurchased(product, "AUser")).thenReturn(product);
        // invoke
        ResponseEntity<Product> response = purchaseController.addToPurchased(product, "AUser");
        // analysis
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    public void testAddToPurchaseHistoryAlreadyIn() throws IOException {
        // setup
        Product product = new Product(1, "Book 1", 2132);
        Product[] products = new Product[1];
        products[0] = product;
        when(mockProductDAO.createProduct(product)).thenReturn(product);
        when(mockProductDAO.findProducts(product.getName())).thenReturn(products);
        when(mockPurchaseDAO.addToPurchased(product, "Joe")).thenReturn(product);
        when(mockPurchaseDAO.searchPurchases(product.getName(), "Joe")).thenReturn(products);
        // invoke
        ResponseEntity<Product> response = purchaseController.addToPurchased(product, "Joe");
        // analysis
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    public void testAddToPurchaseHistoryInternalError() throws IOException {
        // setup
        Product product = new Product(1, "Book 1", 2132);
        String user = "Joe";
        doThrow(new IOException()).when(mockPurchaseDAO).searchPurchases(product.getName(), user);
        Product[] products = new Product[1];
        products[0] = product;
        when(mockProductDAO.createProduct(product)).thenReturn(product);
        when(mockProductDAO.findProducts(product.getName())).thenReturn(products);
        // invoke
        ResponseEntity<Product> response = purchaseController.addToPurchased(product, user);
        // analysis
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetPurchasedProducts() throws IOException {
        // setup
        Product product = new Product(1, "Book 1", 2132);
        Product product2 = new Product(2, "Book 2", 2);
        Product[] products = new Product[2];
        products[0] = product;
        products[1] = product2;
        when(mockPurchaseDAO.getPurchasedProducts("Joe")).thenReturn(products);
        // invoke
        ResponseEntity<Product[]> response = purchaseController.getPurchasedContents("Joe");
        // analysis
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(products, response.getBody());
    }

    @Test
    public void testGetPurchasedProductsInternalError() throws IOException {
        // setup
        doThrow(new IOException()).when(mockPurchaseDAO).getPurchasedProducts("Joe");
        // invoke
        ResponseEntity<Product[]> response = purchaseController.getPurchasedContents("Joe");
        // analysis
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetPurchasedProduct() throws IOException {
        // setup
        Product product = new Product(1, "Book 1", 2132);
        Product[] products = new Product[1];
        products[0] = product;
        when(mockPurchaseDAO.getPurchasedProduct(1, "Joe")).thenReturn(product);
        // invoke
        ResponseEntity<Product> response = purchaseController.getPurchasedProduct(1, "Joe");
        // analysis
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody());
    }

    @Test
    public void testGetPurchasedProductNotFound() throws IOException {
        // setup
        Product product = new Product(1, "Book 1", 2132);
        Product[] products = new Product[1];
        products[0] = product;
        when(mockPurchaseDAO.getPurchasedProduct(6, "Joe")).thenReturn(null);
        // invoke
        ResponseEntity<Product> response = purchaseController.getPurchasedProduct(1, "Joe");
        // analysis
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    public void testGetPurchasedProductInternalError() throws IOException {
        // setup
        doThrow(new IOException()).when(mockPurchaseDAO).getPurchasedProduct(1, "Joe");
        // invoke
        ResponseEntity<Product> response = purchaseController.getPurchasedProduct(1, "Joe");
        // analysis
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testRemoveFromPurchased() throws IOException {
        // setup
        Product product = new Product(1, "Book 1", 2132);
        Product[] products = new Product[1];
        products[0] = product;
        when(mockPurchaseDAO.getPurchasedProduct(1, "Joe")).thenReturn(product);
        when(mockPurchaseDAO.removeFromPurchased(1, "Joe")).thenReturn(true);
        // invoke
        ResponseEntity<Product> response = purchaseController.removeFromPurchased(1, "Joe");
        // analysis
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    public void testRemoveFromPurchasedNotFound() throws IOException {
        // setup
        Product product = new Product(1, "Book 1", 2132);
        Product[] products = new Product[1];
        products[0] = product;
        when(mockPurchaseDAO.getPurchasedProduct(1, "Joe")).thenReturn(null);
        when(mockPurchaseDAO.removeFromPurchased(83, "Joe")).thenReturn(false);
        // invoke
        ResponseEntity<Product> response = purchaseController.removeFromPurchased(1, "Joe");
        // analysis
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testRemoveFromPurchasedInternalError() throws IOException {
        // setup
        doThrow(new IOException()).when(mockPurchaseDAO).getPurchasedProduct(1,"Joe");
        // invoke
        ResponseEntity<Product> response = purchaseController.removeFromPurchased(1, "Joe");
        // analysis
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testSearchPurchases() throws IOException {
        // setup
        Product product = new Product(1, "Book 1", 2132);
        Product[] products = new Product[1];
        products[0] = product;
        when(mockPurchaseDAO.searchPurchases("Boo", "Joe")).thenReturn(products);
        // invoke
        ResponseEntity<Product[]> response = purchaseController.searchPurchased("Boo", "Joe");
        // analysis
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(products, response.getBody());
    }

    @Test
    public void testSearchPurchasesNotFound() throws IOException {
        // setup
        Product[] products = new Product[0];
        when(mockPurchaseDAO.searchPurchases("Stinky", "Joe")).thenReturn(products);
        // invoke
        ResponseEntity<Product[]> response = purchaseController.searchPurchased("Stinky", "Joe");
        // analysis
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testSearchPurchasesInternalError() throws IOException { 
        // setup
        doThrow(new IOException()).when(mockPurchaseDAO).searchPurchases("Test", "Joe");
        // invoke
        ResponseEntity<Product[]> response = purchaseController.searchPurchased("Test", "Joe");
        // analysis
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
