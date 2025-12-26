package se.albert.personalfinanceguidb.services;

import se.albert.personalfinanceguidb.models.Transaction;
import se.albert.personalfinanceguidb.repositories.ITransactionRepository;
import se.albert.personalfinanceguidb.repositories.IUserRepository;

import java.sql.Time;
import java.sql.Timestamp;
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
}
