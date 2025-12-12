package com.senac.projectmanagement.dto;

import com.senac.projectmanagement.entity.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.senac.projectmanagement.entity.Transaction;
public class TransactionResponseDTO {

    private Long transactionId;
    private Long userId;
    private Long categoryId;
    private TransactionType transactionType;
    private BigDecimal amount;
    private String currency;
    private String description;
    private String notes;
    private LocalDate transactionDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isDeleted;

    // Constructors, getters, setters

    public TransactionResponseDTO() {}

    public TransactionResponseDTO(Long transactionId, Long userId, Long categoryId, TransactionType transactionType, BigDecimal amount, String currency, String description, String notes, LocalDate transactionDate, LocalDateTime createdAt, LocalDateTime updatedAt, Boolean isDeleted) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.categoryId = categoryId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.currency = currency;
        this.description = description;
        this.notes = notes;
        this.transactionDate = transactionDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isDeleted = isDeleted;
    }

    public TransactionResponseDTO(Transaction t) {
        this.transactionId = t.getTransactionId();
        this.amount = t.getTransactionAmount();
        this.description = t.getTransactionDescription();
        this.transactionType = t.getTransactionType();
        this.userId = t.getUser().getUserId();
        this.categoryId = t.getCategory().getCategoryId();
        this.currency = t.getTransactionCurrency();
        this.notes = t.getTransactionNotes();
        this.transactionDate = t.getTransactionDate();
        this.createdAt = t.getTransactionCreatedAt();
        this.updatedAt = t.getTransactionUpdatedAt();
        this.isDeleted = t.getTransactionIsDeleted();
    }
    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

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

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}
