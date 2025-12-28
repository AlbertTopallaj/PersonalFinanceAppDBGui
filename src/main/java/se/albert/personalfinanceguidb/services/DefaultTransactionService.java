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

    private ITransactionRepository transactionRepository;

    public DefaultTransactionService(ITransactionRepository transactionRepository){
        this.transactionRepository = transactionRepository;
    }


    @Override
    public Transaction createTransaction(UUID id, UUID userId, int amount, String description, String type, Timestamp created_at) throws Exception {

        Transaction transaction = new Transaction(id, userId, description, amount, type, created_at);
        try {
            transactionRepository.save(transaction);
        } catch (Exception e){
            throw new Exception();
        }
        return transaction;
    }

    @Override
    public int getTotalIncome(String type, UUID userId) throws Exception {
        return transactionRepository.getTotalIncome("Inkomst", userId);
    }

    @Override
    public int getTotalExpense(String type, UUID userId) throws Exception {
        return transactionRepository.getTotalExpense("Spendering", userId);
    }

    @Override
    public int getDailyIncome(String type, UUID userId) throws Exception {
        return transactionRepository.getDailyIncome("Inkomst", userId);
    }

    @Override
    public int getWeeklyIncome(String type, UUID userId) throws Exception {
        return transactionRepository.getWeeklyIncome("Inkomst", userId);
    }

    @Override
    public int getMonthlyIncome(String type, UUID userId) throws Exception {
        return transactionRepository.getMonthlyIncome("Inkomst", userId);
    }

    @Override
    public int getYearlyIncome(String type, UUID userId) throws Exception {
        return transactionRepository.getYearlyIncome("Inkomst", userId);
    }

    @Override
    public int getDailyExpense(String type, UUID userId) throws Exception {
        return transactionRepository.getDailyExpense("Spendering", userId);
    }

    @Override
    public int getWeeklyExpense(String type, UUID userId) throws Exception {
        return transactionRepository.getWeeklyExpense("Spendering", userId);
    }

    @Override
    public int getMonthlyExpense(String type, UUID userId) throws Exception {
        return transactionRepository.getMonthlyExpense("Spendering", userId);
    }

    @Override
    public int getYearlyExpense(String type, UUID userId) throws Exception {
        return transactionRepository.getYearlyExpense("Spendering", userId);
    }
}
