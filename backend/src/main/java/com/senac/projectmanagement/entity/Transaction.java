package com.senac.projectmanagement.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
public class Transaction {




    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long transactionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private TransactionType transactionType;

    @Column(name = "transaction_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal transactionAmount;

    @Column(name = "transaction_currency", length = 3)
    private String transactionCurrency = "BRL";

    @Column(name = "transaction_description", nullable = false, length = 255)
    private String transactionDescription;

    @Column(name = "transaction_notes", columnDefinition = "TEXT", nullable = false)
    private String transactionNotes;

    @Column(name = "transaction_date", nullable = false)
    private LocalDate transactionDate;

    @Column(name = "transaction_created_at", nullable = false)
    private LocalDateTime transactionCreatedAt;

    @Column(name = "transaction_updated_at", nullable = false)
    private LocalDateTime transactionUpdatedAt;

    @Column(name = "transaction_is_deleted")
    private Boolean transactionIsDeleted = false;

    // Constructors, getters, setters

    public Transaction() {}

    public Transaction(User user, Category category, TransactionType transactionType, BigDecimal transactionAmount, String transactionDescription, LocalDate transactionDate) {
        this.user = user;
        this.category = category;
        this.transactionType = transactionType;
        this.transactionAmount = transactionAmount;
        this.transactionDescription = transactionDescription;
        this.transactionDate = transactionDate;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionCurrency() {
        return transactionCurrency;
    }

    public void setTransactionCurrency(String transactionCurrency) {
        this.transactionCurrency = transactionCurrency;
    }

    public String getTransactionDescription() {
        return transactionDescription;
    }

    public void setTransactionDescription(String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }

    public String getTransactionNotes() {
        return transactionNotes;
    }

    public void setTransactionNotes(String transactionNotes) {
        this.transactionNotes = transactionNotes;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public LocalDateTime getTransactionCreatedAt() {
        return transactionCreatedAt;
    }

    public void setTransactionCreatedAt(LocalDateTime transactionCreatedAt) {
        this.transactionCreatedAt = transactionCreatedAt;
    }

    public LocalDateTime getTransactionUpdatedAt() {
        return transactionUpdatedAt;
    }

    public void setTransactionUpdatedAt(LocalDateTime transactionUpdatedAt) {
        this.transactionUpdatedAt = transactionUpdatedAt;
    }

    public Boolean getTransactionIsDeleted() {
        return transactionIsDeleted;
    }

    public void setTransactionIsDeleted(Boolean transactionIsDeleted) {
        this.transactionIsDeleted = transactionIsDeleted;
    }

    @PrePersist
    protected void onCreate() {
        transactionCreatedAt = LocalDateTime.now();
        transactionUpdatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        transactionUpdatedAt = LocalDateTime.now();
    }
}
