package com.peerrank.peerrank.controller;

import com.peerrank.peerrank.dto.RegisterRequest;
import com.peerrank.peerrank.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.peerrank.peerrank.dto.AuthResponse;
import com.peerrank.peerrank.dto.LoginRequest;
import com.peerrank.peerrank.dto.RegisterResponse;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public RegisterResponse register(@RequestBody RegisterRequest request) {

        return authService.register(request);

    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {

        return authService.login(request);

    }

}