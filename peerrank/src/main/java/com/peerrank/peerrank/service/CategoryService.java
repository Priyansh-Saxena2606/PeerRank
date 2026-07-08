package com.peerrank.peerrank.service;

import com.peerrank.peerrank.dto.ItemDTO;
import com.peerrank.peerrank.entity.Category;
import com.peerrank.peerrank.exception.DuplicateResourceException;
import com.peerrank.peerrank.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import com.peerrank.peerrank.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.peerrank.peerrank.dto.CategoryDTO;
import com.peerrank.peerrank.mapper.CategoryMapper;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    private static final Logger logger =
            LoggerFactory.getLogger(CategoryService.class);

    public CategoryService(CategoryRepository categoryRepository,
                           CategoryMapper categoryMapper) {

        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public CategoryDTO createCategory(CategoryDTO dto) {

        if (categoryRepository.findByName(dto.getName()).isPresent()) {
            throw new DuplicateResourceException(
                    "Category '" + dto.getName() + "' already exists."
            );
        }
        Category category = categoryMapper.toEntity(dto);

        logger.info("Creating category: {}", category.getName());

        Category savedCategory = categoryRepository.save(category);

        logger.info("Category created successfully with id: {}", savedCategory.getId());

        return categoryMapper.toDTO(savedCategory);
    }

    public List<CategoryDTO> getAllCategories() {

        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toDTO)
                .toList();
    }
    public CategoryDTO getCategoryById(Long id) {

        logger.info("Fetching category with id: {}", id);

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found with id " + id));

        return categoryMapper.toDTO(category);
    }
    public CategoryDTO getCategoryByName(String name) {

        logger.info("Searching category with name: {}", name);

        Category category = categoryRepository.findByName(name)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found with name: " + name));

        return categoryMapper.toDTO(category);
    }
    public Page<CategoryDTO> getCategories(Pageable pageable) {

        logger.info("Fetching categories page {}", pageable.getPageNumber());

        return categoryRepository.findAll(pageable)
                .map(categoryMapper::toDTO);
    }
    public CategoryDTO updateCategory(Long id, CategoryDTO dto) {

        logger.info("Updating category with id: {}", id);

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found with id " + id));

        categoryRepository.findByName(dto.getName())
                .ifPresent(existingCategory -> {
                    if (!existingCategory.getId().equals(id)) {
                        throw new DuplicateResourceException(
                                "Category '" + dto.getName() + "' already exists."
                        );
                    }
                });

        category.setName(dto.getName());

        Category updatedCategory = categoryRepository.save(category);

        logger.info("Category updated successfully with id: {}", updatedCategory.getId());

        return categoryMapper.toDTO(updatedCategory);
    }
    public void deleteCategory(Long id) {

        logger.info("Deleting category with id: {}", id);

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found with id " + id));

        categoryRepository.delete(category);

        logger.info("Category deleted successfully with id: {}", id);
    }


}