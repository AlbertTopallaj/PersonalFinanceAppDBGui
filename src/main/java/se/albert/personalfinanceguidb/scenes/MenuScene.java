package se.albert.personalfinanceguidb.scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import se.albert.personalfinanceguidb.models.Transaction;


// Importerade bibliotek för UI och andra klasser

public class MenuScene { // Klassens namn

    public Scene create(Stage primaryStage) { // Metoden för att skapa scenen
        VBox root = new VBox(20); // Root sätts
        root.setPadding(new Insets(30)); // Mellanrum sätts
        root.setAlignment(Pos.CENTER); // Postioneringen sätts

        Scene scene = new Scene(root, 400, 300); // Sidan sätts, root visar innehåll samt så sätts måtten för fönstret

        primaryStage.setWidth(500); // Bredden sätts
        primaryStage.setHeight(700); // Höjden sätts
        primaryStage.setResizable(false); // Användaren kan inte ändra fönstrets storlek


        Label title = new Label("Huvudmeny"); // Rubriken för sidan sätts
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;"); // Textstorlek och fetmarkerad text sätts


        int balance = DataStore.getTransactions() // Variabel för att visa kontobalans sätts
                .stream() // Allt som strömmar genom AddTransaction tas emot
                .mapToInt(Transaction::getAmount) // Man tar emot det som går igenom konstruktorn getAmount
                .sum(); //  Man plusar ihop allting för att se totalen

        Label balanceLabel = new Label("Din kontobalans: " + balance + " kr"); // Balansen sätts i UI
        balanceLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #2e7d32; -fx-font-weight: bold;"); // Textstorlek sätts, Färgen sätts som fetmarkerad text sätts

        // Själva menyn börjar nu
        Button addTransaction = new Button("Lägg till en ny transaktion"); // Knapp för att gå in till AddTransactionScene
        addTransaction.setMaxWidth(Double.MAX_VALUE); // Bredden sätts dubbla max värdet
        addTransaction.setOnAction(e -> // Om man trycker på knappen händer följande
                primaryStage.setScene(new AddTransactionScene().create(primaryStage)) // Man sätter scenen till AddTransactionScene
        );

        Button viewTransactions = new Button("Visa transaktioner"); // Knapp för att gå in till ViewTransactionsScene
        viewTransactions.setMaxWidth(Double.MAX_VALUE); // Bredden sätts dubbla max värdet
        viewTransactions.setOnAction(e -> // Om man trycker på knappen händer följande
                primaryStage.setScene(new ViewTransactionScene().create(primaryStage)) // Man sätter scenen till ViewTransactionsScene
        );

        Button quit = new Button("Stäng av programmet"); // Knapp för att stänga av programmet
        quit.setMaxWidth(Double.MAX_VALUE); // Bredden sätts dubbla max värdet
        quit.setOnAction(e -> primaryStage.close()); // Om man trycker på knappen så stängs programmet ner

        root.getChildren().addAll( // Alla delar samlas ihop och visas i scenen
                title,
                balanceLabel,
                addTransaction,
                viewTransactions,
                quit
        );

        return scene; // Möjliggör för att faktiskt visa scenen
    }
}
