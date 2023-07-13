package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import com.estore.api.estoreapi.model.Product;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Implements JSON file persistence for Products.
 * 
 * @author Rylan Arbour (Add your name!)
 */
@Component
public class ProductFileDAO implements ProductDAO {

    Map<Integer, Product> products; // local cache of products so you don't
                                    // need to read file every time

    private ObjectMapper objectMapper; // allows for conversion from product
                                       // object and JSON file

    private static int nextId; // the next id to use when making a new product

    private String filename; // the file name to read and write to

    /**
     * Creates a product file DAO
     * 
     * @param filename     the name of the file to read and write to
     * @param objectMapper allows for serialization/deserialization
     * 
     * @throws IOException throw if the file cannot be accessed
     */
    public ProductFileDAO(@Value("${products.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }

    /**
     * Generates the next id for a new {@linkplain Product product}
     * 
     * @return the next id to use
     */
    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    /**
     * Generates an array of {@linkplain Product products} from tree map
     * Runs if empty, otherwise uses below implementation
     * 
     * @return The array of {@linkplain Product products}
     */
    private Product[] getProductArray() {
        return getProductArray(null);
    }

    /**
     * Creates an array of {@linkplain Product products} from tree map for any
     * {@linkplain Product products} that have containsText in their name
     * 
     * If containsText is null, return every {@linkplain Product product} in the map
     * 
     * @param containsText
     * @return
     */
    private Product[] getProductArray(String containsText) {
        ArrayList<Product> productArrayList = new ArrayList<>();

        for (Product product : products.values()) {
            if (containsText == null || product.getName().contains(containsText)) {
                productArrayList.add(product);
            }
        }

        Product[] productArray = new Product[productArrayList.size()];
        productArrayList.toArray(productArray);
        return productArray;
    }

    /**
     * saves the {@linkplain Product products} from the map as JSON objects
     * 
     * @return true on success
     * @throws IOException if the file cannot be accessed
     */
    private boolean save() throws IOException {
        Product[] productArray = getProductArray();

        objectMapper.writeValue(new File(filename), productArray);
        return true;
    }

    /**
     * loads {@linkplain Product products} as JSON objects into java objects
     * 
     * @return true if successful
     * @throws IOException if the file cannot be accessed
     */
    private boolean load() throws IOException {
        products = new TreeMap<>();
        nextId = 0;

        Product[] productArray = objectMapper.readValue(new File(filename), Product[].class);

        for (Product product : productArray) {
            products.put(product.getId(), product);
            if (product.getId() > nextId)
                nextId = product.getId();
        }

        ++nextId;
        return true;
    }

    /**
     ** {@inheritDoc}}
     */
    @Override
    public Product createProduct(Product product) throws IOException {
        synchronized (products) {
            Product newProduct = new Product(nextId(), product.getName(), product.getPrice());
            products.put(newProduct.getId(), newProduct);
            save();
            return newProduct;
        }
    }

    /**
     ** {@inheritDoc}
     */
    public Product[] getProducts() throws IOException {
        synchronized (products) {
            return getProductArray();
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public Product[] findProducts(String containsText) {
        synchronized (products) {
            return getProductArray(containsText);
        }
    }

    /**
     ** {@inheritDoc}
     */
    public Product getProduct(int id) throws IOException {
        synchronized (products) {
            if (products.containsKey(id))
                return products.get(id);
            else
                return null;
        }
    }

    /**
     * Updates and saves a {@linkplain Product product}
     * 
     * @param {@link Product product} object to be updated and saved
     * @return the updated {@link Product product} if successful, null if
     *         {@link Product product} could not be found
     * @throws IOException if underlying storage cannot be accessed
     */
    @Override
    public Product updateProduct(Product product) throws IOException {
        synchronized (products) {
            if (products.containsKey(product.getId()) == false)
                return null; // product does not exist

            products.put(product.getId(), product);
            save(); // may throw IOException
            return product;
        }
    }

    /**
     * Deletes a product with a given id.
     * 
     * @param id The id of the product to delete.
     * @return Boolean of whether or not the product deletion was successful.
     * @throws IOException if underlying storage cannot be accessed
     * @author Rylan Arbour
     */
    @Override
    public boolean deleteProduct(int id) throws IOException {
        synchronized (products) {
            if (products.containsKey(id)) {
                products.remove(id);
                return save();
            } else {
                return false;
            }
        }
    }
}
