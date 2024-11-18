package com.ooad.newsaggregator.services;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.ooad.newsaggregator.models.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class ArticleService {

    @Autowired
    private Firestore firestore;

    @Autowired
    private FirebaseAuthService firebaseAuthService;

    @Autowired
    private NotificationService notificationService;

    private static final String NEWS_API_URL = "https://api.thenewsapi.com/v1/news/all";
    private RestTemplate restTemplate = new RestTemplate();

    @Value("${news.api.token}")
    private String apiToken;

    // Predefined categories
    private static final List<String> ALL_CATEGORIES = Arrays.asList(
            "Apple", "Google", "Meta", "Uber", "Airbnb", "Mike Tyson", "Tom cruise", "Fiction"
    );

    public void fetchAndStoreArticles() {
        // Fetch articles for each preferred category
        for (String category : ALL_CATEGORIES) {

            String apiUrl = UriComponentsBuilder.fromUriString(NEWS_API_URL)
                    .queryParam("api_token", apiToken)
                    .queryParam("language", "en")
                    .queryParam("limit", 10)
                    .queryParam("search", category)
                    .toUriString();

            // Make the API request
            Map<String, Object> response = restTemplate.getForObject(apiUrl, Map.class);

            if (response != null && response.containsKey("data")) {
                List<Map<String, Object>> articlesData = (List<Map<String, Object>>) response.get("data");

                // Process and save each article
                for (Map<String, Object> articleData : articlesData) {
                    Article article = new Article();
                    article.setTitle((String) articleData.get("title"));
                    article.setContent((String) articleData.get("description"));
                    article.setUrl((String) articleData.get("image_url"));
                    article.setPublished_at(Instant.parse((String)articleData.get("published_at")));
                    article.setCategory(category);
                    article.setSentiment(""); // Optional, implement sentiment analysis if required

                    // Save the article to Firebase Firestore
                    saveArticleToFirebase(article);
                }
            }
        }
    }

    public void saveArticleToFirebase(Article article) {
        try {
            // Get the reference to the "articles" collection
            CollectionReference reference = firestore.collection("articles");
            // Generate a new document reference to get the document ID
            DocumentReference documentReference = reference.document();
            // Set the document ID as the articleId
            article.setArticleId(documentReference.getId());
            // Add the article with a generated document ID
            reference.add(article).get();  // This is a blocking call to ensure the document is added before continuing

            System.out.println("Article saved successfully");
            notificationService.sendArticleNotification(article.getTitle(),article.getContent());
        } catch (Exception e) {
            System.err.println("Error saving article: " + e.getMessage());
        }
    }

}
