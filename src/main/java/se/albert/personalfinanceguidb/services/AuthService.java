package se.albert.personalfinanceguidb.services;

import java.util.UUID;

public class AuthService {

    private static UUID userID;

    public static UUID getuserID(){
        return userID;
    }

    public static void setUserID(UUID userID){
        AuthService.userID = userID;
    }

}
