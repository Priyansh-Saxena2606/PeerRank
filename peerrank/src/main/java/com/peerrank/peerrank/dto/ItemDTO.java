package com.peerrank.peerrank.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ItemDTO {

    private Long id;
    private String imageUrl;

    private String genre;

    private Integer releaseYear;

    @NotBlank(message = "Title cannot be blank")
    @Size(min = 2, max = 100)
    private String title;

    @Size(max = 1000)
    private String description;

    @NotNull(message = "Category is required")
    private Long categoryId;

    private String categoryName;

    public ItemDTO() {
    }

    public ItemDTO(Long id,
                   String title,
                   String description,
                   String imageUrl,
                   String genre,
                   Integer releaseYear,
                   Long categoryId,
                   String categoryName) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getGenre() {
        return genre;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }





}