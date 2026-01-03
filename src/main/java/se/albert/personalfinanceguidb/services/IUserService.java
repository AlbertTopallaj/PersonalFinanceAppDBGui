package se.albert.personalfinanceguidb.services;

import se.albert.personalfinanceguidb.models.User;

import java.util.Optional;
import java.util.UUID;

public interface IUserService {

    // Skapa användaren
    User createUser(String username, String password) throws Exception;

    // Kontrollera inloggningsuppgifter
    Optional<User> checkUserLogin(String username, String password) throws Exception;

}
