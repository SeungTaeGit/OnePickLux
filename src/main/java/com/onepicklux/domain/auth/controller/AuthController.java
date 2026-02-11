package com.onepicklux.domain.auth.controller;

import com.onepicklux.domain.auth.dto.LoginRequest;
import com.onepicklux.domain.auth.dto.MemberResponse;
import com.onepicklux.domain.auth.dto.SignupRequest;
import com.onepicklux.domain.auth.dto.TokenResponse;
import com.onepicklux.domain.auth.service.AuthService;
import com.onepicklux.global.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ApiResponse<MemberResponse> signup(@Valid @RequestBody SignupRequest request) {
        return ApiResponse.created(authService.signup(request));
    }

    @PostMapping("/login")
    public ApiResponse<TokenResponse> login(@Valid @RequestBody LoginRequest request) {
        return ApiResponse.success(authService.login(request));
    }
}