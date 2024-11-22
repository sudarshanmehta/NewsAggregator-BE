package com.ooad.newsaggregator.controllers;

import com.ooad.newsaggregator.models.AIResponse;
import com.ooad.newsaggregator.models.Article;
import com.ooad.newsaggregator.services.AIService;
import com.ooad.newsaggregator.services.ArticleService;
import com.ooad.newsaggregator.services.EndpointLogger;
import com.ooad.newsaggregator.services.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/some")
public class SomeController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private AIService aiService;

    private static final Logger logger = LoggerFactory.getLogger(EndpointLogger.class);

    @GetMapping("/info")
    public String getUserInfo() {
        articleService.fetchAndStoreArticles();
        return "User UID: " + "hi";
    }

//    @GetMapping("/summarize")
//    public String summarizeArticle(@RequestBody String articleText) {
//        AIResponse summary = aiService.summarizeArticle(articleText);
//        if (summary == null) {
//            return "Failed to summarize article";
//        }
//        return summary.getSummary();
//    }
//
//    @GetMapping("/fetch_and_summarize")
//    public String summarizeArticle() {
//        List<Article> articles = articleService.fetchArticles();
//        for (Article article : articles) {
//            AIResponse summary = aiService.summarizeArticle(article.getContent());
//            if (summary == null) {
//                logger.error("Failed to summarize article");
//            } else {
//                logger.info("summarizing article");
//                if(summary.getSentiment() == null){
//                    logger.info("sentiment not found");
//                }else{
//                    article.setSentiment(summary.getSentiment());
//                    }
//                article.setContent(summary.getSummary());
//            }
//            articleService.saveArticleToFirebase(article);
//        }
//        return "Num articles stored: " + articles.size();
//    }

    @GetMapping("/test")
    public void TestNotification(){
        notificationService.sendArticleNotification("Test","Test");
    }
}
