package se.albert.personalfinanceguidb.services;

import se.albert.personalfinanceguidb.models.User;

import java.util.UUID;

public class AuthService {

    private static UUID userID;
    private static User currentUser;

    public static UUID getuserID(){
        return userID;
    }

    public static void setUserID(UUID userID){
        AuthService.userID = userID;
    }

    public static User getCurrentUser(){
        return currentUser;
    }

    public static void setCurrentUser(User user){
        currentUser = user;
    }

}
