package com.senac.projectmanagement.repository;

import com.senac.projectmanagement.entity.Transaction;
import com.senac.projectmanagement.entity.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByUser_UsersIdAndIsDeletedFalse(Long userId);

    List<Transaction> findByUser_UsersIdAndTransactionDateBetweenAndIsDeletedFalse(Long userId, LocalDate startDate, LocalDate endDate);

    List<Transaction> findByUser_UsersIdAndTransactionTypeAndIsDeletedFalse(Long userId, TransactionType transactionType);

    @Query("SELECT t FROM Transaction t WHERE t.user.usersId = :userId AND t.category.categoriesId = :categoryId AND t.isDeleted = false")
    List<Transaction> findByUserAndCategory(@Param("userId") Long userId, @Param("categoryId") Long categoryId);

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.user.usersId = :userId AND t.transactionType = :type AND t.isDeleted = false")
    Double sumAmountByUserAndType(@Param("userId") Long userId, @Param("type") TransactionType type);
}
