package com.peerrank.peerrank.dto;

public class DashboardDTO {

    private long totalCategories;
    private long totalItems;
    private long totalReviews;
    private double averageRating;
    private long totalUsers;

    public DashboardDTO(long totalCategories,
                        long totalItems,
                        long totalReviews,
                        double averageRating,
                        long totalUsers) {

        this.totalCategories = totalCategories;
        this.totalItems = totalItems;
        this.totalReviews = totalReviews;
        this.averageRating = averageRating;
        this.totalUsers = totalUsers;
    }

    public long getTotalCategories() {
        return totalCategories;
    }

    public long getTotalItems() {
        return totalItems;
    }

    public long getTotalReviews() {
        return totalReviews;
    }

    public double getAverageRating() {
        return averageRating;
    }
    public long getTotalUsers() {
        return totalUsers;
    }
}