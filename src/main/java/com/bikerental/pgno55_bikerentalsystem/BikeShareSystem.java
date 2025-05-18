package com.bikerental.pgno55_bikerentalsystem;

import java.util.*;
import java.io.*;

public class BikeShareSystem {
    private List<Bike> bikes;
    private List<Review> reviews;
    private Queue<RentalRequest> rentalQueue;
    private int reviewIdCounter;
    private static final String REVIEWS_FILE = "data/reviews.csv";

    public BikeShareSystem() {
        bikes = new ArrayList<>();
        reviews = new ArrayList<>();
        rentalQueue = new LinkedList<>();
        reviewIdCounter = 1;

        bikes.add(new Bike("1234", "Trek FX 2", 5));
        bikes.add(new Bike("5678", "Specialized Sirrus X 3.0", 3));

        loadReviewsFromFile();
    }

    public Review submitReview(String bikeId, int rating, String text) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        Review review = new Review(reviewIdCounter++, bikeId, rating, text);
        reviews.add(review);
        saveReviewsToFile();
        return review;
    }

    public Review editReview(int reviewId, int newRating, String newText) {
        if (newRating < 1 || newRating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        for (Review review : reviews) {
            if (review.getReviewId() == reviewId) {
                review.setRating(newRating);
                review.setText(newText);
                saveReviewsToFile();
                return review;
            }
        }
        throw new IllegalArgumentException("Review not found");
    }

    public boolean deleteReview(int reviewId) {
        boolean removed = reviews.removeIf(review -> review.getReviewId() == reviewId);
        if (removed) {
            saveReviewsToFile();
        }
        return removed;
    }

    private void saveReviewsToFile() {
        File dir = new File("data");
        if (!dir.exists()) dir.mkdirs();
        try (PrintWriter writer = new PrintWriter(new File(REVIEWS_FILE))) {
            writer.println("reviewId,bikeId,rating,text,timestamp");
            for (Review review : reviews) {
                writer.println(String.format("%d,%s,%d,%s,%s",
                        review.getReviewId(),
                        review.getBikeId(),
                        review.getRating(),
                        review.getText().replace(",", ";"),
                        review.getTimestamp()));
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error saving reviews: " + e.getMessage());
        }
    }

    private void loadReviewsFromFile() {
        File file = new File(REVIEWS_FILE);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 5);
                if (parts.length == 5) {
                    int reviewId = Integer.parseInt(parts[0]);
                    String bikeId = parts[1];
                    int rating = Integer.parseInt(parts[2]);
                    String text = parts[3].replace(";", ",");
                    reviews.add(new Review(reviewId, bikeId, rating, text));
                    reviewIdCounter = Math.max(reviewIdCounter, reviewId + 1);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading reviews: " + e.getMessage());
        }
    }

    public Map<String, Object> getStats() {
        Map<String, Object> stats = new HashMap<>();
        if (reviews.isEmpty()) {
            stats.put("averageRating", 0.0);
            stats.put("totalReviews", 0);
            stats.put("ratingDistribution", new int[]{0, 0, 0, 0, 0});
            return stats;
        }

        double totalRating = 0;
        int[] ratingDistribution = new int[5];
        for (Review review : reviews) {
            totalRating += review.getRating();
            ratingDistribution[review.getRating() - 1]++;
        }

        double averageRating = totalRating / reviews.size();
        stats.put("averageRating", Math.round(averageRating * 10.0) / 10.0);
        stats.put("totalReviews", reviews.size());
        stats.put("ratingDistribution", ratingDistribution);
        return stats;
    }

    public void addRentalRequest(String userId, String bikeId) {
        rentalQueue.offer(new RentalRequest(userId, bikeId));
    }

    public RentalRequest processRentalRequest() {
        RentalRequest request = rentalQueue.poll();
        if (request != null) {
            for (Bike bike : bikes) {
                if (bike.getBikeId().equals(request.getBikeId()) && bike.getAvailability() > 0) {
                    bike.setAvailability(bike.getAvailability() - 1);
                    return request;
                }
            }
            rentalQueue.offer(request);
        }
        return null;
    }

    public List<Bike> getSortedBikesByAvailability() {
        Bike[] bikeArray = bikes.toArray(new Bike[0]);
        quickSort(bikeArray, 0, bikeArray.length - 1);
        return new ArrayList<>(Arrays.asList(bikeArray));
    }

    private void quickSort(Bike[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    private int partition(Bike[] arr, int low, int high) {
        int pivot = arr[high].getAvailability();
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j].getAvailability() >= pivot) {
                i++;
                Bike temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        Bike temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }

    public List<Review> getReviews() {
        return new ArrayList<>(reviews);
    }

    public List<Bike> getBikes() {
        return new ArrayList<>(bikes);
    }
}