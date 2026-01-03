package se.albert.personalfinanceguidb.repositories;

import se.albert.personalfinanceguidb.models.User;

import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;


public interface IUserRepository {

    // Hitta användaren
    Optional<User> findByUsername(String username) throws SQLException;

    // Spara användaren
    void save(User user) throws SQLException;
}
