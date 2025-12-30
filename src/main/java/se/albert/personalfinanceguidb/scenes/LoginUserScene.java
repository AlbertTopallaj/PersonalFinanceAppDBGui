package se.albert.personalfinanceguidb.scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import se.albert.personalfinanceguidb.models.User;
import se.albert.personalfinanceguidb.repositories.ITransactionRepository;
import se.albert.personalfinanceguidb.repositories.IUserRepository;
import se.albert.personalfinanceguidb.services.AuthService;
import se.albert.personalfinanceguidb.services.IUserService;

import java.util.Optional;

// Importerade bibliotek och klasser

public class LoginUserScene { // Klassens namn

    private final ITransactionRepository transactionRepository;
    private final IUserRepository userRepository;
    private final IUserService userService;

    public LoginUserScene(IUserRepository userRepository, IUserService userService, ITransactionRepository transactionRepository) {

        this.userRepository = userRepository;
        this.userService = userService;
        this.transactionRepository = transactionRepository;

    }

    public Scene create(Stage primaryStage) { // Metod för att skapa scenen

        VBox root = new VBox(20); // Root sätts
        root.setPadding(new Insets(30)); // Mellanrum sätts
        root.setAlignment(Pos.CENTER); // Postioneringen sätts

        Scene scene = new Scene(root, 400, 300); // Scenen skapas

        primaryStage.setWidth(500); // Bredden sätts
        primaryStage.setHeight(700); // Höjden sätts
        primaryStage.setResizable(false); // Användaren kan inte ändra fönstrets storlek

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

            String username = usernameField.getText();
            String password = passwordField.getText();

            Optional<User> optional;

            try {
                optional = userService.checkUserLogin(username, password);
            } catch (Exception ex) {
                ex.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Fel vid login: " + ex.getMessage());
                alert.showAndWait();
                return;
            }

            if (optional.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("fel 2 ;(");
                alert.showAndWait();
                return;
            }

            User user = optional.get();
            AuthService.setUserID(user.getId());

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Inloggning lyckades");
            alert.setHeaderText("Välkommen " + user.getUsername());
            alert.setContentText("Inloggning lyckades");
            alert.showAndWait();

            primaryStage.setScene(new MainMenuScene(userRepository, transactionRepository, userService).create(primaryStage));

        });

        Button goToregisterSceneButton = new Button("Har du inget konto? Registera dig");
        goToregisterSceneButton.setMaxWidth(Double.MAX_VALUE);

        goToregisterSceneButton.setOnAction(e-> {
            primaryStage.setScene(new RegisterUserScene(userRepository, transactionRepository, userService).create(primaryStage));

        });


        root.getChildren().addAll( // Tar emot alla delar och lägger in de i root
                title,
                userLabel, usernameField,
                passLabel, passwordField,
                loginButton,
                goToregisterSceneButton
        );

        return scene; // Möjliggör för att scenen ska synas
    }
}
