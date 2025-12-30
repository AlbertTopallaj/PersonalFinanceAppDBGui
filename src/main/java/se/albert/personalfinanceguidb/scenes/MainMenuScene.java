package se.albert.personalfinanceguidb.scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import se.albert.personalfinanceguidb.models.User;
import se.albert.personalfinanceguidb.repositories.ITransactionRepository;
import se.albert.personalfinanceguidb.repositories.IUserRepository;
import se.albert.personalfinanceguidb.services.AuthService;
import se.albert.personalfinanceguidb.services.ITransactionService;
import se.albert.personalfinanceguidb.services.IUserService;

import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;


// Importerade bibliotek för UI och andra klasser

public class MainMenuScene { // Klassens namn

    private final ITransactionRepository transactionRepository;
    private final ITransactionService transactionService;
    private final IUserRepository userRepository;
    private final IUserService userService;

    public MainMenuScene(IUserRepository userRepository, ITransactionRepository transactionRepository, ITransactionService transactionService, IUserService userService){
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.transactionService = transactionService;
        this.userService = userService;
    }


    public Scene create(Stage primaryStage) { // Metoden för att skapa scenen
        VBox root = new VBox(20); // Root sätts
        root.setPadding(new Insets(30)); // Mellanrum sätts
        root.setAlignment(Pos.CENTER); // Postioneringen sätts

        Scene scene = new Scene(root, 400, 300); // Sidan sätts, root visar innehåll samt så sätts måtten för fönstret

        primaryStage.setWidth(500); // Bredden sätts
        primaryStage.setHeight(700); // Höjden sätts
        primaryStage.setResizable(false); // Användaren kan inte ändra fönstrets storlek

        UUID currentUser = AuthService.getuserID();
        User username = AuthService.getCurrentUser();

        Label title = new Label("Välkommen " + username.getUsername()); // Rubriken för sidan sätts
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;"); // Textstorlek och fetmarkerad text sätts

        UUID currentUserId = AuthService.getuserID();

        Label balanceLabel; // Balansen sätts i UI
        try {
            balanceLabel = new Label("Din kontobalans: " + transactionRepository.getTotalIncome("Inkomst", currentUserId) + " kr");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        balanceLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #2e7d32; -fx-font-weight: bold;"); // Textstorlek sätts, Färgen sätts som fetmarkerad text sätts


        // Själva menyn börjar nu
        Button addTransaction = new Button("Lägg till en ny transaktion"); // Knapp för att gå in till AddTransactionScene
        addTransaction.setMaxWidth(Double.MAX_VALUE); // Bredden sätts dubbla max värdet
        addTransaction.setOnAction(e -> // Om man trycker på knappen händer följande
                primaryStage.setScene(new AddTransactionScene(userRepository, transactionRepository, transactionService, userService).create(primaryStage)) // Man sätter scenen till AddTransactionScene
        );

        Button viewTransactions = new Button("Visa transaktioner"); // Knapp för att gå in till ViewTransactionsScene
        viewTransactions.setMaxWidth(Double.MAX_VALUE); // Bredden sätts dubbla max värdet
        viewTransactions.setOnAction(e -> // Om man trycker på knappen händer följande
                {
                    try {
                        primaryStage.setScene(new ViewTransactionScene(transactionRepository, transactionService, userRepository, userService).create(primaryStage));
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                } // Man sätter scenen till ViewTransactionsScene
        );

        Button logout = new Button("Logga ut ur systemet");
        logout.setMaxWidth(Double.MAX_VALUE);
        logout.setOnAction(e ->

                {
                    primaryStage.setScene(new LoginUserScene(userRepository, userService, transactionRepository, transactionService).create(primaryStage));
                }
        );

        Button quit = new Button("Stäng av programmet"); // Knapp för att stänga av programmet
        quit.setMaxWidth(Double.MAX_VALUE); // Bredden sätts dubbla max värdet
        quit.setOnAction(e -> primaryStage.close()); // Om man trycker på knappen så stängs programmet ner

        root.getChildren().addAll( // Alla delar samlas ihop och visas i scenen
                title,
                balanceLabel,
                addTransaction,
                viewTransactions,
                logout,
                quit
        );

        return scene; // Möjliggör för att faktiskt visa scenen
    }
}
