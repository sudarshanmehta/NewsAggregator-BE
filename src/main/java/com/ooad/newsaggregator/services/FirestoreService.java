package com.ooad.newsaggregator.services;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FirestoreService {

    private static final String USER_COLLECTION = "users";

    @Autowired
    private FirebaseAuth firebaseAuth;

    @Autowired
    private Firestore firestore;

    public UserRecord createUser(String email, String password) throws FirebaseAuthException {
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(email)
                .setPassword(password);
        UserRecord userRecord = firebaseAuth.createUser(request);
        initializeUserCollections(userRecord.getUid());
        return userRecord;
    }

    public String loginUser(String uid) throws FirebaseAuthException {
        return FirebaseAuth.getInstance().createCustomToken(uid);
    }

    private void initializeUserCollections(String userId) {
        // Add default user profile in the `users` collection
        Map<String, Object> userProfile = new HashMap<>();
        userProfile.put("username", "");
        userProfile.put("preferredCategories", List.of());  // Default empty preferences as a List
        userProfile.put("notificationSettings", Map.of("breaking_news", true, "daily_summary", false));
        userProfile.put("createdAt", com.google.cloud.Timestamp.now());
        userProfile.put("lastLogin", com.google.cloud.Timestamp.now());

        firestore.collection(USER_COLLECTION).document(userId).set(userProfile);

        // Initialize bookmarks subcollection
        firestore.collection(USER_COLLECTION).document(userId).collection("bookmarks").document("defaultBookmark")
                .set(Map.of("createdAt", com.google.cloud.Timestamp.now()));

        // Initialize recommendations document
        firestore.collection("recommendations").document(userId)
                .set(Map.of("userRef", firestore.collection(USER_COLLECTION).document(userId),
                        "recommendedArticles", List.of(),  // Use an empty List instead of array
                        "generatedAt", com.google.cloud.Timestamp.now()));

        // Initialize notifications collection with a welcome notification
        firestore.collection("notifications").document(userId)
                .set(Map.of("title", "Welcome!", "content", "Welcome to our platform!",
                        "category", "welcome", "sentAt", com.google.cloud.Timestamp.now(), "status", "unread"));
    }
}
