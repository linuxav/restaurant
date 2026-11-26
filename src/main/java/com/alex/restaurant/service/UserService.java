package com.alex.restaurant.service;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.alex.restaurant.entity.User;
import com.alex.restaurant.exception.DuplicateRegistrationException;
import com.alex.restaurant.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicateRegistrationException("An account with this email already exists.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException ex) {
            if (userRepository.existsByEmail(user.getEmail())) {
                throw new DuplicateRegistrationException("Email already exists.", ex);
            }
            throw ex;
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
