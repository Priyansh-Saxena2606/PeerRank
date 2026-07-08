package com.peerrank.peerrank.service;

import com.peerrank.peerrank.dto.ReviewDTO;
import com.peerrank.peerrank.entity.Item;
import com.peerrank.peerrank.entity.Review;
import com.peerrank.peerrank.entity.User;
import com.peerrank.peerrank.exception.ResourceNotFoundException;
import com.peerrank.peerrank.mapper.ReviewMapper;
import com.peerrank.peerrank.repository.ItemRepository;
import com.peerrank.peerrank.repository.ReviewRepository;
import com.peerrank.peerrank.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.peerrank.peerrank.dto.TopRatedItemDTO;
import com.peerrank.peerrank.dto.MostReviewedItemDTO;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ItemRepository itemRepository;
    private final ReviewMapper reviewMapper;

    private static final Logger logger =
            LoggerFactory.getLogger(ReviewService.class);

    private final UserRepository userRepository;

    public ReviewService(
            ReviewRepository reviewRepository,
            ItemRepository itemRepository, ReviewMapper reviewMapper,
            UserRepository userRepository
    ) {
        this.reviewRepository = reviewRepository;
        this.itemRepository = itemRepository;
        this.reviewMapper = reviewMapper;
        this.userRepository = userRepository;
    }

    public ReviewDTO createReview(ReviewDTO dto) {

        logger.info("Creating review for item id: {}", dto.getItemId());

        Item item = itemRepository.findById(dto.getItemId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Item not found with id " + dto.getItemId()));

        Review review = new Review();
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());
        review.setItem(item);
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();
        System.out.println("Authenticated Email = " + email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        System.out.println("User Found = " + user.getUsername());

        if (reviewRepository.existsByUserIdAndItemId(
                user.getId(),
                item.getId())) {

            throw new RuntimeException(
                    "You have already reviewed this item."
            );
        }

        review.setUser(user);
        System.out.println("Review User = " + review.getUser().getUsername());

        Review savedReview = reviewRepository.save(review);
        System.out.println("Saved Review ID = " + savedReview.getId());
        System.out.println("Saved Review User = " +
                (savedReview.getUser() == null
                        ? "NULL"
                        : savedReview.getUser().getUsername()));

        logger.info("Review created successfully with id: {}", savedReview.getId());

        return reviewMapper.toDTO(savedReview);
    }

    public List<ReviewDTO> getAllReviews() {

        return reviewRepository.findAll()
                .stream()
                .map(reviewMapper::toDTO)
                .toList();
    }

    public List<ReviewDTO> getReviewsByItemId(Long itemId) {

        return reviewRepository.findByItemId(itemId)
                .stream()
                .map(reviewMapper::toDTO)
                .toList();
    }
    public Double getAverageRatingByItemId(Long itemId) {

        logger.info("Calculating average rating for item {}", itemId);

        List<Review> reviews = reviewRepository.findByItemId(itemId);

        if (reviews.isEmpty()) {
            return 0.0;
        }

        double sum = 0;

        for (Review review : reviews) {
            sum += review.getRating();
        }

        return sum / reviews.size();
    }

    public List<TopRatedItemDTO> getTopRatedItems() {

        logger.info("Fetching top rated items");

        return reviewRepository.getTopRatedItems();
    }
    public List<MostReviewedItemDTO> getMostReviewedItems() {

        logger.info("Fetching most reviewed items");

        return reviewRepository.getMostReviewedItems();
    }
    public ReviewDTO updateReview(Long id, ReviewDTO dto) {

        logger.info("Updating review with id: {}", id);

        Review review = reviewRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Review not found with id " + id));
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User loggedInUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (review.getUser() == null ||
                !review.getUser().getId().equals(loggedInUser.getId())) {

            throw new RuntimeException(
                    "You are not allowed to update this review."
            );
        }

        Item item = itemRepository.findById(dto.getItemId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Item not found with id " + dto.getItemId()));

        review.setRating(dto.getRating());
        review.setComment(dto.getComment());
        review.setItem(item);

        Review updatedReview = reviewRepository.save(review);

        logger.info("Review updated successfully with id: {}", updatedReview.getId());

        return reviewMapper.toDTO(updatedReview);
    }
    public void deleteReview(Long id) {

        logger.info("Deleting review with id: {}", id);

        Review review = reviewRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Review not found with id " + id));
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User loggedInUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (review.getUser() == null ||
                !review.getUser().getId().equals(loggedInUser.getId())) {

            throw new RuntimeException(
                    "You are not allowed to delete this review."
            );
        }

        reviewRepository.delete(review);

        logger.info("Review deleted successfully with id: {}", id);
    }


}
