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
import se.albert.personalfinanceguidb.repositories.PostgreUserRepository;
import se.albert.personalfinanceguidb.services.AuthService;
import se.albert.personalfinanceguidb.services.ITransactionService;
import se.albert.personalfinanceguidb.services.IUserService;

// Importerade bibliotek och klasser

public class RegisterUserScene {// Klassens namn

    // Hämta olika repos och services
    private final IUserRepository userRepository;
    private final ITransactionRepository transactionRepository;
    private final ITransactionService transactionService;
    private IUserService userService;


    public RegisterUserScene(IUserRepository userRepository, ITransactionRepository transactionRepository, ITransactionService transactionService, IUserService userService){
        // Konstruktor och dependency injections
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.transactionService = transactionService;
        this.userService = userService;

    }
    public Scene create(Stage primaryStage) { // Metod för att skapa scenen

        StackPane root = new StackPane(); // Root sätts
        root.setPadding(new Insets(40)); // Mellanrum sätts

        VBox content = new VBox(20);
        content.setAlignment(Pos.TOP_CENTER);
        content.setPadding(new Insets(20));
        content.setMaxWidth(800);

        Label title = new Label("Registera konto"); // Rubriken sätts för sidan
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;"); // Textstorlek och fetmarkerad text sätts

        Label userLabel = new Label("Användarnamn:"); // Label för Användarnamn
        TextField usernameField = new TextField(); // Användaren kan skriva sitt användarnamn här
        usernameField.setPromptText("Skriv ditt användarnamn"); // I TextField står detta i för att göra det tydligt vad som ska ske

        Label passLabel = new Label("Lösenord:"); // Label för Lösenord
        PasswordField passwordField = new PasswordField(); // Användaren kan skriva sitt lösenörd här
        passwordField.setPromptText("Skriv ditt lösenord"); // Detta står i textfield för att användaren ska veta vad som ska göras

        Label validatePassLabel = new Label("Lösenord:"); // Label för Lösenord
        PasswordField validatePasswordField = new PasswordField(); // Användaren kan skriva sitt lösenörd här
        validatePasswordField.setPromptText("Upprepa ditt lösenord"); // Detta står i textfield för att användaren ska veta vad som ska göras

        Button registerButton = new Button("Registera"); // Knappen för att logga in
        registerButton.setMaxWidth(Double.MAX_VALUE); // Bredden sätts dubbla max värdet

        Button login = new Button("Har du redan ett konto? Logga in");
        login.setMaxWidth(Double.MAX_VALUE);

        login.setOnAction(e -> {
            // Man skickas till login scenen

            primaryStage.setScene(new LoginUserScene(userRepository, userService, transactionRepository, transactionService).create(primaryStage));

        });

        registerButton.setOnAction(e -> { //
            // Om man trycker på register knappen händer följande


            try {
                // Hämta användarnamn och lösenord
                String username = usernameField.getText();
                String password = passwordField.getText();

                // Regel 1 för lösenord: Måste vara längre än 5 tecken
                if (password.length() < 5) {
                    // Alert
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Ditt lösenord är för kort!");
                    alert.setHeaderText(null);
                    alert.setContentText("Lösenordet är för kort, det måste vara längre än 5 tecken");
                    alert.showAndWait();
                    return;

                }

                // Regel 2 för lösenord: Får inte vara tomt
                if (password.isBlank() || password == null) {

                    // Alert
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Du måste ange ett lösenord");
                    alert.setHeaderText(null);
                    alert.setContentText("Inget lösenord har angivits");
                    alert.showAndWait();
                    return;

                }

                // Regel 1 för användarnamn: Får inte vara tomt
                if (username.isBlank() || username == null){

                    // Alert
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Du måste ange ett användarnamn");
                    alert.setHeaderText(null);
                    alert.setContentText("Inget användarnamn har angivits");
                    alert.showAndWait();
                    return;

                }

                // Regel 2 för användarnamn: Måste vara längre än 5 tecken
                if (username.length() < 5){

                    // Alert
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Ditt användarnamn är för kort!");
                    alert.setHeaderText(null);
                    alert.setContentText("Användarnamnet är för kort, det måste vara längre än 5 tecken");
                    alert.showAndWait();
                    return;
                }

                // Skapa user-objekt med text-input från användare
                User user = new User(username, password);

                // Sätter nuvarande användare med nya objektet
                AuthService.setCurrentUser(user);

                // Skapa användaren
                userService.createUser(username, password);

                // Alert
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Registering lyckades");
                alert.setHeaderText("Välkommen " + username);
                alert.setContentText("Registeringen lyckades!");
                alert.showAndWait();

                // Skickas till mainmenu
                primaryStage.setScene(new MainMenuScene(userRepository, transactionRepository, transactionService, userService).create(primaryStage));


                // Om det finns fel så visas det till användaren
            } catch (Exception exception){
                exception.printStackTrace();
            }
        });

        content.getChildren().addAll( // Tar emot alla delar och lägger in de i root
                title,
                userLabel, usernameField,
                passLabel, passwordField,
                registerButton,
                login
        );

        // Tar emot hela content och skickar till root
        root.getChildren().add(content);

        // Returnera scenen med mått
        return new Scene(root, 900, 700);
    }
}
