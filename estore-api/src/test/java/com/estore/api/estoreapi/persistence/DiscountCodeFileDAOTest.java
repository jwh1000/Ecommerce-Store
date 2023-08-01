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
                testCodes[1] = new DiscountCode("sh2", 20);
                testCodes[2] = new DiscountCode("ab2", 15);

                // When the object mapper is supposed to read from the file
                // the mock object mapper will return the product array above
                when(mockObjectMapper
                                .readValue(new File("doesnt_matter.txt"), DiscountCode[].class))
                                .thenReturn(testCodes);
                discountFileDAO = new DiscountCodeFileDAO("doesnt_matter.txt", mockObjectMapper);
        
        
        }

    
    /**
     * Tests removing a product from the cart
     */
    @Test
    public void testRemoveFromCart() {
        // invoke
        boolean result = assertDoesNotThrow(() -> discountFileDAO.deleteDiscountCode("ab1"),
                "Unexpected exception thrown");

        DiscountCode[] testCodeRem = Arrays.copyOf(testCodes, 0);

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
        boolean result = assertDoesNotThrow(() -> discountFileDAO.deleteDiscountCode("9k1"),
                "Unexpected exception thrown");

        // analyze
        DiscountCode[] results = new DiscountCode[1];
        DiscountCode res = assertDoesNotThrow(() -> discountFileDAO.findDiscountCode("user"),
                "Unexpected exception thrown");
        results[1] = res;
        
        assertArrayEquals(testCodes, results);
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
