package com.ooad.newsaggregator.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.cloud.firestore.annotation.DocumentId;
import com.ooad.newsaggregator.utils.CustomTimestampDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    @DocumentId
    private String articleId;
    private String title;
    private String content;
    private String url;
    private String category;
    private String sentiment;
    @JsonDeserialize(using = CustomTimestampDeserializer.class)
    private Instant published_at;
}
