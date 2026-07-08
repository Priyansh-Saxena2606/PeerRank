package com.peerrank.peerrank.controller;


import com.peerrank.peerrank.dto.CategoryDTO;
import com.peerrank.peerrank.response.ApiResponseBuilder;
import com.peerrank.peerrank.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.peerrank.peerrank.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;


import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @PostMapping
    public ResponseEntity<ApiResponse<CategoryDTO>> createCategory(
            @Valid @RequestBody CategoryDTO categoryDTO) {

        CategoryDTO responseDTO = categoryService.createCategory(categoryDTO);

        ApiResponse<CategoryDTO> response =
                ApiResponseBuilder.success(
                        "Category created successfully",
                        responseDTO
                );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryDTO>>> getAllCategories() {

        ApiResponse<List<CategoryDTO>> response =
                ApiResponseBuilder.success(
                        "Categories fetched successfully",
                        categoryService.getAllCategories()
                );

        return ResponseEntity.ok(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryDTO>> getCategoryById(@PathVariable Long id) {

        ApiResponse<CategoryDTO> response =
                ApiResponseBuilder.success(
                        "Category fetched successfully",
                        categoryService.getCategoryById(id)
                );

        return ResponseEntity.ok(response);
    }


    @GetMapping("/search")
    public ResponseEntity<ApiResponse<CategoryDTO>> getCategoryByName(
            @RequestParam String name) {

        ApiResponse<CategoryDTO> response =
                ApiResponseBuilder.success(
                        "Category fetched successfully",
                        categoryService.getCategoryByName(name)
                );

        return ResponseEntity.ok(response);
    }


    @GetMapping("/page")
    public ResponseEntity<ApiResponse<Page<CategoryDTO>>> getCategories(
            Pageable pageable) {

        ApiResponse<Page<CategoryDTO>> response =
                ApiResponseBuilder.success(
                        "Categories fetched successfully",
                        categoryService.getCategories(pageable)
                );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryDTO>> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryDTO categoryDTO) {

        ApiResponse<CategoryDTO> response =
                ApiResponseBuilder.success(
                        "Category updated successfully",
                        categoryService.updateCategory(id, categoryDTO)
                );

        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteCategory(
            @PathVariable Long id) {

        categoryService.deleteCategory(id);

        ApiResponse<Object> response =
                ApiResponseBuilder.success(
                        "Category deleted successfully",
                        null
                );

        return ResponseEntity.ok(response);
    }
}