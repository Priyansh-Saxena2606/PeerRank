package com.peerrank.peerrank.service;

import com.peerrank.peerrank.dto.DashboardDTO;
import com.peerrank.peerrank.entity.Review;
import com.peerrank.peerrank.repository.CategoryRepository;
import com.peerrank.peerrank.repository.ItemRepository;
import com.peerrank.peerrank.repository.ReviewRepository;
import com.peerrank.peerrank.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {

    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    public DashboardService(CategoryRepository categoryRepository,
                            ItemRepository itemRepository,
                            ReviewRepository reviewRepository,
                            UserRepository userRepository) {

        this.categoryRepository = categoryRepository;
        this.itemRepository = itemRepository;
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }

    public DashboardDTO getDashboard() {

        long categories = categoryRepository.count();
        long items = itemRepository.count();
        long reviews = reviewRepository.count();

        List<Review> reviewList = reviewRepository.findAll();

        double average = reviewList.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0);
        long users = userRepository.count();

        return new DashboardDTO(
                categories,
                items,
                reviews,
                average,
                users
        );
    }
}