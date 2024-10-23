package com.ElVikingoStore.Viking_App.Services;

import java.util.*;
import java.util.stream.Collectors;

import com.ElVikingoStore.Viking_App.DTOs.UserDto;
import com.ElVikingoStore.Viking_App.Exception.UnauthorizedException;
import com.ElVikingoStore.Viking_App.Models.Role;
import com.ElVikingoStore.Viking_App.Models.UserRole;
import com.ElVikingoStore.Viking_App.Repositories.RoleRepo;
import com.ElVikingoStore.Viking_App.Repositories.RoleRepo;
import com.ElVikingoStore.Viking_App.Repositories.UserRoleRepo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ElVikingoStore.Viking_App.Models.User;
import com.ElVikingoStore.Viking_App.Repositories.UserRepo;

import jakarta.transaction.Transactional;
@Schema(name = "UserService", description = "Servicio para la gestión de usuarios")
@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private UserRoleRepo userRoleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    public void CustomUsersDetailsService (UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Operation(summary = "Obtener todos los usuarios", description = "Obtiene una lista de todos los usuarios registrados en el sistema")
    public ArrayList<User> getAllUsers(){
        return (ArrayList<User>) userRepo.findAll();
    }

    @Operation(summary = "Obtener usuario por ID", description = "Obtiene un usuario por su ID")
    public Optional<User> getUserById(UUID id) {
        return userRepo.findById(id);
    }
    @Operation(summary = "Obtener usuario por su rolId", description = "Obtiene una lista de usuarios por su rolId")
    public List<User> getUsersByRoleId(UUID rolId) {
        List<UserRole> userRoles = userRoleRepo.findByRole_Id(rolId);

        return userRoles.stream()
                .map(userRole -> userRepo.findById(userRole.getUser().getId())
                        .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userRole.getUser().getId())))
                .collect(Collectors.toList());
    }

@Operation(summary = "Obtener usuario por DNI", description = "Obtiene un usuario por su DNI")
public User getUserByDni(Integer dni) {
        return userRepo.findByDni(dni)
                .orElseThrow(() -> new NoSuchElementException("User not found with DNI: " + dni));
    }
    @Operation(summary = "Obtener usuario por email", description = "Obtiene un usuario por su email")
    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("User not found with email: " + email));
    }
@Operation(summary = "Obtener usuario por CUIT", description = "Obtiene un usuario por su CUIT")
public User getUserByCuit(String cuit) {
        return userRepo.findByCuit(cuit)
                .orElseThrow(() -> new NoSuchElementException("User not found with CUIT: " + cuit));
    }
    @Operation(summary = "Guardar Instancia de Usuario", description = "Guarda un nuevo usuario en la base de datos")
    @Transactional
    public String saveUserInstance(UserDto userDto) {
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
        return "User created successfully";
    }
    @Operation(summary = "Actualizar usuario", description = "Actualiza un usuario existente en el sistema")
    @Transactional
    public UserDto updateUser(UserDto userDto) {
        User existingUser = userRepo.findById(userDto.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Actualizar los campos del usuario
        existingUser.setName(userDto.getName());
        existingUser.setDni(userDto.getDni());
        existingUser.setUserType(userDto.getUserType());
        existingUser.setAddress(userDto.getAddress());
        existingUser.setPhoneNumber(userDto.getPhoneNumber());
        existingUser.setSecondaryPhoneNumber(userDto.getSecondaryPhoneNumber());
        existingUser.setEmail(userDto.getEmail());
        existingUser.setCuit(userDto.getCuit());

        // Actualizar la contraseña si se proporciona una nueva
        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            existingUser.setPassword(encodePassword(userDto.getPassword()));
        }

        // Guardar los cambios del usuario
        userRepo.save(existingUser);

        // Actualizar el rol si se proporciona un nuevo roleId
        if (userDto.getRoleId() != null) {
            Role newRole = validateRole(userDto.getRoleId());

            // Buscar el UserRole existente
            Optional<UserRole> existingUserRoleOpt = userRoleRepo.findRoleIdByUserId(existingUser.getId());

            if (existingUserRoleOpt.isPresent()) {
                UserRole existingUserRole = existingUserRoleOpt.get();
                existingUserRole.setRole(newRole);
                userRoleRepo.save(existingUserRole);
            } else {
                // Si no existe un UserRole, creamos uno nuevo
                UserRole newUserRole = new UserRole();
                newUserRole.setUser(existingUser);
                newUserRole.setRole(newRole);
                userRoleRepo.save(newUserRole);
            }
        }

        return convertToDto(existingUser);
    }

@Operation(summary = "Encriptar Contraseña", description = "Encripta la contraseña del usuario en caso de necesitarse")
private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }


@Operation(summary = "Eliminar usuario", description = "Elimina un usuario del sistema")
public boolean deleteUser(UUID id) {
        try {
            Optional<User> optionalUser = userRepo.findById(id);
            if (optionalUser.isEmpty()) {
                return false; // Usuario no encontrado
            }

            userRepo.deleteById(id);
            return true; // Usuario eliminado exitosamente
        } catch (Exception e) {
            return false; // Error al eliminar
        }
    }
    @Operation(summary = "Verificar si el usuario tiene un rol específico", description = "Verifica si un usuario tiene un rol específico")
    public boolean hasRoleId(String email, UUID roleId) {
        Optional<User> userOptional = userRepo.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Buscar si el usuario tiene el rol especificado
            Optional<UserRole> userRole = userRoleRepo.findRoleIdByUserId(user.getId());
            return userRole.isPresent() && userRole.get().getRoleId().equals(roleId);
        }
        return false;
    }
@Operation(summary = "Validar Rol", description = "Valida la existencia de un rol en la base de datos")
private Role validateRole(UUID roleId) {
        return roleRepo.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado con ID: " + roleId));
    }
@Operation(summary = "Convertir a DTO", description = "Convierte un objeto User a UserDto")
    private UserDto convertToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setDni(user.getDni());
        dto.setUserType(user.getUserType());
        dto.setAddress(user.getAddress());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setSecondaryPhoneNumber(user.getSecondaryPhoneNumber());
        dto.setEmail(user.getEmail());
        dto.setCuit(user.getCuit());

        // Obtener el roleId del UserRole asociado
        Optional<UserRole> userRoleOpt = userRoleRepo.findRoleIdByUserId(user.getId());
        userRoleOpt.ifPresent(userRole -> dto.setRoleId(userRole.getRole().getId()));

        return dto;
    }
    @Operation(summary = "Obtener información del usuario actual", description = "Obtiene la información del usuario autenticado")
    public UserDto getCurrentUserInfo() {
        Authentication authentication = getAuthentication();
        String email = getEmailFromAuthentication(authentication);
        User user = getUserByEmail(email); // Usando tu método existente
        return convertToDto(user); // Usando tu método existente
    }
    @Operation(summary = "Verificacion de Autenticación", description = "Verifica si el usuario está autenticado")
    private Authentication getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new UnauthorizedException("No hay usuario autenticado");
        }
        return authentication;
    }
    @Operation(summary = "Obtener el email del usuario autenticado", description = "Obtiene el email del usuario autenticado")
    private String getEmailFromAuthentication(Authentication authentication) {
        String email = authentication.getName();
        if (email == null || email.isEmpty()) {
            throw new UnauthorizedException("Email de usuario no válido");
        }
        return email;
    }
}
