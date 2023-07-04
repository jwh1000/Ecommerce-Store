package com.estore.api.estoreapi.persistence;

import com.estore.api.estoreapi.model.User;
import java.io.IOException;
import com.estore.api.estoreapi.model.Product;

public class UserAuthentication {

    public User createUser(User user) throws IOException{
        // synchronized (users) {
            User newUser = new User(user.getUsername());

            return newUser;
        }
    // Concept idea for authentication might need a controller honestly
    // public static void authentication(String args[]){
    // String username
    // Scanner s = new Scanner(System.in);
    // System.out.print("Enter username:")
    // username = s.nextLine();
    // equals will either have to run through a json pair till true or there is none left not sure how it will work yet                   
    // if(username.equals(){
    //  System.out.println("Authentication succeeded!!! Enjoy Shopping.");
    //} else {
    // System.out.println("Authentication failled. Please try again")
    //
    //}
}


