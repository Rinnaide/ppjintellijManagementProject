<<<<<<< HEAD
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
=======
package com.senac.projectmanagement.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categories_id")
    private Long categoriesId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private User user;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "icon_name", length = 50)
    private String iconName = "default";

    @Column(name = "color_hex", length = 7)
    private String colorHex = "#6B7280";

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type")
    private TransactionType transactionType = TransactionType.BOTH;

    @Column(name = "is_predefined")
    private Boolean isPredefined = false;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // Constructors, getters, setters

    public Category() {}

    public Category(User user, String name, TransactionType transactionType) {
        this.user = user;
        this.name = name;
        this.transactionType = transactionType;
    }

    public Long getCategoriesId() {
        return categoriesId;
    }

    public void setCategoriesId(Long categoriesId) {
        this.categoriesId = categoriesId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public String getColorHex() {
        return colorHex;
    }

    public void setColorHex(String colorHex) {
        this.colorHex = colorHex;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Boolean getIsPredefined() {
        return isPredefined;
    }

    public void setIsPredefined(Boolean isPredefined) {
        this.isPredefined = isPredefined;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
>>>>>>> a93ef3251f7e8d9785c06f73e8935488a15f2178
