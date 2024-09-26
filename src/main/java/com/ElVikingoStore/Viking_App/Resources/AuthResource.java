package com.ElVikingoStore.Viking_App.Resources;

import com.ElVikingoStore.Viking_App.DTOs.UserDto;
import com.ElVikingoStore.Viking_App.Models.User;
import com.ElVikingoStore.Viking_App.Repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ElVikingoStore.Viking_App.DTOs.JwtAuthResponse;
import com.ElVikingoStore.Viking_App.DTOs.LoginUserDto;
import com.ElVikingoStore.Viking_App.JWT.JwtTokenProvider;
import com.ElVikingoStore.Viking_App.Services.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthResource {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserDto userDto) {
        String response = authService.registerUser(userDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginUserDto loginDto) {
        log.info("Login attempt for user: {}", loginDto.getEmail());
        try {
            JwtAuthResponse response = authService.loginUser(loginDto);
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            log.warn("Failed login attempt for user: {}", loginDto.getEmail());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        } catch (Exception e) {
            log.error("Unexpected error during login for user: {}", loginDto.getEmail(), e);
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
