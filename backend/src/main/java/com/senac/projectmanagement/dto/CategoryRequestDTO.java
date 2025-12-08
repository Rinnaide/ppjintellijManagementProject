package com.senac.projectmanagement.dto;

import com.senac.projectmanagement.entity.TransactionType;

public class CategoryRequestDTO {

    private Long userId;
    private String categoryName;
    private String categoryDescription;
    private TransactionType categoryTransactionType;
    private String categoryIconName;
    private String categoryColorHex;

    // Constructors, getters, setters

    public CategoryRequestDTO() {}

    public CategoryRequestDTO(Long userId, String categoryName, String categoryDescription, TransactionType categoryTransactionType, String categoryIconName, String categoryColorHex) {
        this.userId = userId;
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
        this.categoryTransactionType = categoryTransactionType;
        this.categoryIconName = categoryIconName;
        this.categoryColorHex = categoryColorHex;
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

    public TransactionType getCategoryTransactionType() {
        return categoryTransactionType;
    }

    public void setCategoryTransactionType(TransactionType categoryTransactionType) {
        this.categoryTransactionType = categoryTransactionType;
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
}
