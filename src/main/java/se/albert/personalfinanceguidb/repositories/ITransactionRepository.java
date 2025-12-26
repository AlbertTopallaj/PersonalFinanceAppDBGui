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

    int getTotalIncome(String type) throws Exception;

    int getTotalExpense(String type) throws Exception;

    int getDailyIncome(String type) throws Exception;

    int getWeeklyIncome(String type) throws Exception;

    int getMonthlyIncome(String type) throws Exception;

    int getYearlyIncome(String type) throws Exception;

    int getDailyExpense(String type) throws Exception;

    int getWeeklyExpense(String type) throws Exception;

    int getMonthlyExpense(String type) throws Exception;

    int getYearlyExpense(String type) throws Exception;

}
