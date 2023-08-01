package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.estore.api.estoreapi.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Unit tests for the PurchaseHistoryFileDAO class
 * 
 * @author Cole DenBleyker
 */
@Tag("Persistence-tier")
public class PurchaseHistoryFileDAOTest {
    PurchaseHistoryFileDAO purchasedFileDAO;
    Product[] testProducts;
    ObjectMapper mockObjectMapper;


    /**
     * Creates mock purchase history with products and changes the current cart to be user's
     * tells object mapper to product the mock list when writing values
     */
    @BeforeEach
    public void setupPurchaseHistoryFileDAO() throws IOException {
        // setup
        mockObjectMapper = mock(ObjectMapper.class);

        testProducts = new Product[3];
        testProducts[0] = new Product(0, "Akira", 29.99);
        testProducts[1] = new Product(1, "Neon Genesis Evangelion", 21.99);
        testProducts[2] = new Product(2, "Monster", 19.99);

        when(mockObjectMapper
            .readValue(new File("estore-api/data/purchases/user.json"), Product[].class))
            .thenReturn(testProducts);

        purchasedFileDAO = new PurchaseHistoryFileDAO("estore-api/data/purchases/", mockObjectMapper);
        purchasedFileDAO.updatePurchased("user");
    }

    @Test
    public void testGetPurchasedContentsSuccess() {
        // invoke
        Product[] result = assertDoesNotThrow(() -> purchasedFileDAO.getPurchasedProducts("user"),
                    "Unexpected exception thrown");
        // analyze
        assertArrayEquals(testProducts, result);
        assertEquals(testProducts.length, purchasedFileDAO.products.size());
    }

    @Test
    public void testGetPurchasedContentsEmpty() {
        // setup
        Product[] empty = new Product[0];

        assertDoesNotThrow(() -> when(mockObjectMapper
                    .readValue(new File("estore-api/data/purchases/user.json"), Product[].class))
                    .thenReturn(empty));

        assertDoesNotThrow(() -> purchasedFileDAO.updatePurchased("user"));

        // invoke
        Product[] result = assertDoesNotThrow(() -> purchasedFileDAO.getPurchasedProducts("user"),
                    "Unexpected exception thrown");

        // analyze
        assertArrayEquals(empty, result);
        assertEquals(empty.length, purchasedFileDAO.products.size());
    }

    @Test
    public void testGetPurchasedProductSuccess() {
        // invoke
        Product result1 = assertDoesNotThrow(() -> purchasedFileDAO.getPurchasedProduct(0, "user"),
                    "unexpected exception thrown");
        Product result2 = assertDoesNotThrow(() -> purchasedFileDAO.getPurchasedProduct(1, "user"),
                    "unexpected exception thrown");
        Product result3 = assertDoesNotThrow(() -> purchasedFileDAO.getPurchasedProduct(2, "user"),
                    "unexpected exception thrown");
        // analyze
        assertEquals(testProducts[0], result1);
        assertEquals(testProducts[1], result2);
        assertEquals(testProducts[2], result3);
    }

    @Test
    public void testGetPurchasedProductNotFound() {
        // invoke
        Product notFound = assertDoesNotThrow(() -> purchasedFileDAO.getPurchasedProduct(6123, "user"),
                    "Unexpected exception thrown");
        // analyze
        assertEquals(null, notFound);
    }

    @Test
    public void testSearchPurchased() {
        // setup
        Product[] result1 = assertDoesNotThrow(() -> purchasedFileDAO.searchPurchases("Mo", "user"),
        "Unexpected exception thrown");
        Product[] result2 = assertDoesNotThrow(() -> purchasedFileDAO.searchPurchases("i", "user"),
        "Unexpected exception thrown");
        Product[] search1 = new Product[1];
        // invoke
        search1[0] = testProducts[2];
        Product[] search2 = new Product[2];
        search2[0] = testProducts[0];
        search2[1] = testProducts[1];
        // analysis
        assertArrayEquals(search1, result1);
        assertArrayEquals(search2, result2);
    }

    @Test
    public void testAddToPurchased() {
        // setup
        Product add = new Product(3, "Vagabond", 24.99);
        // invoke
        Product result = assertDoesNotThrow(() -> purchasedFileDAO.addToPurchased(add, "user"),
                    "Unexpected exception thrown");

        Product[] testProductsAdd = Arrays.copyOf(testProducts, 4);
        testProductsAdd[3] = result;
        // analysis
        Product[] results2 = assertDoesNotThrow(() -> purchasedFileDAO.getPurchasedProducts("user"),
                    "Unexpected exception thrown");

        assertArrayEquals(testProductsAdd, results2);
    }

    @Test
    public void testRemoveFromPurchased() {
        // setup
        boolean result = assertDoesNotThrow(() -> purchasedFileDAO.removeFromPurchased(2, "user"),
                    "Unexpected exception thrown");

        Product[] testProductRemaining = Arrays.copyOf(testProducts, 2);
        // invoke
        Product[] results = assertDoesNotThrow(() -> purchasedFileDAO.getPurchasedProducts("user"),
                    "Unexpected exception thrown");
        // analysis
        assertArrayEquals(testProductRemaining, results);
        assertEquals(true, result);
    }

    @Test
    public void testRemoveFromPurchasedNotFound() {
        // setup
        boolean result = assertDoesNotThrow(() -> purchasedFileDAO.removeFromPurchased(754, "user"),
                    "Unexpected exception thrown");
        // invoke
        Product[] results = assertDoesNotThrow(() -> purchasedFileDAO.getPurchasedProducts("user"),
                    "Unexpected exception thrown");
        // analysis
        assertArrayEquals(testProducts, results);
        assertEquals(false, result);
    }
}
