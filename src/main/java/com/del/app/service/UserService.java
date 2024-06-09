package com.del.app.service;

import com.del.app.exceptions.UserNotFoundException;
import com.del.app.exceptions.UserRegistrationException;
import com.del.app.model.User;
import com.del.app.repository.UserRepository;
import com.del.app.utils.RegistrationUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) throws UserRegistrationException, NoSuchAlgorithmException {
        if (!RegistrationUtility.isValidEmail(user.getEmail())) {
            throw new UserRegistrationException("Invalid email format");
        }

        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new UserRegistrationException("Email already exists");
        }
        user.setPassword(RegistrationUtility.hashPassword(user.getPassword()));
        return userRepository.save(user);
    }

    public User getUserById(Long userId) throws UserNotFoundException {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
    }

    public User updateUser(Long userId, User updatedUser) throws UserNotFoundException {
        User existingUser = getUserById(userId);
        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setAddress(updatedUser.getAddress());
        return userRepository.save(existingUser);
    }

}