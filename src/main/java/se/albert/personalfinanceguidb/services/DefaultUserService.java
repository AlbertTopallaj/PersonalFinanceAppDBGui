package se.albert.personalfinanceguidb.services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import se.albert.personalfinanceguidb.models.User;
import se.albert.personalfinanceguidb.repositories.IUserRepository;
import java.util.Optional;
import java.util.UUID;

public class DefaultUserService implements IUserService {

    // Hämtar repo
    private final IUserRepository userRepository;

    // Konstruktor - Dependency injection
    public DefaultUserService(IUserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    // Skapa användaren
    public User createUser(String username, String password) throws Exception {

        // Om användaren finns
        Optional<User> existing = Optional.empty();
        try {
            // Hämta via användarnamn
            existing = userRepository.findByUsername(username);
        } catch (Exception e) {
            // Printa fel för utvecklare om det finns
            e.printStackTrace();
        }
        if (existing.isPresent()) {
            throw new Exception();
        }

        // Hasha lösenordet med BCrypt
        String passwordHash = BCrypt
                .withDefaults()
                .hashToString(12, password.toCharArray());

        // Skapa nytt User-objekt med hashad lösenord
        User user = new User(username, passwordHash);
        try {
            // Spara användaren
            userRepository.save(user);
        } catch (Exception e) {
            throw new Exception();
        }

        // Returnera objektet
        return user;
    }

    @Override
    // Kontrollera inloggningsuppgifter
    public Optional<User> checkUserLogin(String username, String password) throws Exception {
        Optional<User> optional;
        try {
            // Leta efter användare med användarnamn
            optional = userRepository.findByUsername(username);
        } catch (Exception e){
            // Om det finns fel visa till utvecklare
            throw new Exception();
        }

        if (optional.isEmpty()){
            return optional;
        }

        // Hämta User-objekt
        User user = optional.get();

        // Unhasha lösenord för att kunna jämföra
        BCrypt.Result result = BCrypt
                .verifyer()
                .verify(password.toCharArray(), user.getPassword());

        // Om det inte går
        if (!result.verified) {
            return Optional.empty();
        }

        // Om det finns träff
        return Optional.of(user);

    }
}
