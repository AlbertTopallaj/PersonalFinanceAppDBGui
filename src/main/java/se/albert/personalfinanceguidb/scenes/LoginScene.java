package se.albert.personalfinanceguidb.scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

// Importerade bibliotek och klasser

public class LoginScene { // Klassens namn

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
            String user = usernameField.getText(); // Det som skrivits som Användarnamn tas emot
            String pass = passwordField.getText(); // Det som skrivits som Lösenord tas emot

            if (user.equals("admin") && pass.equals("1234")) { // Om användarnamnet och lösenordet är ett viss värde så händer följande
                primaryStage.setScene(new MenuScene().create(primaryStage)); // Meny scenen syns
            } else { // Om annat
                Alert alert = new Alert(Alert.AlertType.ERROR); // En popup som visar fel dyker upp
                alert.setTitle("Fel"); // Titeln sätts
                alert.setHeaderText(null); // Ingen headertext
                alert.setContentText("Fel lösenord eller användarnamn"); // Felmeddelande
                alert.showAndWait(); // Visa och vänta tills användaren stänger ner popup
            }
        });

        root.getChildren().addAll( // Tar emot alla delar och lägger in de i root
                title,
                userLabel, usernameField,
                passLabel, passwordField,
                loginButton
        );

        return scene; // Möjliggör för att scenen ska synas
    }
}
