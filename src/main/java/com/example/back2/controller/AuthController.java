package com.example.back2.controller;

import com.example.back2.dto.AuthenticationRequest;
import com.example.back2.dto.AuthenticationResponse;
import com.example.back2.dto.RegisterRequest;
import com.example.back2.dto.RegisterResponse;
import com.example.back2.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Kayıt endpoint'i
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        RegisterResponse response = null;
        try {
            response = authService.register(request);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());

        }
        return ResponseEntity.ok(response);
    }

    // Giriş endpoint'i
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse response = null;  // Login işlemi
        try {
            response = authService.authenticate(request);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }
}
