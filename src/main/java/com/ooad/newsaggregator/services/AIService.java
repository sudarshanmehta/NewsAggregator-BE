package com.ooad.newsaggregator.services;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ooad.newsaggregator.models.AIResponse;
import com.ooad.newsaggregator.services.interfaces.FirebaseWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.HttpHeaders;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;

import java.util.HashMap;
import java.util.Map;


@Service
public class AIService {

    private static final Logger logger = LoggerFactory.getLogger(EndpointLogger.class);

//    public static class AIResponse {
//        private String sentiment;
//        private String summary;
//
//        // Constructor
//        public AIResponse(String sentiment, String summary) {
//            this.sentiment = sentiment;
//            this.summary = summary;
//        }
//
//        // Getters and Setters (Optional, for accessing and modifying fields)
//        public String getSentiment() {
//            return sentiment;
//        }
//
//        public void setSentiment(String sentiment) {
//            this.sentiment = sentiment;
//        }
//
//        public String getSummary() {
//            return summary;
//        }
//
//        public void setSummary(String summary) {
//            this.summary = summary;
//        }
//    }


    @Autowired
    private Firestore firestore;

    @Autowired
    private FirebaseAuthService firebaseAuthService;

//    private static final String SUMMARIZE_API_URL = "http://EC2-public-ip:8000/generate";
    private static final String SUMMARIZE_API_URL = "http://35.94.30.116:8000/generate";
    public AIResponse summarizeArticle(String textToSummarize) {
        logger.info("received article to summarize: " + textToSummarize );
        String apiUrl = UriComponentsBuilder.fromUriString(SUMMARIZE_API_URL)
                .toUriString();
        logger.info("Sending request to URL: {}", apiUrl);

        Map<String, String> requestPayload = new HashMap<>();
        requestPayload.put("news_article", textToSummarize);

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

                // Print the extracted fields
               // logger.info("Image URL: " + imagePath);
                logger.info("Sentiment: " + sentiment);
                logger.info("Summary: " + summary);

                return new AIResponse(sentiment, summary);
            } else {
                logger.error("AI Service response body is empty");
            }
        } catch (Exception e) {
            logger.error("An unexpected error occurred: " + e.getMessage());
        }

        return null;

    }

    public static class FirebaseUserInfoWriter extends FirebaseWriter {

        @Override
        protected DatabaseReference getTargetCollection() {
            // Define the Firebase path for users
            return FirebaseDatabase.getInstance().getReference("users");
        }
    }
}
