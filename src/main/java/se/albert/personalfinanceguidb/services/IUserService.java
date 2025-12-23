package se.albert.personalfinanceguidb.services;

import se.albert.personalfinanceguidb.models.User;

import java.util.Optional;

public interface IUserService {

    User createUser(String username, String password) throws Exception;

    Optional<User> checkUserLogin(String username, String password) throws Exception;

}
