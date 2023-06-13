package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for Product class.
 * 
 * @author Rylan Arbour
 */
public class ProductTest {
    @Test
    public void test_Constructor() {
        //setup
        int expected_id = 100;
        double expected_price = 5.99;
        String expected_name = "House of Leaves";
        String expected_description = "Lorem Ipsum";
        int expected_quantity = 0;

        //invoke
        Product product = new Product(expected_name, expected_price, expected_id);

        //analyze
        assertEquals(expected_id, product.getId());
        assertEquals(expected_name, product.getName());
        assertEquals(expected_price, product.getPrice());
        assertEquals(expected_description, product.getDescription());
        assertEquals(expected_quantity, product.getQuantity());
    }

    @Test
    public void test_toString() {
        //setup
        int id = 100;
        double price = 5.99;
        String name = "House of Leaves";
        Product product = new Product(name, price, id);
        String expected_string = "Product ID 100: House of Leaves";

        //invoke
        String actual_string = product.toString();

        //analyze
        assertEquals(expected_string, actual_string);
    }
}
