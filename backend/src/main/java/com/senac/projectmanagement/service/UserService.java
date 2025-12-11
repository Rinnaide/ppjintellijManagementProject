package com.senac.projectmanagement.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.senac.projectmanagement.dto.UserLoginRequestDTO;
import com.senac.projectmanagement.dto.UserLoginResponseDTO;
import com.senac.projectmanagement.dto.UserRequestDTO;
import com.senac.projectmanagement.dto.UserResponseDTO;
import com.senac.projectmanagement.entity.User;
import com.senac.projectmanagement.repository.UserRepository;
import com.senac.projectmanagement.security.JwtService;
import com.senac.projectmanagement.security.UserDetailsImpl;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;


    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        if (userRepository.existsByUserEmail(userRequestDTO.getEmail())) {
            throw new RuntimeException("Email já existe");
        }
        User user = new User();
        user.setUserEmail(userRequestDTO.getEmail());
        user.setUserPasswordHash(passwordEncoder.encode(userRequestDTO.getPassword()));
        user.setUserFirstName(userRequestDTO.getFirstName());
        user.setUserLastName(userRequestDTO.getLastName());
        user.setUserPhone(userRequestDTO.getPhone());
        user.setUserDefaultCurrency(userRequestDTO.getDefaultCurrency() != null ? userRequestDTO.getDefaultCurrency() : "BRL");
        user.setUserTimezone(userRequestDTO.getTimezone() != null ? userRequestDTO.getTimezone() : "America/Sao_Paulo");
        user.setUserIsActive(userRequestDTO.getIsActive() != null ? userRequestDTO.getIsActive() : true);
        user.setUserEmailVerified(userRequestDTO.getEmailVerified() != null ? userRequestDTO.getEmailVerified() : false);

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
        return userRepository.findByUserEmail(email).map(this::mapToResponseDTO);
    }

    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setUserFirstName(userRequestDTO.getFirstName());
            user.setUserLastName(userRequestDTO.getLastName());
            user.setUserPhone(userRequestDTO.getPhone());
            user.setUserDefaultCurrency(userRequestDTO.getDefaultCurrency());
            user.setUserTimezone(userRequestDTO.getTimezone());
            user.setUserIsActive(userRequestDTO.getIsActive());
            user.setUserEmailVerified(userRequestDTO.getEmailVerified());
            User updatedUser = userRepository.save(user);
            return mapToResponseDTO(updatedUser);
        }
        throw new RuntimeException("Usuário não encontrado");
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public UserLoginResponseDTO login(UserLoginRequestDTO userLoginRequestDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userLoginRequestDTO.getUsuarioEmail(),
                            userLoginRequestDTO.getUsuario_senha()
                    )
            );

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            String token = jwtService.generateToken(userDetails.getUsername());

            UserLoginResponseDTO response = new UserLoginResponseDTO();
            response.setUsuario_id(userDetails.getIdUsuario());
            response.setUsuario_nome(userDetails.getNomeUsuario());
            response.setUsuario_token(token);
            return response;

        } catch (Exception e) {
            throw new RuntimeException("Auth Failed: " + e.getMessage());
        }
    }

    private UserResponseDTO mapToResponseDTO(User user) {
        return new UserResponseDTO(
                user.getUserId(),
                user.getUserEmail(),
                user.getUserFirstName(),
                user.getUserLastName(),
                user.getUserPhone(),
                user.getUserDefaultCurrency(),
                user.getUserTimezone(),
                user.getUserIsActive(),
                user.getUserEmailVerified(),
                user.getUserCreatedAt(),
                user.getUserUpdatedAt()
        );
    }
}
