package com.estore.api.estoreapi.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.persistence.ProductDAO;
import com.estore.api.estoreapi.persistence.PurchaseHistoryDAO;
import com.estore.api.estoreapi.model.User;

/**
 * Purchase history controller
 * 
 * @author Cole DenBleyker
 */
@RestController
@RequestMapping("estore")
public class PurchaseHistoryController {
    
    private PurchaseHistoryDAO purchasedDAO;
    private ProductDAO productDAO; // required for searching inventory

    /**
     * Creates a REST API controller to respond to HTTP requests
     * regarding a {@linkplain User user's} purchase history
     * 
     * @param purchasedDAO the DAO to perform CRUD operations on the purchase history
     * @param productDAO the DAO to interact with the {@linkplain Product product} inventory
     */
    public PurchaseHistoryController(PurchaseHistoryDAO purchasedDAO, ProductDAO productDAO) {
        this.purchasedDAO = purchasedDAO;
        this.productDAO = productDAO;
    }

    /**
     * adds a {@linkplain Product product} to a {@linkplain User user's} purchase history
     * checks if the {@linkplain Product product} is in the inventory
     *  and if it is already in the {@linkplain User user's} purchase history
     * 
     * @param product the {@linkplain Product product} to add to purchase history
     * @param username the {@linkplain User user} to add a {@linkplain Product product} to their purchase history
     * @return {@link ResponseEntity ResponseEntity} HTTP status of NOT_FOUND if product doesn't exist<br>
     *         {@link ResponseEntity ResponseEntity} HTTP status of CONFLICT if the product is in the cart already<br>
     *         {@link ResponseEntity ResponseEntity} HTTP status of CREATED on success<br>
     *         {@link ResponseEntity ResponseEntity} HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("/purchases/{username}")
    public ResponseEntity<Product> addToPurchased(@RequestBody Product product, @PathVariable String username) {
        try {
            purchasedDAO.updatePurchased(username);

            boolean found = false;

            // check if product exists in inventory
            Product[] products = productDAO.findProducts(product.getName());
            for(Product searchProduct : products) {
                if(searchProduct.getName().equals(product.getName())) {
                    found = true;
                    break;
                }
            }

            if(!found) {
                return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
            }

            // check if product is already in purchase history
            products = purchasedDAO.searchPurchases(product.getName(), username);
            for(Product searchProduct : products) {
                if(searchProduct.getName().equals(product.getName())) {
                    return new ResponseEntity<Product>(HttpStatus.CONFLICT);
                }
            }
            Product newProduct = purchasedDAO.addToPurchased(product, username);
            return new ResponseEntity<Product>(newProduct, HttpStatus.CREATED);

        } catch (IOException e) {
            return new ResponseEntity<Product>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Gets the contents of a {@linkplain User user's} purchase history
     * 
     * @param username the {@linkplain User user} who to get the purchase history contents of
     * @return {@link ResponseEntity ResponseEntity} HTTP status of OK on success<br>
     *         {@link ResponseEntity ResponseEntity} HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/purchases/{username}")
    public ResponseEntity<Product[]> getPurchasedContents(@PathVariable String username) {
        try {
            purchasedDAO.updatePurchased(username);

            Product[] products = purchasedDAO.getPurchasedProducts(username);
            return new ResponseEntity<Product[]>(products, HttpStatus.OK);

        } catch (IOException e) {
            return new ResponseEntity<Product[]>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Gets a single {@linkplain Product product} of a given id from a {@linkplain User user's} purchase history
     * 
     * @param id the id of the {@linkplain Product product} to get
     * @param username the {@linkplain User user} who to get the purchase history contents of
     * @return {@link ResponseEntity ResponseEntity} HTTP status of OK on success<br>
     *         {@link ResponseEntity ResponseEntity} HTTP status of NOT_FOUND if the product cannot be found<br>
     *         {@link ResponseEntity ResponseEntity} HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("purchases/{username}/{id}")
    public ResponseEntity<Product> getPurchasedProduct(@PathVariable int id, @PathVariable String username) {
        try {
            purchasedDAO.updatePurchased(username);

            Product product = purchasedDAO.getPurchasedProduct(id, username);
            if(product != null)
                return new ResponseEntity<Product>(product, HttpStatus.OK);
            else
                return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);

        } catch (IOException e) {
            return new ResponseEntity<Product>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a {@link Product product} from the given {@linkplain User user's} purchase history
     * 
     * @param id The id of the {@link Product product} to delete
     * @param username The username of the {@linkplain User user} whose purchase history you wish to delete a {@link Product product} from
     * @return {@link ResponseEntity ResponseEntity} HTTP status of OK if deleted<br>
     *         {@link ResponseEntity ResponseEntity} HTTP status of NOT_FOUND if not found<br>
     *         {@link ResponseEntity ResponseEntity} HTTP status of INTERNAL_SERVER_ERROR otherwise
     * 
     */
    @DeleteMapping("/purchases/{username}/{id}")
    public ResponseEntity<Product> removeFromPurchased(@PathVariable int id, @PathVariable String username) {
        try {
            purchasedDAO.updatePurchased(username);

            Product product = purchasedDAO.getPurchasedProduct(id, username);
            if(product != null) {
                purchasedDAO.removeFromPurchased(id, username);
                return new ResponseEntity<Product>(HttpStatus.OK);
            } else
                return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);

        } catch (IOException e) {
            return new ResponseEntity<Product>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Searches a {@linkplain User user's} purchase history with a given search term
     * 
     * @param name the search parameter
     * @param username the name of the {@linkplain User user} whose purchase history to search
     * @return {@link ResponseEntity ResponseEntity} HTTP status of OK on success<br>
     *         {@link ResponseEntity ResponseEntity} HTTP status of NOT_FOUND if the product cannot be found
     *         {@link ResponseEntity ResponseEntity} HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/purchases/{username}/")
    public ResponseEntity<Product[]> searchPurchased(@RequestParam String name, @PathVariable  String username) {
        try {
            purchasedDAO.updatePurchased(username);

            Product[] product = purchasedDAO.searchPurchases(name, username);
            if(product.length != 0)
                return new ResponseEntity<Product[]>(product, HttpStatus.OK);
            else
                return new ResponseEntity<Product[]>(HttpStatus.NOT_FOUND);

        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
