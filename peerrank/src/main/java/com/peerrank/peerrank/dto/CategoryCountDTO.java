package com.peerrank.peerrank.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryCountDTO {

    private String category;

    private Long count;

}