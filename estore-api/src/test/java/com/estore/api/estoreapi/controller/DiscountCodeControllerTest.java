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


/**
 * Tests for the DiscountCodeController class.
 * 
 * @author Rylan Arbour, Ryan Robison
 */
public class DiscountCodeControllerTest {
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

    /*
     * Tests creating a discount code.
     */
    @Test
    public void testCreateDiscountCode() throws IOException {  
        // Setup
        DiscountCode discountCode = new DiscountCode("Test", 10);
        // when create discount code is called, creates discount code
        when(mockDiscountDAO.createDiscountCode(discountCode)).thenReturn(discountCode);
        when(mockDiscountDAO.findDiscountCode(discountCode.getCode())).thenReturn(null);

        // Invoke
        ResponseEntity<DiscountCode> response = discountController.createDiscountCode(discountCode);

        // Analyze
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(discountCode,response.getBody());
    }

    /**
     * Tests creating a discount code that fails as there is a conflict (already exists)
     * @throws IOException
     */
    @Test
    public void testCreateDiscountCodeFail() throws IOException { 
        DiscountCode discountCode = new DiscountCode("Test", 10);
        // when create discount code is called but there is conflict aka discount code exists
        when(mockDiscountDAO.createDiscountCode(discountCode)).thenReturn(null);
        when(mockDiscountDAO.findDiscountCode(discountCode.getCode())).thenReturn(discountCode);
        // Invoke
        ResponseEntity<DiscountCode> response = discountController.createDiscountCode(discountCode);

        // Analyze
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }

    /**
     * Tests creating a discount code where some kind of internal error is encountered.
     * @throws IOException
     */
    @Test
    public void testCreateDiscountCodeInternalError() throws IOException {  
        // Setup
        DiscountCode discountCode = new DiscountCode("Test", 10);
        // when create discount code is called, an internal server error is thrown
        doThrow(new IOException()).when(mockDiscountDAO).findDiscountCode(discountCode.getCode());
    
        // Invoke
        ResponseEntity<DiscountCode> response = discountController.createDiscountCode(discountCode);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
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

    /**
     * Tests getting all discount codes.
     * @throws IOException
     */
    @Test
    public void testGetDiscountCodes() throws IOException { 
        // Setup
        DiscountCode[] codes = new DiscountCode[2];
        codes[0] = new DiscountCode("Test", 10);
        codes[1] = new DiscountCode("Test2", 5);
        // When get discount codes is called returns books above
        when(mockDiscountDAO.getDiscountCodes()).thenReturn(codes);

        // Invoke
        ResponseEntity<DiscountCode[]> response = discountController.getDiscountCodes();

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(codes,response.getBody());
    }

    /**
     * Tests an internal error occuring while getting all discount codes.
     * @throws IOException
     */
    @Test
    public void testGetDiscountCodesInternalError() throws IOException {  
        // Setup
        //When get discount codes is called returns internal error
        doThrow(new IOException()).when(mockDiscountDAO).getDiscountCodes();
    
        // Invoke
        ResponseEntity<DiscountCode[]> response = discountController.getDiscountCodes();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    /**
     * Tests getting a single discount code.
     * @throws IOException
     */
    @Test
    public void testGetDiscountCode() throws IOException { 
        // Setup
        DiscountCode[] discountCodes = new DiscountCode[2];
        discountCodes[0] = new DiscountCode("Test", 10);
        discountCodes[1] = new DiscountCode("Test2", 5);
        // When get discount code is called returns specified discount code above
        when(mockDiscountDAO.findDiscountCode("Test")).thenReturn(discountCodes[0]);

        // Invoke
        ResponseEntity<DiscountCode> response = discountController.getDiscountCode("Test");

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(discountCodes[0],response.getBody());
    }

    /**
     * Tests getting a discount code when that discount code doesn't exist.
     * @throws IOException
     */
    @Test
    public void testGetDiscountCodeNotFound() throws IOException { 
        // Setup
        DiscountCode[] discountCodes = new DiscountCode[2];
        discountCodes[0] = new DiscountCode("Test", 10);
        discountCodes[1] = new DiscountCode("Test2", 5);
        // When get discount code is called returns null as discount code wanted is not there
        when(mockDiscountDAO.findDiscountCode("Blah")).thenReturn(null);

        // Invoke
        ResponseEntity<DiscountCode> response = discountController.getDiscountCode("Blah");

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
        assertEquals(null,response.getBody());
    }

    /**
     * Tests an internal error being thrown while getting a discount code
     * @throws IOException
     */
    @Test
    public void testGetDiscountCodeInternalError() throws IOException {  
        // Setup
        //When get discount code is called returns internal error
        doThrow(new IOException()).when(mockDiscountDAO).findDiscountCode("Blah");
    
        // Invoke
        ResponseEntity<DiscountCode> response = discountController.getDiscountCode("Blah");

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
}
