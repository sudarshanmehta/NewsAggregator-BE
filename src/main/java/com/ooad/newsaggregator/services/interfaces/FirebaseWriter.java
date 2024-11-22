package com.ooad.newsaggregator.services.interfaces;

import com.google.firebase.database.DatabaseReference;

public abstract class FirebaseWriter implements FirebaseWriterInterface {

    // The abstract method that child classes will implement
    // to return the target Firebase database collection or reference.
    protected abstract DatabaseReference getTargetCollection();

    @Override
    public boolean WriteDataItem(Object curr) {
        // Get the target collection (Firebase reference)
        DatabaseReference targetCollection = getTargetCollection();

        // Perform the write operation to Firebase
//        if (curr != null) {
//            try {
//                // Ensure the object is of a compatible type
//                targetCollection.push().setValue(curr);
//                return true;
//            } catch (Exception e) {
//                System.out.println("Failed to write data: " + e.getMessage());
//                return false;
//            }
//        } else {
//            System.out.println("Cannot write null object.");
//            return false;
//        }
        return true;
    }
}
