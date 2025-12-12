package com.senac.projectmanagement.dto;

import com.senac.projectmanagement.entity.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionRequestDTO {

    private Long userId;
    private Long categoryId;
    private TransactionType transactionType;
    private BigDecimal amount;
    private String currency;
    private String description;
    private String notes;
    private LocalDate transactionDate;

    // Constructors
    public TransactionRequestDTO() {}

    public TransactionRequestDTO(Long userId, Long categoryId, TransactionType transactionType, BigDecimal amount, String currency, String description, String notes, LocalDate transactionDate) {
        this.userId = userId;
        this.categoryId = categoryId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.currency = currency;
        this.description = description;
        this.notes = notes;
        this.transactionDate = transactionDate;
    }

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }
}
