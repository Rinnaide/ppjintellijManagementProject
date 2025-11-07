<<<<<<< HEAD
package com.senac.projectmanagement.repository;

import com.senac.projectmanagement.entity.Category;
import com.senac.projectmanagement.entity.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByUser_UserIdAndCategoryIsActiveTrue(Long userId);

    List<Category> findByCategoryTransactionTypeAndCategoryIsActiveTrue(TransactionType transactionType);

    List<Category> findByUser_UserIdAndCategoryTransactionTypeAndCategoryIsActiveTrue(Long userId, TransactionType transactionType);
}
=======
package com.senac.projectmanagement.repository;

import com.senac.projectmanagement.entity.Category;
import com.senac.projectmanagement.entity.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByUser_UsersIdAndIsActiveTrue(Long userId);

    List<Category> findByTransactionTypeAndIsActiveTrue(TransactionType transactionType);

    List<Category> findByUser_UsersIdAndTransactionTypeAndIsActiveTrue(Long userId, TransactionType transactionType);
}
>>>>>>> a93ef3251f7e8d9785c06f73e8935488a15f2178
