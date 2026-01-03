package se.albert.personalfinanceguidb.services;

import se.albert.personalfinanceguidb.models.Transaction;
import se.albert.personalfinanceguidb.repositories.ITransactionRepository;
import se.albert.personalfinanceguidb.repositories.IUserRepository;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class DefaultTransactionService implements ITransactionService{

    // Hämta repo
    private ITransactionRepository transactionRepository;

    // Konstruktur - Dependency injection
    public DefaultTransactionService(ITransactionRepository transactionRepository){
        this.transactionRepository = transactionRepository;
    }


    @Override
    // Skapa transaktion
    public Transaction createTransaction(UUID id, UUID userId, int amount, String description, String type, Timestamp created_at) throws Exception {

        // Skapa Transaction objekt
        Transaction transaction = new Transaction(id, userId, description, amount, type, created_at);
        try {
            // Spara till databas
            transactionRepository.save(transaction);
        } catch (Exception e){
            // Skicka fel till utvecklare om det finns fel
            throw new Exception();
        }
        // Returnera transaction objekt
        return transaction;
    }

    @Override
    public int getTotalIncome(String type, UUID userId) throws Exception {
        // Hämta totala inkomsten med typ och userId
        return transactionRepository.getTotalIncome("Inkomst", userId);
    }

    @Override
    public int getTotalExpense(String type, UUID userId) throws Exception {
        // Hämta totala spenderingen med typ och userId
        return transactionRepository.getTotalExpense("Spendering", userId);
    }

    @Override
    public int getDailyIncome(String type, UUID userId) throws Exception {
        // Hämta dagliga inkomsten med typ och userId
        return transactionRepository.getDailyIncome("Inkomst", userId);
    }

    @Override
    public int getWeeklyIncome(String type, UUID userId) throws Exception {
        // Hämta veckovis inkomst med typ och userId
        return transactionRepository.getWeeklyIncome("Inkomst", userId);
    }

    @Override
    public int getMonthlyIncome(String type, UUID userId) throws Exception {
        // Hämta månadsvis inkomst med typ och userId
        return transactionRepository.getMonthlyIncome("Inkomst", userId);
    }

    @Override
    public int getYearlyIncome(String type, UUID userId) throws Exception {
        // Hämta årlig inkomst med typ och userId
        return transactionRepository.getYearlyIncome("Inkomst", userId);
    }

    @Override
    public int getDailyExpense(String type, UUID userId) throws Exception {
        // Hämta dagliga utgift med typ och userId
        return transactionRepository.getDailyExpense("Spendering", userId);
    }

    @Override
    public int getWeeklyExpense(String type, UUID userId) throws Exception {
        // Hämta veckovis utgift med typ och userId
        return transactionRepository.getWeeklyExpense("Spendering", userId);
    }

    @Override
    public int getMonthlyExpense(String type, UUID userId) throws Exception {
        // Hämta månadvis utgift med typ och userId
        return transactionRepository.getMonthlyExpense("Spendering", userId);
    }

    @Override
    public int getYearlyExpense(String type, UUID userId) throws Exception {
        // Hämta årlig utgift med typ och userId
        return transactionRepository.getYearlyExpense("Spendering", userId);
    }

    @Override
    public int getTransactionCount(UUID userId) throws Exception {
        return transactionRepository.getTransactionCount(userId);
    }
}
