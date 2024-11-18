package com.ooad.newsaggregator.services;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private  FirebaseMessaging firebaseMessaging;

    public void sendArticleNotification(String title, String description) {
        try {
            // Create a notification message
            Notification notification = Notification.builder()
                    .setTitle(title)
                    .setBody(description)
                    .build();

            // Define the target audience or topic (e.g., "news-updates")
            Message message = Message.builder()
                    .setTopic("news-updates") // Send to a specific topic
                    .setNotification(notification)
                    .build();

            // Send the notification
            String response = firebaseMessaging.send(message);
            System.out.println("Successfully sent message: " + response);

        } catch (Exception e) {
            System.err.println("Error sending notification: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

