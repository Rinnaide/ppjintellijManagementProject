package com.senac.projectmanagement.controller;

import com.senac.projectmanagement.dto.TransactionRequestDTO;
import com.senac.projectmanagement.dto.TransactionResponseDTO;
import com.senac.projectmanagement.entity.Transaction;
import com.senac.projectmanagement.security.UserDetailsImpl;
import com.senac.projectmanagement.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<TransactionResponseDTO> createTransaction(@RequestBody TransactionRequestDTO transactionRequestDTO, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // Check if the userId in the request matches the authenticated user or if the user is admin
        if (!transactionRequestDTO.getUserId().equals(userDetails.getIdUsuario()) && !userDetails.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMINISTRATOR"))) {
            return ResponseEntity.status(403).build(); // Forbidden
        }
        Transaction createdTransaction = transactionService.createTransaction(transactionRequestDTO);

        // Convert Entity to DTO before returning
        TransactionResponseDTO response = new TransactionResponseDTO(createdTransaction);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<TransactionResponseDTO>> getTransactionsByUser(@PathVariable Long userId, @RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "0") int offset, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // Check if the userId matches the authenticated user or if the user is admin
        if (!userId.equals(userDetails.getIdUsuario()) && !userDetails.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMINISTRATOR"))) {
            return ResponseEntity.status(403).build(); // Forbidden
        }
        List<Transaction> transactions = transactionService.getTransactionsByUser(userId, limit, offset);
        List<TransactionResponseDTO> response = transactions.stream()
                .map(TransactionResponseDTO::new) // Chama o construtor para cada transação
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}/date-range")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<TransactionResponseDTO>> getTransactionsByUserAndDateRange(@PathVariable Long userId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // Check if the userId matches the authenticated user or if the user is admin
        if (!userId.equals(userDetails.getIdUsuario()) && !userDetails.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMINISTRATOR"))) {
            return ResponseEntity.status(403).build(); // Forbidden
        }
        List<Transaction> transactions = transactionService.getTransactionsByUserAndDateRange(userId, startDate, endDate);
        List<TransactionResponseDTO> response = transactions.stream()
                .map(TransactionResponseDTO::new)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<TransactionResponseDTO> getTransactionById(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Optional<Transaction> transactionOpt = transactionService.getTransactionById(id);
        if (transactionOpt.isPresent()) {
            Transaction transaction = transactionOpt.get();
            // Check if the transaction belongs to the authenticated user or if the user is admin
            if (!transaction.getUser().getUserId().equals(userDetails.getIdUsuario()) && !userDetails.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMINISTRATOR"))) {
                return ResponseEntity.status(403).build(); // Forbidden
            }
            return ResponseEntity.ok(new TransactionResponseDTO(transaction));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<TransactionResponseDTO> updateTransaction(@PathVariable Long id, @RequestBody TransactionRequestDTO transactionRequestDTO, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // First, check if the transaction exists and belongs to the user
        Optional<Transaction> transactionOpt = transactionService.getTransactionById(id);
        if (transactionOpt.isPresent()) {
            Transaction transaction = transactionOpt.get();
            if (!transaction.getUser().getUserId().equals(userDetails.getIdUsuario()) && !userDetails.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMINISTRATOR"))) {
                return ResponseEntity.status(403).build(); // Forbidden
            }
        } else {
            return ResponseEntity.notFound().build();
        }
        try {
            Transaction updatedTransaction = transactionService.updateTransaction(id, transactionRequestDTO);
            return ResponseEntity.ok(new TransactionResponseDTO(updatedTransaction));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // First, check if the transaction exists and belongs to the user
        Optional<Transaction> transactionOpt = transactionService.getTransactionById(id);
        if (transactionOpt.isPresent()) {
            Transaction transaction = transactionOpt.get();
            if (!transaction.getUser().getUserId().equals(userDetails.getIdUsuario()) && !userDetails.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMINISTRATOR"))) {
                return ResponseEntity.status(403).build(); // Forbidden
            }
        } else {
            return ResponseEntity.notFound().build();
        }
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}/total-income")
    public ResponseEntity<Double> getTotalIncome(@PathVariable Long userId) {
        Double totalIncome = transactionService.getTotalIncome(userId);
        return ResponseEntity.ok(totalIncome);
    }

    @GetMapping("/user/{userId}/total-expense")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Double> getTotalExpense(@PathVariable Long userId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // Check if the userId matches the authenticated user or if the user is admin
        if (!userId.equals(userDetails.getIdUsuario()) && !userDetails.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMINISTRATOR"))) {
            return ResponseEntity.status(403).build(); // Forbidden
        }
        Double totalExpense = transactionService.getTotalExpense(userId);
        return ResponseEntity.ok(totalExpense);
    }

    @GetMapping("/user/{userId}/filtered")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<TransactionResponseDTO>> getFilteredTransactions(
            @PathVariable Long userId,
            @RequestParam(required = false) String searchQuery,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // Check if the userId matches the authenticated user or if the user is admin
        if (!userId.equals(userDetails.getIdUsuario()) && !userDetails.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMINISTRATOR"))) {
            return ResponseEntity.status(403).build(); // Forbidden
        }
        List<Transaction> transactions = transactionService.getFilteredTransactions(userId, searchQuery, categoryId, type, startDate, endDate);
        List<TransactionResponseDTO> response = transactions.stream()
                .map(TransactionResponseDTO::new)
                .toList();
        return ResponseEntity.ok(response);
    }
}
