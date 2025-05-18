package com.bikerental.pgno55_bikerentalsystem;

public class Main {
    public static void main(String[] args) {
        BikeShareSystem system = new BikeShareSystem();

        System.out.println("Submitting reviews...");
        system.submitReview("1234", 5, "Excellent bike, smooth ride!");
        system.submitReview("5678", 3, "Comfortable but brake issue.");
        system.submitReview("1234", 4, "Great for commuting.");

        System.out.println("\nStats:");
        System.out.println(system.getStats());

        System.out.println("\nEditing review ID 1...");
        system.editReview(1, 4, "Updated: Very good bike!");
        System.out.println("Reviews after edit:");
        system.getReviews().forEach(System.out::println);

        System.out.println("\nDeleting review ID 2...");
        system.deleteReview(2);
        System.out.println("Reviews after deletion:");
        system.getReviews().forEach(System.out::println);

        System.out.println("\nAdding rental requests...");
        system.addRentalRequest("user1", "1234");
        system.addRentalRequest("user2", "5678");
        System.out.println("Processing rental request: " + system.processRentalRequest());
        System.out.println("Processing rental request: " + system.processRentalRequest());

        System.out.println("\nBikes sorted by availability:");
        system.getSortedBikesByAvailability().forEach(System.out::println);
    }
}