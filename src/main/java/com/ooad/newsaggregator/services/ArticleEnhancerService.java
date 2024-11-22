package com.ooad.newsaggregator.services;

import com.google.cloud.firestore.Firestore;
import com.ooad.newsaggregator.models.Article;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Service
public class ArticleEnhancerService {
    private static final Logger logger = LoggerFactory.getLogger(ArticleEnhancerService.class);

    @Autowired
    private Firestore firestore;

    @Autowired
    private FirebaseAuthService firebaseAuthService;

    @Value("${news.api.summarize}")
    private static String SUMMARIZE_API_URL;

    public void enhanceArticle(Article article) {
        String apiUrl = UriComponentsBuilder.fromUriString(SUMMARIZE_API_URL)
                .toUriString();

        Map<String, String> requestPayload = new HashMap<>();
        requestPayload.put("news_article", article.getContent());

        // Set the headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        // Wrap the request payload and headers in an HttpEntity
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestPayload, headers);

        // Create RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();
        try {
            // Execute the POST request
            ResponseEntity<Map> responseEntity = restTemplate.postForEntity(apiUrl, entity, Map.class);

            Map<String, Object> responseBody = responseEntity.getBody();

            if (responseBody != null) {
                // String imagePath = (String) responseBody.get("image_path");
                String sentiment = (String) responseBody.get("sentiment");
                String summary = (String) responseBody.get("summary");
                article.setContent(summary);
                article.setSentiment(sentiment);
            } else {
                logger.error("AI Service response body is empty");
            }
        } catch (Exception e) {
            logger.error("An unexpected error occurred: " + e.getMessage());
        }
    }
}
