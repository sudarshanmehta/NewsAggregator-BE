package com.ooad.newsaggregator.services.interfaces;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseArticleWriter extends FirebaseWriter {

    @Override
    protected DatabaseReference getTargetCollection() {
        // Define the Firebase path for articles
        return FirebaseDatabase.getInstance().getReference("articles");
    }
}
