package se.albert.personalfinanceguidb.repositories;

import se.albert.personalfinanceguidb.models.User;
import se.albert.personalfinanceguidb.services.AuthService;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

// Den utgår från IUserRepository
public class PostgreUserRepository implements IUserRepository {

    // Deklarera connection
    private final Connection connection;

    public PostgreUserRepository(String url, String user, String password) throws SQLException {
        // Anslutningen sker
        connection = DriverManager.getConnection(url, user, password);

        // När anslutningen har gått igenom
        try(Statement statement = connection.createStatement()){
            // Skapa tabellen
            statement.execute("CREATE TABLE IF NOT EXISTS users (" +
                    "id UUID PRIMARY KEY," +
                    "username TEXT UNIQUE NOT NULL," +
                    "password TEXT NOT NULL," +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");
        }
    }

    // Leta efter användare
    public Optional<User> findByUsername(String username) throws SQLException {
        // Ta alla användare där användarnamnet är det vi söker
        String sql = "SELECT * FROM users WHERE username = ?";

        // PreparedStatement för att motverka SQL injection
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Sätter namnet som söks
            preparedStatement.setString(1, username);

            // Uppdatera och visar resultat
            ResultSet set = preparedStatement.executeQuery();

            // Om ingenting hittas returnera tomt
            if (!set.next()) {
                return Optional.empty();

            }

            // Sätt övriga objekt
            UUID id = set.getObject("id", UUID.class);
            username = set.getString("username");
            String password = set.getString("password");
            Timestamp createdAt = set.getTimestamp("created_at");

            // Skapa user objekt
            User user = new User(id, username, password, createdAt);

            // Returnera user objekt
            return Optional.of(user);
        }
    }

    @Override
    // Spara användare
    public void save(User user) throws SQLException {

        // Lägg till i tabellen users, id, användarnamn samt lösenord resten sköter databasen
        String sql = "INSERT INTO users (id, username, password) VALUES (?, ?, ?)";

        // Kör igång SQL querie
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            // Sätta objekt
            preparedStatement.setObject(1, user.getId());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.getPassword());

            // Om det inte går kastas ett fel
            if (preparedStatement.executeUpdate() != 1){
                throw new SQLException("Failed to insert user");
            }

            // Här sätts man userId för att undvika att första användaren i systemet inte kan transaktioner
            AuthService.setUserID(user.getId());
        }
    }
}
