package com.ElVikingoStore.Viking_App.Resources;

import com.ElVikingoStore.Viking_App.DTOs.UserDto;

import com.ElVikingoStore.Viking_App.Models.Role;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.BadCredentialsException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ElVikingoStore.Viking_App.DTOs.JwtAuthResponse;
import com.ElVikingoStore.Viking_App.DTOs.LoginUserDto;

import com.ElVikingoStore.Viking_App.Services.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
@Tag(name = "Auth", description = "Endpoints para autenticación y registro de usuarios")

@RestController
@RequestMapping("/auth")
public class AuthResource {

    @Autowired
    private AuthService authService;
    @Operation(summary = "Registro de usuario", description = "Crea un nuevo usuario en el sistema")
    @PostMapping(value = "/signup", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserDto userDto) {
        String response = authService.registerUser(userDto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Login de usuario", description = "Inicia sesión en el sistema")
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginUserDto loginDto) {
        try {
            JwtAuthResponse response = authService.loginUser(loginDto);
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred");
        }
    }


    @GetMapping("/validate")
    public ResponseEntity<Boolean> validateToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }
        String token = bearerToken.substring(7);
        boolean isValid = authService.validateToken(token);
        return ResponseEntity.ok(isValid);
    }
}
