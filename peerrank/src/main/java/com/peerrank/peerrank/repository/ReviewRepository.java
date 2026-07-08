package com.peerrank.peerrank.repository;
import com.peerrank.peerrank.dto.TopRatedItemDTO;
import org.springframework.data.jpa.repository.Query;
import com.peerrank.peerrank.dto.MostReviewedItemDTO;

import com.peerrank.peerrank.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository
        extends JpaRepository<Review, Long> {

    List<Review> findByItemId(Long itemId);
    boolean existsByUserIdAndItemId(Long userId, Long itemId);
    long countByUserId(Long userId);

    @Query("""
    SELECT new com.peerrank.peerrank.dto.TopRatedItemDTO(
        r.item.id,
        r.item.title,
        AVG(r.rating),
        r.item.imageUrl,
        r.item.genre,
        r.item.releaseYear
    )
    FROM Review r
    GROUP BY
        r.item.id,
        r.item.title,
        r.item.imageUrl,
        r.item.genre,
        r.item.releaseYear
    ORDER BY AVG(r.rating) DESC
""")
    List<TopRatedItemDTO> getTopRatedItems();

    @Query("""
SELECT new com.peerrank.peerrank.dto.MostReviewedItemDTO(
    r.item.id,
    r.item.title,
    r.item.imageUrl,
    r.item.genre,
    AVG(r.rating),
    COUNT(r)
)
FROM Review r
GROUP BY
    r.item.id,
    r.item.title,
    r.item.imageUrl,
    r.item.genre
ORDER BY COUNT(r) DESC
""")
    List<MostReviewedItemDTO> getMostReviewedItems();

    @Query("""
    SELECT COALESCE(AVG(r.rating), 0)
    FROM Review r
    WHERE r.user.id = :userId
""")
    Double getAverageRatingByUserId(@Param("userId") Long userId);
    List<Review> findByUserIdOrderByIdDesc(Long userId);
}