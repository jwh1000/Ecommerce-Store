package com.estore.api.estoreapi.persistence;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.DiscountCode;

/**
 * Implements JSON file persistence for discount codes.
 * 
 * @author Rylan Arbour
 */
@Component
public class DiscountCodeFileDAO implements DiscountCodeDAO {

    @Override
    public DiscountCode[] getDiscountCodes() throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDiscountCodes'");
    }

    @Override
    public DiscountCode[] findDiscountCodes(String containsText) throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findDiscountCodes'");
    }

    @Override
    public DiscountCode createDiscountCode(DiscountCode discountCode) throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createDiscountCode'");
    }

    @Override
    public boolean deleteProduct(String code) throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteProduct'");
    }
    
}
