package se.albert.personalfinanceguidb.scenes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import se.albert.personalfinanceguidb.models.Transaction;
import se.albert.personalfinanceguidb.repositories.ITransactionRepository;
import se.albert.personalfinanceguidb.repositories.IUserRepository;
import se.albert.personalfinanceguidb.services.AuthService;
import se.albert.personalfinanceguidb.services.ITransactionService;
import se.albert.personalfinanceguidb.services.IUserService;


import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.IsoFields;
import java.util.List;
import java.util.UUID;

// Importerade bibiliotek och andra klasser

public class ViewTransactionScene { // Klassens namn

    private final ITransactionRepository transactionRepository;
    private final ITransactionService transactionService;
    private final IUserRepository userRepository;
    private final IUserService userService;

    public ViewTransactionScene(ITransactionRepository transactionRepository, ITransactionService transactionService, IUserRepository userRepository, IUserService userService){
        this.transactionRepository = transactionRepository;
        this.transactionService = transactionService;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public Scene create(Stage primaryStage) throws SQLException { // Metoden för scenen, stage är primaryStage

        StackPane root = new StackPane(); // Root skapas
        root.setPadding(new Insets(40)); // Ger mellanrum

        VBox content = new VBox(20);
        content.setAlignment(Pos.TOP_CENTER);
        content.setPadding(new Insets(20));
        content.setMaxWidth(800);

        Label title = new Label("Transaktioner"); // En label som ska vara en rubrik för sidan
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;"); // Styling för rubriken, ändra textstorlek samt göra texten tjockmarkerad


        HBox filters = new HBox(10); // Fixa boxen för filteringen
        filters.setAlignment(Pos.CENTER); // Fixa postioneringen i mitten

        ComboBox<String> transactionsTypeFilter = new ComboBox<>(); // En combobox för att ge användaren möjligheten att välja mellan olika valen
        transactionsTypeFilter.getItems().addAll("Alla", "Inkomst", "Spendering"); // Här är valen användaren kan göra
        transactionsTypeFilter.setValue("Alla"); // Standardvärdet är alltid Alla

        ComboBox<String> dateFilter = new ComboBox<>(); // En till combobox för att kunna sätta vilken tid för att se när transaktionerna lades till
        dateFilter.getItems().addAll("Alla", "Idag", "Denna vecka", "Denna månad", "Detta år"); // Här är valen
        dateFilter.setValue("Alla"); // Standardvärdet är alltid Alla

        filters.getChildren().addAll(new Label("Typ:"), transactionsTypeFilter, // Här tar man in text till filterna
                new Label("Datum:"), dateFilter); // Typ och datum

        // Här kommer själva listan av data, när en användare skickar in en ny transaktion skickas till hit och sparas tillfälligt i listan
        List<Transaction> transactionList = transactionRepository.findAllByUserId(AuthService.getuserID());
      ObservableList<Transaction> observableTransactions =
               FXCollections.observableArrayList(transactionList); // Tar emot datan från klassen DataStore
       FilteredList<Transaction> filteredTransactions = // Lista för filterade transaktioner
              new FilteredList<>(observableTransactions); // Som ovan

       ListView<Transaction> transactionListView = new ListView<>(filteredTransactions); // En listview för filterade transaktioner
       transactionListView.setPrefHeight(300); // Höjden sätts för listview

        // --- Statistik ---
        VBox statsBox = new VBox(8); // En ny Vbox för statistik sätts med 8 i mellanrum
        statsBox.setAlignment(Pos.CENTER_LEFT); // Postioneringen är i mitten till vänster

        Label statsTitle = new Label("Statistik"); // Rubriken sätts
        statsTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;"); // Styling för rubriken, textstorlek samt fetmarkerad text

        Label dailySpending = new Label();
        Label dailyIncome = new Label();// Labels för att kunna visa data
        Label weeklySpending = new Label();
        Label weeklyIncome = new Label();
        Label monthlyAll = new Label();
        Label monthlySpending = new Label();
        Label monthlyIncome = new Label();
        Label yearlySpending = new Label();
        Label yearlyIncome = new Label();
        Label totalSpending = new Label();
        Label totalIncome = new Label();
        Label transactionCount = new Label();



        Runnable updateStats = () -> { // Möjliggör uppdatering av labels för att visa data

            // Hämtar userId från aktiv användare
            UUID currentUserId = AuthService.getuserID();
            // Sätter text och så att datan visas för samtliga labels
            try {
                dailySpending.setText("Spenderat idag: "+ transactionService.getDailyExpense("Spendering", currentUserId));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            try {
                dailyIncome.setText("Inkomst idag: "+ transactionService.getDailyIncome("Inkomst", currentUserId));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            try {
                weeklySpending.setText("Spenderat denna vecka: "+ transactionService.getWeeklyExpense("Spendering", currentUserId));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            try {
                weeklyIncome.setText("Inkomst denna vecka: "+ transactionService.getWeeklyIncome("Inkomst", currentUserId));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            try {
                monthlySpending.setText("Spenderat denna månad: "+ transactionService.getMonthlyExpense("Spending", currentUserId));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            try {
                monthlyIncome.setText("Inkomst denna månad: " + transactionService.getMonthlyIncome("Inkomst", currentUserId));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            try {
                yearlySpending.setText("Spenderat detta år: " + transactionService.getYearlyExpense("Spendering", currentUserId));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            try {
                yearlyIncome.setText("Spenderat idag: "+ transactionService.getYearlyIncome("Inkomst", currentUserId));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            try {
                totalSpending.setText("Spenderat totalt: "+ transactionService.getTotalExpense("Spendering", currentUserId));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            try {
                totalIncome.setText("Inkomst totalt: "+ transactionService.getTotalIncome("Inkomst", currentUserId));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            try {
                transactionCount.setText("Totalt antal transaktioner: " + transactionService.getTransactionCount(currentUserId));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
        updateStats.run(); // En funktion som körs för att uppdatera de

        statsBox.getChildren().addAll(statsTitle, dailySpending, weeklyIncome, monthlyAll, yearlySpending, transactionCount); // Ger statistik Vboxen alla nya delar som ska visas

        // --- Datumfilter logik ---
        dateFilter.setOnAction(e -> { // När man använder dateFilter så händer följande:
            String selectedDate = dateFilter.getValue(); // Det angivna datumet tas emot
            filteredTransactions.setPredicate(t -> { // Filterade transaktioner listan tar emot datan
                LocalDate today = LocalDate.now(); // Dagens datum tas emot
                LocalDate transactionDate = t.getDate().toLocalDateTime().toLocalDate();

                switch (selectedDate) { // En switch-case
                    case "Idag": // Om man tar idag
                      return transactionDate.equals(today); // Man ser alla gjorda transaktioner gjorda idag
                    case "Denna vecka": // Om man tar denna vecka
                        return transactionDate.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR) == // Man ser alla gjorda transaktioner gjorda denna vecka
                        today.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR)
                         && transactionDate.getYear() == today.getYear();
                    case "Denna månad": // Om man tar denna månad
                        return transactionDate.getMonth() == today.getMonth() // Man ser alla gjorda transaktioner denna månad
                           && transactionDate.getYear() == today.getYear();
                    case "Detta år": // Om man tar detta år
                        return transactionDate.getYear() == today.getYear(); // Man ser alla gjorda transaktioner detta år
                    default: // Om annat avslutas
                       return true;
                }
            });
        });

        // --- Typfilter logik ---
       transactionsTypeFilter.setOnAction(e -> { // Om man använder filtern för olika typer av transaktioner
           String selectedType = transactionsTypeFilter.getValue(); // Man tar emot det angivna värdet för vilken typ man vill ha
          filteredTransactions.setPredicate(t -> { // Listan tar emot det
            if ("Alla".equals(selectedType)) return true; // Om man har alla så syns alla transaktioner
              return t.getType().equals(selectedType); // Beroende på vilken typ man valt så visas just den typen
            });
            updateStats.run(); // Uppdatera listan så att det man vill se syns
        });


        Button deleteBtn = new Button("🗑 Radera vald transaktion"); // Knapp för att radera transaktioner
        deleteBtn.setMaxWidth(Double.MAX_VALUE); // Bredden sätts med dubbla max värdet
        deleteBtn.setOnAction(e -> { // Om man trycker på radera transaktions knappen så händer följande
          Transaction selected = transactionListView.getSelectionModel().getSelectedItem(); // Man markerar transaktionen
            if (selected != null) { // Om man har valt en transaktion
                try {
                    transactionRepository.delete(selected.getId()); // Man tillkallar removeTransaction i dataStore
                    observableTransactions.remove(selected); // Man raderar den från listan
                    updateStats.run();
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setContentText("Transaktion raderad.");
                    alert.showAndWait();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                updateStats.run(); // Man uppdaterar listan så att den försvinner
            } else { // Om annat
                Alert alert = new Alert(Alert.AlertType.WARNING); // En liten popup med varning dyker upp
                alert.setTitle("Ingen transaktion vald"); // Titeln för varningen sätts
                alert.setHeaderText(null); // Ingen headertext
                alert.setContentText("Välj en transaktion att radera."); // Meddelande sätts
                alert.showAndWait(); // Popupen visas och försvinner när användaren stänger av varningen
            }
        });

        Button backToMenu = new Button("<--- Tillbaka"); // Knapp för att gå tilbaka till Huvudmenyn
        backToMenu.setMaxWidth(Double.MAX_VALUE); // Bredden sätts med dubbla max värdet
        backToMenu.setOnAction(e -> primaryStage.setScene(new MainMenuScene(userRepository, transactionRepository, transactionService, userService).create(primaryStage))); // Om man trycker på knappen skickas man till menyn

        // Lägg ihop
        content.getChildren().addAll(title, filters, transactionListView, statsBox, deleteBtn, backToMenu); // Hela ViewTransactionScenes delar sätts ihop och visas

        root.getChildren().add(content);

        return new Scene(root, 900, 700);
    }
}
