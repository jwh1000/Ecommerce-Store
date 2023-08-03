package com.estore.api.estoreapi.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
     * Creates a {@linkplain DiscountCode discount code} with a given discount code object.
     * 
     * @param discountCode the {@link DiscountCode discount code} to create
     * 
     * @return a ResponseEntity with the new discount code and an OK status on success
     *         or a ResponseEntity with an internal server error
     */
    @PostMapping("/discount-code/codes")
    public ResponseEntity<DiscountCode> createDiscountCode(@RequestBody DiscountCode discountCode) {
        try {
            DiscountCode searchDiscountCode = discountDAO.findDiscountCode(discountCode.getCode());

            if (searchDiscountCode != null && searchDiscountCode.equals(discountCode)) {
                return new ResponseEntity<DiscountCode>(HttpStatus.CONFLICT);
            }

            DiscountCode newDiscountCode = discountDAO.createDiscountCode(discountCode);
            return new ResponseEntity<DiscountCode>(newDiscountCode, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<DiscountCode>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Deletes a discount code with the given code.
     * 
     * @param code code of the discount code to delete.
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     *         ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/discount-code/")
    public ResponseEntity<DiscountCode> deleteDiscountCode(@RequestParam String code) {
        try {
            if (discountDAO.findDiscountCode(code) == null) // check to see if the discount code with given code doesn't exist
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); // return 404
            else { // otherwise go ahead and delete it
                discountDAO.deleteDiscountCode(code);
                return new ResponseEntity<DiscountCode>(HttpStatus.OK); // return 200 (OK)
            }
        } catch (IOException e) { // catch any error and return internal server error
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Gets single discount code
     * 
     * @param id
     * 
     * @return ResponseEntity HTTP status of OK if item is found and returned
     *         ResponseEntity with HTTP status of NOT_FOUND if not found
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * @author Ryan Robison
     */
    @GetMapping("/discount-code/")
    public ResponseEntity<DiscountCode> getDiscountCode(@RequestParam String code) {
        try {
            DiscountCode co = discountDAO.findDiscountCode(code);
            if (co != null)
                return new ResponseEntity<DiscountCode>(co, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            return new ResponseEntity<DiscountCode>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Gets all discount codes.
     * 
     * @return ResponseEntity HTTP status of OK when complete list is returned
     *         Returned list contains all discount codes or an empty list if none
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/discount-code/codes")
    public ResponseEntity<DiscountCode[]> getDiscountCodes() {
        try {
            DiscountCode[] codes = discountDAO.getDiscountCodes();
            return new ResponseEntity<DiscountCode[]>(codes, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<DiscountCode[]>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
