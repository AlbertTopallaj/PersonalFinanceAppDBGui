package se.albert.personalfinanceguidb.models;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

// Importerade bibliotek

public class Transaction { // Klassens namn
    private UUID id;
    private int amount; // En konstruktor för alla delar för en transaction, första är värdet i kronor
    private String description; // Beskrivningen
    private String type; // Typen av transaktion
    private Timestamp created_at; // Datumet

    public Transaction(UUID id, String description, int amount, String type, Timestamp date) {

        this.id = id;
        this.description = description;
        this.amount = amount;
        this.type = type;
        this.created_at = date;

    }

    public Transaction(UUID id, int amount, String description, String type, LocalDate date) {

        this.id = id;
        this.description = description;
        this.amount = amount;
        this.type = type;
        this.created_at = Timestamp.valueOf(date.atStartOfDay());

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getAmount() { return amount; } // Getter, returnerar värden.
    public String getDescription() { return description; }
    public String getType() { return type; }
    public Timestamp getDate() { return created_at; }


    public String toString(){ // Metod för att visa hur det ska skickas ut i listan

        return id + ": " + type + ": " + description + " (" + amount + " kr) " +  created_at; // Returnerar utskrift till lista

    }
}
