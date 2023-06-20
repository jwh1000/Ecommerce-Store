package com.estore.api.estoreapi.persistence;

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
     * Before each test, setup the proper objects in memory such as the ProductFileDAO,
     * using the @BeforeEach JUnit annotation.
     * Uses a mock object mapper to avoid having to use an actual file.
     * @throws IOException
     */
    @BeforeEach
    public void setupProductFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testProduct = new Product[3];
        testProduct[0] = new Product("The Shining", 4.99, 1);
        testProduct[1] = new Product("1984", 8.50, 2);
        testProduct[2] = new Product("Pride and Prejudice", 2.99, 3);

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the product array above
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"),Product[].class))
                .thenReturn(testProduct);
        productFileDAO = new ProductFileDAO("doesnt_matter.txt",mockObjectMapper);
    }

    @Test
    public void testDeleteHero_valid() {
        //invoke
        boolean result = assertDoesNotThrow(() -> productFileDAO.deleteProduct(1),
                            "Unexpected exception thrown");
        //analyze
        assertEquals(result,true);
        assertEquals(productFileDAO.products.size(),testProduct.length-1);
    }

    @Test
    public void testDeleteHero_invalid_id() {
        //invoke
        boolean result = assertDoesNotThrow(() -> productFileDAO.deleteProduct(100),
                            "Unexpected exception thrown");
        //analyze
        assertEquals(result,false); //false as the delete fails
        assertEquals(productFileDAO.products.size(),testProduct.length);
    }
}
