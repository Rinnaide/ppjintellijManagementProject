package com.senac.projectmanagement.service;

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

    public Category createCategory(Long userId, String name, String description, TransactionType transactionType) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            Category category = new Category(user.get(), name, transactionType);
            category.setDescription(description);
            return categoryRepository.save(category);
        }
        throw new RuntimeException("Usuário não encontrado");
    }

    public List<Category> getCategoriesByUser(Long userId) {
        return categoryRepository.findByUser_UsersIdAndIsActiveTrue(userId);
    }

    public List<Category> getCategoriesByType(TransactionType transactionType) {
        return categoryRepository.findByTransactionTypeAndIsActiveTrue(transactionType);
    }

    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public Category updateCategory(Long id, String name, String description, String iconName, String colorHex) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            category.setName(name);
            category.setDescription(description);
            category.setIconName(iconName);
            category.setColorHex(colorHex);
            return categoryRepository.save(category);
        }
        throw new RuntimeException("Categoria não encontrada");
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
