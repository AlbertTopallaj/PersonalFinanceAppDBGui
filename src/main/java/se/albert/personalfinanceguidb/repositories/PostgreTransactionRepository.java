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

    private final Connection connection;

    public PostgreTransactionRepository(String url, String user, String password) throws SQLException {

        connection = DriverManager.getConnection(url, user, password);

        try(Statement statement = connection.createStatement()){

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
    public List<Transaction> findAllByUserId(UUID userId) throws SQLException {
        List<Transaction> transactions = new ArrayList<>();

        String sql = "SELECT * FROM transactions WHERE user_Id = ? ";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            preparedStatement.setObject(1, userId);

            ResultSet set =  preparedStatement.executeQuery();
            while (set.next()){

                UUID id = set.getObject("id", UUID.class);
                UUID userid = set.getObject("user_id", UUID.class);
                String description = set.getString("description");
                int amount = set.getInt("amount");
                String type = set.getString("type");
                Timestamp date = set.getTimestamp("created_at");

                Transaction transaction = new Transaction(id, userid, description, amount, type, date);
                transactions.add(transaction);

            }

        }
        return transactions;

    }

    @Override
    public void save(Transaction transaction) throws SQLException {

        String sql = "INSERT INTO transactions (id, user_id, description, amount, type, created_at) VALUES (?, ?, ?, ?, ?, ?)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            preparedStatement.setObject(1, transaction.getId());
            preparedStatement.setObject(2, transaction.getUserId());
            preparedStatement.setString(3, transaction.getDescription());
            preparedStatement.setInt(4, transaction.getAmount());
            preparedStatement.setString(5, transaction.getType());
            preparedStatement.setTimestamp(6, transaction.getDate());

            preparedStatement.executeUpdate();


        } catch (Exception e){
            System.out.println("Something went wrong ;( ");
            e.printStackTrace();

        }

    }

        @Override
        public void delete (UUID transactionId) throws SQLException {
        String sql = "DELETE FROM transactions WHERE id = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            preparedStatement.setObject(1, transactionId);

            preparedStatement.executeUpdate();

          }

        }

    @Override
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
    public int getAccountBalance(UUID userId) throws SQLException {
        return getTotalIncome("Inkomst", userId) - getTotalExpense("Spendering", userId);
    }

    @Override
    public int getTransactionCount(UUID userId) throws SQLException {

    }
}

