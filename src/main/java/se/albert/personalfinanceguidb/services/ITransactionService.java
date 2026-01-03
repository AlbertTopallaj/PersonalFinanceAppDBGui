package se.albert.personalfinanceguidb.services;

import se.albert.personalfinanceguidb.models.Transaction;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

public interface ITransactionService {

    // Skapa transaktionen
    Transaction createTransaction(UUID id, UUID userId, int amount, String description, String type, Timestamp created_at) throws Exception;

    // Hämta totala inkomsten
    int getTotalIncome(String type, UUID userId) throws Exception;

    // Hämta totala spenderingen
    int getTotalExpense(String type, UUID userId) throws Exception;

    // Hämta dagliga inkomsten
    int getDailyIncome(String type, UUID userId) throws Exception;

    // Hämta veckosvis inkomst
    int getWeeklyIncome(String type, UUID userId) throws Exception;

    // Hämta månadsvis inkomst
    int getMonthlyIncome(String type, UUID userId) throws Exception;

    // Hämta årliga inkomsten
    int getYearlyIncome(String type, UUID userId) throws Exception;

    // Hämta dagliga utgifter
    int getDailyExpense(String type, UUID userId) throws Exception;

    // Hämta veckovis utgift
    int getWeeklyExpense(String type, UUID userId) throws Exception;

    // Hämta månadsvis utgift
    int getMonthlyExpense(String type, UUID userId) throws Exception;

    // Hämta årlig utgift
    int getYearlyExpense(String type, UUID userId) throws Exception;

}
