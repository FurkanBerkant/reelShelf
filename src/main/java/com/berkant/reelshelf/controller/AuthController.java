package com.berkant.reelshelf.controller;

import com.berkant.reelshelf.dto.AuthResponse;
import com.berkant.reelshelf.dto.UserDTO;
import com.berkant.reelshelf.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDTO userDTO) {
        authService.register(userDTO);
        return new ResponseEntity<>("Registration successful", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody UserDTO userDTO) {
        String token = authService.login(userDTO);
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
