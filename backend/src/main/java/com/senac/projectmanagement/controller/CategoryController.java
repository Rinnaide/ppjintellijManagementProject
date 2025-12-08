package com.senac.projectmanagement.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.senac.projectmanagement.dto.CategoryRequestDTO;
import com.senac.projectmanagement.entity.Category;
import com.senac.projectmanagement.security.UserDetailsImpl;
import com.senac.projectmanagement.service.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Category> createCategory(@RequestBody CategoryRequestDTO categoryRequestDTO, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // Check if the userId in the request matches the authenticated user or if the user is admin
        if (!categoryRequestDTO.getUserId().equals(userDetails.getIdUsuario()) && !userDetails.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMINISTRATOR"))) {
            return ResponseEntity.status(403).build(); // Forbidden
        }
        Category createdCategory = categoryService.createCategory(categoryRequestDTO);
        return ResponseEntity.ok(createdCategory);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Category>> getCategoriesByUser(@PathVariable Long userId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // Check if the userId matches the authenticated user or if the user is admin
        if (!userId.equals(userDetails.getIdUsuario()) && !userDetails.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMINISTRATOR"))) {
            return ResponseEntity.status(403).build(); // Forbidden
        }
        List<Category> categories = categoryService.getCategoriesByUser(userId);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Optional<Category> categoryOpt = categoryService.getCategoryById(id);
        if (categoryOpt.isPresent()) {
            Category category = categoryOpt.get();
            // Check if the category belongs to the authenticated user or if the user is admin
            if (!category.getUser().getUserId().equals(userDetails.getIdUsuario()) && !userDetails.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMINISTRATOR"))) {
                return ResponseEntity.status(403).build(); // Forbidden
            }
            return ResponseEntity.ok(category);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody CategoryRequestDTO categoryRequestDTO, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // First, check if the category exists and belongs to the user
        Optional<Category> categoryOpt = categoryService.getCategoryById(id);
        if (categoryOpt.isPresent()) {
            Category category = categoryOpt.get();
            if (!category.getUser().getUserId().equals(userDetails.getIdUsuario()) && !userDetails.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMINISTRATOR"))) {
                return ResponseEntity.status(403).build(); // Forbidden
            }
        } else {
            return ResponseEntity.notFound().build();
        }
        try {
            Category updatedCategory = categoryService.updateCategory(id, categoryRequestDTO);
            return ResponseEntity.ok(updatedCategory);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // First, check if the category exists and belongs to the user
        Optional<Category> categoryOpt = categoryService.getCategoryById(id);
        if (categoryOpt.isPresent()) {
            Category category = categoryOpt.get();
            if (!category.getUser().getUserId().equals(userDetails.getIdUsuario()) && !userDetails.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMINISTRATOR"))) {
                return ResponseEntity.status(403).build(); // Forbidden
            }
        } else {
            return ResponseEntity.notFound().build();
        }
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
