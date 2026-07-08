package com.peerrank.peerrank.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserReviewDTO {

    private Long id;
    private Long itemId;
    private String itemTitle;
    private Integer rating;
    private String comment;
}