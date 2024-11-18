package com.ooad.newsaggregator.controllers;

import com.ooad.newsaggregator.services.ArticleService;
import com.ooad.newsaggregator.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/some")
public class SomeController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/info")
    public String getUserInfo() {
        articleService.fetchAndStoreArticles();
        return "User UID: " + "hi";
    }

    @GetMapping("/test")
    public void TestNotification(){
        notificationService.sendArticleNotification("Test","Test");
    }
}
