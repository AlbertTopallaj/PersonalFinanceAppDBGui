package se.albert.personalfinanceguidb.scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import se.albert.personalfinanceguidb.models.Transaction;
import se.albert.personalfinanceguidb.repositories.ITransactionRepository;
import se.albert.personalfinanceguidb.repositories.IUserRepository;
import se.albert.personalfinanceguidb.repositories.PostgreTransactionRepository;
import se.albert.personalfinanceguidb.services.AuthService;
import se.albert.personalfinanceguidb.services.ITransactionService;
import se.albert.personalfinanceguidb.services.IUserService;


import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.UUID;

// Importerade bibliotek och klasser

public class AddTransactionScene { // Klassens namn

    // Deklarerar repos och services

    private final IUserRepository userRepository;

    private final ITransactionRepository transactionRepository;

    private final ITransactionService transactionService;

    private final IUserService userService;


    public AddTransactionScene(IUserRepository userRepository, ITransactionRepository transactionRepository, ITransactionService transactionService, IUserService userService){

        // Konstruktor och dependency injections
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.transactionService = transactionService;
        this.userService = userService;

    }

    public Scene create(Stage primaryStage) { // Metoden för att skapa scenen
        StackPane root = new StackPane(); // Root sätts
        root.setPadding(new Insets(40)); // Mellanrum sätts

        VBox content = new VBox(20);
        content.setAlignment(Pos.TOP_CENTER);
        content.setPadding(new Insets(20));
        content.setMaxWidth(800);

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

                // Skapar ett randomiserat UUID id
                UUID id = UUID.randomUUID();

                // Tar emot userId för att sätta så att endast den användaren kan se transaktionen
                UUID userId = AuthService.getuserID();

                String type = typeBox.getValue(); // Värdet för vad för typ av transaktion tas emot
                int amount = Integer.parseInt(amountField.getText()); // Värdet för hur mycket kronor transaktionen innehåller tas emot, man parsar int så att det kan bli string
                LocalDate date = datePicker.getValue(); // Värdet för datum tas emot
                String description = descriptionField.getText(); // Värdet för beskrivningen tas emot

                // Man samlar ihop alla inputs och skickar upp hela den till konstruktorn och skapar nya transaktionen

                // Spara i databasen
                transactionService.createTransaction(
                        id,
                        userId,
                        amount,
                        description,
                        type,
                        Timestamp.valueOf(date.atStartOfDay())
                );

                transactionSaved.setText("Transaktion sparad!"); // Text för att bekräfta att transaktionen sparades
                amountField.clear(); // Tömma samtliga input field
                descriptionField.clear();
                datePicker.setValue(LocalDate.now()); // Återställ datum till dagens datum

            } catch (NumberFormatException ex) { // Om nummerformatet är fel händer följande
                transactionSaved.setText("Ogiltigt belopp. Ange ett heltal."); // Felmeddelande sätts
                transactionSaved.setStyle("-fx-text-fill: red");
            }
            catch (SQLException exception ){
                // Om det finns fel printa till utvecklare
                exception.printStackTrace();

            } catch (Exception ex) {
                ex.printStackTrace();
                transactionSaved.setText("Ett fel uppstod vid sparande.");
                transactionSaved.setStyle("-fx-text-fill: red");
            }
        });

        backBtn.setOnAction(e -> // Om man trycker på tillbaka knappen händer följande
                {
                    try {
                        // Tillbaka till mainmenu
                        primaryStage.setScene(new MainMenuScene(userRepository, transactionRepository, transactionService, userService).create(primaryStage));
                    } catch (Exception ex) {

                        throw new RuntimeException(ex);
                    }
                }
        );


        content.getChildren().addAll( // Alla delar sätts ihop
                title,
                new Label("Typ:"), typeBox,
                new Label("Belopp:"), amountField,
                new Label("Datum:"), datePicker,
                new Label("Beskrivning:"), descriptionField,
                transactionSaved,
                saveBtn, backBtn
        );

        // Alla delar sätts i root
        root.getChildren().add(content);

        // Returnera scenen med mått
        return new Scene(root, 900, 700);
    }
}
