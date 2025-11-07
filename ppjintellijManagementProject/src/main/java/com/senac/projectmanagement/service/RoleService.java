package com.senac.projectmanagement.service;

import com.senac.projectmanagement.dto.RoleRequestDTO;
import com.senac.projectmanagement.dto.RoleResponseDTO;
import com.senac.projectmanagement.entity.Role;
import com.senac.projectmanagement.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role createRole(Integer roleId, String roleName) {
        Role role = new Role(roleId, roleName);
        return roleRepository.save(role);
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Optional<Role> getRoleById(Integer id) {
        return roleRepository.findById(id);
    }

    public Optional<Role> getRoleByName(String roleName) {
        return Optional.ofNullable(roleRepository.findByRoleName(roleName));
    }

    public Role updateRole(Integer id, String roleName) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        if (optionalRole.isPresent()) {
            Role role = optionalRole.get();
            role.setRoleName(roleName);
            return roleRepository.save(role);
        }
        throw new RuntimeException("Role não encontrada");
    }

    public void deleteRole(Integer id) {
        roleRepository.deleteById(id);
    }

    public RoleResponseDTO createRole(RoleRequestDTO requestDTO) {
        Role role = new Role(requestDTO.getRoleId(), requestDTO.getRoleName());
        Role savedRole = roleRepository.save(role);
        return convertToResponseDTO(savedRole);
    }

    public List<RoleResponseDTO> getAllRolesDTO() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream().map(this::convertToResponseDTO).collect(Collectors.toList());
    }

    public Optional<RoleResponseDTO> getRoleByIdDTO(Integer id) {
        return roleRepository.findById(id).map(this::convertToResponseDTO);
    }

    public RoleResponseDTO updateRole(Integer id, RoleRequestDTO requestDTO) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        if (optionalRole.isPresent()) {
            Role role = optionalRole.get();
            role.setRoleName(requestDTO.getRoleName());
            Role savedRole = roleRepository.save(role);
            return convertToResponseDTO(savedRole);
        }
        throw new RuntimeException("Role não encontrada");
    }

    public void deleteRoleDTO(Integer id) {
        roleRepository.deleteById(id);
    }

    private RoleResponseDTO convertToResponseDTO(Role role) {
        RoleResponseDTO dto = new RoleResponseDTO();
        dto.setRoleId(role.getRoleId());
        dto.setRoleName(role.getRoleName());
        return dto;
    }
}
