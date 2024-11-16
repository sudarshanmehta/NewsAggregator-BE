package com.ooad.newsaggregator.models;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.DocumentReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bookmark {
    @DocumentId
    private String articleId;
    private DocumentReference articleRef;
    private Timestamp createdAt;
}
