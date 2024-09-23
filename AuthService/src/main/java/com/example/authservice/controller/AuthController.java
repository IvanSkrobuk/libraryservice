package com.example.authservice.controller;

import com.example.authservice.util.JWTUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JWTUtil jwtUtil;

    @Autowired
    public AuthController(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Operation(summary = "User Login", description = "Генерирует JWT токен для входа в LibraryAPI")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Токен сгенерирован"),
            @ApiResponse(responseCode = "401", description = "Произошла ощибка"),
    })
    @PostMapping("/login")
    public Map<String, String> login(
            @Parameter(description = "Username of the user", required = true) @RequestParam String username) {

            String token = jwtUtil.generateToken(username); // Генерация токена
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return response;

    }
}
