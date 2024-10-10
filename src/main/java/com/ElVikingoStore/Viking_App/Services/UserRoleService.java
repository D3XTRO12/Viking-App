package com.ElVikingoStore.Viking_App.Services;

import com.ElVikingoStore.Viking_App.Models.Role;
import com.ElVikingoStore.Viking_App.Models.User;
import com.ElVikingoStore.Viking_App.Models.UserRole;
import com.ElVikingoStore.Viking_App.Repositories.RoleRepo;
import com.ElVikingoStore.Viking_App.Repositories.RoleRepo;
import com.ElVikingoStore.Viking_App.Repositories.UserRepo;
import com.ElVikingoStore.Viking_App.Repositories.UserRoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserRoleService {

    @Autowired
    private UserRoleRepo userRoleRepo;  // Repositorio de user_roles

    @Autowired
    private RoleRepo roleRepo;  // Repositorio de roles

    @Autowired
    private UserRepo userRepository; // Repositorio de usuarios

    // Método para obtener el permiso del usuario autenticado basándose en su email
    public String getUserPermission(String email) {
        // Obtener el ID del usuario según el email
        UUID userId = userRepository.findIdByEmail(email); // Método que necesitas agregar en UserRepo

        if (userId != null) {
            // Obtener el rol del usuario
            Optional<UserRole> roleId = userRoleRepo.findRoleIdByUserId(userId);
            if (roleId != null) {
                // Obtener el permiso correspondiente al rol
                Role role = (Role) roleRepo.findById(roleId).orElse(null);
                return role != null ? role.getPermission() : null;
            }
        }

        return null;
    }

    public boolean isUserStaff(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return userRoleRepo.findByUser(user)
                .map(userRole -> "staff".equals(userRole.getRole().getDescripcion()))
                .orElse(false);
    }
}
