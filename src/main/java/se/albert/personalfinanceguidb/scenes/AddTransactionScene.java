package se.albert.personalfinanceguidb.scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import se.albert.personalfinanceguidb.models.Transaction;


import java.time.LocalDate;

// Importerade bibliotek och klasser

public class AddTransactionScene { // Klassens namn

    public Scene create(Stage primaryStage) { // Metoden för att skapa scenen
        VBox root = new VBox(20); // Root sätts
        root.setPadding(new Insets(20)); // Mellanrum sätts
        root.setAlignment(Pos.TOP_CENTER); // Postioneringen sätts

        Scene scene = new Scene(root, 400, 400); // Scenen skapas

        primaryStage.setWidth(500); // Bredden sätts
        primaryStage.setHeight(700); // Höjden sätts
        primaryStage.setResizable(false); // Användaren kan inte ändra fönstrets storlek


        Label title = new Label("Lägg till transaktion"); // Rubriken sätts
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;"); // Textstorlek samt fetmarkerad text sätts


        ComboBox<String> typeBox = new ComboBox<>(); // ComboBox för val sätts
        typeBox.getItems().addAll("Inkomst", "Spendering"); // 2 val finns
        typeBox.setValue("Inkomst"); // Valet Inkomst kommer alltid vara först

        TextField amountField = new TextField(); // Ruta för att skriva in kronor för transaktionen
        amountField.setPromptText("Belopp (kr)"); // Text i rutan för att visa användaren vad som ska göras

        DatePicker datePicker = new DatePicker(LocalDate.now()); // Variabel datePicker sätts med den aktuella tiden

        TextField descriptionField = new TextField(); // Ruta för att skriva in beskrivning av transaktion sätts
        descriptionField.setPromptText("Beskrivning (valfritt)"); // Text i rutan för att visa användaren vad som ska göras

        Label transactionSaved = new Label(); // En label som bekräftar att transaktionen sparas
        transactionSaved.setStyle("-fx-text-fill: green;"); // Texten sätts med grön färg

        Button saveBtn = new Button("Spara transaktion"); // Knapp för att spara transaktionen
        saveBtn.setMaxWidth(Double.MAX_VALUE); // Bredden sätts dubbla max värdet

        Button backBtn = new Button("<--- Tillbaka"); // Knapp för att komma tillbaka till menyn
        backBtn.setMaxWidth(Double.MAX_VALUE); // Bredden sätts dubbla max värdet


        saveBtn.setOnAction(e -> { // Om man trycker på spara transaktion händer följande
            try { // Try catch för att hantera fel
                String type = typeBox.getValue(); // Värdet för vad för typ av transaktion tas emot
                int amount = Integer.parseInt(amountField.getText()); // Värdet för hur mycket kronor transaktionen innehåller tas emot, man parsar int så att det kan bli string
                LocalDate date = datePicker.getValue(); // Värdet för datum tas emot
                String description = descriptionField.getText(); // Värdet för beskrivningen tas emot

                Transaction transaction = new Transaction(id, amount, description, type, date); // Man samlar ihop alla inputs och skickar upp hela den till konstruktorn och skapar nya transaktionen
                DataStore.addTransaction(transaction); // Transaktionen skickas till DataStore
                transactionSaved.setText("Transaktion sparad!"); // Text för att bekräfta att transaktionen sparades
                amountField.clear(); // Tömma samtliga input field
                descriptionField.clear();
                datePicker.setValue(LocalDate.now()); // Återställ datum till dagens datum

            } catch (NumberFormatException ex) { // Om nummerformatet är fel händer följande
                transactionSaved.setText("Ogiltigt belopp. Ange ett heltal."); // Felmeddelande sätts
            }
        });

        backBtn.setOnAction(e -> // Om man trycker på tillbaka knappen händer följande
                primaryStage.setScene(new MenuScene().create(primaryStage)) // Scenen blir Menyn
        );

        // Layout
        root.getChildren().addAll( // Alla delar sätts ihop och visas i root
                title,
                new Label("Typ:"), typeBox,
                new Label("Belopp:"), amountField,
                new Label("Datum:"), datePicker,
                new Label("Beskrivning:"), descriptionField,
                transactionSaved,
                saveBtn, backBtn
        );

        return scene; // Möjliggör för att scenen faktiskt ska synas
    }
}
