package com.estore.api.estoreapi.persistence;

import com.estore.api.estoreapi.model.User;
import java.io.IOException;
import com.estore.api.estoreapi.persistence.ProductDAO;

public class UserAuthentication {

    public void admin(User user) {
        String admin = "admin";
        if (user.getUsername().equals(admin)){
            user.setAdmin("yes");  
        }

    } 

//    public void authentication(String username) {
        // 
//        if(username.equals()){
//           System.out.println("Authentication succeeded!!! Enjoy Shopping."); 
//        }
//        else {
//            System.out.println("Authentication failled. Please try again")
//        }
//    }


}


