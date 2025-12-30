package se.albert.personalfinanceguidb.services;

import se.albert.personalfinanceguidb.models.User;

import java.util.Optional;
import java.util.UUID;

public interface IUserService {

    User createUser(String username, String password) throws Exception;

    Optional<User> checkUserLogin(String username, String password) throws Exception;

}
