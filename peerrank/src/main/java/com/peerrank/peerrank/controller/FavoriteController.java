package com.peerrank.peerrank.controller;

import com.peerrank.peerrank.dto.FavoriteDTO;
import com.peerrank.peerrank.response.ApiResponse;
import com.peerrank.peerrank.response.ApiResponseBuilder;
import com.peerrank.peerrank.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.peerrank.peerrank.dto.CategoryCountDTO;

import java.util.List;

@RestController
@RequestMapping("/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping("/{itemId}")
    public ResponseEntity<ApiResponse<Void>> addFavorite(
            @PathVariable Long itemId) {

        favoriteService.addFavorite(itemId);

        return ResponseEntity.ok(
                ApiResponseBuilder.success(
                        "Added to favorites",
                        null
                )
        );
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<ApiResponse<Void>> removeFavorite(
            @PathVariable Long itemId) {

        favoriteService.removeFavorite(itemId);

        return ResponseEntity.ok(
                ApiResponseBuilder.success(
                        "Removed from favorites",
                        null
                )
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<FavoriteDTO>>> getFavorites() {

        return ResponseEntity.ok(
                ApiResponseBuilder.success(
                        "Favorites fetched successfully",
                        favoriteService.getFavorites()
                )
        );
    }

    @GetMapping("/categories")
    public ResponseEntity<ApiResponse<List<CategoryCountDTO>>> getFavoriteCategories() {

        return ResponseEntity.ok(
                ApiResponseBuilder.success(
                        "Favorite categories fetched successfully",
                        favoriteService.getFavoriteCategories()
                )
        );

    }


    @GetMapping("/{itemId}")
    public ResponseEntity<ApiResponse<Boolean>> isFavorite(
            @PathVariable Long itemId) {

        return ResponseEntity.ok(
                ApiResponseBuilder.success(
                        "Favorite status fetched",
                        favoriteService.isFavorite(itemId)
                )
        );
    }

}