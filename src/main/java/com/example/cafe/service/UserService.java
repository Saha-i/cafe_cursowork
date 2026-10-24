package com.example.cafe.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.cafe.model.User;
import com.example.cafe.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;

    public UserService(UserRepository userRepository, PasswordHasher passwordHasher) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
    }

    public boolean loginTaken(String login) {
        return userRepository.existsByLoginIgnoreCase(login.trim());
    }

    @Transactional
    public User register(String login, String password) {
        User user = new User(login.trim(), passwordHasher.hash(password));
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public Optional<User> authenticate(String login, String password) {
        return userRepository.findByLoginIgnoreCase(login.trim())
                .filter(user -> passwordHasher.matches(password, user.getPassword()));
    }
}


