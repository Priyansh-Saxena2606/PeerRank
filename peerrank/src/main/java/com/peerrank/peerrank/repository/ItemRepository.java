package com.peerrank.peerrank.repository;

import java.util.List;
import java.util.Optional;
import com.peerrank.peerrank.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Optional<Item> findByTitle(String title);

    List<Item> findByCategoryId(Long categoryId);
    List<Item> findByTitleContainingIgnoreCase(String title);
}