package com.ooad.newsaggregator.services.interfaces;

public interface FirebaseWriterInterface extends DataWriterInterface {
    boolean WriteDataItem(Object object); // Abstract method to be implemented by subclasses
}


