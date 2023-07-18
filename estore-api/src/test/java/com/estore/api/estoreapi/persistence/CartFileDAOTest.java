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

/**
 * Junit test suite for CartFileDAO
 * 
 * @author Jack Hunsberger
 */
@Tag("Persistence-tier")
public class CartFileDAOTest {
    CartFileDAO cartFileDAO;
    Product[] testProducts;
    ObjectMapper mockObjectMapper;

    /**
     * creates mock cart full of products and changes the current cart to be user's
     * tells object mapper to produce the mock list when writing values
     */
    @BeforeEach
    public void setupCartFileDAO() throws IOException {
        // setup
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

    /**
     * tests when getting the contents of the cart is a success
     */
    @Test
    public void testGetCartContentsSuccess() {
        // invoke
        Product[] result = assertDoesNotThrow(() -> cartFileDAO.getCartContents("user"),
                "Unexpected exception thrown");

        // analyze
        assertArrayEquals(testProducts, result);
        assertEquals(testProducts.length, cartFileDAO.products.size());
    }

    /**
     * tests when getting the contents of the cart when it is empty
     */
    @Test
    public void testGetCartContentsEmpty() {
        // setup
        Product[] empty = new Product[0];

        assertDoesNotThrow(() -> when(mockObjectMapper
                .readValue(new File("estore-api/data/carts/user.json"), Product[].class))
                .thenReturn(empty));
        
        assertDoesNotThrow(() -> cartFileDAO.updateCart("user"));

        // invoke
        Product[] result = assertDoesNotThrow(() -> cartFileDAO.getCartContents("user"),
                "Unexpected exception thrown");

        // analyze
        assertArrayEquals(empty, result);
        assertEquals(empty.length, cartFileDAO.products.size());
    }

    /**
     * tests when getting a certain product from the cart
     */
    @Test
    public void testGetCartProductSuccess() {
        // invoke
        Product result1 = assertDoesNotThrow(() -> cartFileDAO.getCartProduct(0, "user"),
                "Unexpected exception thrown");

        Product result2 = assertDoesNotThrow(() -> cartFileDAO.getCartProduct(1, "user"),
                "Unexpected exception thrown");

        Product result3 = assertDoesNotThrow(() -> cartFileDAO.getCartProduct(2, "user"),
                "Unexpected exception thrown");

        // analyze
        assertEquals(testProducts[0], result1);
        assertEquals(testProducts[1], result2);
        assertEquals(testProducts[2], result3);
    }

    /**
     * tests when the product cannot be found when getting a product
     */
    @Test
    public void testGetCartProductNotFound() {
        // invoke
        Product notFound = assertDoesNotThrow(() -> cartFileDAO.getCartProduct(5, "user"),
                "Unexpected exception thrown");

        // analyze
        assertEquals(null, notFound);
    }

    /**
     * tests searching a cart with a search term
     */
    @Test
    public void testSearchCart() {
        // invoke
        Product[] result1 = assertDoesNotThrow(() -> cartFileDAO.searchCart("Good", "user"),
                "Unexpected exception thrown");

        Product[] result2 = assertDoesNotThrow(() -> cartFileDAO.searchCart("ni", "user"),
                "Unexpected exception thrown");

        // setup
        Product[] search1 = new Product[1];
        search1[0] = testProducts[0];

        Product[] search2 = new Product[2];
        search2[0] = testProducts[0];
        search2[1] = testProducts[2];

        // analyze
        assertArrayEquals(search1, result1);
        assertArrayEquals(search2, result2);
    }

    /**
     * Tests adding a product to the cart
     */
    @Test
    public void testAddToCart() {
        // setup
        Product add = new Product(3, "Moby Dick", 9.99);

        // invoke
        Product result = assertDoesNotThrow(() -> cartFileDAO.addToCart(add, "user"),
                "Unexpected exception thrown");

        Product[] testProductsAdd = Arrays.copyOf(testProducts, 4);
        testProductsAdd[3] = result;

        // analyze
        Product[] results2 = assertDoesNotThrow(() -> cartFileDAO.getCartContents("user"),
                "Unexpected exception thrown");

        assertArrayEquals(testProductsAdd, results2);
    }

    /**
     * Tests removing a product from the cart
     */
    @Test
    public void testRemoveFromCart() {
        // invoke
        boolean result = assertDoesNotThrow(() -> cartFileDAO.removeFromCart(2, "user"),
                "Unexpected exception thrown");

        Product[] testProductsRem = Arrays.copyOf(testProducts, 2);

        // analyze
        Product[] results = assertDoesNotThrow(() -> cartFileDAO.getCartContents("user"),
                "Unexpected exception thrown");

        assertArrayEquals(testProductsRem, results);
        assertEquals(true, result);
    }

    /**
     * Tests removing a product from the cart that is not currently in it
     */
    @Test
    public void testRemoveFromCartNotFound() {
        // invoke
        boolean result = assertDoesNotThrow(() -> cartFileDAO.removeFromCart(5, "user"),
                "Unexpected exception thrown");

        // analyze
        Product[] results = assertDoesNotThrow(() -> cartFileDAO.getCartContents("user"),
                "Unexpected exception thrown");
        
        assertArrayEquals(testProducts, results);
        assertEquals(false, result);

    }

}
