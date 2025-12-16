package se.albert.personalfinanceguidb.models;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Importerade bibliotek

public class DataStore { // Klassens namn
    private static List<Transaction> transactions = new ArrayList<>(); // En arraylist sätts
    private static final String FILE_NAME = "transactions.csv"; // Filen deklareras

    public static void addTransaction(Transaction transaction) { // Funktion för att skicka in nya transaktioner
        transactions.add(transaction); // Lägger till transaktioner
        saveTransactions(); // Sparar direkt när transaktionen läggs till
    }

    public static List<Transaction> getTransactions() { // Listan för att ta emot transaktioner sätts
        return transactions; // Returnerar listan
    }

    public static void removeTransaction(Transaction transaction){ // Funktion för att ta bort transaktioner

        transactions.remove(transaction); // Trasaktioner tas bort från transactions
        saveTransactions(); // Sparar direkt efter transsaktionen tas bort

    }

    public static void saveTransactions(){ // Metod för att spara transaktioner i fil

        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) { // Try catch, definerar en skrivare för fil i filen

            for (Transaction t : transactions) { // for loop, den loopar igenom alla transaktioner

                writer.println(t.getDate() + ";" + // får ut alla transaktioner och skriver ut som följande
                               t.getType() + ";" +
                               t.getAmount() + ";" +
                               t.getDescription() + ";");
            }

            } catch (IOException e) {
            e.printStackTrace(); // Printar ut felmeddelande


        }
    }

    public static void loadTransactions(){ // Metod för att ladda transaktioner

        transactions.clear(); // Rensa listan så det inte blir dubletter
        File file = new File(FILE_NAME); // Filen deklareras
        if (!file.exists()) return; // Om filen inte existeras, då avbryts det direkt

        try(BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) { // try catch, en läsare till filen skapas

            String line;
            // Läs filen rad för rad
            while ((line = reader.readLine()) != null) { // While loop, den läser in allting

                String[] parts = line.split(";"); // Delar upp raden i delar med semikolon
                if (parts.length < 4) continue; // Hoppar över ogiltiga rader

                // Skapar en ny transaktion från datan
                LocalDate date = LocalDate.parse(parts[0]);
                String type = parts[1];
                int amount = Integer.parseInt(parts[2]);
                String description = parts[3];

                // Transaktionen läggs till
                transactions.add(new Transaction(amount, type, description, date));

            }

            } catch (IOException e) {

            // Skriv ut fel om något går fel vid läsning
            e.printStackTrace();

        }

    }
}
