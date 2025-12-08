package com.senac.projectmanagement.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserBalanceResponseDTO {

    private Long userBalanceId;
    private Long userId;
    private BigDecimal currentBalance;
    private BigDecimal totalIncome;
    private BigDecimal totalExpense;
    private LocalDate lastTransactionDate;
    private LocalDateTime updatedAt;

    // Constructors, getters, setters

    public UserBalanceResponseDTO() {}

    public UserBalanceResponseDTO(Long userBalanceId, Long userId, BigDecimal currentBalance, BigDecimal totalIncome, BigDecimal totalExpense, LocalDate lastTransactionDate, LocalDateTime updatedAt) {
        this.userBalanceId = userBalanceId;
        this.userId = userId;
        this.currentBalance = currentBalance;
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
        this.lastTransactionDate = lastTransactionDate;
        this.updatedAt = updatedAt;
    }

    public Long getUserBalanceId() {
        return userBalanceId;
    }

    public void setUserBalanceId(Long userBalanceId) {
        this.userBalanceId = userBalanceId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    public BigDecimal getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(BigDecimal totalIncome) {
        this.totalIncome = totalIncome;
    }

    public BigDecimal getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(BigDecimal totalExpense) {
        this.totalExpense = totalExpense;
    }

    public LocalDate getLastTransactionDate() {
        return lastTransactionDate;
    }

    public void setLastTransactionDate(LocalDate lastTransactionDate) {
        this.lastTransactionDate = lastTransactionDate;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
