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
import com.estore.api.estoreapi.persistence.CartDAO;
import com.estore.api.estoreapi.persistence.ProductDAO;

/**
 * Tests for the Product Controller Class
 * 
 * @author Ryan Robison
 */

@Tag("Controller-tier")
public class CartControllerTest {
    private CartController cartController;
    private CartDAO mockCartDAO;
    private ProductDAO mockProductDAO;

    /**
     * Before each test, create a new ProductController object and inject a mock
     * Product DAO
     */
    @BeforeEach
    public void setupProductController() {
        mockCartDAO = mock(CartDAO.class);
        mockProductDAO = mock(ProductDAO.class);
        cartController = new CartController(mockCartDAO,mockProductDAO);
    }

    @Test
    public void testAddtoCart() throws IOException {
        // setup
        Product product = new Product(6, "to Add product", 10);
        Product product1 = new Product(5, "Ka", 10);
        Product[] products = new Product[1];
        Product[] products2 = new Product[1];
        products[0] = product;
        products2[0] = product1;
        // when add to cart is called 
        when(mockProductDAO.createProduct(product)).thenReturn(product);
        when(mockProductDAO.findProducts(product.getName())).thenReturn(products);
        when(mockCartDAO.addToCart(product, "Bob")).thenReturn(product);
        when(mockCartDAO.searchCart(product.getName(), "Bob")).thenReturn(products2);
        // invoke
        ResponseEntity<Product> response = cartController.addToCart(product, "Bob");
        // analysis
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(product, response.getBody());
    }
    @Test
    public void testAddtoCartNotFound() throws IOException {
        // setup
        Product product = new Product(6, "to Add product", 10);
        Product product1 = new Product(5, "Ka", 10);
        Product[] products = new Product[1];
        products[0] = product1;
        // when add to cart is called put the needed product is not in inventory
        when(mockProductDAO.createProduct(product)).thenReturn(product);
        when(mockProductDAO.findProducts(product.getName())).thenReturn(products);
        when(mockCartDAO.addToCart(product, "Bob")).thenReturn(product);

        // invoke
        ResponseEntity<Product> response = cartController.addToCart(product, "Bob");
        // analysis
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    public void testAddtoCartAlreadyIn() throws IOException {
        // setup
        Product product = new Product(6, "to Add product", 10);
        Product[] products = new Product[1];
        products[0] = product;
        // when add to cart is called put the needed product is already in cart
        when(mockProductDAO.createProduct(product)).thenReturn(product);
        when(mockProductDAO.findProducts(product.getName())).thenReturn(products);
        when(mockCartDAO.addToCart(product, "Bob")).thenReturn(product);
        //returns cart with item already inside
        when(mockCartDAO.searchCart(product.getName(), "Bob")).thenReturn(products);
        // invoke
        ResponseEntity<Product> response = cartController.addToCart(product, "Bob");
        // analysis
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    public void testAddToCartInternalError() throws IOException {
        // setup
        Product product = new Product(6, "to Add product", 10);
        String user = "Bob";
        doThrow(new IOException()).when(mockCartDAO).searchCart(product.getName(),user);
        Product[] products = new Product[1];
        products[0] = product;
        when(mockProductDAO.createProduct(product)).thenReturn(product);
        when(mockProductDAO.findProducts(product.getName())).thenReturn(products);

        // invoke
        ResponseEntity<Product> response = cartController.addToCart(product, user);
        // analysis
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetCartContents() throws IOException {
        // setup
        Product product = new Product(6, "to Add product", 10);
        Product product1 = new Product(5, "Ka", 10);
        Product[] products = new Product[2];
        products[0] = product;
        products[1] = product1;
        // when get Cart Contents is called
        when(mockCartDAO.getCartContents("Bob")).thenReturn(products);
        // invoke
        ResponseEntity<Product[]> response = cartController.getCartContents("Bob");
        // analysis
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(products, response.getBody());
    }
    @Test
    public void testGetCartContentsInternalError() throws IOException {
        // setup
        // when get cart contents is called and Internal Server Error
        doThrow(new IOException()).when(mockCartDAO).getCartContents("Bob");
        // invoke
        ResponseEntity<Product[]> response = cartController.getCartContents("Bob");
        // analysis
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetCartProduct() throws IOException {
        // setup
        Product product = new Product(6, "to Add product", 10);
        Product[] products = new Product[1];
        products[0] = product;
        // when get Cart product is called
        when(mockCartDAO.getCartProduct(6,"Bob")).thenReturn(product);
        // invoke
        ResponseEntity<Product> response = cartController.getCartProduct(6,"Bob");
        // analysis
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody());
    }

    @Test
    public void testGetCartProductNotFound() throws IOException {
        // setup
        Product product = new Product(6, "to Add product", 10);
        Product[] products = new Product[1];
        products[0] = product;
        // when get Cart product is called and not found
        when(mockCartDAO.getCartProduct(5,"Bob")).thenReturn(null);
        // invoke
        ResponseEntity<Product> response = cartController.getCartProduct(6,"Bob");
        // analysis
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    public void testGetCartProductInternalError() throws IOException {
        // setup
        // when get cart product is called and Internal Server Error
        doThrow(new IOException()).when(mockCartDAO).getCartProduct(1,"Bob");
        // invoke
        ResponseEntity<Product> response = cartController.getCartProduct(1,"Bob");
        // analysis
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testRemoveFromCart() throws IOException {
        // setup
        Product product = new Product(6, "to Add product", 10);
        Product[] products = new Product[1];
        products[0] = product;
        // when get Remove from cart is called
        when(mockCartDAO.getCartProduct(6,"Bob")).thenReturn(product);
        when(mockCartDAO.removeFromCart(6,"Bob")).thenReturn(true);
        // invoke
        ResponseEntity<Product> response = cartController.removeFromCart(6,"Bob");
        // analysis
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(null,response.getBody());
    }

    @Test
    public void testRemoveFromCartNotFound() throws IOException {
        // setup
        Product product = new Product(6, "to Add product", 10);
        Product[] products = new Product[1];
        products[0] = product;
        // when get Remove from cart is called and item not found
        when(mockCartDAO.getCartProduct(6,"Bob")).thenReturn(null);
        when(mockCartDAO.removeFromCart(5,"Bob")).thenReturn(false);
        // invoke
        ResponseEntity<Product> response = cartController.removeFromCart(6,"Bob");
        // analysis
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testRemoveFromCartInternalError() throws IOException {
        // setup
        doThrow(new IOException()).when(mockCartDAO).getCartProduct(1,"Bob");
        // invoke
        ResponseEntity<Product> response = cartController.removeFromCart(1,"Bob");
        // analysis
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testSearchProducts() throws IOException {
        // setup
        Product product = new Product(6, "Search", 10);
        Product[] products = new Product[1];
        products[0] = product;
        // when search cart is called
        when(mockCartDAO.searchCart("Sea","Bob")).thenReturn(products);
        // invoke
        ResponseEntity<Product[]> response = cartController.searchProducts("Sea","Bob");
        // analysis
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(products, response.getBody());
    }

    @Test
    public void testSearchProductsNotFound() throws IOException {
        // setup
        Product[] products = new Product[0];
        // when search cart is called and not found
        when(mockCartDAO.searchCart("Ka","Bob")).thenReturn(products);
        // invoke
        ResponseEntity<Product[]> response = cartController.searchProducts("Ka","Bob");
        // analysis
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testSearchCartInternalError() throws IOException {
        // setup
        doThrow(new IOException()).when(mockCartDAO).searchCart("Sea","Bob");
        // invoke
        ResponseEntity<Product[]> response = cartController.searchProducts("Sea","Bob");
        // analysis
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}