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

    private CartDAO cartDAO;
    private ProductDAO productDAO; // needed for searching the inventory

    /**
     * Creates a REST API controller to respond to http requests regarding
     * a user's shopping cart
     * 
     * @param cartDAO the DAO to perform CRUD operations on cart
     * @param productDAO the DAO to interact with the product inventory
     * 
     * @author Jack Hunsberger, Cole DenBleyker
     */
    public CartController(CartDAO cartDAO, ProductDAO productDAO) {
        this.cartDAO = cartDAO;
        this.productDAO = productDAO;
    }

    /**
     * adds a {@linkplain Product product} to a user's shopping cart
     * checks if the product is in the inventory and if the product is already
     * in the user's cart
     * 
     * @param product the product to add to the cart
     * @param username the user whose cart to add the product to
     * @return {@link ResponseEntity ResponseEntity} HTTP status of NOT_FOUND if product doesn't exist<br>
     *         {@link ResponseEntity ResponseEntity} HTTP status of CONFLICT if the product is in the cart already<br>
     *         {@link ResponseEntity ResponseEntity} HTTP status of CREATED on success<br>
     *         {@link ResponseEntity ResponseEntity} HTTP status of INTERNAL_SERVER_ERROR otherwise
     * 
     * @author Jack Hunsberger
     */
    @PostMapping("/carts/{username}/product")
    public ResponseEntity<Product> addToCart(@RequestBody Product product, @PathVariable String username) {
        try {
            cartDAO.updateCart(username);

            boolean found = false;
            
            // check if in inventory
            Product[] products = productDAO.findProducts(product.getName());
            for (Product searchProduct : products) {
                if (searchProduct.getName().equals(product.getName())) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
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

    /**
     * Gets the contents of a user's shopping cart
     * 
     * @param username the user whose cart to get the contents of
     * @return {@link ResponseEntity ResponseEntity} HTTP status of OK on success<br>
     *         {@link ResponseEntity ResponseEntity} HTTP status of INTERNAL_SERVER_ERROR otherwise
     * 
     * @author Jack Hunsberger
     */
    @GetMapping("/carts/{username}")
    public ResponseEntity<Product[]> getCartContents(@PathVariable String username) {
        try {
            cartDAO.updateCart(username);

            Product[] products = cartDAO.getCartContents(username);
            return new ResponseEntity<Product[]>(products, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<Product[]>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * gets a single {@linkplain Product product} of a given id from a user's cart
     * 
     * @param id the id of the product to get
     * @param username the name of the user whose cart to search 
     * @return {@link ResponseEntity ResponseEntity} HTTP status of OK on success<br>
     *         {@link ResponseEntity ResponseEntity} HTTP status of NOT_FOUND if the product cannot be found<br>
     *         {@link ResponseEntity ResponseEntity} HTTP status of INTERNAL_SERVER_ERROR otherwise
     * 
     * @author Jack Hunsberger
     */
    @GetMapping("/carts/{username}/product/{id}")
    public ResponseEntity<Product> getCartProduct(@PathVariable int id, @PathVariable String username) {
        try {
            cartDAO.updateCart(username);

            Product product = cartDAO.getCartProduct(id, username);
            if (product != null)
                return new ResponseEntity<Product>(product, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            return new ResponseEntity<Product>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a {@link Product product} from the given user's shopping cart
     * 
     * @param id The id of the {@link Product product} to delete
     * @param username The username of the user whose shopping cart you wish to remove a {@link Product product} from
     * @return {@link ResponseEntity ResponseEntity} HTTP status of OK if deleted<br>
     *         {@link ResponseEntity ResponseEntity} HTTP status of NOT_FOUND if not found<br>
     *         {@link ResponseEntity ResponseEntity} HTTP status of INTERNAL_SERVER_ERROR otherwise
     * 
     * @author Cole DenBleyker
     */
    @DeleteMapping("/carts/{username}/product/{id}")
    public ResponseEntity<Product> removeFromCart(@PathVariable int id, @PathVariable String username) {
        try {
            cartDAO.updateCart(username);

            Product product = cartDAO.getCartProduct(id, username);
            if (product != null) {
                cartDAO.removeFromCart(id, username);
                return new ResponseEntity<Product>(HttpStatus.OK);
            } else
                return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            return new ResponseEntity<Product>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes everything in the cart
     * 
     * @param username The username of the user whose shopping cart you wish to remove a {@link Product product} from
     * @return {@link ResponseEntity ResponseEntity} HTTP status of OK if deleted<br>
     *         {@link ResponseEntity ResponseEntity} HTTP status of INTERNAL_SERVER_ERROR otherwise
     * 
     * @author Jack Hunsberger
     */
    @DeleteMapping("/carts/{username}")
    public ResponseEntity<Product> clearCart(@PathVariable String username) {
        try {
            cartDAO.updateCart(username);
            cartDAO.clearCart(username);
            return new ResponseEntity<Product>(HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<Product>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * searches a users cart with a given search term
     * 
     * @param name the search parameter
     * @param username the name of the user whose cart to search
     * @return {@link ResponseEntity ResponseEntity} HTTP status of OK on success<br>
     *         {@link ResponseEntity ResponseEntity} HTTP status of NOT_FOUND if the product cannot be found
     *         {@link ResponseEntity ResponseEntity} HTTP status of INTERNAL_SERVER_ERROR otherwise
     * 
     * @author Jack Hunsberger
     */
    @GetMapping("/carts/{username}//")
    public ResponseEntity<Product[]> searchProducts(@RequestParam String name, @PathVariable String username) {
        try {
            cartDAO.updateCart(username);

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