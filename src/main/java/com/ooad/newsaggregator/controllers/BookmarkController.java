package com.ooad.newsaggregator.controllers;

import com.ooad.newsaggregator.models.Article;
import com.ooad.newsaggregator.services.FirebaseAuthService;
import com.ooad.newsaggregator.services.interfaces.BookmarkService;
import com.ooad.newsaggregator.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/bookmark")
public class BookmarkController {

    @Autowired
    private BookmarkService bookmarkServiceImpl;

    @Autowired
    private FirebaseAuthService firebaseAuthService;

    @PostMapping("/createBookmark")
    public ResponseEntity<Boolean> createBookmark(@RequestBody Article articleRef) {
        boolean isSaved = bookmarkServiceImpl.bookmarkArticle(UserContext.getUserId(), articleRef);
        return ResponseEntity.ok(isSaved);
    }

    @PostMapping("/removeBookmark")
    public ResponseEntity<Boolean> removeBookmark(@RequestBody Article articleRef) {
        boolean isRemoved = bookmarkServiceImpl.removeBookmarkArticle(UserContext.getUserId(), articleRef);
        return ResponseEntity.ok(isRemoved);
    }
}
