package com.ooad.newsaggregator.services.interfaces;

import com.ooad.newsaggregator.models.Article;

import java.util.List;

public interface ArticleService {
    void saveArticleToFirebase(List<Article> articles);
    boolean fetchAndStoreArticles();
}
