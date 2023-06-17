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

public class ProductFileDAO implements ProductDAO{
    
    Map<Integer, Product> products; // local cache of products so you don't
                                    // need to read file every time

    private ObjectMapper objectMapper; // allows for conversion from product
                                       // object and JSON file

    private static int nextId; // the next id to use when making a new product

    private String filename; // the file name to read and write to

    /**
     * Creates a product file DAO
     * 
     * @param filename the name of the file to read and write to
     * @param objectMapper allows for serialization/deserialization
     * 
     * @throws IOException throw if the file cannot be accessed
     */
    public ProductFileDAO(@Value("${products.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }

    
    private boolean save() throws IOException {
        Product[] productArray = getProductArray();
    }


    private boolean load() throws IOException {
        // not done!
    }


    public Product createProduct(Product product) throws IOException {
        // not done!
    }

    
}
