package com.peerrank.peerrank.controller;

import com.peerrank.peerrank.dto.ItemDTO;
import com.peerrank.peerrank.response.ApiResponse;
import com.peerrank.peerrank.response.ApiResponseBuilder;
import com.peerrank.peerrank.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ItemDTO>> createItem(
            @Valid @RequestBody ItemDTO itemDTO) {

        ApiResponse<ItemDTO> response =
                ApiResponseBuilder.success(
                        "Item created successfully",
                        itemService.createItem(itemDTO)
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ItemDTO>>> getAllItems() {

        ApiResponse<List<ItemDTO>> response =
                ApiResponseBuilder.success(
                        "Items fetched successfully",
                        itemService.getAllItems()
                );

        return ResponseEntity.ok(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ItemDTO>> getItemById(
            @PathVariable Long id) {

        ApiResponse<ItemDTO> response =
                ApiResponseBuilder.success(
                        "Item fetched successfully",
                        itemService.getItemById(id)
                );

        return ResponseEntity.ok(response);
    }
    @GetMapping("/page")
    public ResponseEntity<ApiResponse<Page<ItemDTO>>> getItems(
            Pageable pageable) {

        ApiResponse<Page<ItemDTO>> response =
                ApiResponseBuilder.success(
                        "Items fetched successfully",
                        itemService.getItems(pageable)
                );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ItemDTO>> updateItem(
            @PathVariable Long id,
            @Valid @RequestBody ItemDTO itemDTO) {

        ApiResponse<ItemDTO> response =
                ApiResponseBuilder.success(
                        "Item updated successfully",
                        itemService.updateItem(id, itemDTO)
                );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteItem(
            @PathVariable Long id) {

        itemService.deleteItem(id);

        ApiResponse<Object> response =
                ApiResponseBuilder.success(
                        "Item deleted successfully",
                        null
                );

        return ResponseEntity.ok(response);
    }
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse<List<ItemDTO>>> getItemsByCategory(
            @PathVariable Long categoryId) {

        ApiResponse<List<ItemDTO>> response =
                ApiResponseBuilder.success(
                        "Items fetched successfully",
                        itemService.getItemsByCategory(categoryId)
                );

        return ResponseEntity.ok(response);

    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<ItemDTO>>> searchItems(
            @RequestParam String query
    ) {

        return ResponseEntity.ok(

                ApiResponseBuilder.success(

                        "Items fetched successfully",

                        itemService.searchItems(query)

                )

        );

    }
}