package com.senac.projectmanagement.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.senac.projectmanagement.dto.UserLoginRequestDTO;
import com.senac.projectmanagement.dto.UserLoginResponseDTO;
import com.senac.projectmanagement.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDTO> login(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");

        UserLoginRequestDTO requestDTO = new UserLoginRequestDTO();
        requestDTO.setUsuarioEmail(email);
        requestDTO.setUsuario_senha(password);

        UserLoginResponseDTO response = userService.login(requestDTO);
        return ResponseEntity.ok(response);
    }
}
