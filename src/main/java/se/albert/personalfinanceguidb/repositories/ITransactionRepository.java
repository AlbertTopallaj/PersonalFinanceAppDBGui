package se.albert.personalfinanceguidb.repositories;

import se.albert.personalfinanceguidb.models.Transaction;

import java.security.spec.ECField;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface ITransactionRepository {

    Transaction findById(UUID transactionId) throws SQLException;

    List<Transaction> findAll() throws SQLException;

    void save(Transaction transaction) throws SQLException;

    void delete(UUID transactionId) throws SQLException;

}
