package com.senac.projectmanagement.controller;

import com.senac.projectmanagement.dto.RoleRequestDTO;
import com.senac.projectmanagement.dto.RoleResponseDTO;
import com.senac.projectmanagement.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/roles")
@Tag(name = "Role Management", description = "APIs for managing roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping
    @Operation(summary = "Create a new role")
    public ResponseEntity<RoleResponseDTO> createRole(@Valid @RequestBody RoleRequestDTO requestDTO) {
        RoleResponseDTO responseDTO = roleService.createRole(requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all roles")
    public ResponseEntity<List<RoleResponseDTO>> getAllRoles() {
        List<RoleResponseDTO> roles = roleService.getAllRolesDTO();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get role by ID")
    public ResponseEntity<RoleResponseDTO> getRoleById(@PathVariable Integer id) {
        return roleService.getRoleByIdDTO(id)
                .map(role -> new ResponseEntity<>(role, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update role")
    public ResponseEntity<RoleResponseDTO> updateRole(@PathVariable Integer id, @Valid @RequestBody RoleRequestDTO requestDTO) {
        try {
            RoleResponseDTO responseDTO = roleService.updateRole(id, requestDTO);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete role by ID")
    public ResponseEntity<Void> deleteRole(@PathVariable Integer id) {
        roleService.deleteRoleDTO(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
