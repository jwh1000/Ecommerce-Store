package com.estore.api.estoreapi.persistence;

import java.io.IOException;

import com.estore.api.estoreapi.model.DiscountCode;

/**
 * DAO interface for discount code persistence.
 * 
 * @author Rylan Arbour
 */
public interface DiscountCodeDAO {
    /**
     * Returns all {@linkplain DiscountCode discount codes}
     * @return An array of all {@link DiscountCode discount codes}
     * @throws IOException if an issue with underlying storage
     */
    DiscountCode[] getDiscountCodes() throws IOException;

    /**
     * Finds all {@linkplain DiscountCode discount codes} whose name contain the text
     * @param containsText The text to compare to
     * @return An array of {@link DiscountCode discount codes} whoes names contain the text
     * @throws IOException if an issue with underlying storage
     */
    DiscountCode[] findDiscountCodes(String containsText) throws IOException;

    /**
     * Creates and saves a {@linkplain DiscountCode discount code}
     * @param product {@linkplain DiscountCode discount code} object to be created
     * @return new {@link DiscountCode discount code} if successful, fail otherwise
     * @throws IOException if there is issue with underlying storage
     */
    DiscountCode createDiscountCode(DiscountCode discountCode) throws IOException;

    /**
     * Deletes a discount code with a given id.
     * @param code The code (of which a discount code can be uniquely identified) of the discount code to delete.
     * @return Boolean of whether or not the discount code deletion was successful.
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteProduct(String code) throws IOException;
}
