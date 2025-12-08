package com.senac.projectmanagement.controller;

import com.senac.projectmanagement.dto.UserBalanceRequestDTO;
import com.senac.projectmanagement.dto.UserBalanceResponseDTO;
import com.senac.projectmanagement.service.UserBalanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/user-balances")
@Tag(name = "User Balance Management", description = "APIs for managing user balances")
public class UserBalanceController {

    @Autowired
    private UserBalanceService userBalanceService;

    @PostMapping
    @Operation(summary = "Create a new user balance")
    public ResponseEntity<UserBalanceResponseDTO> createUserBalance(@Valid @RequestBody UserBalanceRequestDTO requestDTO) {
        UserBalanceResponseDTO responseDTO = userBalanceService.createUserBalance(requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all user balances")
    public ResponseEntity<List<UserBalanceResponseDTO>> getAllUserBalances() {
        List<UserBalanceResponseDTO> userBalances = userBalanceService.getAllUserBalances();
        return new ResponseEntity<>(userBalances, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user balance by ID")
    public ResponseEntity<UserBalanceResponseDTO> getUserBalanceById(@PathVariable Long id) {
        return userBalanceService.getUserBalanceById(id)
                .map(userBalance -> new ResponseEntity<>(userBalance, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update user balance")
    public ResponseEntity<UserBalanceResponseDTO> updateUserBalance(@PathVariable Long id, @Valid @RequestBody UserBalanceRequestDTO requestDTO) {
        try {
            UserBalanceResponseDTO responseDTO = userBalanceService.updateUserBalance(id, requestDTO);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user balance by ID")
    public ResponseEntity<Void> deleteUserBalance(@PathVariable Long id) {
        userBalanceService.deleteUserBalance(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
