package com.peerrank.peerrank.repository;

import com.peerrank.peerrank.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import com.peerrank.peerrank.dto.CategoryCountDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FavoriteRepository
        extends JpaRepository<Favorite, Long> {

    boolean existsByUserIdAndItemId(Long userId, Long itemId);

    Optional<Favorite> findByUserIdAndItemId(Long userId, Long itemId);

    List<Favorite> findByUserId(Long userId);

    @Query("""
       SELECT new com.peerrank.peerrank.dto.CategoryCountDTO(
           f.item.category.name,
           COUNT(f)
       )
       FROM Favorite f
       WHERE f.user.id = :userId
       GROUP BY f.item.category.name
       ORDER BY COUNT(f) DESC
       """)
    List<CategoryCountDTO> getFavoriteCategories(@Param("userId") Long userId);

}