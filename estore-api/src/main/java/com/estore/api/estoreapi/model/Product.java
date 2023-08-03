package com.estore.api.estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class to represent a Product in our EStore.
 * Each product has a name, price, description, id, and quantity.
 * 
 * @author Jack Hunsberger, Cole DenBleyker, Ryan Robison, Rylan Arbour, Xin Huang
 */
public class Product {
    /**
     * The name of the product.
     */
    @JsonProperty("name")
    private String name;
    /**
     * The price of the product.
     */
    @JsonProperty("price")
    private double price;
    /**
     * The description of the product.
     * Defaults to "Lorem Ipsum".
     */
    @JsonProperty("description")
    private String description;
    /**
     * The id of the product.
     */
    @JsonProperty("id")
    private int id;
    /**
     * The amount of this product the owner has available to sell.
     * Defaults to 0.
     */
    @JsonProperty("quantity")
    private int quantity;

    /**
     * Creates a product with the given name, price, and ID.
     * 
     * @param name  The name of the product
     * @param price The price of the product
     * @param id    The id of the product
     */
    public Product(@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("price") double price) {
        this.name = name;
        this.price = price;
        this.id = id;

        // these fields can be changed from the admin panel
        this.quantity = 1;
        this.description = "Lorem Ipsum";
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    // Mutators for all fields, so that an Owner will have an easier time editing
    // any particular product.
    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean purchase() {
        this.quantity -= 1;
        if (this.quantity < 0) {
            this.quantity = 0;
            return false;
        } else {
            return true;
        }
    }

    // String representation of product, for tests/debugging
    @Override
    public String toString() {
        return "Product ID " + this.id + ": " + this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Product)) {
            return false;
        }

        Product product = (Product) o;

        if (this.getId() == product.getId() && 
            this.getName() == product.getName() && 
            this.getPrice() == product.getPrice() &&
            this.getDescription() == product.getDescription() &&
            this.getQuantity() == product.getQuantity()) {
                
            return true;
            
        } else {
            return false;
        }
    }
}
