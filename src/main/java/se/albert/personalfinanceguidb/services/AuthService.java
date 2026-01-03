package se.albert.personalfinanceguidb.services;

import se.albert.personalfinanceguidb.models.User;

import java.util.UUID;

public class AuthService {

    // Deklarerar userId
    private static UUID userID;

    // Deklarerar den nuvarande användaren
    private static User currentUser;

    // Hämta userId
    public static UUID getuserID(){
        return userID;
    }

    // Sätt userId
    public static void setUserID(UUID userID){
        AuthService.userID = userID;
    }

    // Hämta nuvarande användaren
    public static User getCurrentUser(){
        return currentUser;
    }

    // Sätt nuvarande användaren
    public static void setCurrentUser(User user){
        currentUser = user;
    }

}
