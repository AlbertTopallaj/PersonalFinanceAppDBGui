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

    int getTotalIncome(String type, UUID userId) throws Exception;

    int getTotalExpense(String type, UUID userId) throws Exception;

    int getDailyIncome(String type, UUID userId) throws Exception;

    int getWeeklyIncome(String type, UUID userId) throws Exception;

    int getMonthlyIncome(String type, UUID userId) throws Exception;

    int getYearlyIncome(String type, UUID userId) throws Exception;

    int getDailyExpense(String type, UUID userId) throws Exception;

    int getWeeklyExpense(String type, UUID userId) throws Exception;

    int getMonthlyExpense(String type, UUID userId) throws Exception;

    int getYearlyExpense(String type, UUID userId) throws Exception;

    int getAccountBalance(UUID userId) throws Exception;
}
