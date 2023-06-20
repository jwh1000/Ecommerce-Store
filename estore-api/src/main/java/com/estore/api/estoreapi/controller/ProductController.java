package com.estore.api.estoreapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import com.estore.api.estoreapi.persistence.ProductDAO;
import com.estore.api.estoreapi.model.Product;


/**
 * Handles the REST API requests for the the estore
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST
 * API
 * method handler to the Spring framework
 * 
 * @author Jack Hunsberger, Cole DenBleyker (when you edit this, add your name here)
 */
@RestController
@RequestMapping("estore")
public class ProductController {

    private ProductDAO productDAO;

    /**
     * Creates a REST API controller to reponds to http requests
     * 
     * @param productDAO the DAO to perform the basic CRUD operations
     *                   this is injected by spring
     */
    public ProductController(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    /**
     * creates a {@linkplain Product product} with a given product object
     * 
     * @param product the {@link Product product} to create
     * 
     * @return a ResponseEntity with the new product and an OK status on success
     *         or a ResponseEntity with an internal server error 
     */
    @PostMapping("/inventory/product")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        try {
            //TODO search if the product exists or not, if it does return status CONFLICT

            Product newProduct = productDAO.createProduct(product);
            return new ResponseEntity<Product>(newProduct, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<Product>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/inventory")
    public ResponseEntity<Product[]> getProducts(){
        try{
            Product[] products = productDAO.getProducts();
            return new ResponseEntity<Product[]>(products,HttpStatus.OK);
        }
        catch(IOException e){
            return new ResponseEntity<Product[]>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
    @PutMapping("/inventory/product")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product){
        try{
            Product newProduct = productDAO.updateProduct(product);
            if(newProduct != null)
                return new ResponseEntity<Product>(newProduct, HttpStatus.OK);
            else
                return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e){
            return new ResponseEntity<Product>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a product with the given ID.
     * 
     * @param id The id of the product to delete.
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * @author Rylan Arbour
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable int id) {
        try {
            if (productDAO.getProduct(id) == null) //check to see if the product with given id doesn't exist
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); //return 404
            else { //otherwise go ahead and delete it
                productDAO.deleteProduct(id);
                return new ResponseEntity<Product>(HttpStatus.OK); //return 200 (OK)
            }
        }
        catch(IOException e) { //catch any error and return internal server error
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain Product products} whose name contains
     * the text in name
     * 
     * @param name The name parameter which contains the text used to find the {@link Product products}
     * 
     * @return ResponseEntity with array of {@link Products product} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * <p>
     * Example: Find all products that contain the text "ma"
     * GET http://localhost:8080/inventory/?name=ma
     */

    @GetMapping("/")
    public ResponseEntity<Product[]> searchProducts(@RequestParam String name) {
        try {
            Product[] product = productDAO.findProducts(name); 
        if (product != null)
            return new ResponseEntity<Product[]>(product,HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }    
    }
}
