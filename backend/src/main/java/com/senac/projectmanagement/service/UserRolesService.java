package com.senac.projectmanagement.service;

import com.senac.projectmanagement.dto.UserRolesRequestDTO;
import com.senac.projectmanagement.dto.UserRolesResponseDTO;
import com.senac.projectmanagement.entity.User;
import com.senac.projectmanagement.entity.Role;
import com.senac.projectmanagement.entity.UserRoles;
import com.senac.projectmanagement.repository.UserRepository;
import com.senac.projectmanagement.repository.RoleRepository;
import com.senac.projectmanagement.repository.UserRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserRolesService {

    @Autowired
    private UserRolesRepository userRolesRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public UserRoles assignRoleToUser(Long userId, Integer roleId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Role> role = roleRepository.findById(roleId);
        if (user.isPresent() && role.isPresent()) {
            UserRoles userRoles = new UserRoles(user.get(), role.get());
            return userRolesRepository.save(userRoles);
        }
        throw new RuntimeException("Usuário ou Role não encontrado");
    }

    public List<UserRoles> getRolesByUser(Long userId) {
        return userRolesRepository.findByUser_UserId(userId);
    }

    public List<UserRoles> getUsersByRole(Integer roleId) {
        return userRolesRepository.findByRole_RoleId(roleId);
    }

    public void removeRoleFromUser(Long userId, Integer roleId) {
        Optional<UserRoles> userRoles = userRolesRepository.findByUser_UserIdAndRole_RoleId(userId, roleId);
        if (userRoles.isPresent()) {
            userRolesRepository.delete(userRoles.get());
        }
    }

    public UserRolesResponseDTO assignRoleToUser(UserRolesRequestDTO requestDTO) {
        Optional<User> user = userRepository.findById(requestDTO.getUserId());
        Optional<Role> role = roleRepository.findById(requestDTO.getRoleId());
        if (user.isPresent() && role.isPresent()) {
            UserRoles userRoles = new UserRoles(user.get(), role.get());
            UserRoles savedUserRoles = userRolesRepository.save(userRoles);
            return convertToResponseDTO(savedUserRoles);
        }
        throw new RuntimeException("Usuário ou Role não encontrado");
    }

    public List<UserRolesResponseDTO> getRolesByUserDTO(Long userId) {
        List<UserRoles> userRoles = userRolesRepository.findByUser_UserId(userId);
        return userRoles.stream().map(this::convertToResponseDTO).collect(Collectors.toList());
    }

    public List<UserRolesResponseDTO> getUsersByRoleDTO(Integer roleId) {
        List<UserRoles> userRoles = userRolesRepository.findByRole_RoleId(roleId);
        return userRoles.stream().map(this::convertToResponseDTO).collect(Collectors.toList());
    }

    public void removeRoleFromUser(UserRolesRequestDTO requestDTO) {
        Optional<UserRoles> userRoles = userRolesRepository.findByUser_UserIdAndRole_RoleId(requestDTO.getUserId(), requestDTO.getRoleId());
        if (userRoles.isPresent()) {
            userRolesRepository.delete(userRoles.get());
        }
    }

    private UserRolesResponseDTO convertToResponseDTO(UserRoles userRoles) {
        UserRolesResponseDTO dto = new UserRolesResponseDTO();
        dto.setUserId(userRoles.getUser().getUserId());
        dto.setUserEmail(userRoles.getUser().getUserEmail());
        dto.setRoleId(userRoles.getRole().getRoleId());
        dto.setRoleName(userRoles.getRole().getRoleName());
        return dto;
    }
}
