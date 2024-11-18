package com.ooad.newsaggregator.models;

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
    private String content;
    private String url;
    private String category;
    private String sentiment;
}
