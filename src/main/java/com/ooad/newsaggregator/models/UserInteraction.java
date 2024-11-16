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
public class UserInteraction {
    @DocumentId
    private String interactionId;
    private DocumentReference userRef;
    private DocumentReference articleRef;
    private String interactionType;
    private Long timeSpent;
    private Timestamp timestamp;
}
