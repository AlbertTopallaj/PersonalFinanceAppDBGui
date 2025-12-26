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
    public Transaction createTransaction(UUID id, int amount, String description, String type, Timestamp created_at) throws Exception {

        Transaction transaction = new Transaction(id, description, amount, type, created_at);
        try {
            transactionRepository.save(transaction);
        } catch (Exception e){
            throw new Exception();
        }
        return transaction;
    }

    @Override
    public int getTotalIncome(String type) throws Exception {
        return transactionRepository.getTotalIncome("Inkomst");
    }

    @Override
    public int getTotalExpense(String type) throws Exception {
        return transactionRepository.getTotalExpense("Spendering");
    }

    @Override
    public int getDailyIncome(String type) throws Exception {
        return transactionRepository.getDailyIncome("Inkomst");
    }

    @Override
    public int getWeeklyIncome(String type) throws Exception {
        return transactionRepository.getWeeklyIncome("Inkomst");
    }

    @Override
    public int getMonthlyIncome(String type) throws Exception {
        return transactionRepository.getMonthlyIncome("Inkomst");
    }

    @Override
    public int getYearlyIncome(String type) throws Exception {
        return transactionRepository.getYearlyIncome("Inkomst");
    }

    @Override
    public int getDailyExpense(String type) throws Exception {
        return transactionRepository.getDailyExpense("Spendering");
    }

    @Override
    public int getWeeklyExpense(String type) throws Exception {
        return transactionRepository.getWeeklyExpense("Spendering");
    }

    @Override
    public int getMonthlyExpense(String type) throws Exception {
        return transactionRepository.getMonthlyExpense("Spendering");
    }

    @Override
    public int getYearlyExpense(String type) throws Exception {
        return transactionRepository.getYearlyExpense("Spendering");
    }
}
