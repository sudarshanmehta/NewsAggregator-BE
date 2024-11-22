package com.ooad.newsaggregator.controllers;

import com.ooad.newsaggregator.models.Article;
import com.ooad.newsaggregator.services.interfaces.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/fetch")
    private ResponseEntity<Boolean> fetchArticles(){
        boolean loaded = articleService.fetchAndStoreArticles();
        return ResponseEntity.ok(loaded);
    }

    @PostMapping("/store")
    private ResponseEntity<Boolean> storeArticle(@RequestBody List<Article> articles){
        boolean loaded = articleService.fetchAndStoreArticles();
        return ResponseEntity.ok(loaded);
    }
}
