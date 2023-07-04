package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import com.estore.api.estoreapi.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;

@Tag("Persistence-tier")
public class CartFileDAOTest {
    CartFileDAO cartFileDAO;
    Product[] testProducts;
    ObjectMapper mockObjectMapper;

    /**
     * Set up an example cart with some products in it
     * @throws IOException
     */
    @BeforeEach
    public void setupCartFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);

        testProducts = new Product[3];
        testProducts[0] = new Product(0, "Goodnight PunPun", 8.99);
        testProducts[1] = new Product(1, "The Way of Kings", 18.99);
        testProducts[2] = new Product(2, "Infnite Jest", 15.99);

        when(mockObjectMapper
              .readValue(new File("estore-api/data/carts/user.json"), Product[].class))
                .thenReturn(testProducts);
        
        cartFileDAO = new CartFileDAO("estore-api/data/carts/", mockObjectMapper);
        cartFileDAO.updateCart("user");
    }

    @Test
    public void testGetCartContentsSuccess() {
        Product[] result = assertDoesNotThrow(() -> cartFileDAO.getCartContents("user"),
                  "Unexpected exception thrown");
    
        assertArrayEquals(testProducts, result);
        assertEquals(testProducts.length, cartFileDAO.products.size());
    }

    @Test
    public void testGetCartContentsEmpty() {
        Product[] empty = new Product[0];

        assertDoesNotThrow(() -> when(mockObjectMapper
              .readValue(new File("estore-api/data/carts/user.json"), Product[].class))
                .thenReturn(empty));

        assertDoesNotThrow(() -> cartFileDAO.updateCart("user"));

        Product[] result = assertDoesNotThrow(() -> cartFileDAO.getCartContents("user"),
                  "Unexpected exception thrown");

        assertArrayEquals(empty, result);
        assertEquals(empty.length, cartFileDAO.products.size());
    }

    @Test
    public void testGetCartProductSuccess() {
        Product result1 = assertDoesNotThrow(() -> cartFileDAO.getCartProduct(0, "user"),
                  "Unexpected exception thrown");

        Product result2 = assertDoesNotThrow(() -> cartFileDAO.getCartProduct(1, "user"),
                  "Unexpected exception thrown");

        Product result3 = assertDoesNotThrow(() -> cartFileDAO.getCartProduct(2, "user"),
                  "Unexpected exception thrown");

        assertEquals(testProducts[0], result1);
        assertEquals(testProducts[1], result2);
        assertEquals(testProducts[2], result3);
    }

    @Test
    public void testGetCartProductNotFound() {
        Product notFound = assertDoesNotThrow(() -> cartFileDAO.getCartProduct(5, "user"),
                  "Unexpected exception thrown");
        
        assertEquals(null, notFound);
    }

    @Test
    public void testSearchCart() {
        Product[] result1 = assertDoesNotThrow(() -> cartFileDAO.searchCart("Good", "user"),
                  "Unexpected exception thrown");

        Product[] result2 = assertDoesNotThrow(() -> cartFileDAO.searchCart("ni", "user"),
                  "Unexpected exception thrown");

        Product[] search1 = new Product[1];
        search1[0] = testProducts[0];

        Product[] search2 = new Product[2];
        search2[0] = testProducts[0];
        search2[1] = testProducts[2];
        
        assertArrayEquals(search1, result1);
        assertArrayEquals(search2, result2);
    }

    @Test
    public void testAddToCart() {
        Product add = new Product(3, "Moby Dick", 9.99);

        Product result = assertDoesNotThrow(() -> cartFileDAO.addToCart(add, "user"), 
                "Unexpected exception thrown");

        Product[] testProductsAdd = Arrays.copyOf(testProducts, 4);
        testProductsAdd[3] = result;

        Product[] results2 = assertDoesNotThrow(() -> cartFileDAO.getCartContents("user"),
                  "Unexpected exception thrown");

        assertArrayEquals(testProductsAdd, results2);
    }

    @Test
    public void testRemoveFromCart() {
        boolean result = assertDoesNotThrow(() -> cartFileDAO.removeFromCart(2, "user"), 
                "Unexpected exception thrown");

        Product[] testProductsRem = Arrays.copyOf(testProducts, 2);

        Product[] results = assertDoesNotThrow(() -> cartFileDAO.getCartContents("user"),
                  "Unexpected exception thrown");

        assertArrayEquals(testProductsRem, results);
        assertEquals(true, result);
    }

    @Test
    public void testRemoveFromCartNotFound() {
        boolean result = assertDoesNotThrow(() -> cartFileDAO.removeFromCart(5, "user"), 
                "Unexpected exception thrown");

        Product[] results = assertDoesNotThrow(() -> cartFileDAO.getCartContents("user"),
                  "Unexpected exception thrown");

        assertArrayEquals(testProducts, results);
        assertEquals(false, result);

    }

}