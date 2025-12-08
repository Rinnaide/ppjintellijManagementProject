package com.senac.projectmanagement.service;

import com.senac.projectmanagement.dto.CategoryRequestDTO;
import com.senac.projectmanagement.entity.Category;
import com.senac.projectmanagement.entity.TransactionType;
import com.senac.projectmanagement.entity.User;
import com.senac.projectmanagement.repository.CategoryRepository;
import com.senac.projectmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    public Category createCategory(CategoryRequestDTO categoryRequestDTO) {
        Optional<User> user = userRepository.findById(categoryRequestDTO.getUserId());
        if (user.isPresent()) {
            Category category = new Category(user.get(), categoryRequestDTO.getCategoryName(), categoryRequestDTO.getCategoryTransactionType());
            category.setCategoryDescription(categoryRequestDTO.getCategoryDescription());
            category.setCategoryIconName(categoryRequestDTO.getCategoryIconName());
            category.setCategoryColorHex(categoryRequestDTO.getCategoryColorHex());
            return categoryRepository.save(category);
        }
        throw new RuntimeException("Usuário não encontrado");
    }

    public List<Category> getCategoriesByUser(Long userId) {
        return categoryRepository.findByUser_UserIdAndCategoryIsActiveTrue(userId);
    }

    public List<Category> getCategoriesByType(TransactionType transactionType) {
        return categoryRepository.findByCategoryTransactionTypeAndCategoryIsActiveTrue(transactionType);
    }

    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public Category updateCategory(Long id, CategoryRequestDTO categoryRequestDTO) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            category.setCategoryName(categoryRequestDTO.getCategoryName());
            category.setCategoryDescription(categoryRequestDTO.getCategoryDescription());
            category.setCategoryIconName(categoryRequestDTO.getCategoryIconName());
            category.setCategoryColorHex(categoryRequestDTO.getCategoryColorHex());
            return categoryRepository.save(category);
        }
        throw new RuntimeException("Categoria não encontrada");
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
