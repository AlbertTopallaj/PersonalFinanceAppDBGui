package se.albert.personalfinanceguidb.services;

import se.albert.personalfinanceguidb.models.Transaction;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

public interface ITransactionService {

    Transaction createTransaction(UUID id, UUID userId, int amount, String description, String type, Timestamp created_at) throws Exception;

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

}
