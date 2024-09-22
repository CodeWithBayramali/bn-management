package com.codewithali.bnmanagement.controller;

import com.codewithali.bnmanagement.dto.UserDto;
import com.codewithali.bnmanagement.dto.request.CreateUserRequest;
import com.codewithali.bnmanagement.dto.request.LoginRequest;
import com.codewithali.bnmanagement.dto.response.SuccessReponse;
import com.codewithali.bnmanagement.dto.response.TokenResponse;
import com.codewithali.bnmanagement.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.authenticate(loginRequest));
    }

    @GetMapping("/getUser/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") String id) {
        return ResponseEntity.ok(authService.getUser(id));
    }

    @PostMapping("/register")
    public ResponseEntity<SuccessReponse> register(@RequestBody CreateUserRequest createUserRequest) {
        return ResponseEntity.ok(authService.registerUser(createUserRequest));
    }

}
