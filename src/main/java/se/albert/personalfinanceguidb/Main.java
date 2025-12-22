package se.albert.personalfinanceguidb;

import javafx.application.Application;
import javafx.stage.Stage;
import se.albert.personalfinanceguidb.UI.LoginScene;


// Importerade bibliotek samt importerad klass så att det går att koppla de samman.


public class Main extends Application {  // Main klassen, vad som ska köras
    @Override
    public void start(Stage primaryStage) { // Metoden som ska köras när appen startas
        DataStore.loadTransactions();
        LoginScene login = new LoginScene(); // En logga in sida
        primaryStage.setScene(login.create(primaryStage)); // Man sätter första scenen som logga in scenen
        primaryStage.setTitle("Personal Finance App"); // Titeln för applikationen är Personal Finance APP
        primaryStage.show(); // Visar primaryStage
    }

    public static void main(String[] args) { // Metod för att starta
        String dbUrl = System.getenv("DATABASE_URL");
        String dbUser = System.getenv("DATABASE_USER");
        String dbPassword = System.getenv("DATABASE_PASSWORD");

        launch(args); // Startar applikationen
    }
}


