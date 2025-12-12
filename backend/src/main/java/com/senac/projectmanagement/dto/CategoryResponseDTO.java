package com.senac.projectmanagement.dto;

import com.senac.projectmanagement.entity.Category;
import com.senac.projectmanagement.entity.TransactionType;
import java.time.LocalDateTime;

public class CategoryResponseDTO {

    private Long categoryId;
    private Long userId;
    private String categoryName;
    private String categoryDescription;
    private String categoryIconName;
    private String categoryColorHex;
    private TransactionType categoryTransactionType;
    private Boolean categoryIsPredefined;
    private Boolean categoryIsActive;
    private LocalDateTime categoryCreatedAt;
    private LocalDateTime categoryUpdatedAt;

    public CategoryResponseDTO() {
    }

    public CategoryResponseDTO(Category category) {
        this.categoryId = category.getCategoryId();


        if (category.getUser() != null) {
            this.userId = category.getUser().getUserId();
        }

        this.categoryName = category.getCategoryName();
        this.categoryDescription = category.getCategoryDescription();
        this.categoryIconName = category.getCategoryIconName();
        this.categoryColorHex = category.getCategoryColorHex();
        this.categoryTransactionType = category.getCategoryTransactionType();
        this.categoryIsPredefined = category.getCategoryIsPredefined();
        this.categoryIsActive = category.getCategoryIsActive();
        this.categoryCreatedAt = category.getCategoryCreatedAt();
        this.categoryUpdatedAt = category.getCategoryUpdatedAt();
    }

    // 3. Getters e Setters manuais

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public String getCategoryIconName() {
        return categoryIconName;
    }

    public void setCategoryIconName(String categoryIconName) {
        this.categoryIconName = categoryIconName;
    }

    public String getCategoryColorHex() {
        return categoryColorHex;
    }

    public void setCategoryColorHex(String categoryColorHex) {
        this.categoryColorHex = categoryColorHex;
    }

    public TransactionType getCategoryTransactionType() {
        return categoryTransactionType;
    }

    public void setCategoryTransactionType(TransactionType categoryTransactionType) {
        this.categoryTransactionType = categoryTransactionType;
    }

    public Boolean getCategoryIsPredefined() {
        return categoryIsPredefined;
    }

    public void setCategoryIsPredefined(Boolean categoryIsPredefined) {
        this.categoryIsPredefined = categoryIsPredefined;
    }

    public Boolean getCategoryIsActive() {
        return categoryIsActive;
    }

    public void setCategoryIsActive(Boolean categoryIsActive) {
        this.categoryIsActive = categoryIsActive;
    }

    public LocalDateTime getCategoryCreatedAt() {
        return categoryCreatedAt;
    }

    public void setCategoryCreatedAt(LocalDateTime categoryCreatedAt) {
        this.categoryCreatedAt = categoryCreatedAt;
    }

    public LocalDateTime getCategoryUpdatedAt() {
        return categoryUpdatedAt;
    }

    public void setCategoryUpdatedAt(LocalDateTime categoryUpdatedAt) {
        this.categoryUpdatedAt = categoryUpdatedAt;
    }
}