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


import java.time.LocalDate;
import java.time.temporal.IsoFields;

// Importerade bibiliotek och andra klasser

public class ViewTransactionScene { // Klassens namn

    public Scene create(Stage primaryStage) { // Metoden för scenen, stage är primaryStage

        VBox root = new VBox(20); // Root skapas
        root.setPadding(new Insets(20)); // Ger mellanrum
        root.setAlignment(Pos.TOP_CENTER); // Övre centrala delen är allt på

        Scene scene = new Scene(root, 500, 700); // Scenen skapas

        primaryStage.setWidth(500); // Bredden på fönstret
        primaryStage.setHeight(700); // Höjden på fönstret
        primaryStage.setResizable(false); // Användaren kan inte ändra fönstrets storlek


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
      //  ObservableList<Transaction> observableTransactions =
               // FXCollections.observableArrayList(DataStore.getTransactions()); // Tar emot datan från klassen DataStore
       // FilteredList<Transaction> filteredTransactions = // Lista för filterade transaktioner
              //  new FilteredList<>(observableTransactions); // Som ovan

       // ListView<Transaction> transactionListView = new ListView<>(filteredTransactions); // En listview för filterade transaktioner
    //    transactionListView.setPrefHeight(300); // Höjden sätts för listview

        // --- Statistik ---
        VBox statsBox = new VBox(8); // En ny Vbox för statistik sätts med 8 i mellanrum
        statsBox.setAlignment(Pos.CENTER_LEFT); // Postioneringen är i mitten till vänster

        Label statsTitle = new Label("Statistik"); // Rubriken sätts
        statsTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;"); // Styling för rubriken, textstorlek samt fetmarkerad text

        Label dailySpending = new Label(); // Labels för att kunna visa data
        Label weeklyIncome = new Label();
        Label monthlyAll = new Label();
        Label yearlySpending = new Label();

        Runnable updateStats = () -> { // Möjliggör uppdatering av labels för att visa data
            dailySpending.setText("Spenderat idag: "+ "kr"); // Sätter text och så att datan visas för samtliga labels
            weeklyIncome.setText("Inkomst denna vecka: " + " kr");
            monthlyAll.setText("Totalt denna månad: ");
            yearlySpending.setText("Spenderat detta år: ");
        };
        updateStats.run(); // En funktion som körs för att uppdatera de

        statsBox.getChildren().addAll(statsTitle, dailySpending, weeklyIncome, monthlyAll, yearlySpending); // Ger statistik Vboxen alla nya delar som ska visas

        // --- Datumfilter logik ---
        dateFilter.setOnAction(e -> { // När man använder dateFilter så händer följande:
            String selectedDate = dateFilter.getValue(); // Det angivna datumet tas emot
           // filteredTransactions.setPredicate(t -> { // Filterade transaktioner listan tar emot datan
                LocalDate today = LocalDate.now(); // Dagens datum tas emot
                switch (selectedDate) { // En switch-case
                    case "Idag": // Om man tar idag
                      //  return t.getDate().isEqual(today); // Man ser alla gjorda transaktioner gjorda idag
                    case "Denna vecka": // Om man tar denna vecka
                        //return t.getDate().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR) == // Man ser alla gjorda transaktioner gjorda denna vecka
                        //        today.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR)
                      //          && t.getDate().getYear() == today.getYear();
                    case "Denna månad": // Om man tar denna månad
                        //return t.getDate().getMonth() == today.getMonth() // Man ser alla gjorda transaktioner denna månad
                          //      && t.getDate().getYear() == today.getYear();
                    case "Detta år": // Om man tar detta år
                       // return t.getDate().getYear() == today.getYear(); // Man ser alla gjorda transaktioner detta år
                    default: // Om annat avslutas
                     //   return true;
                }
            });

        //});

        // --- Typfilter logik ---
       // transactionsTypeFilter.setOnAction(e -> { // Om man använder filtern för olika typer av transaktioner
         //   String selectedType = transactionsTypeFilter.getValue(); // Man tar emot det angivna värdet för vilken typ man vill ha
          //  filteredTransactions.setPredicate(t -> { // Listan tar emot det
            //    if ("Alla".equals(selectedType)) return true; // Om man har alla så syns alla transaktioner
              //  return t.getType().equals(selectedType); // Beroende på vilken typ man valt så visas just den typen
            //});
           // updateStats.run(); // Uppdatera listan så att det man vill se syns
        //});


        Button deleteBtn = new Button("🗑 Radera vald transaktion"); // Knapp för att radera transaktioner
        deleteBtn.setMaxWidth(Double.MAX_VALUE); // Bredden sätts med dubbla max värdet
        deleteBtn.setOnAction(e -> { // Om man trycker på radera transaktions knappen så händer följande
          //  Transaction selected = transactionListView.getSelectionModel().getSelectedItem(); // Man markerar transaktionen
            //if (selected != null) { // Om man har valt en transaktion
             //   DataStore.removeTransaction(selected); // Man tillkallar removeTransaction i dataStore
               // observableTransactions.remove(selected); // Man raderar den från listan
                updateStats.run(); // Man uppdaterar listan så att den försvinner
            //} else { // Om annat
                Alert alert = new Alert(Alert.AlertType.WARNING); // En liten popup med varning dyker upp
                alert.setTitle("Ingen transaktion vald"); // Titeln för varningen sätts
                alert.setHeaderText(null); // Ingen headertext
                alert.setContentText("Välj en transaktion att radera."); // Meddelande sätts
                alert.showAndWait(); // Popupen visas och försvinner när användaren stänger av varningen
           // }
        });



        Button backToMenu = new Button("<--- Tillbaka"); // Knapp för att gå tilbaka till Huvudmenyn
        backToMenu.setMaxWidth(Double.MAX_VALUE); // Bredden sätts med dubbla max värdet
        backToMenu.setOnAction(e -> primaryStage.setScene(new MainMenuScene().create(primaryStage))); // Om man trycker på knappen skickas man till menyn

        // Lägg ihop
        root.getChildren().addAll(title, filters, statsBox, deleteBtn, backToMenu); // Hela ViewTransactionScenes delar sätts ihop och visas

        return scene; // Möjliggör för att faktiskt visa sidan
    }
}
