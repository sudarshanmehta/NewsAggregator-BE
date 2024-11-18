package com.ooad.newsaggregator.services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class BookmarkService {

    @Autowired
    private Firestore firestore;

    public boolean bookmarkArticle(String userId, String articleRef) {
        try {
            // Create a map to store the article reference
            Map<String, Object> bookmarkData = new HashMap<>();
            bookmarkData.put("articleRef", articleRef); // Wrap the article reference in a key-value pair

            // Add the bookmark to the user's 'bookmarks' sub-collection
            firestore.collection("users").document(userId)
                    .collection("bookmarks").add(bookmarkData).get();

            return true;
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging
            return false;
        }
    }

    public boolean removeBookmarkArticle(String userId, String articleRef) {
        try {
            // Get the 'bookmarks' collection for the user
            CollectionReference bookmarksCollection = firestore.collection("users").document(userId).collection("bookmarks");

            // Query the collection for the document with the matching articleRef
            Query query = bookmarksCollection.whereEqualTo("articleRef", articleRef);
            ApiFuture<QuerySnapshot> querySnapshot = query.get();

            // Check if any documents match and delete them
            for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
                DocumentReference documentReference = document.getReference();
                documentReference.delete();
            }

            return true; // Successfully removed the bookmark
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace(); // Log the exception for debugging
            return false; // Failed to remove the bookmark
        }
    }

}
