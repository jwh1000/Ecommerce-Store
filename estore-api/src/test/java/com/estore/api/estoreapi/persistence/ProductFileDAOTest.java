package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.estore.api.estoreapi.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProductFileDAOTest {
        ProductFileDAO productFileDAO;
        Product[] testProduct;
        ObjectMapper mockObjectMapper;

        /**
         * Before each test, setup the proper objects in memory such as the
         * ProductFileDAO,
         * using the @BeforeEach JUnit annotation.
         * Uses a mock object mapper to avoid having to use an actual file.
         * 
         * @throws IOException
         */
        @BeforeEach
        public void setupProductFileDAO() throws IOException {
                mockObjectMapper = mock(ObjectMapper.class);
                testProduct = new Product[3];
                testProduct[0] = new Product(1, "The Shining", 4.99);
                testProduct[1] = new Product(2, "1984", 8.50);
                testProduct[2] = new Product(3, "Pride and Prejudice", 2.99);

                // When the object mapper is supposed to read from the file
                // the mock object mapper will return the product array above
                when(mockObjectMapper
                                .readValue(new File("doesnt_matter.txt"), Product[].class))
                                .thenReturn(testProduct);
                productFileDAO = new ProductFileDAO("doesnt_matter.txt", mockObjectMapper);
        
        
        }

        /**
         * Tests the getProducts function on an inventory with a few valid products
         * 
         * @author Jack Hunsberger
         */
        @Test
        public void testGetProducts() {
                // invoke
                Product[] result = assertDoesNotThrow(() -> productFileDAO.getProducts(),
                                "Unexpected exception thrown");

                // analyze
                assertArrayEquals(testProduct, result);
                assertEquals(testProduct.length, result.length);

        }

        /**
         * Tests searching the inventory for 1 item and more than 1 item
         * 
         * @author Jack Hunsberger
         */
        @Test
        public void testFindProducts() {
                // setup
                Product[] testProductSearch1 = new Product[1];
                testProductSearch1[0] = testProduct[1];

                Product[] testProductSearch2 = new Product[2];
                testProductSearch2[0] = testProduct[0];
                testProductSearch2[1] = testProduct[2];

                // invoke
                Product[] result1 = assertDoesNotThrow(() -> productFileDAO.findProducts("84"),
                                "Unexpected exception thrown");

                Product[] result2 = assertDoesNotThrow(() -> productFileDAO.findProducts("i"),
                                "Unexpected exception thrown");

                // analyze
                assertArrayEquals(testProductSearch1, result1);
                assertArrayEquals(testProductSearch2, result2);
        }

        /**
         * Tests getting a product with a given id from an inventory of valid items
         * 
         * @author Jack Hunsberger
         */
        @Test
        public void testGetProduct() {
                // invoke
                Product result1 = assertDoesNotThrow(() -> productFileDAO.getProduct(2),
                                "Unexpected exception thrown");

                Product result2 = assertDoesNotThrow(() -> productFileDAO.getProduct(3),
                                "Unexpected exception thrown");
                // analyze
                assertEquals(testProduct[1], result1);
                assertEquals(testProduct[2], result2);
        }

        /**
         * Tests not getting a product with a given id due to it not existing giving
         * back a null
         * 
         * @author Xin Huang
         */
        @Test
        public void testGetProductNotFound() {
                // invoke
                Product result = assertDoesNotThrow(() -> productFileDAO.getProduct(4),
                                "Unexpected exception thrown");
                // analyze
                assertEquals(result, null); // null as the get product should return null
        }

        /**
         * Tests creating a new product
         * 
         * @author Xin Huang
         */
        @Test
        public void testCreateProduct() {
                // setup   
                testProduct[2] = new Product(4, "The Game of Thrones", 8.99);    
                // invoke
                Product result = assertDoesNotThrow(() -> productFileDAO.createProduct(testProduct[2]),
                "Unexpected exception thrown");
                // analyze
                assertEquals(testProduct[2].toString(), result.toString());

        }

        /**
         * Tests updates an already existing product.
         * 
         * @author Xin Huang
         */
        @Test
        public void testUpdateProduct() {
                // invoke
                Product result = assertDoesNotThrow(() -> productFileDAO.updateProduct(testProduct[1]),
                                "Unexpected exception thrown");
                // analyze
                assertEquals(result, testProduct[1]);

        }

        /**
         * Tests giving null back due to product not existing
         */
        @Test
        public void testUpdateProductNotFound() {
                // setup
                Product[] testProduct3 = new Product[1];
                testProduct3[0] = new Product(4, "A Song of Ice And Fire", 59.99);

                // invoke
                Product result = assertDoesNotThrow(() -> productFileDAO.updateProduct(testProduct3[0]),
                                "Unexpected exception thrown");
                // analyze
                assertEquals(result, null);
        }

        /**
         * Tests to see if the deletion of a product works
         * 
         * @author Jack Jack Hunsberger
         */
        @Test
        public void testProducteroValid() {
                // invoke
                boolean result = assertDoesNotThrow(() -> productFileDAO.deleteProduct(1),
                                "Unexpected exception thrown");
                // analyze
                assertEquals(result, true);
                assertEquals(productFileDAO.products.size(), testProduct.length - 1);
        }

        /**
         * Tests to see how the scenario if a deletetion fails
         * 
         * @author Jack Hunsberger
         */
        @Test
        public void testDeleteProductInvalidId() {
                // invoke
                boolean result = assertDoesNotThrow(() -> productFileDAO.deleteProduct(100),
                                "Unexpected exception thrown");
                // analyze
                assertEquals(result, false); // false as the delete fails
                assertEquals(productFileDAO.products.size(), testProduct.length);
        }
}
