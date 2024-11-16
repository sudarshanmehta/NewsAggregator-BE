package com.ooad.newsaggregator.models;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.annotation.DocumentId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    @DocumentId
    private String articleId;
    private String title;
    private String source;
    private String content;
    private String summary;
    private String url;
    private Timestamp publishedDate;
    private String category;
    private String sentiment;
}
