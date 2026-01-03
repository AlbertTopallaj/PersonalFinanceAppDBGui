package se.albert.personalfinanceguidb.models;

import se.albert.personalfinanceguidb.utilty.DateUtility;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

// Importerade bibliotek

public class Transaction { // Klassens namn

    // UUID för transaktionen
    private UUID id;

    // Den kopplade userId för transaktionen
    private UUID userId;

    // Värdet för transaktionen
    private int amount;

    // Beskrivningen
    private String description;

    // Typen av transaktion
    private String type;

    // Datumet när transaktionen skapades
    private Date created_at;


    // Konstruktor för transaktion med odeklarerat datum
    public Transaction(UUID id, UUID userId, String description, int amount, String type, Date date) {

        this.id = id;
        this.userId = userId;
        this.description = description;
        this.amount = amount;
        this.type = type;
        this.created_at = date;

    }

    // Här omvandlas det till LocalDate och kör startofday
    public Transaction(UUID id, UUID userId, String description, int amount, String type, LocalDate date){

        this.id = id;
        this.userId = userId;
        this.description = description;
        this.amount = amount;
        this.type = type;
        this.created_at = Timestamp.valueOf(date.atStartOfDay());

    }

    // Getters och setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId(){ return userId;}
    public int getAmount() { return amount; } // Getter, returnerar värden.
    public String getDescription() { return description; }
    public String getType() { return type; }
    public Timestamp getDate() { return (Timestamp) created_at; }


    public String toString(){ // Metod för att visa hur det ska skickas ut i listan

        return "TYP: " + type + " VÄRDE: " + amount + "kr " + "BESKRIVNING: " + description + " SKAPAD: " + DateUtility.DATE_FORMAT.format(created_at) + " ID: " + id;   // Returnerar utskrift till lista

    }
}
