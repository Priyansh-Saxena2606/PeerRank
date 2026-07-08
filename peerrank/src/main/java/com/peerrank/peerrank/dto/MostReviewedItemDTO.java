package com.peerrank.peerrank.dto;

public class MostReviewedItemDTO {

    private Long id;
    private String title;
    private String imageUrl;
    private String genre;
    private Double averageRating;
    private Long reviewCount;

    public MostReviewedItemDTO(Long id,
                               String title,
                               String imageUrl,
                               String genre,
                               Double averageRating,
                               Long reviewCount) {

        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.genre = genre;
        this.averageRating = averageRating;
        this.reviewCount = reviewCount;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getGenre() {
        return genre;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public Long getReviewCount() {
        return reviewCount;
    }
}