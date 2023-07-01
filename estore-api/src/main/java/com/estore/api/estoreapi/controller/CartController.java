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
import com.estore.api.estoreapi.persistence.CartDAO;
import com.estore.api.estoreapi.persistence.ProductDAO;

@RestController
@RequestMapping("estore")
public class CartController {
    //TODO: Documentation

    private CartDAO cartDAO;
    private ProductDAO productDAO;

    public CartController(CartDAO cartDAO, ProductDAO productDAO) {
        this.cartDAO = cartDAO;
        this.productDAO = productDAO;
    }

    @PostMapping("/carts/{username}/product")
    public ResponseEntity<Product> addToCart(@RequestBody Product product, @PathVariable String username) {
        try {
            
            // check if in inventory
            Product[] products = productDAO.findProducts(product.getName());
            for (Product searchProduct : products) {
                if (searchProduct.getName().equals(product.getName())) {
                    break;
                } else {
                    return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
                }
            }

            // check if already in cart
            products = cartDAO.searchCart(product.getName(), username);
            for (Product searchProduct : products) {
                if (searchProduct.getName().equals(product.getName())) {
                    return new ResponseEntity<Product>(HttpStatus.CONFLICT);
                }
            }
            Product newProduct = cartDAO.addToCart(product, username);
            return new ResponseEntity<Product>(newProduct, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<Product>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

    @GetMapping("/carts/{username}")
    public ResponseEntity<Product[]> getCartContents(@PathVariable String username) {
        try {
            Product[] products = cartDAO.getCartContents(username);
            return new ResponseEntity<Product[]>(products, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<Product[]>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/carts/{username}/product/{id}")
    public ResponseEntity<Product> getCartProduct(@PathVariable int id, @PathVariable String username) {
        try {
            Product product = cartDAO.getCartProduct(id, username);
            if (product != null)
                return new ResponseEntity<Product>(product, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            return new ResponseEntity<Product>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //TODO: pretty much just copy this guy from the product controller
    @DeleteMapping("/carts/{username}/product/{id}")
    public ResponseEntity<Product> removeFromCart(@PathVariable int id, @PathVariable String username) {
        return null;
    }

    @GetMapping("/carts/{username}/")
    public ResponseEntity<Product[]> searchProducts(@RequestParam String name, @PathVariable String username) {
        try {
            Product[] product = cartDAO.searchCart(name, username);
            if (product.length != 0)
                return new ResponseEntity<Product[]>(product, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




}

