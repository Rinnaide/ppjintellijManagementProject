package com.senac.projectmanagement.controller;

import com.senac.projectmanagement.dto.TransactionRequestDTO;
import com.senac.projectmanagement.dto.TransactionResponseDTO;
import com.senac.projectmanagement.entity.Transaction;
import com.senac.projectmanagement.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> createTransaction(@RequestBody TransactionRequestDTO transactionRequestDTO) {
        Transaction createdTransaction = transactionService.createTransaction(transactionRequestDTO);

        // Convert Entity to DTO before returning
        TransactionResponseDTO response = new TransactionResponseDTO(createdTransaction);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TransactionResponseDTO>> getTransactionsByUser(@PathVariable Long userId) {
        List<Transaction> transactions = transactionService.getTransactionsByUser(userId);
        List<TransactionResponseDTO> response = transactions.stream()
                .map(TransactionResponseDTO::new) // Chama o construtor para cada transação
                .toList();
        return ResponseEntity.ok(response);
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
    public ResponseEntity<Transaction> updateTransaction(@PathVariable Long id, @RequestBody TransactionRequestDTO transactionRequestDTO) {
        try {
            Transaction updatedTransaction = transactionService.updateTransaction(id, transactionRequestDTO);
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
