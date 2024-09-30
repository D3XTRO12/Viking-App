package com.ElVikingoStore.Viking_App.Services;

import com.ElVikingoStore.Viking_App.DTOs.JwtAuthResponse;
import com.ElVikingoStore.Viking_App.DTOs.LoginUserDto;

import com.ElVikingoStore.Viking_App.DTOs.UserDto;
import com.ElVikingoStore.Viking_App.JWT.JwtTokenProvider;

import com.ElVikingoStore.Viking_App.Models.Role;
import com.ElVikingoStore.Viking_App.Models.User;
import com.ElVikingoStore.Viking_App.Models.UserRole;

import com.ElVikingoStore.Viking_App.Repositories.RoleRepo;
import com.ElVikingoStore.Viking_App.Repositories.UserRepo;
import com.ElVikingoStore.Viking_App.Repositories.UserRoleRepo;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class AuthService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private UserRoleRepo userRoleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private AuthenticationManager authenticationManager;



    public String registerUser(UserDto userDto) {
        // Verificar si el rol existe
        Role role = validateRole(userDto.getRoleId());

        User user = new User();
        user.setName(userDto.getName());
        user.setDni(userDto.getDni());
        user.setUserType(userDto.getUserType());
        user.setAddress(userDto.getAddress());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setSecondaryPhoneNumber(userDto.getSecondaryPhoneNumber());
        user.setEmail(userDto.getEmail());
        user.setPassword(encodePassword(userDto.getPassword()));

        // Guardar el usuario
        userRepo.save(user);

        // Crear y guardar el UserRole
        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role); // Establecer el rol recuperado
        userRoleRepo.save(userRole);

        return ("Successfully registered:" + user);
    }

    public JwtAuthResponse loginUser(LoginUserDto loginDto) throws BadCredentialsException {
        log.info("Attempting to authenticate user with email: {}", loginDto.getEmail());
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = tokenProvider.generateToken(authentication);
            log.info("User {} authenticated successfully, token generated.", loginDto.getEmail());
            return new JwtAuthResponse(jwt);
        } catch (BadCredentialsException e) {
            log.error("Invalid credentials for user: {}", loginDto.getEmail(), e);
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error during authentication for user: {}", loginDto.getEmail(), e);
            throw e;
        }
    }
    // Método para validar el rol
    private Role validateRole(UUID roleId) {
        return roleRepo.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado con ID: " + roleId));
    }

    public boolean validateToken(String token) {

        return tokenProvider.validateToken(token);
    }
    // Método para codificar la contraseña
    private String encodePassword(String password) {

        return passwordEncoder.encode(password);
    }
}



