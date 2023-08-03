package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for DiscountCode class.
 * 
 * @author Rylan Arbour
 */
@Tag("Model-tier")
public class DiscountCodeTest {
    @Test
    public void test_Constructor() {
        // setup
        String expected_code = "deal";
        int expected_discountPercentage = 15;

        // invoke
        DiscountCode discountCode = new DiscountCode(expected_code, expected_discountPercentage);

        // analyze
        assertEquals(expected_code, discountCode.getCode());
        assertEquals(expected_discountPercentage, discountCode.getDiscountPercentage());
    }

    @Test
    public void test_toString() {
        // setup
        String code = "deal";
        int discountPercentage = 15;
        String expected_string = "Discount code: \"deal\" for 15% off";
        DiscountCode discountCode = new DiscountCode(code, discountPercentage);

        // invoke
        String result_string = discountCode.toString();

        // analyze
        assertEquals(expected_string, result_string);
    }

    @Test
    public void test_equals_valid() {
        // setup
        String code1 = "deal";
        String code2 = "deal";
        DiscountCode discountCode1 = new DiscountCode(code1, 15);
        DiscountCode discountCode2 = new DiscountCode(code2, 20);

        // invoke and analyze
        assertEquals(discountCode1, discountCode2);
    }

    @Test
    public void test_equals_invalid() {
        // setup
        String code1 = "deal";
        String code2 = "deal2";
        DiscountCode discountCode1 = new DiscountCode(code1, 20);
        DiscountCode discountCode2 = new DiscountCode(code2, 20);

        // invoke and analyze
        assertNotEquals(discountCode1, discountCode2);
    }
}
