package se.albert.personalfinanceguidb;

import javafx.application.Application;
import javafx.stage.Stage;
import se.albert.personalfinanceguidb.repositories.ITransactionRepository;
import se.albert.personalfinanceguidb.repositories.IUserRepository;
import se.albert.personalfinanceguidb.repositories.PostgreTransactionRepository;
import se.albert.personalfinanceguidb.repositories.PostgreUserRepository;
import se.albert.personalfinanceguidb.scenes.LoginUserScene;
import se.albert.personalfinanceguidb.services.DefaultTransactionService;
import se.albert.personalfinanceguidb.services.DefaultUserService;
import se.albert.personalfinanceguidb.services.ITransactionService;
import se.albert.personalfinanceguidb.services.IUserService;

import java.sql.SQLException;


// Importerade bibliotek samt importerad klass så att det går att koppla de samman.


public class Main extends Application {  // Main klassen, vad som ska köras

    private IUserRepository userRepository;
    private IUserService userService;
    private ITransactionRepository transactionRepository;
    private ITransactionService transactionService;

    @Override
    public void start(Stage primaryStage) throws SQLException { // Metoden som ska köras när appen startas

        String dbUrl = System.getenv("DATABASE_URL");
        String dbUser = System.getenv("DATABASE_USER");
        String dbPassword = System.getenv("DATABASE_PASSWORD");


        userRepository = new PostgreUserRepository(dbUrl, dbUser, dbPassword);
        userService = new DefaultUserService(userRepository);

        transactionRepository = new PostgreTransactionRepository(dbUrl, dbUser, dbPassword);
        transactionService = new DefaultTransactionService(transactionRepository);

        LoginUserScene login = new LoginUserScene(userRepository, userService, transactionRepository, transactionService); // En logga in sida
        primaryStage.setScene(login.create(primaryStage)); // Man sätter första scenen som logga in scenen
        primaryStage.setTitle("Personal Finance App"); // Titeln för applikationen är Personal Finance APP
        primaryStage.show(); // Visar primaryStage
    }

    public static void main(String[] args) { // Metod för att starta
        launch(args); // Startar applikationen
    }
}


