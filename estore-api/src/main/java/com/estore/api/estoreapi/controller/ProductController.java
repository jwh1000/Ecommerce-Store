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
 * @author JacK Hunsberger (when you edit this, add your name here)
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
    @GetMapping("/inventory/product/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id){
        try{
            Product product = productDAO.getProduct(id);
            if(product != null)
                return new ResponseEntity<Product>(product,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e){
            return new ResponseEntity<Product>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
