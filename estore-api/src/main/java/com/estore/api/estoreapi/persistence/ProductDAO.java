package com.estore.api.estoreapi.persistence;

import java.io.IOException;
import com.estore.api.estoreapi.model.Product;

public interface ProductDAO {


    Product[] getProducts() throws IOException;

    Product[] findProducts(String containsText) throws IOException;

    Product[] getProduct(int id) throws IOException;

    /**
     * Creates and saves a {@linkplain Product product}
     * 
     * @param product {@linkplain Product product} object to be created
     * 
     * @return new {@link Product product} if successful, fail otherwise
     * 
     * @throws IOException if there is issue with underlying storage
     */
    Product createProduct(Product product) throws IOException;

    Product updateProduct(Product product) throws IOException;

    boolean deleteProduct(int id) throws IOException;
}
