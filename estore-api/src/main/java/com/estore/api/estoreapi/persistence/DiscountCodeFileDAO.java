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

    /**
     ** {@inheritDoc}}
     */
    @Override
    public DiscountCode[] getDiscountCodes() throws IOException {
        synchronized (codes) {
            ArrayList<DiscountCode> discountCodeArrayList = new ArrayList<>();

            for (DiscountCode discountCode : codes.values()) {
                discountCodeArrayList.add(discountCode);
            }

            DiscountCode[] discountCodeArray = new DiscountCode[discountCodeArrayList.size()];
            discountCodeArrayList.toArray(discountCodeArray);
            return discountCodeArray;
        }
    }

    /**
     ** {@inheritDoc}}
     */
    @Override
    public DiscountCode findDiscountCode(String codeString) throws IOException {
        synchronized (codes) {
            for (DiscountCode discountCode : codes.values()) {
                if (discountCode.getCode().equals(codeString)) {
                    return discountCode;
                }
            }
        }
        return null;
    }

    /**
     ** {@inheritDoc}}
     */
    @Override
    public DiscountCode createDiscountCode(DiscountCode discountCode) throws IOException {
        synchronized (codes) {
            DiscountCode newDiscountCode = new DiscountCode(discountCode.getCode(), discountCode.getDiscountPercentage());
            codes.put(newDiscountCode.getCode(), newDiscountCode);
            save();
            return newDiscountCode;
        }
    }

    /**
     ** {@inheritDoc}}
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
