package com.senac.projectmanagement.service;

import com.senac.projectmanagement.entity.*;
import com.senac.projectmanagement.repository.CategoryRepository;
import com.senac.projectmanagement.repository.TransactionRepository;
import com.senac.projectmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public Transaction createTransaction(Long userId, Long categoryId, TransactionType transactionType, BigDecimal amount, String description, LocalDate transactionDate) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (user.isPresent() && category.isPresent()) {
            Transaction transaction = new Transaction(user.get(), category.get(), transactionType, amount, description, transactionDate);
            return transactionRepository.save(transaction);
        }
        throw new RuntimeException("Usuário ou Categoria não encontrada");
    }

    public List<Transaction> getTransactionsByUser(Long userId) {
        return transactionRepository.findByUser_UsersIdAndIsDeletedFalse(userId);
    }

    public List<Transaction> getTransactionsByUserAndDateRange(Long userId, LocalDate startDate, LocalDate endDate) {
        return transactionRepository.findByUser_UsersIdAndTransactionDateBetweenAndIsDeletedFalse(userId, startDate, endDate);
    }

    public Optional<Transaction> getTransactionById(Long id) {
        return transactionRepository.findById(id);
    }

    public Transaction updateTransaction(Long id, BigDecimal amount, String description, LocalDate transactionDate) {
        Optional<Transaction> optionalTransaction = transactionRepository.findById(id);
        if (optionalTransaction.isPresent()) {
            Transaction transaction = optionalTransaction.get();
            transaction.setAmount(amount);
            transaction.setDescription(description);
            transaction.setTransactionDate(transactionDate);
            return transactionRepository.save(transaction);
        }
        throw new RuntimeException("Transação não encontrada");
    }

    public void deleteTransaction(Long id) {
        Optional<Transaction> optionalTransaction = transactionRepository.findById(id);
        if (optionalTransaction.isPresent()) {
            Transaction transaction = optionalTransaction.get();
            transaction.setIsDeleted(true);
            transactionRepository.save(transaction);
        }
    }

    public Double getTotalIncome(Long userId) {
        return transactionRepository.sumAmountByUserAndType(userId, TransactionType.INCOME);
    }

    public Double getTotalExpense(Long userId) {
        return transactionRepository.sumAmountByUserAndType(userId, TransactionType.EXPENSE);
    }
}
