package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.estore.api.estoreapi.model.DiscountCode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DiscountCodeFileDAOTest {
    DiscountCodeFileDAO discountFileDAO;
    DiscountCode[] testCodes;
    ObjectMapper mockObjectMapper;

    @BeforeEach
        public void setupTestFileDAO() throws IOException {
                mockObjectMapper = mock(ObjectMapper.class);
                testCodes = new DiscountCode[3];
                testCodes[0] = new DiscountCode("ab1", 10);
                testCodes[1] = new DiscountCode("ab2", 15);
                testCodes[2] = new DiscountCode("sh2", 20);

                // When the object mapper is supposed to read from the file
                // the mock object mapper will return the product array above
                when(mockObjectMapper
                                .readValue(new File("doesnt_matter.txt"), DiscountCode[].class))
                                .thenReturn(testCodes);
                discountFileDAO = new DiscountCodeFileDAO("doesnt_matter.txt", mockObjectMapper);
        
        
        }

    /**
     * Tests retrieving all discount codes.
     */
    @Test
    public void testGetDiscountCodes() {
        // invoke
        DiscountCode[] result = assertDoesNotThrow(() -> discountFileDAO.getDiscountCodes(),
                        "Unexpected exception thrown");

        // analyze
        assertArrayEquals(testCodes, result);
        assertEquals(testCodes.length, result.length);
    }

    /**
     * Tests retrieving a specific discount code.
     */
    @Test
    public void testFindDiscountCode() {
        // setup
        String searchString = "ab1";

        // invoke
        DiscountCode result = assertDoesNotThrow(() -> discountFileDAO.findDiscountCode(searchString),
                        "Unexpected exception thrown");

        // analyze
        assertEquals(testCodes[0], result);
    }

    /**
     * Tests retrieving a specific discount code that doesn't exist.
     */
    @Test
    public void testFindDiscountCode_invalid() {
        // setup
        String searchString = "blah";

        // invoke
        DiscountCode result = assertDoesNotThrow(() -> discountFileDAO.findDiscountCode(searchString),
                        "Unexpected exception thrown");

        // analyze
        assertNotEquals(testCodes[0], result);
        assertNotEquals(testCodes[1], result);
        assertNotEquals(testCodes[2], result);
    }

    /**
     * Tests creating a discount code.
     */
    @Test
    public void testCreateDiscountCode() {
        // setup
        testCodes[2] = new DiscountCode("NewCode", 50);

        // invoke
        DiscountCode result = assertDoesNotThrow(() -> discountFileDAO.createDiscountCode(testCodes[2]),
                        "Unexpected exception thrown");

        // analyze
        assertEquals(testCodes[2].toString(), result.toString());
    }

    /**
     * Tests removing a product from the cart
     */
    @Test
    public void testDeleteDiscount() {
        // invoke
        boolean result = assertDoesNotThrow(() -> discountFileDAO.deleteDiscountCode("sh2"),
                "Unexpected exception thrown");

        DiscountCode[] testCodeRem = Arrays.copyOf(testCodes, 2);

        // analyze
        DiscountCode[] results = assertDoesNotThrow(() -> discountFileDAO.getDiscountCodes(),
                "Unexpected exception thrown");

        assertArrayEquals(testCodeRem, results);
        assertEquals(true, result);
    }

    /**
     * Tests deleting code that doesnt exist
     */
    @Test
    public void testDeleteCodeNotFound() {
        // invoke
        DiscountCode[] empty = new DiscountCode[1];
        boolean result = assertDoesNotThrow(() -> discountFileDAO.deleteDiscountCode("9k1"),
                "Unexpected exception thrown");

        // analyze
        DiscountCode[] results = new DiscountCode[1];
        DiscountCode res = assertDoesNotThrow(() -> discountFileDAO.findDiscountCode("9k1"),
              "Unexpected exception thrown");
        results[0] = res;
        
        assertArrayEquals(empty, results);
        assertEquals(false, result);

    }

        /**
         * Tests to when it fails
         * 
         */
        @Test
        public void testDeleteProductInvalidId() {
                // invoke
                boolean result = assertDoesNotThrow(() -> discountFileDAO.deleteDiscountCode("9k1"),
                                "Unexpected exception thrown");
                // analyze
                assertEquals(result, false); // false as the delete fails
                assertEquals(discountFileDAO.codes.size(), testCodes.length);
        }
}
