package com.ooad.newsaggregator.controllers;

import com.google.firebase.auth.FirebaseToken;
import com.ooad.newsaggregator.services.BookmarkService;
import com.ooad.newsaggregator.services.FirebaseAuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/bookmark")
public class BookmarkController {

    @Autowired
    private BookmarkService bookmarkService;

    @Autowired
    private FirebaseAuthService firebaseAuthService;

    @PostMapping("/createBookmark")
    public ResponseEntity<Boolean> createBookmark(@RequestBody String articleRef, HttpServletRequest request) {
        FirebaseToken firebaseToken = (FirebaseToken) request.getAttribute("firebaseToken");
        boolean isSaved = bookmarkService.bookmarkArticle(firebaseToken.getUid(), articleRef);
        return ResponseEntity.ok(isSaved);
    }

    @PostMapping("/removeBookmark")
    public ResponseEntity<Boolean> removeBookmark(@RequestBody String articleRef, HttpServletRequest request) {
        FirebaseToken firebaseToken = (FirebaseToken) request.getAttribute("firebaseToken");
        boolean isRemoved = bookmarkService.removeBookmarkArticle(firebaseToken.getUid(), articleRef);
        return ResponseEntity.ok(isRemoved);
    }
}
