package com.peerrank.peerrank.mapper;

import com.peerrank.peerrank.dto.CategoryDTO;
import com.peerrank.peerrank.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toEntity(CategoryDTO dto) {

        Category category = new Category();
        category.setName(dto.getName());

        return category;
    }

    public CategoryDTO toDTO(Category category) {

        return new CategoryDTO(
                category.getId(),
                category.getName()
        );
    }
}