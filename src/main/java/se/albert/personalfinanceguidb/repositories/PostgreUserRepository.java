package se.albert.personalfinanceguidb.repositories;

import se.albert.personalfinanceguidb.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public class PostgreUserRepository implements IUserRepository {

    private final Connection connection;

    public PostgreUserRepository(String url, String user, String password) throws SQLException {
        connection = DriverManager.getConnection(url, user, password);

        try(Statement statement = connection.createStatement()){
            statement.execute("CREATE TABLE IF NOT EXISTS users (" +
                    "id UUID PRIMARY KEY," +
                    "username TEXT," +
                    "password TEXT," +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");


        }
    }
    public Optional<User> findByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, username);

            ResultSet set = preparedStatement.executeQuery();
            if (!set.next()) {
                return Optional.empty();

            }

            UUID id = set.getObject("id", UUID.class);
            username = set.getString("username");
            String password = set.getString("password");
            Timestamp createdAt = set.getTimestamp("created_At");

            User user = new User(id, username, password, createdAt);
            return Optional.of(user);

        }

    }

    @Override
    public void save(User user) throws SQLException {

        String sql = "INSERT INTO users (id, username, password) VALUES (?, ?, ?)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            preparedStatement.setObject(1, user.getId());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.getPassword());

            if (preparedStatement.executeUpdate() != 1){
                throw new SQLException("Failed to insert user");
            }

        }

    }

}
