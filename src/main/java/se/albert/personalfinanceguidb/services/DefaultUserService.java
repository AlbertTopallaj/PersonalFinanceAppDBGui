package se.albert.personalfinanceguidb.services;

import se.albert.personalfinanceguidb.models.User;
import se.albert.personalfinanceguidb.repositories.IUserRepository;

import java.util.Optional;

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

        User user = new User(username, password);
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

        return Optional.of(user);
    }

}
