package com.ooad.newsaggregator.models;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.annotation.DocumentId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @DocumentId
    private String userId;
    private String username;
    private String email;
    private List<String> preferredCategories;
    private Map<String, Boolean> notificationSettings;
    private Timestamp createdAt;
    private Timestamp lastLogin;
}
