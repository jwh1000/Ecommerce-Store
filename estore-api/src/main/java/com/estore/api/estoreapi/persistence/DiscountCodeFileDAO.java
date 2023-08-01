package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.DiscountCode;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Implements JSON file persistence for discount codes.
 * @author Ryan Robison
 * @author Rylan Arbour
 */
@Component
public class DiscountCodeFileDAO implements DiscountCodeDAO {

    Map<String, DiscountCode> codes; // local cache of codes so you don't
                                    // need to read file every time

    private ObjectMapper objectMapper; // allows for conversion from code
                                       // object and JSON file

    private String filename; // the file name to read and write to

    /**
     * Creates a discount code file DAO
     * 
     * @param filename     the name of the file to read and write to
     * @param objectMapper allows for serialization/deserialization
     * 
     * @throws IOException throw if the file cannot be accessed
     */
    public DiscountCodeFileDAO(@Value("${codes.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }

    @Override
    public DiscountCode[] getDiscountCodes() throws IOException {
        synchronized(codes){
        ArrayList<DiscountCode> discountArrayList = new ArrayList<>();
        for(DiscountCode code : codes.values()){
            discountArrayList.add(code);
        }
        DiscountCode[] discountArray = new DiscountCode[discountArrayList.size()];
        discountArrayList.toArray(discountArray);
        return discountArray;
        }

    }

    @Override
    public DiscountCode findDiscountCode(String text) throws IOException {
        synchronized(codes){
            for(DiscountCode code : codes.values()){
                if(text == null || code.getCode().equals(text)){
                    return code;
                }
            }
            return null;
        }
    }

    @Override
    public DiscountCode createDiscountCode(DiscountCode discountCode) throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createDiscountCode'");
    }

    /**
     * Deletes a product give code
     * @param id The id of the product to delete.
     * @return Boolean of whether or not the product deletion was successful.
     * @throws IOException if underlying storage cannot be accessed
     * @author Ryan Robison
     */

    @Override
    public boolean deleteDiscountCode(String code) throws IOException {
        synchronized(codes){
            if(codes.containsKey(code)){
                codes.remove(code);
                return save();
            } else{
                return false;
            }
        }
    }

    /**
     * loads {@linkplain DiscountCode codes} as JSON objects into java objects
     * 
     * @return true if successful
     * @throws IOException if the file cannot be accessed
     */
    private boolean load() throws IOException {
        codes = new TreeMap<>();

        DiscountCode[] codeArray = objectMapper.readValue(new File(filename), DiscountCode[].class);

        for (DiscountCode code : codeArray) {
            codes.put(code.getCode(), code);
        }
        return true;
    }

    /**
     * saves the {@linkplain DiscountCode codes} from the map as JSON objects
     * 
     * @return true on success
     * @throws IOException if the file cannot be accessed
     */
    private boolean save() throws IOException {
        DiscountCode[] codeArray = getDiscountCodes();

        objectMapper.writeValue(new File(filename), codeArray);
        return true;
    }
    
}
