package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.estore.api.estoreapi.model.DiscountCode;
import com.estore.api.estoreapi.persistence.DiscountCodeDAO;



public class DiscountCodeContorllerTest {
    private DiscountCodeController discountController;
    private DiscountCodeDAO mockDiscountDAO;

    /**
     * Before each test, create a new DiscountController object and inject a mock
     * Discount DAO
     */
    @BeforeEach
    public void setupDiscountController() {
        mockDiscountDAO = mock(DiscountCodeDAO.class);
        discountController = new DiscountCodeController(mockDiscountDAO);
    }

    /**
     * Tests deleting a discount.
     * @throws IOException
     */
    @Test
    public void testDeleteDiscountCode() throws IOException {
        // Setup
        DiscountCode code = new DiscountCode("ab2", 10);
        mockDiscountDAO.createDiscountCode(code);
        //when deleteProduct is called, return true simulating successful delete and save
        String codesearch = "ab2";
        when(mockDiscountDAO.findDiscountCode(codesearch)).thenReturn(code);
        when(mockDiscountDAO.deleteDiscountCode("ab2")).thenReturn(true);

        // Invoke
        ResponseEntity<DiscountCode> response = discountController.deleteDiscountCode(codesearch);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /**
     * Tests deleting a discount that isnt there.
     * @throws IOException
     */
    @Test
    public void testDeleteDiscountNotFound() throws IOException {
        // Setup
        DiscountCode code = new DiscountCode("ab2", 10);
        mockDiscountDAO.createDiscountCode(code);
        //when deleteProduct is called, return true simulating successful delete and save
        String codesearch = "ab2";
        when(mockDiscountDAO.findDiscountCode(codesearch)).thenReturn(code);
        when(mockDiscountDAO.deleteDiscountCode("sb1")).thenReturn(false);

        // Invoke
        ResponseEntity<DiscountCode> response = discountController.deleteDiscountCode("sb1");

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Tests deleting a discount that isnt there.
     * @throws IOException
     */
    @Test
    public void testDeleteDiscountException() throws IOException {
        // Setup
        DiscountCode code = new DiscountCode("ab2", 10);
        mockDiscountDAO.createDiscountCode(code);
        //when deleteProduct is called, return true simulating successful delete and save
        String codesearch = "ab2";
        when(mockDiscountDAO.findDiscountCode(codesearch)).thenReturn(code);
        doThrow(new IOException()).when(mockDiscountDAO).deleteDiscountCode(codesearch);


        // Invoke
        ResponseEntity<DiscountCode> response = discountController.deleteDiscountCode(codesearch);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
