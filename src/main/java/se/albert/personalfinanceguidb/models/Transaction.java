package se.albert.personalfinanceguidb.models;

import java.time.LocalDate;
import java.util.UUID;

// Importerade bibliotek

public class Transaction { // Klassens namn
    private UUID id;
    private int amount; // En konstruktor för alla delar för en transaction, första är värdet i kronor
    private String description; // Beskrivningen
    private String type; // Typen av transaktion
    private LocalDate date; // Datumet

    public Transaction(UUID id, int amount, String description, String type, LocalDate date) { // Metod för att sätta värden, även kallat Setter
        this.id = id;
        this.amount = amount; // Samtliga variabler får sitt värde
        this.description = description;
        this.type = type;
        this.date = date;
    }

    public int getAmount() { return amount; } // Getter, returnerar värden.
    public String getDescription() { return description; }
    public String getType() { return type; }
    public LocalDate getDate() { return date; }


    public String toString(){ // Metod för att visa hur det ska skickas ut i listan

        return id + ": " + type + ": " + description + " (" + amount + " kr) " +  date; // Returnerar utskrift till lista

    }
}
