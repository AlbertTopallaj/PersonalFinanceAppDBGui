package se.albert.personalfinanceguidb.repositories;

import se.albert.personalfinanceguidb.models.Transaction;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class PostgreTransactionRepository implements ITransactionRepository {

    private final Connection connection;

    public PostgreTransactionRepository(String url, String user, String password) throws SQLException {

        connection = DriverManager.getConnection(url, user, password);

        try(Statement statement = connection.createStatement()) {

            statement.execute("CREATE TABLE IF NOT EXISTS transactions (" +
                    "id UUID PRIMARY KEY," +
                    "description TEXT NOT NULL," +
                    "amount INT NOT NULL,"  +
                    "type TEXT NOT NULL" +
                    "created_at CURRENT_TIMESTAMP NOT NULL" +
                    ");");

        }
    }

    @Override
    public Transaction findById(UUID transactionId) throws SQLException {
        String sql = "SELECT * FROM transactions WHERE id = ?";

        try (PreparedStatement statement =  connection.prepareStatement(sql)) {

            statement.setObject(1, transactionId);

            ResultSet set = statement.executeQuery();
            if (!set.next()) {
                return null;
            }

            UUID id = set.getObject("id", UUID.class);
            String description = set.getString("description");
            int amount = set.getInt("amount");
            String type = set.getString("type");
            Timestamp createdAt = set.getTimestamp("created_at");


        }

        return null;
    }

    @Override
    public List<Transaction> findAll() throws SQLException {
        String sql = "SELECT * FROM transactions";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, sql);

            ResultSet set = statement.executeQuery();
            if (!set.next()){
                return null;
            }


        }

        return null;
    }

    @Override
    public void save(Transaction transaction) throws SQLException {

    }

    @Override
    public void delete(UUID transactionId) throws SQLException {

    }

}
