package se.albert.personalfinanceguidb.scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import se.albert.personalfinanceguidb.models.User;
import se.albert.personalfinanceguidb.repositories.ITransactionRepository;
import se.albert.personalfinanceguidb.repositories.IUserRepository;
import se.albert.personalfinanceguidb.services.AuthService;
import se.albert.personalfinanceguidb.services.ITransactionService;
import se.albert.personalfinanceguidb.services.IUserService;

import java.util.Optional;

// Importerade bibliotek och klasser

public class LoginUserScene { // Klassens namn

    // Deklarera services och repos
    private final ITransactionRepository transactionRepository;
    private final ITransactionService transactionService;
    private final IUserRepository userRepository;
    private final IUserService userService;

    public LoginUserScene(IUserRepository userRepository, IUserService userService, ITransactionRepository transactionRepository, ITransactionService transactionService) {

        // Konstruktor, dependency injections
        this.userRepository = userRepository;
        this.userService = userService;
        this.transactionRepository = transactionRepository;
        this.transactionService = transactionService;
    }

    public Scene create(Stage primaryStage) { // Metod för att skapa scenen

        StackPane root = new StackPane(); // Root sätts
        root.setPadding(new Insets(40)); // Mellanrum sätts

        VBox content = new VBox(20);
        content.setAlignment(Pos.TOP_CENTER);
        content.setPadding(new Insets(20));
        content.setMaxWidth(800);

        Label title = new Label(" Logga in"); // Rubriken sätts för sidan
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;"); // Textstorlek och fetmarkerad text sätts

        Label userLabel = new Label("Användarnamn:"); // Label för Användarnamn
        TextField usernameField = new TextField(); // Användaren kan skriva sitt användarnamn här
        usernameField.setPromptText("Skriv ditt användarnamn"); // I TextField står detta i för att göra det tydligt vad som ska ske

        Label passLabel = new Label("Lösenord:"); // Label för Lösenord
        PasswordField passwordField = new PasswordField(); // Användaren kan skriva sitt lösenörd här
        passwordField.setPromptText("Skriv ditt lösenord"); // Detta står i textfield för att användaren ska veta vad som ska göras

        Button loginButton = new Button("Logga in"); // Knappen för att logga in
        loginButton.setMaxWidth(Double.MAX_VALUE); // Bredden sätts dubbla max värdet

        loginButton.setOnAction(e -> { // Om man trycker på knappen händer följande

            // Hämta användarnamn och lösenord
            String username = usernameField.getText();
            String password = passwordField.getText();

            Optional<User> optional;

            try {
                // Kontrollera om inloggningsuppgifter finns och är rätt
                optional = userService.checkUserLogin(username, password);
            } catch (Exception ex) {
                // Skriv ut fel om det finns till utvecklaren
                ex.printStackTrace();

                // Alert
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Fel vid login: " + ex.getMessage());
                alert.showAndWait();
                return;
            }

            // Om det är fel uppgifter
            if (optional.isEmpty()) {

                // Alert
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Felaktiga inloggningsuppgifter, försök igen");
                alert.showAndWait();
                return;
            }


            User user = optional.get();
            // UserId sätts
            AuthService.setUserID(user.getId());

            // Nuvarande användaren sätts
            AuthService.setCurrentUser(user);

            // Alert
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Inloggning lyckades");
            alert.setHeaderText("Välkommen åter " + user.getUsername());
            alert.setContentText("Inloggning lyckades");
            alert.showAndWait();

            // Man skickas till main menu scenen
            primaryStage.setScene(new MainMenuScene(userRepository, transactionRepository, transactionService, userService).create(primaryStage));

        });

        // Registera konto knapp
        Button goToregisterSceneButton = new Button("Har du inget konto? Registera dig");
        goToregisterSceneButton.setMaxWidth(Double.MAX_VALUE);

        // Trycker man så skickas man till register användare scenen
        goToregisterSceneButton.setOnAction(e-> {
            primaryStage.setScene(new RegisterUserScene(userRepository, transactionRepository, transactionService, userService ).create(primaryStage));

        });


        content.getChildren().addAll( // Tar emot alla delar
                title,
                userLabel, usernameField,
                passLabel, passwordField,
                loginButton,
                goToregisterSceneButton
        );

        // Lägger in delarna i root
        root.getChildren().add(content);

        // Returnera scenen med mått
        return new Scene(root, 900, 700);
    }
}
