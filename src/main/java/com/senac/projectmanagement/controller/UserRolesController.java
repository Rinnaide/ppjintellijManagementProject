package com.senac.projectmanagement.controller;

import com.senac.projectmanagement.dto.UserRolesRequestDTO;
import com.senac.projectmanagement.dto.UserRolesResponseDTO;
import com.senac.projectmanagement.service.UserRolesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/user-roles")
@Tag(name = "User Roles Management", description = "APIs for managing user roles")
public class UserRolesController {

    @Autowired
    private UserRolesService userRolesService;

    @PostMapping
    @Operation(summary = "Assign a role to a user")
    public ResponseEntity<UserRolesResponseDTO> assignRoleToUser(@Valid @RequestBody UserRolesRequestDTO requestDTO) {
        UserRolesResponseDTO responseDTO = userRolesService.assignRoleToUser(requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get roles by user ID")
    public ResponseEntity<List<UserRolesResponseDTO>> getRolesByUser(@PathVariable Long userId) {
        List<UserRolesResponseDTO> userRoles = userRolesService.getRolesByUserDTO(userId);
        return new ResponseEntity<>(userRoles, HttpStatus.OK);
    }

    @GetMapping("/role/{roleId}")
    @Operation(summary = "Get users by role ID")
    public ResponseEntity<List<UserRolesResponseDTO>> getUsersByRole(@PathVariable Integer roleId) {
        List<UserRolesResponseDTO> userRoles = userRolesService.getUsersByRoleDTO(roleId);
        return new ResponseEntity<>(userRoles, HttpStatus.OK);
    }

    @DeleteMapping
    @Operation(summary = "Remove role from user")
    public ResponseEntity<Void> removeRoleFromUser(@Valid @RequestBody UserRolesRequestDTO requestDTO) {
        userRolesService.removeRoleFromUser(requestDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
