package com.ooad.newsaggregator.services.interfaces;

import com.ooad.newsaggregator.models.Article;

public interface BookmarkService {
    boolean bookmarkArticle(String userId, Article articleRef);
    boolean removeBookmarkArticle(String userId, Article articleRef);
}
