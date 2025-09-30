package com.senac.projectmanagement.service;

import com.senac.projectmanagement.dto.UserRequestDTO;
import com.senac.projectmanagement.dto.UserResponseDTO;
import com.senac.projectmanagement.entity.User;
import com.senac.projectmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        if (userRepository.existsByEmail(userRequestDTO.getEmail())) {
            throw new RuntimeException("Email já existe");
        }
        User user = new User();
        user.setEmail(userRequestDTO.getEmail());
        user.setPasswordHash(passwordEncoder.encode(userRequestDTO.getPassword()));
        user.setFirstName(userRequestDTO.getFirstName());
        user.setLastName(userRequestDTO.getLastName());
        user.setPhone(userRequestDTO.getPhone());
        user.setDefaultCurrency(userRequestDTO.getDefaultCurrency() != null ? userRequestDTO.getDefaultCurrency() : "BRL");
        user.setTimezone(userRequestDTO.getTimezone() != null ? userRequestDTO.getTimezone() : "America/Sao_Paulo");
        user.setIsActive(userRequestDTO.getIsActive() != null ? userRequestDTO.getIsActive() : true);
        user.setEmailVerified(userRequestDTO.getEmailVerified() != null ? userRequestDTO.getEmailVerified() : false);

        User savedUser = userRepository.save(user);
        return mapToResponseDTO(savedUser);
    }

    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public Optional<UserResponseDTO> getUserById(Long id) {
        return userRepository.findById(id).map(this::mapToResponseDTO);
    }

    public Optional<UserResponseDTO> getUserByEmail(String email) {
        return userRepository.findByEmail(email).map(this::mapToResponseDTO);
    }

    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setFirstName(userRequestDTO.getFirstName());
            user.setLastName(userRequestDTO.getLastName());
            user.setPhone(userRequestDTO.getPhone());
            user.setDefaultCurrency(userRequestDTO.getDefaultCurrency());
            user.setTimezone(userRequestDTO.getTimezone());
            user.setIsActive(userRequestDTO.getIsActive());
            user.setEmailVerified(userRequestDTO.getEmailVerified());
            User updatedUser = userRepository.save(user);
            return mapToResponseDTO(updatedUser);
        }
        throw new RuntimeException("Usuário não encontrado");
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private UserResponseDTO mapToResponseDTO(User user) {
        return new UserResponseDTO(
                user.getUsersId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhone(),
                user.getDefaultCurrency(),
                user.getTimezone(),
                user.getIsActive(),
                user.getEmailVerified(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
