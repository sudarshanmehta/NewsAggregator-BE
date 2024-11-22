package com.ooad.newsaggregator.controllers;

import com.ooad.newsaggregator.services.interfaces.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationServiceImpl;

    @GetMapping("/notify")
    public void testNotification(){
        notificationServiceImpl.sendArticleNotification("Test","Test");
    }
}
