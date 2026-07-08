package com.peerrank.peerrank.controller;

import com.peerrank.peerrank.dto.RegisterRequest;
import com.peerrank.peerrank.entity.User;
import com.peerrank.peerrank.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.peerrank.peerrank.dto.AuthResponse;
import com.peerrank.peerrank.dto.LoginRequest;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public User register(@RequestBody RegisterRequest request) {

        return authService.register(request);

    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {

        return authService.login(request);

    }

}