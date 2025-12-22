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

    }

    @Override
    public Transaction findById(UUID transactionId) throws SQLException {

    }

    @Override
    public List<Transaction> findAll() throws SQLException {

    }

    @Override
    public void save(Transaction transaction) throws SQLException {

    }

        @Override
        public void delete (UUID transactionId) throws SQLException {

        }

    }

