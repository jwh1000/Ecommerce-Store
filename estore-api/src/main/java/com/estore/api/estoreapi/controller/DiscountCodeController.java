package com.estore.api.estoreapi.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.estore.api.estoreapi.model.DiscountCode;
import com.estore.api.estoreapi.persistence.DiscountCodeDAO;

/**
 * Handles the REST API requests for the the estore in regards to discount codes.
 * @author Rylan Arbour
 */
@RestController
@RequestMapping("estore")
public class DiscountCodeController {

    private DiscountCodeDAO discountDAO;

    /**
     * creates REST API controller for http requests for dicount codes
     * 
     * @param discountDAO the DAO to perform the operations 
     */

     public DiscountCodeController(DiscountCodeDAO discountDAO){
        this.discountDAO = discountDAO;
    }


    /**
     * Deletes a product with the given ID.
     * 
     * @param code code of the product to delete.
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     *         ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/discount-code/{code}")
    public ResponseEntity<DiscountCode> deleteProduct(@RequestParam String code) {
        try {
            if (discountDAO.findDiscountCode(code) == null) // check to see if the product with given id doesn't exist
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); // return 404
            else { // otherwise go ahead and delete it
                discountDAO.deleteDiscountCode(code);
                return new ResponseEntity<DiscountCode>(HttpStatus.OK); // return 200 (OK)
            }
        } catch (IOException e) { // catch any error and return internal server error
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
