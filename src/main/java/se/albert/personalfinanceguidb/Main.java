package se.albert.personalfinanceguidb;

import javafx.application.Application;
import javafx.stage.Stage;
import se.albert.personalfinanceguidb.repositories.IUserRepository;
import se.albert.personalfinanceguidb.repositories.PostgreUserRepository;
import se.albert.personalfinanceguidb.scenes.LoginUserScene;

import java.sql.SQLException;


// Importerade bibliotek samt importerad klass så att det går att koppla de samman.


public class Main extends Application {  // Main klassen, vad som ska köras

    private IUserRepository userRepository;

    @Override
    public void start(Stage primaryStage) throws SQLException { // Metoden som ska köras när appen startas
        String dbUrl = System.getenv("DATABASE_URL");
        String dbUser = System.getenv("DATABASE_USER");
        String dbPassword = System.getenv("DATABASE_PASSWORD");


        userRepository = new PostgreUserRepository(dbUrl, dbUser, dbPassword);

        LoginUserScene login = new LoginUserScene(userRepository); // En logga in sida
        primaryStage.setScene(login.create(primaryStage)); // Man sätter första scenen som logga in scenen
        primaryStage.setTitle("Personal Finance App"); // Titeln för applikationen är Personal Finance APP
        primaryStage.show(); // Visar primaryStage
    }

    public static void main(String[] args) { // Metod för att starta
        launch(args); // Startar applikationen
    }
}


