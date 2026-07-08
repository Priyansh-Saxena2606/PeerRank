package com.peerrank.peerrank.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoriteDTO {

    private Long id;

    private Long itemId;

    private String itemTitle;

    private String imageUrl;

    private String category;

}