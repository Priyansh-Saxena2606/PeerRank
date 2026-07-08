package com.peerrank.peerrank.dto;

public class TopRatedItemDTO {

    private String title;
    private Double averageRating;
    private String imageUrl;
    private String genre;
    private Integer releaseYear;
    private Long id;


    public TopRatedItemDTO(
            Long id,
            String title,
            Double averageRating,
            String imageUrl,
            String genre,
            Integer releaseYear) {

        this.title = title;
        this.averageRating = averageRating;
        this.imageUrl = imageUrl;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getGenre() {
        return genre;
    }
    public Long getId() {
        return id;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }
}