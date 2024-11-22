package com.ooad.newsaggregator.services;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.ooad.newsaggregator.models.Article;
import com.ooad.newsaggregator.services.interfaces.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class BookmarkServiceImpl implements BookmarkService {

    @Autowired
    private Firestore firestore;

    public boolean bookmarkArticle(String userId, Article articleRef) {
        try {
            // Create a map to store the article reference
             firestore.collection("users").document(userId)
                    .collection("bookmarks").document(articleRef.getArticleId()).set(articleRef);
            return true;
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging
            return false;
        }
    }

    public boolean removeBookmarkArticle(String userId, Article articleRef) {
        try {
            // Get the 'bookmarks' collection for the user
            CollectionReference bookmarksCollection = firestore.collection("users").document(userId).collection("bookmarks");

            // Use the articleId as the document ID in the collection
            String articleId = articleRef.getArticleId(); // Assuming Article has a `getArticleId` method

            // Reference the document directly using articleId
            DocumentReference documentReference = bookmarksCollection.document(articleId);

            // Delete the document
            documentReference.delete().get(); // Wait for the operation to complete

            return true; // Successfully removed the bookmark
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace(); // Log the exception for debugging
            return false; // Failed to remove the bookmark
        }
    }

}
