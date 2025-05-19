package com.example.library.controller;

import com.example.library.model.Review;
import com.example.library.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public String getAllReviews(Model model) {
        List<Review> reviews = reviewService.getAllReviews();
        model.addAttribute("reviews", reviews);
        return "reviews";
    }

    @GetMapping("/{id}")
    public String getReviewById(@PathVariable Long id, Model model) {
        Review review = reviewService.getReviewById(id);
        model.addAttribute("review", review);
        return "review";
    }

    @GetMapping("/new")
    public String showNewReviewForm(Model model) {
        model.addAttribute("review", new Review());
        return "new_review";
    }

    @PostMapping
    public String createReview(@ModelAttribute Review review) {
        reviewService.createReview(review);
        return "redirect:/reviews";
    }

    @GetMapping("/{id}/edit")
    public String showEditReviewForm(@PathVariable Long id, Model model) {
        Review review = reviewService.getReviewById(id);
        model.addAttribute("review", review);
        return "edit_review";
    }

    @PostMapping("/{id}")
    public String updateReview(@PathVariable Long id, @ModelAttribute Review review) {
        reviewService.updateReview(id, review);
        return "redirect:/reviews";
    }

    @PostMapping("/{id}/delete")
    public String deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return "redirect:/reviews";
    }
}