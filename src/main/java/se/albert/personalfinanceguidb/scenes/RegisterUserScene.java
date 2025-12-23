package se.albert.personalfinanceguidb.scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import se.albert.personalfinanceguidb.models.User;
import se.albert.personalfinanceguidb.repositories.IUserRepository;
import se.albert.personalfinanceguidb.repositories.PostgreUserRepository;

// Importerade bibliotek och klasser

public class RegisterUserScene {// Klassens namn

    private final IUserRepository userRepository;

    public RegisterUserScene(IUserRepository userRepository){

        this.userRepository = userRepository;

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
        passwordField.setPromptText("Upprepa ditt lösenord"); // Detta står i textfield för att användaren ska veta vad som ska göras

        Button registerButton = new Button("Logga in"); // Knappen för att logga in
        registerButton.setMaxWidth(Double.MAX_VALUE); // Bredden sätts dubbla max värdet

        registerButton.setOnAction(e -> { // Om man trycker på knappen händer följande

            try {
                String username = usernameField.getText();
                String password = passwordField.getText();

                User user = new User(username, password);
                userRepository.save(user);

            } catch (Exception exception){
                exception.printStackTrace();
                System.out.println(exception);
            }
        });

        root.getChildren().addAll( // Tar emot alla delar och lägger in de i root
                title,
                userLabel, usernameField,
                passLabel, passwordField,
                registerButton
        );

        return scene; // Möjliggör för att scenen ska synas
    }
}
