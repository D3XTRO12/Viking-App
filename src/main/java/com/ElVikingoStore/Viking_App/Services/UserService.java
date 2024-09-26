package com.ElVikingoStore.Viking_App.Services;

import java.util.*;
import java.util.stream.Collectors;

import com.ElVikingoStore.Viking_App.DTOs.UserDto;
import com.ElVikingoStore.Viking_App.Models.Rol;
import com.ElVikingoStore.Viking_App.Models.UserRole;
import com.ElVikingoStore.Viking_App.Repositories.RolRepo;
import com.ElVikingoStore.Viking_App.Repositories.UserRoleRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ElVikingoStore.Viking_App.Models.User;
import com.ElVikingoStore.Viking_App.Repositories.UserRepo;

import jakarta.transaction.Transactional;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RolRepo rolRepo;

    @Autowired
    private UserRoleRepo userRoleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    public void CustomUsersDetailsService (UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    // Obtener todos los usuarios
    public ArrayList<User> getAllUsers(){
        return (ArrayList<User>) userRepo.findAll();
    }

    // Obtener un usuario por ID
    public Optional<User> getUserById(Long id) {
        return userRepo.findById(id);
    }
    public List<User> getUsersByRoleId(Long rolId) {
        // Obtener los UserRoles por rolId
        List<UserRole> userRoles = userRoleRepo.findByRol_Id(rolId);

        // Extraer los IDs de usuarios y obtener los usuarios
        return userRoles.stream()
                .map(userRole -> userRepo.findById(userRole.getUser().getId())
                        .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userRole.getUser().getId())))
                .collect(Collectors.toList());
    }
    // Obtener un usuario por DNI
    public User getUserByDni(Integer dni) {
        return userRepo.findByDni(dni)
                .orElseThrow(() -> new NoSuchElementException("User not found with DNI: " + dni));
    }
    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("User not found with email: " + email));
    }
    // Obtener un usuario por CUIT (para empresas)
    public User getUserByCuit(String cuit) {
        return userRepo.findByCuit(cuit)
                .orElseThrow(() -> new NoSuchElementException("User not found with CUIT: " + cuit));
    }

    @Transactional
    public String saveUserInstance(UserDto userDto) {
        // Verificar si el rol existe
        Long rolId = userDto.getUserRoles().iterator().next().getRolId();
        Rol rol = rolRepo.findById(rolId)
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado con ID: " + rolId));

        User user = new User();
        user.setName(userDto.getName());
        user.setDni(userDto.getDni());
        user.setUserType(userDto.getUserType());
        user.setAddress(userDto.getAddress());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setSecondaryPhoneNumber(userDto.getSecondaryPhoneNumber());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        // Guardar el usuario
        userRepo.save(user);

        // Crear y guardar el UserRole
        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRol(rol);
        userRoleRepo.save(userRole);

        log.info("User created successfully: {}", user.getEmail());
        return "Successfully registered";
    }

    // Método auxiliar para codificar la contraseña (si es necesario)
    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    // Actualizar un usuario existente con nuevos detalles
//    @Transactional
//    public User updateUser(Optional<User> existingUser, User newUserDetails) throws Exception {
//        existingUser.setName(newUserDetails.getName());
//        existingUser.setAddress(newUserDetails.getAddress());
//        existingUser.setPhoneNumber(newUserDetails.getPhoneNumber());

        // Si el usuario es del tipo "company", actualiza CUIT
//        if (existingUser.getUserType().equals("company")) {
//            existingUser.setCuit(newUserDetails.getCuit());
        //}

        // Guardar los cambios en la base de datos
        //userRepo.save(existingUser);
        //return existingUser;
    //}

    // Eliminar un usuario por ID
    public boolean deleteUser(Long id) {
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
    public boolean hasRoleId(String email, Long roleId) {
        Optional<User> userOptional = userRepo.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Buscar si el usuario tiene el rol especificado
            Optional<UserRole> userRole = userRoleRepo.findRoleIdByUserId(user.getId());
            return userRole.isPresent() && userRole.get().getRolId().equals(roleId);
        }
        return false;
    }
}
