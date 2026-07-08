package com.peerrank.peerrank.service;

import com.peerrank.peerrank.dto.ItemDTO;
import com.peerrank.peerrank.entity.Category;
import com.peerrank.peerrank.entity.Item;
import com.peerrank.peerrank.exception.ResourceNotFoundException;
import com.peerrank.peerrank.mapper.ItemMapper;
import com.peerrank.peerrank.repository.CategoryRepository;
import com.peerrank.peerrank.repository.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final ItemMapper itemMapper;

    private static final Logger logger =
            LoggerFactory.getLogger(ItemService.class);

    public ItemService(ItemRepository itemRepository,
                       CategoryRepository categoryRepository,
                       ItemMapper itemMapper) {

        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
        this.itemMapper = itemMapper;
    }

    public ItemDTO createItem(ItemDTO dto) {

        logger.info("Creating item: {}", dto.getTitle());

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found with id " + dto.getCategoryId()));

        Item item = new Item();
        item.setTitle(dto.getTitle());
        item.setDescription(dto.getDescription());

        item.setImageUrl(dto.getImageUrl());
        item.setGenre(dto.getGenre());
        item.setReleaseYear(dto.getReleaseYear());

        item.setCategory(category);

        Item savedItem = itemRepository.save(item);

        logger.info("Item created successfully with id: {}", savedItem.getId());

        return itemMapper.toDTO(savedItem);
    }

    public List<ItemDTO> getAllItems() {

        return itemRepository.findAll()
                .stream()
                .map(itemMapper::toDTO)
                .toList();
    }
    public ItemDTO getItemById(Long id) {

        logger.info("Fetching item with id: {}", id);

        Item item = itemRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Item not found with id " + id));

        return itemMapper.toDTO(item);
    }
    public ItemDTO getItemByTitle(String title) {

        logger.info("Searching item with title: {}", title);

        Item item = itemRepository.findByTitle(title)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Item not found with title " + title));

        return itemMapper.toDTO(item);
    }
    public Page<ItemDTO> getItems(Pageable pageable) {

        logger.info("Fetching items page {}", pageable.getPageNumber());

        return itemRepository.findAll(pageable)
                .map(itemMapper::toDTO);
    }

    public ItemDTO updateItem(Long id, ItemDTO dto) {

        logger.info("Updating item with id: {}", id);

        Item item = itemRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Item not found with id " + id));

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found with id " + dto.getCategoryId()));

        item.setTitle(dto.getTitle());
        item.setDescription(dto.getDescription());

        item.setImageUrl(dto.getImageUrl());
        item.setGenre(dto.getGenre());
        item.setReleaseYear(dto.getReleaseYear());

        item.setCategory(category);

        Item updatedItem = itemRepository.save(item);

        logger.info("Item updated successfully with id: {}", updatedItem.getId());

        return itemMapper.toDTO(updatedItem);
    }

    public void deleteItem(Long id) {

        logger.info("Deleting item with id: {}", id);

        Item item = itemRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Item not found with id " + id));

        itemRepository.delete(item);

        logger.info("Item deleted successfully with id: {}", id);
    }
    public List<ItemDTO> getItemsByCategory(Long categoryId) {

        return itemRepository.findByCategoryId(categoryId)
                .stream()
                .map(itemMapper::toDTO)
                .toList();

    }
    public List<ItemDTO> searchItems(String query) {

        logger.info("Searching items with query: {}", query);

        return itemRepository
                .findByTitleContainingIgnoreCase(query)
                .stream()
                .map(itemMapper::toDTO)
                .toList();
    }



}