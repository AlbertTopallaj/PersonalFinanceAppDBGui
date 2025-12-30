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
import se.albert.personalfinanceguidb.repositories.PostgreUserRepository;
import se.albert.personalfinanceguidb.services.AuthService;
import se.albert.personalfinanceguidb.services.IUserService;

// Importerade bibliotek och klasser

public class RegisterUserScene {// Klassens namn

    private final IUserRepository userRepository;
    private final ITransactionRepository transactionRepository;
    private IUserService userService;

    public RegisterUserScene(IUserRepository userRepository, ITransactionRepository transactionRepository, IUserService userService){

        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.userService = userService;

    }
    public Scene create(Stage primaryStage) { // Metod för att skapa scenen

        VBox root = new VBox(20); // Root sätts
        root.setPadding(new Insets(30)); // Mellanrum sätts
        root.setAlignment(Pos.CENTER); // Postioneringen sätts

        Scene scene = new Scene(root, 400, 300); // Scenen skapas

        primaryStage.setWidth(500); // Bredden sätts
        primaryStage.setHeight(700); // Höjden sätts
        primaryStage.setResizable(false); // Användaren kan inte ändra fönstrets storlek

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

            primaryStage.setScene(new LoginUserScene(userRepository, userService, transactionRepository).create(primaryStage));

        });

        registerButton.setOnAction(e -> { // Om man trycker på knappen händer följande

            try {
                String username = usernameField.getText();
                String password = passwordField.getText();

                if (password.length() < 5) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Ditt lösenord är för kort!");
                    alert.setHeaderText(null);
                    alert.setContentText("Lösenordet är för kort, det måste vara längre än 5 tecken");
                    alert.showAndWait();
                    return;

                }

                if (password.isBlank() || password == null) {

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Du måste ange ett lösenord");
                    alert.setHeaderText(null);
                    alert.setContentText("Inget lösenord har angivits");
                    alert.showAndWait();
                    return;

                }

                if (username.isBlank() || username == null){

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Du måste ange ett användarnamn");
                    alert.setHeaderText(null);
                    alert.setContentText("Inget användarnamn har angivits");
                    alert.showAndWait();
                    return;

                }

                if (username.length() < 5){

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Ditt användarnamn är för kort!");
                    alert.setHeaderText(null);
                    alert.setContentText("Användarnamnet är för kort, det måste vara längre än 5 tecken");
                    alert.showAndWait();
                    return;
                }

                User user = new User(username, password);
                AuthService.setCurrentUser(user);
                userRepository.save(user);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Registering lyckades");
                alert.setHeaderText("Välkommen " + username);
                alert.setContentText("Registeringen lyckades!");
                alert.showAndWait();

                primaryStage.setScene(new MainMenuScene(userRepository, transactionRepository, userService).create(primaryStage));


            } catch (Exception exception){
                exception.printStackTrace();
                System.out.println(exception);
            }
        });

        root.getChildren().addAll( // Tar emot alla delar och lägger in de i root
                title,
                userLabel, usernameField,
                passLabel, passwordField,
                registerButton,
                login
        );

        return scene; // Möjliggör för att scenen ska synas
    }
}
