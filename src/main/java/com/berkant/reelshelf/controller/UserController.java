package com.berkant.reelshelf.controller;

import com.berkant.reelshelf.dto.AuthResponse;
import com.berkant.reelshelf.dto.UserDTO;
import com.berkant.reelshelf.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid UserDTO userDTO) {
        userService.register(userDTO);
        return new ResponseEntity<>("Registration successful", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid UserDTO userDTO) {
        String token = userService.login(userDTO);
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
