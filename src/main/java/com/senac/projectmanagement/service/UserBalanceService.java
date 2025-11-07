package com.senac.projectmanagement.service;

import com.senac.projectmanagement.dto.UserBalanceRequestDTO;
import com.senac.projectmanagement.dto.UserBalanceResponseDTO;
import com.senac.projectmanagement.entity.UserBalance;
import com.senac.projectmanagement.entity.User;
import com.senac.projectmanagement.repository.UserBalanceRepository;
import com.senac.projectmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserBalanceService {

    @Autowired
    private UserBalanceRepository userBalanceRepository;

    @Autowired
    private UserRepository userRepository;

    public UserBalance createUserBalance(Long userId, BigDecimal balance) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            UserBalance userBalance = new UserBalance(user.get());
            userBalance.setUserBalanceCurrentBalance(balance);
            return userBalanceRepository.save(userBalance);
        }
        throw new RuntimeException("Usuário não encontrado");
    }

    public Optional<UserBalance> getUserBalanceByUser(Long userId) {
        return userBalanceRepository.findByUser_UserId(userId);
    }

    public UserBalance updateBalance(Long userId, BigDecimal newBalance) {
        Optional<UserBalance> optionalUserBalance = userBalanceRepository.findByUser_UserId(userId);
        if (optionalUserBalance.isPresent()) {
            UserBalance userBalance = optionalUserBalance.get();
            userBalance.setUserBalanceCurrentBalance(newBalance);
            userBalance.setUserBalanceUpdatedAt(LocalDateTime.now());
            return userBalanceRepository.save(userBalance);
        }
        throw new RuntimeException("UserBalance não encontrado");
    }

    public void deleteUserBalanceByUserId(Long userId) {
        Optional<UserBalance> optionalUserBalance = userBalanceRepository.findByUser_UserId(userId);
        if (optionalUserBalance.isPresent()) {
            userBalanceRepository.delete(optionalUserBalance.get());
        }
    }

    public UserBalanceResponseDTO createUserBalance(UserBalanceRequestDTO requestDTO) {
        Optional<User> user = userRepository.findById(requestDTO.getUserId());
        if (user.isPresent()) {
            UserBalance userBalance = new UserBalance(user.get());
            if (requestDTO.getCurrentBalance() != null) {
                userBalance.setUserBalanceCurrentBalance(requestDTO.getCurrentBalance());
            }
            if (requestDTO.getTotalIncome() != null) {
                userBalance.setUserBalanceTotalIncome(requestDTO.getTotalIncome());
            }
            if (requestDTO.getTotalExpense() != null) {
                userBalance.setUserBalanceTotalExpense(requestDTO.getTotalExpense());
            }
            if (requestDTO.getLastTransactionDate() != null) {
                userBalance.setUserBalanceLastTransactionDate(requestDTO.getLastTransactionDate());
            }
            UserBalance savedUserBalance = userBalanceRepository.save(userBalance);
            return convertToResponseDTO(savedUserBalance);
        }
        throw new RuntimeException("Usuário não encontrado");
    }

    public List<UserBalanceResponseDTO> getAllUserBalances() {
        List<UserBalance> userBalances = userBalanceRepository.findAll();
        return userBalances.stream().map(this::convertToResponseDTO).collect(Collectors.toList());
    }

    public Optional<UserBalanceResponseDTO> getUserBalanceById(Long id) {
        return userBalanceRepository.findById(id).map(this::convertToResponseDTO);
    }

    public UserBalanceResponseDTO updateUserBalance(Long id, UserBalanceRequestDTO requestDTO) {
        Optional<UserBalance> optionalUserBalance = userBalanceRepository.findById(id);
        if (optionalUserBalance.isPresent()) {
            UserBalance userBalance = optionalUserBalance.get();
            if (requestDTO.getCurrentBalance() != null) {
                userBalance.setUserBalanceCurrentBalance(requestDTO.getCurrentBalance());
            }
            if (requestDTO.getTotalIncome() != null) {
                userBalance.setUserBalanceTotalIncome(requestDTO.getTotalIncome());
            }
            if (requestDTO.getTotalExpense() != null) {
                userBalance.setUserBalanceTotalExpense(requestDTO.getTotalExpense());
            }
            if (requestDTO.getLastTransactionDate() != null) {
                userBalance.setUserBalanceLastTransactionDate(requestDTO.getLastTransactionDate());
            }
            UserBalance savedUserBalance = userBalanceRepository.save(userBalance);
            return convertToResponseDTO(savedUserBalance);
        }
        throw new RuntimeException("UserBalance não encontrado");
    }

    public void deleteUserBalance(Long id) {
        userBalanceRepository.deleteById(id);
    }

    private UserBalanceResponseDTO convertToResponseDTO(UserBalance userBalance) {
        UserBalanceResponseDTO dto = new UserBalanceResponseDTO();
        dto.setUserBalanceId(userBalance.getUserBalanceId());
        dto.setUserId(userBalance.getUser().getUserId());
        dto.setCurrentBalance(userBalance.getUserBalanceCurrentBalance());
        dto.setTotalIncome(userBalance.getUserBalanceTotalIncome());
        dto.setTotalExpense(userBalance.getUserBalanceTotalExpense());
        dto.setLastTransactionDate(userBalance.getUserBalanceLastTransactionDate());
        dto.setUpdatedAt(userBalance.getUserBalanceUpdatedAt());
        return dto;
    }
}
