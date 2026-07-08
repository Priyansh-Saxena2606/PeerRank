package com.peerrank.peerrank.controller;

import com.peerrank.peerrank.dto.ItemDTO;
import com.peerrank.peerrank.dto.MostReviewedItemDTO;
import com.peerrank.peerrank.dto.ReviewDTO;
import com.peerrank.peerrank.dto.TopRatedItemDTO;
import com.peerrank.peerrank.response.ApiResponse;
import com.peerrank.peerrank.response.ApiResponseBuilder;
import com.peerrank.peerrank.service.ItemService;
import com.peerrank.peerrank.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ReviewDTO>> createReview(
            @Valid @RequestBody ReviewDTO reviewDTO) {

        ApiResponse<ReviewDTO> response =
                ApiResponseBuilder.success(
                        "Review created successfully",
                        reviewService.createReview(reviewDTO)
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ReviewDTO>>> getAllReviews() {

        ApiResponse<List<ReviewDTO>> response =
                ApiResponseBuilder.success(
                        "Reviews fetched successfully",
                        reviewService.getAllReviews()
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/item/{itemId}")
    public ResponseEntity<ApiResponse<List<ReviewDTO>>> getReviewsByItemId(
            @PathVariable Long itemId) {

        ApiResponse<List<ReviewDTO>> response =
                ApiResponseBuilder.success(
                        "Reviews fetched successfully",
                        reviewService.getReviewsByItemId(itemId)
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/item/{itemId}/rating")
    public ResponseEntity<ApiResponse<Double>> getAverageRating(
            @PathVariable Long itemId) {

        ApiResponse<Double> response =
                ApiResponseBuilder.success(
                        "Average rating calculated successfully",
                        reviewService.getAverageRatingByItemId(itemId)
                );

        return ResponseEntity.ok(response);
    }
    @GetMapping("/top-rated")
    public ResponseEntity<ApiResponse<List<TopRatedItemDTO>>> getTopRatedItems() {

        ApiResponse<List<TopRatedItemDTO>> response =
                ApiResponseBuilder.success(
                        "Top rated items fetched successfully",
                        reviewService.getTopRatedItems()
                );

        return ResponseEntity.ok(response);
    }
    @GetMapping("/most-reviewed")
    public ResponseEntity<ApiResponse<List<MostReviewedItemDTO>>> getMostReviewedItems() {

        ApiResponse<List<MostReviewedItemDTO>> response =
                ApiResponseBuilder.success(
                        "Most reviewed items fetched successfully",
                        reviewService.getMostReviewedItems()
                );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ReviewDTO>> updateReview(
            @PathVariable Long id,
            @Valid @RequestBody ReviewDTO reviewDTO) {

        ApiResponse<ReviewDTO> response =
                ApiResponseBuilder.success(
                        "Review updated successfully",
                        reviewService.updateReview(id, reviewDTO)
                );

        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteReview(
            @PathVariable Long id) {

        reviewService.deleteReview(id);

        ApiResponse<Object> response =
                ApiResponseBuilder.success(
                        "Review deleted successfully",
                        null
                );

        return ResponseEntity.ok(response);
    }
}