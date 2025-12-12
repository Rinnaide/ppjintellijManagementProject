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
    public ResponseEntity<List<TransactionResponseDTO>> getTransactionsByUser(@PathVariable Long userId, @RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "0") int offset) {
        List<Transaction> transactions = transactionService.getTransactionsByUser(userId, limit, offset);
        List<TransactionResponseDTO> response = transactions.stream()
                .map(TransactionResponseDTO::new) // Chama o construtor para cada transação
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}/date-range")
    public ResponseEntity<List<TransactionResponseDTO>> getTransactionsByUserAndDateRange(@PathVariable Long userId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Transaction> transactions = transactionService.getTransactionsByUserAndDateRange(userId, startDate, endDate);
        List<TransactionResponseDTO> response = transactions.stream()
                .map(TransactionResponseDTO::new)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponseDTO> getTransactionById(@PathVariable Long id) {
        return transactionService.getTransactionById(id)
                .map(transaction -> ResponseEntity.ok(new TransactionResponseDTO(transaction)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionResponseDTO> updateTransaction(@PathVariable Long id, @RequestBody TransactionRequestDTO transactionRequestDTO) {
        try {
            Transaction updatedTransaction = transactionService.updateTransaction(id, transactionRequestDTO);
            return ResponseEntity.ok(new TransactionResponseDTO(updatedTransaction));
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

    @GetMapping("/user/{userId}/filtered")
    public ResponseEntity<List<TransactionResponseDTO>> getFilteredTransactions(
            @PathVariable Long userId,
            @RequestParam(required = false) String searchQuery,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Transaction> transactions = transactionService.getFilteredTransactions(userId, searchQuery, categoryId, type, startDate, endDate);
        List<TransactionResponseDTO> response = transactions.stream()
                .map(TransactionResponseDTO::new)
                .toList();
        return ResponseEntity.ok(response);
    }
}
