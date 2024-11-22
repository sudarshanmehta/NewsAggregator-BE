package com.ooad.newsaggregator.controllers;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import com.ooad.newsaggregator.models.UserLogin;
import com.ooad.newsaggregator.services.EndpointLogger;
import com.ooad.newsaggregator.services.FirestoreService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;

@RestController
@CrossOrigin
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(EndpointLogger.class);

    @Autowired
    private FirestoreService firestoreService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserLogin userLogin) {
        try {
            logger.info("Enter /register");
            UserRecord userRecord = firestoreService.createUser(userLogin.getEmail(), userLogin.getPassword());
            userLogin.setUserId(userRecord.getUid());
            return ResponseEntity.ok("User created with UID: " + userLogin.getUserId());
        } catch (FirebaseAuthException e) {
            logger.info("Exception in /register");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody UserLogin userLogin) throws FirebaseAuthException {
        String token = firestoreService.loginUser(userLogin.getUserId());
        return token;
    }

    @GetMapping("/info")
    public String getUserInfo(@RequestAttribute("firebaseToken") FirebaseToken firebaseToken) {
        return "User UID: " + firebaseToken.getUid() + ", Email: " + firebaseToken.getEmail();
    }
}
