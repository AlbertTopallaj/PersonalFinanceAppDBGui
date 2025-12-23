package se.albert.personalfinanceguidb.repositories;

import se.albert.personalfinanceguidb.models.User;

import java.sql.SQLException;
import java.util.Optional;


public interface IUserRepository {

    Optional<User> findByUsername(String username) throws SQLException;
    void save(User user) throws SQLException;
}
