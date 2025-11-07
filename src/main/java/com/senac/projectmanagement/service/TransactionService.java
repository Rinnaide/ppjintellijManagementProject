package com.senac.projectmanagement.service;

import com.senac.projectmanagement.dto.TransactionRequestDTO;
import com.senac.projectmanagement.entity.*;
import com.senac.projectmanagement.repository.CategoryRepository;
import com.senac.projectmanagement.repository.TransactionRepository;
import com.senac.projectmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Transaction createTransaction(TransactionRequestDTO transactionRequestDTO) {
        Optional<User> user = userRepository.findById(transactionRequestDTO.getUserId());
        Optional<Category> category = categoryRepository.findById(transactionRequestDTO.getCategoryId());
        if (user.isPresent() && category.isPresent()) {
            Transaction transaction = new Transaction(user.get(), category.get(), transactionRequestDTO.getTransactionType(), transactionRequestDTO.getAmount(), transactionRequestDTO.getDescription(), transactionRequestDTO.getTransactionDate());
            transaction.setTransactionCurrency(transactionRequestDTO.getCurrency() != null ? transactionRequestDTO.getCurrency() : "BRL");
            transaction.setTransactionNotes(transactionRequestDTO.getNotes());
            return transactionRepository.save(transaction);
        }
        throw new RuntimeException("Usuário ou Categoria não encontrada");
    }

    public List<Transaction> getTransactionsByUser(Long userId) {
        return transactionRepository.findByUser_UserIdAndTransactionIsDeletedFalse(userId);
    }

    public List<Transaction> getTransactionsByUserAndDateRange(Long userId, LocalDate startDate, LocalDate endDate) {
        return transactionRepository.findByUser_UserIdAndTransactionDateBetweenAndTransactionIsDeletedFalse(userId, startDate, endDate);
    }

    public Optional<Transaction> getTransactionById(Long id) {
        return transactionRepository.findById(id);
    }

    public Transaction updateTransaction(Long id, TransactionRequestDTO transactionRequestDTO) {
        Optional<Transaction> optionalTransaction = transactionRepository.findById(id);
        if (optionalTransaction.isPresent()) {
            Transaction transaction = optionalTransaction.get();
            transaction.setTransactionAmount(transactionRequestDTO.getAmount());
            transaction.setTransactionDescription(transactionRequestDTO.getDescription());
            transaction.setTransactionDate(transactionRequestDTO.getTransactionDate());
            transaction.setTransactionCurrency(transactionRequestDTO.getCurrency() != null ? transactionRequestDTO.getCurrency() : transaction.getTransactionCurrency());
            transaction.setTransactionNotes(transactionRequestDTO.getNotes());
            return transactionRepository.save(transaction);
        }
        throw new RuntimeException("Transação não encontrada");
    }

    public void deleteTransaction(Long id) {
        Optional<Transaction> optionalTransaction = transactionRepository.findById(id);
        if (optionalTransaction.isPresent()) {
            Transaction transaction = optionalTransaction.get();
            transaction.setTransactionIsDeleted(true);
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
