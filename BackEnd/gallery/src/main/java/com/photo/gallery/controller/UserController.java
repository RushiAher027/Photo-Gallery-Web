package com.photo.gallery.controller;


import com.photo.gallery.dtos.LoginRequest;
import com.photo.gallery.dtos.UserDTO;
import com.photo.gallery.model.User;
import com.photo.gallery.service.UserService;

import java.io.Console;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService ;
    
    
    
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user){
        User savedUser = userService.saveUser(user);
        return new ResponseEntity<>(savedUser , HttpStatus.CREATED);
    }
      
    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody LoginRequest user) {
    	// Manually validating email and password
        if (user.getEmail().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email Must Be Entered");
        }
        
        if (user.getPassword().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password Must Be Entered");
        }
        
        try {
            // Calling the service to perform the login action
//        	System.out.println(email+" "+ password);
            return userService.loginUser(user.getEmail(), user.getPassword());
        } catch (RuntimeException ex) {
            // Catch the exception and return an appropriate error message
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
        
    }

    
}
