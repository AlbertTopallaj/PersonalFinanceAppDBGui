package se.albert.personalfinanceguidb.services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import se.albert.personalfinanceguidb.models.User;
import se.albert.personalfinanceguidb.repositories.IUserRepository;
import java.util.Optional;
import java.util.UUID;

public class DefaultUserService implements IUserService {

    private final IUserRepository userRepository;

    public DefaultUserService(IUserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(String username, String password) throws Exception {

        Optional<User> existing = Optional.empty();
        try {
            existing = userRepository.findByUsername(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (existing.isPresent()) {
            throw new Exception();
        }

        String passwordHash = BCrypt
                .withDefaults()
                .hashToString(12, password.toCharArray());

        User user = new User(username, passwordHash);
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new Exception();
        }

        return user;
    }

    @Override
    public Optional<User> checkUserLogin(String username, String password) throws Exception {
        Optional<User> optional;
        try {
            optional = userRepository.findByUsername(username);
        } catch (Exception e){
            throw new Exception();
        }

        if (optional.isEmpty()){
            return optional;
        }

        User user = optional.get();

        BCrypt.Result result = BCrypt
                .verifyer()
                .verify(password.toCharArray(), user.getPassword());

        if (!result.verified) {
            return Optional.empty();
        }
        return Optional.of(user);

    }
}
