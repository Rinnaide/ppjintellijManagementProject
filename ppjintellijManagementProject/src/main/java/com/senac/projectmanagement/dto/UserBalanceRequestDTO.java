package com.senac.projectmanagement.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class UserBalanceRequestDTO {

    private Long userId;
    private BigDecimal currentBalance;
    private BigDecimal totalIncome;
    private BigDecimal totalExpense;
    private LocalDate lastTransactionDate;

    // Constructors, getters, setters

    public UserBalanceRequestDTO() {}

    public UserBalanceRequestDTO(Long userId, BigDecimal currentBalance, BigDecimal totalIncome, BigDecimal totalExpense, LocalDate lastTransactionDate) {
        this.userId = userId;
        this.currentBalance = currentBalance;
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
        this.lastTransactionDate = lastTransactionDate;
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
}
