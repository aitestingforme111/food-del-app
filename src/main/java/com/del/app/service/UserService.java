package com.del.app.service;

import com.del.app.exceptions.UserNotFoundException;
import com.del.app.exceptions.UserRegistrationException;
import com.del.app.model.User;
import com.del.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) throws UserRegistrationException, NoSuchAlgorithmException {
        // Validate user data (e.g., email format, password strength)
        if (!isValidEmail(user.getEmail())) {
            throw new UserRegistrationException("Invalid email format");
        }
        // Check if user with email already exists
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new UserRegistrationException("Email already exists");
        }
        // Hash password before saving (security best practice)
        user.setPassword(hashPassword(user.getPassword()));
        return userRepository.save(user);
    }

    public User getUserById(Long userId) throws UserNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        return user;
    }

    public User updateUser(Long userId, User updatedUser) throws UserNotFoundException {
        User existingUser = getUserById(userId);
        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setAddress(updatedUser.getAddress());
        return userRepository.save(existingUser);
    }

    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(password.getBytes());
        byte[] hashedBytes = messageDigest.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : hashedBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

}