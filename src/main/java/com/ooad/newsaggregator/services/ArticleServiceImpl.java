package com.ooad.newsaggregator.services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.ooad.newsaggregator.models.Article;
import com.ooad.newsaggregator.services.interfaces.ArticleService;
import com.ooad.newsaggregator.services.interfaces.NotificationService;
import com.ooad.newsaggregator.utils.UserContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {

    private static final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);

    @Autowired
    private Firestore firestore;

    @Autowired
    private FirebaseAuthService firebaseAuthService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private ArticleEnhancerService articleEnhancerService;

    private static final String NEWS_API_URL = "https://api.thenewsapi.com/v1/news/all";
    private RestTemplate restTemplate = new RestTemplate();

    @Value("${news.api.token}")
    private String apiToken;

    // Predefined categories
    private static final List<String> ALL_CATEGORIES = new ArrayList<>();

    public boolean fetchAndStoreArticles() {
        try {
            loadPreferredCategories(UserContext.getUserId());
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
                    List<Article> data = new ArrayList<>();
                    // Process and save each article
                    for (Map<String, Object> articleData : articlesData) {
                        Article article = new Article();
                        article.setTitle((String) articleData.get("title"));
                        article.setContent((String) articleData.get("description"));
                        article.setUrl((String) articleData.get("image_url"));
                        article.setPublished_at(Instant.parse((String) articleData.get("published_at")));
                        article.setCategory(category);
                        article.setSentiment(""); // Optional, implement sentiment analysis if required

                        // Save the article to Firebase Firestore
                        data.add(article);
                    }
                    saveArticleToFirebase(data);
                }
            }
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    public void saveArticleToFirebase(List<Article> articles) {
        articles.forEach(article -> {
            try {
                articleEnhancerService.enhanceArticle(article);
                // Get the reference to the "articles" collection
                CollectionReference reference = firestore.collection("articles");
                // Generate a new document reference to get the document ID
                DocumentReference documentReference = reference.document();
                // Set the document ID as the articleId
                article.setArticleId(documentReference.getId());
                // Add the article with a generated document ID
                reference.add(article).get();  // This is a blocking call to ensure the document is added before continuing
                logger.info("Article saved successfully " + article.getArticleId());
                notificationService.sendArticleNotification(article.getTitle(), article.getContent());
            } catch (Exception e) {
                System.err.println("Error saving article: " + e.getMessage());
            }
        });

    }

    private void loadPreferredCategories(String userId) {
        // Reference to the user's document
        DocumentReference userDocRef = firestore.collection("users").document(userId);

        // Fetch document
        ApiFuture<DocumentSnapshot> future = userDocRef.get();

        try {
            DocumentSnapshot document = future.get();

            if (document.exists()) {
                // Retrieve the preferredCategories array
                List<String> categories = (List<String>) document.get("preferredCategories");

                if (categories != null) {
                    // Update ALL_CATEGORIES
                    ALL_CATEGORIES.clear();
                    ALL_CATEGORIES.addAll(categories);

                    System.out.println("Preferred categories loaded: " + ALL_CATEGORIES);
                } else {
                    System.out.println("No preferred categories found for user.");
                }
            } else {
                System.out.println("User document does not exist.");
            }
        } catch (Exception e) {
            System.err.println("Error loading preferred categories: " + e.getMessage());
        }
    }

}
