package com.del.app.controller;

import com.del.app.exceptions.UserRegistrationException;
import com.del.app.model.User;
import com.del.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/register")
  public ResponseEntity<User> registerUser(@RequestBody User user) throws UserRegistrationException, NoSuchAlgorithmException {
    User registeredUser = userService.registerUser(user);
    return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
  }
}