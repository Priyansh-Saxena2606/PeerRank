package com.peerrank.peerrank.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ReviewDTO {

    private Long id;

    @NotNull
    @Min(1)
    @Max(5)
    private Integer rating;

    @NotBlank
    private String comment;

    @NotNull
    private Long itemId;

    private String itemTitle;
    private String username;
    private String imageUrl;

    public ReviewDTO() {
    }

    public ReviewDTO(Long id,
                     Integer rating,
                     String comment,
                     Long itemId,
                     String itemTitle,
                     String username,
                     String imageUrl) {

        this.id = id;
        this.rating = rating;
        this.comment = comment;
        this.itemId = itemId;
        this.itemTitle = itemTitle;
        this.username = username;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public Integer getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public Long getItemId() {
        return itemId;
    }

    public String getItemTitle() {
        return itemTitle;
    }
    public String getUsername() {
        return username;
    }
    public String getImageUrl() {
        return imageUrl;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}