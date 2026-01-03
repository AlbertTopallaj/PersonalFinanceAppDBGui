package se.albert.personalfinanceguidb.repositories;

import se.albert.personalfinanceguidb.models.Transaction;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PostgreTransactionRepository implements ITransactionRepository {

    // Deklarera connection
    private final Connection connection;


    public PostgreTransactionRepository(String url, String user, String password) throws SQLException {

        // Anslut till databasen
        connection = DriverManager.getConnection(url, user, password);

        // När anslutningen har skett
        try(Statement statement = connection.createStatement()){

            // Skapa tabell
            statement.execute("CREATE TABLE IF NOT EXISTS transactions (" +
                    "id UUID PRIMARY KEY," +
                    "user_id UUID NOT NULL REFERENCES users(id)," +
                    "description TEXT NOT NULL," +
                    "amount INT NOT NULL," +
                    "type TEXT NOT NULL," +
                    "created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP)");
        }
    }

    @Override
    public Transaction findById(UUID transactionId) throws SQLException {

        String sql = "SELECT * FROM transactions WHERE id = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setObject(1, transactionId);

            ResultSet set = preparedStatement.executeQuery();
            if (!set.next()){
                return null;
            }

            UUID id = set.getObject("id", UUID.class);
            UUID userId = set.getObject("user_id", UUID.class);
            String description = set.getString("description");
            int amount = set.getInt("amount");
            String type = set.getString("type");
            Timestamp date = set.getTimestamp("created_at");

            return new Transaction(id, userId, description, amount, type, date);
        }

    }

    @Override
    // Hämta alla transaktioner per användare
    public List<Transaction> findAllByUserId(UUID userId) throws SQLException {

        // En arraylist för transactions
        List<Transaction> transactions = new ArrayList<>();

        // SQL querie
        String sql = "SELECT * FROM transactions WHERE user_Id = ? ";

        // Kör querie
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            // Sätt userId
            preparedStatement.setObject(1, userId);

            // Uppdatera
            ResultSet set =  preparedStatement.executeQuery();

            // Medan den uppdateras
            while (set.next()){

                // Sätt samtliga objekt
                UUID id = set.getObject("id", UUID.class);
                UUID userid = set.getObject("user_id", UUID.class);
                String description = set.getString("description");
                int amount = set.getInt("amount");
                String type = set.getString("type");
                Timestamp date = set.getTimestamp("created_at");

                // Skapa transaktions objekt
                Transaction transaction = new Transaction(id, userid, description, amount, type, date);

                // Lägg till i arraylist
                transactions.add(transaction);

            }

        }
        // Returnera arraylistan
        return transactions;

    }

    @Override
    // Spara till databasen
    public void save(Transaction transaction) throws SQLException {

        // SQL QUERIE för att inserta in i transactions tabellen
        String sql = "INSERT INTO transactions (id, user_id, description, amount, type, created_at) VALUES (?, ?, ?, ?, ?, ?)";

        // Kör querie
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            // Sätt samtliga object
            preparedStatement.setObject(1, transaction.getId());
            preparedStatement.setObject(2, transaction.getUserId());
            preparedStatement.setString(3, transaction.getDescription());
            preparedStatement.setInt(4, transaction.getAmount());
            preparedStatement.setString(5, transaction.getType());
            preparedStatement.setTimestamp(6, transaction.getDate());

            // Uppdatera
            preparedStatement.executeUpdate();


        } catch (Exception e){
            // Om det är fel skriv ut till utvecklaren
            e.printStackTrace();

        }

    }

        @Override
        // Radera transaktion
        public void delete (UUID transactionId) throws SQLException {

        // Radera där id för transaktionen stämmer överens
        String sql = "DELETE FROM transactions WHERE id = ?";

        // Kör querie
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            // Sätt id
            preparedStatement.setObject(1, transactionId);

            // Uppdatera
            preparedStatement.executeUpdate();

          }

        }

        // Samtliga metoder är för att få statistik kring spendering, inkomster, totalt antal transaktioner.

    @Override

    // Summan av alla inkomst transaktioner per användare
    public int getTotalIncome(String type, UUID userId) throws SQLException {
        String sql = "SELECT SUM(amount) FROM transactions WHERE type = ? AND user_id = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, type);
            preparedStatement.setObject(2, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        }
    }

    @Override
    // Summan av alla utgifts transaktioner per användare
    public int getTotalExpense(String type, UUID userId) throws SQLException {
        String sql = "SELECT SUM(amount) FROM transactions WHERE type = ? AND user_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, type);
            preparedStatement.setObject(2, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);

        }
    }

    @Override
    // Summan av alla inkomster idag per användare
    public int getDailyIncome(String type, UUID userId) throws SQLException {
        String sql = "SELECT SUM(amount) FROM transactions WHERE type = ? AND DATE(created_at) = CURRENT_DATE AND user_id = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, type);
            preparedStatement.setObject(2, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        }
    }

    @Override
    // Summan av den veckovisa inkomst per användaren
    public int getWeeklyIncome(String type, UUID userId) throws SQLException {
        String sql = "SELECT sum(amount) FROM transactions WHERE type = ? AND DATE_TRUNC('week', created_at) = DATE_TRUNC('week', CURRENT_DATE) AND user_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, type);
            preparedStatement.setObject(2, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        }
    }

    @Override
    // Summan av den månadvisa inkomsten per användare
    public int getMonthlyIncome(String type, UUID userId) throws SQLException {
        String sql = "SELECT sum(amount) FROM transactions WHERE type = ? AND DATE_TRUNC('month', created_at) = DATE_TRUNC('month', CURRENT_DATE) AND user_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, type);
            preparedStatement.setObject(2, userId);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            return resultSet.getInt(1);
        }
    }

    @Override
    // Summan av den årliga inkomsten per användare
    public int getYearlyIncome(String type, UUID userId) throws SQLException {
        String sql = "SELECT sum(amount) FROM transactions WHERE type = ? AND DATE_TRUNC('year', created_at) = DATE_TRUNC('year', CURRENT_DATE) AND user_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, type);
            preparedStatement.setObject(2, userId);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            return resultSet.getInt(1);
        }
    }

    @Override
    // Summan av den dagliga utgiften per användare
    public int getDailyExpense(String type, UUID userId) throws SQLException {
        String sql = "SELECT sum(amount) FROM transactions WHERE type = ? AND DATE(created_at) = CURRENT_DATE AND user_id = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, type);
            preparedStatement.setObject(2, userId);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            return resultSet.getInt(1);
        }
    }

    @Override
    // Summan av den veckovisa utgiften per användare
    public int getWeeklyExpense(String type, UUID userId) throws SQLException {
        String sql = "SELECT sum(amount) FROM transactions WHERE type = ? AND DATE_TRUNC('week', created_at) = DATE_TRUNC('week', CURRENT_DATE) AND user_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, type);
            preparedStatement.setObject(2, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        }
    }

    @Override
    // Summan av den månadvisa utgiften per användare
    public int getMonthlyExpense(String type, UUID userId) throws SQLException {
        String sql = "SELECT sum(amount) FROM transactions WHERE type = ? AND DATE_TRUNC('month', created_at) = DATE_TRUNC('month', CURRENT_DATE) AND user_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, type);
            preparedStatement.setObject(2, userId);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            return resultSet.getInt(1);
        }
    }

    @Override
    // Summan av den årliga utgiften per användare
    public int getYearlyExpense(String type, UUID userId) throws SQLException {
        String sql = "SELECT sum(amount) FROM transactions WHERE type = ? AND DATE_TRUNC('year', created_at) = DATE_TRUNC('year', CURRENT_DATE) AND user_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, type);
            preparedStatement.setObject(2, userId);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            return resultSet.getInt(1);
        }
    }

    @Override
    // Summan av den totala kontobalansen per användare
    public int getAccountBalance(UUID userId) throws SQLException {
        return getTotalIncome("Inkomst", userId) - getTotalExpense("Spendering", userId);
    }

    @Override
    // Totalt antal transaktioner per användare
    public int getTransactionCount(UUID userId) throws SQLException {

        String sql =
                "SELECT COUNT(t.id) AS transaction_count " + // Räkna alla transaktioner
                        "FROM users u " + // Från tabellen users
                        "INNER JOIN transactions t ON t.user_id = u.id " + // JOIN (koppla samman) med transactions tabellen
                        "WHERE u.id = ?"; // Där endast för den valda användaren

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setObject(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("transaction_count");
                }
                return 0;
            }
        }
    }

}

