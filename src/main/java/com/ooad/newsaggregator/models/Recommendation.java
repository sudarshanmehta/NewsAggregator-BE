package com.ooad.newsaggregator.models;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.DocumentReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recommendation {
    @DocumentId
    private String userId;
    private DocumentReference userRef;
    private List<DocumentReference> recommendedArticles;
    private Timestamp generatedAt;
}
