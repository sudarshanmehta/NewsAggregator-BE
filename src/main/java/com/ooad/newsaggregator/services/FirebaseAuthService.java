package com.ooad.newsaggregator.services;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.stereotype.Service;

@Service
public class FirebaseAuthService {

    public FirebaseToken verifyToken(String idToken) throws FirebaseAuthException {
        // Verify the ID token with Firebase Auth
        return FirebaseAuth.getInstance().verifyIdToken(idToken);
    }

    public String getUserUid(String idToken) throws FirebaseAuthException {
        FirebaseToken decodedToken = verifyToken(idToken);
        return decodedToken.getUid();  // Firebase UID
    }
}
