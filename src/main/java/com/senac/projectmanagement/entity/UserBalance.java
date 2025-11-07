<<<<<<< HEAD
package com.senac.projectmanagement.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_balance")
public class UserBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_balance_id")
    private Long userBalanceId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "user_balance_current_balance", precision = 15, scale = 2)
    private BigDecimal userBalanceCurrentBalance = BigDecimal.ZERO;

    @Column(name = "user_balance_total_income", precision = 15, scale = 2)
    private BigDecimal userBalanceTotalIncome = BigDecimal.ZERO;

    @Column(name = "user_balance_total_expense", precision = 15, scale = 2)
    private BigDecimal userBalanceTotalExpense = BigDecimal.ZERO;

    @Column(name = "user_balance_last_transaction_date")
    private LocalDate userBalanceLastTransactionDate;

    @Column(name = "user_balance_updated_at")
    private LocalDateTime userBalanceUpdatedAt;

    // Constructors, getters, setters

    public UserBalance() {}

    public UserBalance(User user) {
        this.user = user;
    }

    public Long getUserBalanceId() {
        return userBalanceId;
    }

    public void setUserBalanceId(Long userBalanceId) {
        this.userBalanceId = userBalanceId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getUserBalanceCurrentBalance() {
        return userBalanceCurrentBalance;
    }

    public void setUserBalanceCurrentBalance(BigDecimal userBalanceCurrentBalance) {
        this.userBalanceCurrentBalance = userBalanceCurrentBalance;
    }

    public BigDecimal getUserBalanceTotalIncome() {
        return userBalanceTotalIncome;
    }

    public void setUserBalanceTotalIncome(BigDecimal userBalanceTotalIncome) {
        this.userBalanceTotalIncome = userBalanceTotalIncome;
    }

    public BigDecimal getUserBalanceTotalExpense() {
        return userBalanceTotalExpense;
    }

    public void setUserBalanceTotalExpense(BigDecimal userBalanceTotalExpense) {
        this.userBalanceTotalExpense = userBalanceTotalExpense;
    }

    public LocalDate getUserBalanceLastTransactionDate() {
        return userBalanceLastTransactionDate;
    }

    public void setUserBalanceLastTransactionDate(LocalDate userBalanceLastTransactionDate) {
        this.userBalanceLastTransactionDate = userBalanceLastTransactionDate;
    }

    public LocalDateTime getUserBalanceUpdatedAt() {
        return userBalanceUpdatedAt;
    }

    public void setUserBalanceUpdatedAt(LocalDateTime userBalanceUpdatedAt) {
        this.userBalanceUpdatedAt = userBalanceUpdatedAt;
    }

    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        userBalanceUpdatedAt = LocalDateTime.now();
    }
}
=======
package com.senac.projectmanagement.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "User_Balances")
public class UserBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_balances_id")
    private Long userBalancesId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", nullable = false, unique = true)
    private User user;

    @Column(name = "current_balance", precision = 15, scale = 2)
    private BigDecimal currentBalance = BigDecimal.ZERO;

    @Column(name = "total_income", precision = 15, scale = 2)
    private BigDecimal totalIncome = BigDecimal.ZERO;

    @Column(name = "total_expense", precision = 15, scale = 2)
    private BigDecimal totalExpense = BigDecimal.ZERO;

    @Column(name = "last_transaction_date")
    private LocalDate lastTransactionDate;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // Constructors, getters, setters

    public UserBalance() {}

    public UserBalance(User user) {
        this.user = user;
    }

    public Long getUserBalancesId() {
        return userBalancesId;
    }

    public void setUserBalancesId(Long userBalancesId) {
        this.userBalancesId = userBalancesId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
>>>>>>> a93ef3251f7e8d9785c06f73e8935488a15f2178
