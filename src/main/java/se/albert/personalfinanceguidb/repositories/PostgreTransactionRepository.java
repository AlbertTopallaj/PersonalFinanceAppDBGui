package se.albert.personalfinanceguidb.repositories;

import se.albert.personalfinanceguidb.models.Transaction;
import se.albert.personalfinanceguidb.utilty.DateUtility;

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
                    "description TEXT," +
                    "amount INT," +
                    "type, TEXT," +
                    "created_at TIMESTAMP,");
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
            String description = set.getString("description");
            int amount = set.getInt("amount");
            String type = set.getString("type");
            Timestamp date = set.getTimestamp("created_at");

            return new Transaction(id, description, amount, type, date);
        }

    }

    @Override
    public List<Transaction> findAll() throws SQLException {
        List<Transaction> transactions = new ArrayList<>();

        try(Statement statement = connection.createStatement()){

            ResultSet set = statement.executeQuery("SELECT * FROM transactions");

            while (set.next()){

                UUID id = set.getObject("id", UUID.class);
                String description = set.getString("description");
                int amount = set.getInt("amount");
                String type = set.getString("type");
                Timestamp date = set.getTimestamp("created_at");

                Transaction transaction = new Transaction(id, description, amount, type, date);
                transactions.add(transaction);


            }

        }
        return transactions;

    }

    @Override
    public void save(Transaction transaction) throws SQLException {

        String sql = "INSERT INTO transactions (id, description, amount, type, created_at) VALUES (?, ?, ?, ?, ?)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            preparedStatement.setObject(1, transaction.getId());
            preparedStatement.setString(2, transaction.getDescription());
            preparedStatement.setInt(3, transaction.getAmount());
            preparedStatement.setString(4, transaction.getType());
            preparedStatement.setDate(5, (Date) transaction.getDate());


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

    }

