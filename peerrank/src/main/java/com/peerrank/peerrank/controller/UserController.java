package com.peerrank.peerrank.controller;

import com.peerrank.peerrank.dto.ReviewDTO;
import com.peerrank.peerrank.dto.UserProfileDTO;
import com.peerrank.peerrank.dto.UserReviewDTO;
import com.peerrank.peerrank.response.ApiResponse;
import com.peerrank.peerrank.response.ApiResponseBuilder;
import com.peerrank.peerrank.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me/reviews")
    public ResponseEntity<ApiResponse<List<ReviewDTO>>> getMyReviews() {

        return ResponseEntity.ok(

                ApiResponseBuilder.success(
                        "Reviews fetched successfully",
                        userService.getMyReviews()
                )

        );
    }


    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserProfileDTO>> getMyProfile() {

        return ResponseEntity.ok(

                ApiResponseBuilder.success(
                        "Profile fetched successfully",
                        userService.getMyProfile()
                )

        );
    }
}