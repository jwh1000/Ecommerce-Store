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
 * @author Cole DenBleyker, Rylan Arbour, Ryan Robison
 */
@Tag("Controller-tier")
public class ProductControllerTest {
    private ProductController productController;
    private ProductDAO mockProductDAO;

    /**
     * Before each test, create a new ProductController object and inject a mock
     * Product DAO
     */
    @BeforeEach
    public void setupProductController() {
        mockProductDAO = mock(ProductDAO.class);
        productController = new ProductController(mockProductDAO);
    }

    /**
     * Tests updating a product that should pass
     * @throws IOException
     * @author Cole DenBleyker
     */
    @Test
    public void testUpdateProduct() throws IOException {
        // Setup
        Product product = new Product(6, "Not Updated Product", 10);
        // when updateProduct is called, return true simulating successful update and
        // save
        when(mockProductDAO.updateProduct(product)).thenReturn(product);
        ResponseEntity<Product> response = productController.updateProduct(product);
        product.setName("Updated Product");
        // Invoke
        response = productController.updateProduct(product);
        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody());
    }

    /**
     * Tests updating a product that should fail
     * @throws IOException
     * @author Cole DenBleyker
     */
    @Test
    public void testUpdateProductFailed() throws IOException {
        // Setup
        Product product = new Product(6, "A Product", 15);
        // when updateProduct is called, return true simulating successful update and save
        when(mockProductDAO.updateProduct(product)).thenReturn(null);
        // Invoke
        ResponseEntity<Product> response = productController.updateProduct(product);
        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Tests updating a product that should throw an IOException
     * @throws IOException
     * @author Cole DenBleyker
     */
    @Test
    public void testUpdateProductHandleException() throws IOException {
        // Setup
        Product product = new Product(6, "A Product", 15);
        // when updateProduct is called on the mock Product DAO, throw an IOException
        doThrow(new IOException()).when(mockProductDAO).updateProduct(product);
        // Invoke
        ResponseEntity<Product> response = productController.updateProduct(product);
        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testSearchProducts() throws IOException { // findProducts may throw IOException
        // Setup
        String searchString = "Att";
        Product[] products = new Product[2];
        products[0] = new Product(6, "Attack on Titan V1", 15);
        products[1] = new Product(5, "All about Bodhisattva", 14);
        // When findProducts is called with the search string, return the two heroes above
        when(mockProductDAO.findProducts(searchString)).thenReturn(products);

        // Invoke
        ResponseEntity<Product[]> response = productController.searchProducts(searchString);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(products, response.getBody());
    }

    @Test
    public void testSearchProductsHandleException() throws IOException { // findHeroes may throw IOException
        // Setup
        String searchString = "an";
        // When findProducts is called on the Mock Product DAO, throw an IOException
        doThrow(new IOException()).when(mockProductDAO).findProducts(searchString);

        // Invoke
        ResponseEntity<Product[]> response = productController.searchProducts(searchString);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    }

    /**
     * Tests deleting a product.
     * @throws IOException
     * @author Rylan Arbour
     */
    @Test
    public void testDeleteProduct() throws IOException {
        // Setup
        Product product = new Product(6, "A Product", 15);
        mockProductDAO.createProduct(product);
        //when deleteProduct is called, return true simulating successful delete and save
        when(mockProductDAO.getProduct(6)).thenReturn(product);
        when(mockProductDAO.deleteProduct(6)).thenReturn(true);

        // Invoke
        ResponseEntity<Product> response = productController.deleteProduct(6);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /**
     * Tests deleting a product that doesn't exist, giving a 404 NOT_FOUND.
     * @throws IOException
     * @author Rylan Arbour
     */
    @Test
    public void testDeleteProductNotFound() throws IOException {
        // Setup
        Product product = new Product(6, "A Product", 15);
        mockProductDAO.createProduct(product);
        //when deleteProduct is called, return false simulating failed delete and save
        when(mockProductDAO.getProduct(6)).thenReturn(product);
        when(mockProductDAO.deleteProduct(1)).thenReturn(false);

        // Invoke
        ResponseEntity<Product> response = productController.deleteProduct(1);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Tests deleting a product, when an internal server error happens, giving a 500 INTERNAL_SERVER_ERROR
     * @throws IOException
     * @author Rylan Arbour
     */
    @Test
    public void testDeleteProductHandleException() throws IOException {
        // Setup
        Product product = new Product(1, "A Product", 15);
        mockProductDAO.createProduct(product);
        // when deleteProduct is called, throw IOException
        when(mockProductDAO.getProduct(1)).thenReturn(product);
        doThrow(new IOException()).when(mockProductDAO).deleteProduct(1);

        // Invoke
        ResponseEntity<Product> response = productController.deleteProduct(1);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
    /*
     * Create and Get test Methods Below
     * @author Ryan Robison
     */
    @Test
    public void testCreateProduct() throws IOException {  
        // Setup
        Product product = new Product(6, "BookTest", 10.99);
        Product[] products = new Product[0];
        // when create product is called, creates product
        when(mockProductDAO.createProduct(product)).thenReturn(product);
        when(mockProductDAO.findProducts(product.getName())).thenReturn(products);

        // Invoke
        ResponseEntity<Product> response = productController.createProduct(product);

        // Analyze
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(product,response.getBody());
    }

    @Test
    public void testCreateProductFail() throws IOException { 
        Product product = new Product(6, "BookTest", 10.99);
        Product[] products = new Product[1];
        products[0] = product;
        // when create product is called but there is conflict aka product exists
        when(mockProductDAO.createProduct(product)).thenReturn(null);
        when(mockProductDAO.findProducts(product.getName())).thenReturn(products);
        // Invoke
        ResponseEntity<Product> response = productController.createProduct(product);

        // Analyze
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }

    @Test
    public void testCreateProductInternalError() throws IOException {  
        // Setup
        Product product = new Product(6, "BookTest", 10.99);
        // when create product is called and internal server error
        doThrow(new IOException()).when(mockProductDAO).findProducts(product.getName());
    
        // Invoke
        ResponseEntity<Product> response = productController.createProduct(product);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testGetProducts() throws IOException { 
        // Setup
        Product[] products = new Product[2];
        products[0] = new Product(6, "BookTest", 10.99);
        products[1] = new Product(7, "BookTest2", 5.00);
        // When get products is called returns books above
        when(mockProductDAO.getProducts()).thenReturn(products);

        // Invoke
        ResponseEntity<Product[]> response = productController.getProducts();

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(products,response.getBody());
    }

    @Test
    public void testGetProductsInternalError() throws IOException {  
        // Setup
        //When get products is called returns internal error
        doThrow(new IOException()).when(mockProductDAO).getProducts();
    
        // Invoke
        ResponseEntity<Product[]> response = productController.getProducts();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testGetProduct() throws IOException { 
        // Setup
        Product[] products = new Product[2];
        products[0] = new Product(6, "BookTest", 10.99);
        products[1] = new Product(7, "BookTest2", 5.00);
        // When get product is called returns specified book above
        when(mockProductDAO.getProduct(6)).thenReturn(products[0]);

        // Invoke
        ResponseEntity<Product> response = productController.getProduct(6);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(products[0],response.getBody());
    }

    @Test
    public void testGetProductNotFound() throws IOException { 
        // Setup
        Product[] products = new Product[2];
        products[0] = new Product(6, "BookTest", 10.99);
        products[1] = new Product(7, "BookTest2", 5.00);
        // When get product is called returns null as book wanted is not there
        when(mockProductDAO.getProduct(5)).thenReturn(null);

        // Invoke
        ResponseEntity<Product> response = productController.getProduct(5);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
        assertEquals(null,response.getBody());
    }

    @Test
    public void testGetProductInternalError() throws IOException {  
        // Setup
        //When get product is called returns internal error
        doThrow(new IOException()).when(mockProductDAO).getProduct(6);
    
        // Invoke
        ResponseEntity<Product> response = productController.getProduct(6);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
}
