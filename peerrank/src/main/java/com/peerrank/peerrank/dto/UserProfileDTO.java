package com.peerrank.peerrank.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileDTO {

    private String username;
    private String email;
    private LocalDateTime joinedAt;

    private long reviewCount;
    private double averageRatingGiven;
}