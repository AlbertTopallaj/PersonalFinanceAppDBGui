package se.albert.personalfinanceguidb.models;

import java.time.LocalDate;
import java.time.temporal.IsoFields;
import java.util.List;

// Importerade bibliotek

public class Stats { // Klassens namn

    public static int getDailySum(String type) { // Funktion för att få den dagliga summan
        LocalDate today = LocalDate.now(); // Dagens datum sätts
        return sumByFilter(type, t -> t.getDate().isEqual(today)); // Man summerar vad för typ av transaktion och returnerar just den typen av transaktion över idag
    }

    public static int getWeeklySum(String type) { // Funktion för att få summan över vecka
        LocalDate today = LocalDate.now(); // Dagens datum sätts
        return sumByFilter(type, t -> // Retunera följande
                t.getDate().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR) == // Går igenom vecka för vecka
                        today.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR) // Jämför med dagens datum och veckan
                        && t.getDate().getYear() == today.getYear() // Idag och då tar man året
        );
    }

    public static int getMonthlySum(String type) { // Funktion för att få summan över en månad
        LocalDate today = LocalDate.now(); // Dagens datum säts
        return sumByFilter(type, t -> // Returnera följande
                t.getDate().getMonth() == today.getMonth() // Dagens datum tas emot månaden
                        && t.getDate().getYear() == today.getYear() // Dagens datum tar emot året
        );
    }

    public static int getYearlySum(String type) { // Tar den årliga summan
        LocalDate today = LocalDate.now(); // Dagens datum
        return sumByFilter(type, t -> t.getDate().getYear() == today.getYear()); // Dagens datum, tar året
    }


    private static int sumByFilter(String type, java.util.function.Predicate<Transaction> filter) { // Metod för att få ut summan av filter
        List<Transaction> transactions = DataStore.getTransactions(); // Ta emot listan i DataStore
        return transactions.stream() // Returnera
                .filter(filter)// Filter
                .filter(t -> "Alla".equals(type) || t.getType().equals(type)) // Typen av transaktion
                .mapToInt(Transaction::getAmount) // Ta emot värdet i kronor
                .sum(); // Summera ihop
    }
}
