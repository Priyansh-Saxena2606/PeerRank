package com.peerrank.peerrank.mapper;

import com.peerrank.peerrank.dto.ReviewDTO;
import com.peerrank.peerrank.entity.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    public ReviewDTO toDTO(Review review) {

        return new ReviewDTO(
                review.getId(),
                review.getRating(),
                review.getComment(),
                review.getItem().getId(),
                review.getItem().getTitle(),
                review.getUser() != null
                        ? review.getUser().getUsername()
                        : "Unknown User",
                review.getItem().getImageUrl()
        );
    }
}