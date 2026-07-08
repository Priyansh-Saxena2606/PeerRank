package com.peerrank.peerrank.service;

import com.peerrank.peerrank.dto.CategoryCountDTO;
import com.peerrank.peerrank.dto.FavoriteDTO;
import com.peerrank.peerrank.entity.Favorite;
import com.peerrank.peerrank.entity.Item;
import com.peerrank.peerrank.entity.User;
import com.peerrank.peerrank.repository.FavoriteRepository;
import com.peerrank.peerrank.repository.ItemRepository;
import com.peerrank.peerrank.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void addFavorite(Long itemId) {

        User user = getCurrentUser();

        if (favoriteRepository.existsByUserIdAndItemId(user.getId(), itemId)) {
            return;
        }

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() ->
                        new RuntimeException("Item not found"));

        Favorite favorite = Favorite.builder()
                .user(user)
                .item(item)
                .build();

        favoriteRepository.save(favorite);
    }

    public void removeFavorite(Long itemId) {

        User user = getCurrentUser();

        Favorite favorite = favoriteRepository
                .findByUserIdAndItemId(user.getId(), itemId)
                .orElseThrow(() ->
                        new RuntimeException("Favorite not found"));

        favoriteRepository.delete(favorite);
    }

    public boolean isFavorite(Long itemId) {

        User user = getCurrentUser();

        return favoriteRepository
                .existsByUserIdAndItemId(user.getId(), itemId);
    }

    public List<FavoriteDTO> getFavorites() {

        User user = getCurrentUser();

        return favoriteRepository.findByUserId(user.getId())
                .stream()
                .map(favorite -> FavoriteDTO.builder()
                        .id(favorite.getId())
                        .itemId(favorite.getItem().getId())
                        .itemTitle(favorite.getItem().getTitle())
                        .imageUrl(favorite.getItem().getImageUrl())
                        .category(favorite.getItem().getCategory().getName())
                        .build())
                .toList();
    }
    public List<CategoryCountDTO> getFavoriteCategories() {

        User user = getCurrentUser();

        return favoriteRepository.getFavoriteCategories(user.getId());

    }
}