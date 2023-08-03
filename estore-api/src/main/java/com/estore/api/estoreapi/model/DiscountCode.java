package com.estore.api.estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model class to represent a discount code.
 * 
 * @author Rylan Arbour
 */
public class DiscountCode {
    /**
     * The code which a user will enter to get a discount on their purchase.
     */
    @JsonProperty("code")
    private String code;

    /**
     * The amount in a percentage that the code will discount a user's purchase upon applying the code.
     */
    @JsonProperty("discountPercentage")
    private int discountPercentage;

    /**
     * Creates a discount code with a given code and discount percentage.
     * @param code The code a user will enter.
     * @param discountPercentage The percentage off of the discount.
     */
    public DiscountCode(@JsonProperty("code") String code, @JsonProperty("discountPercentage") int discountPercentage) {
        this.code = code;
        this.discountPercentage = discountPercentage;
    }

    public String getCode() {
        return code;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    // String representation of code, for tests/debugging
    @Override
    public String toString() {
        return "Discount code: \"" + this.code + "\" for " + this.discountPercentage + "% off";
    }

    //A discount code equals another if they have the same 'code'.
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof DiscountCode)) {
            return false;
        }

        DiscountCode discountCode = (DiscountCode) o;

        if (this.code.equals(discountCode.code)) {
            return true;
        } else {
            return false;
        }
    }
}
