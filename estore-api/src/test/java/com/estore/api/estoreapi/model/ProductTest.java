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
        // setup
        int expected_id = 100;
        double expected_price = 5.99;
        String expected_name = "House of Leaves";
        String expected_description = "Lorem Ipsum";
        int expected_quantity = 1;

        // invoke
        Product product = new Product(expected_id, expected_name, expected_price);

        // analyze
        assertEquals(expected_id, product.getId());
        assertEquals(expected_name, product.getName());
        assertEquals(expected_price, product.getPrice());
        assertEquals(expected_description, product.getDescription());
        assertEquals(expected_quantity, product.getQuantity());
    }

    @Test
    public void test_toString() {
        // setup
        int id = 100;
        double price = 5.99;
        String name = "House of Leaves";
        Product product = new Product(id, name, price);
        String expected_string = "Product ID 100: House of Leaves";

        // invoke
        String actual_string = product.toString();

        // analyze
        assertEquals(expected_string, actual_string);
    }

    @Test
    public void test_equals_valid_same_details() {
        // setup
        int id = 100;
        double price = 5.99;
        String name = "House of Leaves";
        Product product = new Product(id, name, price);
        int id2 = 100;
        double price2 = 5.99;
        String name2 = "House of Leaves";
        Product product2 = new Product(id2, name2, price2);
        boolean expected = true;

        // invoke
        boolean result = product.equals(product2);

        // analyze
        assertEquals(expected, result);
    }

    @Test
    public void test_equals_valid_same_instance() {
        // setup
        int id = 100;
        double price = 5.99;
        String name = "House of Leaves";
        Product product = new Product(id, name, price);
        boolean expected = true;

        // invoke
        boolean result = product.equals(product);

        // analyze
        assertEquals(expected, result);
    }

    @Test
    public void test_equals_invalid_not_a_product() {
        // setup
        int id = 100;
        double price = 5.99;
        String name = "House of Leaves";
        Product product = new Product(id, name, price);
        Object obj = new Object();
        boolean expected = false;

        // invoke
        boolean result = product.equals(obj);

        // analyze
        assertEquals(expected, result);
    }

    @Test
    public void test_equals_invalid_id() {
        // setup
        int id = 100;
        double price = 5.99;
        String name = "House of Leaves";
        Product product = new Product(id, name, price);
        int id2 = 101;
        double price2 = 5.99;
        String name2 = "House of Leaves";
        Product product2 = new Product(id2, name2, price2);
        boolean expected = false;

        // invoke
        boolean result = product.equals(product2);

        // analyze
        assertEquals(expected, result);
    }

    @Test
    public void test_equals_invalid_price() {
        // setup
        int id = 100;
        double price = 5.99;
        String name = "House of Leaves";
        Product product = new Product(id, name, price);
        int id2 = 100;
        double price2 = 100.99;
        String name2 = "House of Leaves";
        Product product2 = new Product(id2, name2, price2);
        boolean expected = false;

        // invoke
        boolean result = product.equals(product2);

        // analyze
        assertEquals(expected, result);
    }

    @Test
    public void test_equals_invalid_name() {
        // setup
        int id = 100;
        double price = 5.99;
        String name = "House of Leaves";
        Product product = new Product(id, name, price);
        int id2 = 100;
        double price2 = 5.99;
        String name2 = "1984";
        Product product2 = new Product(id2, name2, price2);
        boolean expected = false;

        // invoke
        boolean result = product.equals(product2);

        // analyze
        assertEquals(expected, result);
    }

    @Test
    public void test_equals_invalid_quantity() {
        // setup
        int id = 100;
        double price = 5.99;
        String name = "House of Leaves";
        Product product = new Product(id, name, price);
        Product product2 = new Product(id, name, price);
        product2.setQuantity(1000);
        boolean expected = false;

        // invoke
        boolean result = product.equals(product2);

        // analyze
        assertEquals(expected, result);
    }

    @Test
    public void test_equals_invalid_description() {
        // setup
        int id = 100;
        double price = 5.99;
        String name = "House of Leaves";
        Product product = new Product(id, name, price);
        Product product2 = new Product(id, name, price);
        product2.setDescription("Blargh.");
        boolean expected = false;

        // invoke
        boolean result = product.equals(product2);

        // analyze
        assertEquals(expected, result);
    }
}
