package com.peerrank.peerrank.service;

import com.peerrank.peerrank.dto.ReviewDTO;
import com.peerrank.peerrank.dto.UserProfileDTO;
import com.peerrank.peerrank.entity.User;
import com.peerrank.peerrank.mapper.ReviewMapper;
import com.peerrank.peerrank.repository.ReviewRepository;
import com.peerrank.peerrank.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.peerrank.peerrank.dto.UserReviewDTO;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    public UserProfileDTO getMyProfile() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return UserProfileDTO.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .joinedAt(user.getCreatedAt())
                .reviewCount(
                        reviewRepository.countByUserId(user.getId())
                )
                .averageRatingGiven(
                        reviewRepository.getAverageRatingByUserId(user.getId())
                )
                .build();
    }
    public List<ReviewDTO> getMyReviews() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return reviewRepository
                .findByUserIdOrderByIdDesc(user.getId())
                .stream()
                .map(reviewMapper::toDTO)
                .toList();
    }
}