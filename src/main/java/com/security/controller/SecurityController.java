package com.security.controller;

import com.security.entity.User_;
import com.security.repository.ProductRepository;
import com.security.repository.User_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping
public class SecurityController {

    @Autowired
    private User_repository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String goHome() {
        return "This is the home page";
    }
    @PostMapping("/user/save")
    public ResponseEntity<Object> saveUser(@RequestBody User_ user_) {
        user_.setPassword(passwordEncoder.encode(user_.getPassword()));
        User_ result = userRepository.save(user_);
        if (result.getId() > 0) {
            return ResponseEntity.ok("User is saved");
        }
        return ResponseEntity.status(404).body("Error : User not saved");
    }
    @GetMapping("/product/all")
    //@PreAuthorize("hasAuthority ('ADMIN') or hasAuthority ('USER')")
    public ResponseEntity<Object> getAllProducts(){
        return ResponseEntity.of(Optional.of(productRepository.findAll()));
    }
    @GetMapping("/users/all")
    @PreAuthorize("hasAuthority ('ADMIN')")
    public ResponseEntity<Object> getAllUsers(){
        return ResponseEntity.ok(userRepository.findAll());
    }
    @GetMapping("/user/single")
    @PreAuthorize("hasAuthority ('ADMIN') or hasAuthority ('USER')")
    public ResponseEntity<Object> getSingleUser(){
        return ResponseEntity.ok(userRepository.findByEmail(getLoggedInUserDetails().getUsername()));
    }

    public UserDetails getLoggedInUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            return (UserDetails) authentication.getPrincipal();
        }
        return null;
    }
}
