package com.estore.api.estoreapi.persistence;

import java.io.IOException;
import com.estore.api.estoreapi.model.Product;

public interface ProductDAO {
    /**
     * Creates and saves a {@linkplain Product product}
     * 
     * @param product {@linkplain Product product} object to be created
     * 
     * @return new {@link Product product} if successful, fail otherwise
     * 
     * @throws IOException if there is issue with underlying storage
     */
    Product creaProduct(Product product) throws IOException;
}
