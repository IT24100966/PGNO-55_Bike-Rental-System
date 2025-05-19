package com.example.library.service;

import com.example.library.model.Review;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {
    private final String FILE_PATH = "src/main/resources/data/reviews.json";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<Review> getAllReviews() {
        try {
            return objectMapper.readValue(Paths.get(FILE_PATH).toFile(),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Review.class));
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public Review getReviewById(Long id) {
        return getAllReviews().stream().filter(r -> r.getId().equals(id)).findFirst().orElse(null);
    }

    public Review createReview(Review review) {
        List<Review> reviews = getAllReviews();
        Long maxId = reviews.stream().mapToLong(Review::getId).max().orElse(0L);
        review.setId(maxId + 1);
        review.setTimestamp(LocalDateTime.now());
        reviews.add(review);
        saveReviews(reviews);
        return review;
    }

    public Review updateReview(Long id, Review updatedReview) {
        List<Review> reviews = getAllReviews();
        for (int i = 0; i < reviews.size(); i++) {
            if (reviews.get(i).getId().equals(id)) {
                updatedReview.setId(id);
                updatedReview.setTimestamp(LocalDateTime.now());
                reviews.set(i, updatedReview);
                saveReviews(reviews);
                return updatedReview;
            }
        }
        return null;
    }

    public void deleteReview(Long id) {
        List<Review> reviews = getAllReviews();
        reviews.removeIf(r -> r.getId().equals(id));
        saveReviews(reviews);
    }

    private void saveReviews(List<Review> reviews) {
        try {
            objectMapper.writeValue(Paths.get(FILE_PATH).toFile(), reviews);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}