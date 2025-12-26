package se.albert.personalfinanceguidb.services;

import se.albert.personalfinanceguidb.models.Transaction;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

public interface ITransactionService {

    Transaction createTransaction(UUID id, int amount, String description, String type, Timestamp created_at) throws Exception;

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
