package com.senac.projectmanagement.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "category_name", nullable = false, length = 100)
    private String categoryName;

    @Column(name = "category_description", columnDefinition = "TEXT", nullable = false)
    private String categoryDescription;

    @Column(name = "category_icon_name", length = 50)
    private String categoryIconName = "default";

    @Column(name = "category_color_hex", length = 7)
    private String categoryColorHex = "#667280";

    @Enumerated(EnumType.STRING)
    @Column(name = "category_transaction_type")
    private TransactionType categoryTransactionType = TransactionType.BOTH;

    @Column(name = "category_is_predefined")
    private Boolean categoryIsPredefined = false;

    @Column(name = "category_is_active")
    private Boolean categoryIsActive = true;

    @Column(name = "category_created_at", nullable = false)
    private LocalDateTime categoryCreatedAt;

    @Column(name = "category_updated_at", nullable = false)
    private LocalDateTime categoryUpdatedAt;

    // Constructors, getters, setters

    public Category() {}

    public Category(User user, String categoryName, TransactionType categoryTransactionType) {
        this.user = user;
        this.categoryName = categoryName;
        this.categoryTransactionType = categoryTransactionType;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    @PrePersist
    protected void onCreate() {
        categoryCreatedAt = LocalDateTime.now();
        categoryUpdatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        categoryUpdatedAt = LocalDateTime.now();
    }
}