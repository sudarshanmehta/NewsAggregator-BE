package com.ooad.newsaggregator.models;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.DocumentReference;

public class Notification {
    @DocumentId
    private String notificationId;
    private DocumentReference userRef;
    private String title;
    private String content;
    private String link;
    private String category;
    private Timestamp sentAt;
    private String status;
}

