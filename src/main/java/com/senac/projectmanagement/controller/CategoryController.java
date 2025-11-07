package com.senac.projectmanagement.controller;

import com.senac.projectmanagement.dto.CategoryRequestDTO;
import com.senac.projectmanagement.entity.Category;
import com.senac.projectmanagement.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody CategoryRequestDTO categoryRequestDTO) {
        Category createdCategory = categoryService.createCategory(categoryRequestDTO);
        return ResponseEntity.ok(createdCategory);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Category>> getCategoriesByUser(@PathVariable Long userId) {
        List<Category> categories = categoryService.getCategoriesByUser(userId);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody CategoryRequestDTO categoryRequestDTO) {
        try {
            Category updatedCategory = categoryService.updateCategory(id, categoryRequestDTO);
            return ResponseEntity.ok(updatedCategory);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}