package com.senac.projectmanagement.controller;

import com.senac.projectmanagement.entity.Transaction;
import com.senac.projectmanagement.entity.TransactionType;
import com.senac.projectmanagement.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestParam Long userId, @RequestParam Long categoryId, @RequestParam TransactionType transactionType, @RequestParam BigDecimal amount, @RequestParam String description, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate transactionDate) {
        Transaction createdTransaction = transactionService.createTransaction(userId, categoryId, transactionType, amount, description, transactionDate);
        return ResponseEntity.ok(createdTransaction);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Transaction>> getTransactionsByUser(@PathVariable Long userId) {
        List<Transaction> transactions = transactionService.getTransactionsByUser(userId);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/user/{userId}/date-range")
    public ResponseEntity<List<Transaction>> getTransactionsByUserAndDateRange(@PathVariable Long userId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Transaction> transactions = transactionService.getTransactionsByUserAndDateRange(userId, startDate, endDate);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
        return transactionService.getTransactionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable Long id, @RequestParam BigDecimal amount, @RequestParam String description, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate transactionDate) {
        try {
            Transaction updatedTransaction = transactionService.updateTransaction(id, amount, description, transactionDate);
            return ResponseEntity.ok(updatedTransaction);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}/total-income")
    public ResponseEntity<Double> getTotalIncome(@PathVariable Long userId) {
        Double totalIncome = transactionService.getTotalIncome(userId);
        return ResponseEntity.ok(totalIncome);
    }

    @GetMapping("/user/{userId}/total-expense")
    public ResponseEntity<Double> getTotalExpense(@PathVariable Long userId) {
        Double totalExpense = transactionService.getTotalExpense(userId);
        return ResponseEntity.ok(totalExpense);
    }
}
