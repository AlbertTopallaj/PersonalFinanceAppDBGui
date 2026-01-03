package se.albert.personalfinanceguidb.repositories;

import se.albert.personalfinanceguidb.models.Transaction;

import java.security.spec.ECField;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface ITransactionRepository {

    Transaction findById(UUID transactionId) throws SQLException;

    List<Transaction> findAllByUserId(UUID userId) throws SQLException;

    void save(Transaction transaction) throws SQLException;

    void delete(UUID transactionId) throws SQLException;

    int getTotalIncome(String type, UUID userId) throws SQLException;

    int getTotalExpense(String type, UUID userId) throws SQLException;

    int getDailyIncome(String type, UUID userId) throws SQLException;

    int getWeeklyIncome(String type, UUID userId) throws SQLException;

    int getMonthlyIncome(String type, UUID userId) throws SQLException;

    int getYearlyIncome(String type, UUID userId) throws SQLException;

    int getDailyExpense(String type, UUID userId) throws SQLException;

    int getWeeklyExpense(String type, UUID userId) throws SQLException;

    int getMonthlyExpense(String type, UUID userId) throws SQLException;

    int getYearlyExpense(String type, UUID userId) throws SQLException;

    int getAccountBalance(UUID userId) throws SQLException;

    int getTransactionCount(UUID userId) throws SQLException;
}
