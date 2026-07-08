package com.peerrank.peerrank.mapper;

import com.peerrank.peerrank.dto.ItemDTO;
import com.peerrank.peerrank.entity.Item;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {

    public ItemDTO toDTO(Item item) {

        return new ItemDTO(
                item.getId(),
                item.getTitle(),
                item.getDescription(),
                item.getImageUrl(),
                item.getGenre(),
                item.getReleaseYear(),
                item.getCategory().getId(),
                item.getCategory().getName()
        );
    }
}