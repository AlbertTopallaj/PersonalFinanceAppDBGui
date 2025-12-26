package se.albert.personalfinanceguidb.services;

import se.albert.personalfinanceguidb.models.Transaction;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

public interface ITransactionService {

    Transaction createTransaction(UUID id, int amount, String description, String type, Timestamp created_at) throws Exception;

}
