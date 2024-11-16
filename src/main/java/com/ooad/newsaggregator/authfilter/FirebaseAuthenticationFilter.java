package com.ooad.newsaggregator.authfilter;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.ooad.newsaggregator.services.FirebaseAuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class FirebaseAuthenticationFilter extends OncePerRequestFilter {

    private final FirebaseAuthService firebaseAuthService;

    public FirebaseAuthenticationFilter(FirebaseAuthService firebaseAuthService) {
        this.firebaseAuthService = firebaseAuthService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        String idToken = header.substring(7);
        try {
            FirebaseToken token = firebaseAuthService.verifyToken(idToken);
            request.setAttribute("firebaseToken", token);  // Add token to request for further processing
        } catch (FirebaseAuthException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
            return;
        }
        chain.doFilter(request, response);
    }
}
