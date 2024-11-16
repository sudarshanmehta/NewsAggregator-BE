package com.ooad.newsaggregator.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/some")
public class SomeController {

    @GetMapping("/info")
    public String getUserInfo() {
        return "User UID: " + "hi";
    }
}
