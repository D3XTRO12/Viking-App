package com.ElVikingoStore.Viking_App.Services;

import com.ElVikingoStore.Viking_App.Models.Rol;
import com.ElVikingoStore.Viking_App.Models.User;
import com.ElVikingoStore.Viking_App.Models.UserRole;
import com.ElVikingoStore.Viking_App.Repositories.RolRepo;
import com.ElVikingoStore.Viking_App.Repositories.UserRepo;
import com.ElVikingoStore.Viking_App.Repositories.UserRoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserRoleService {

    @Autowired
    private UserRoleRepo userRoleRepo;  // Repositorio de user_roles

    @Autowired
    private RolRepo roleRepo;  // Repositorio de roles

    @Autowired
    private UserRepo userRepository; // Repositorio de usuarios

    // Método para obtener el permiso del usuario autenticado basándose en su email
    public String getUserPermission(String email) {
        // Obtener el ID del usuario según el email
        Long userId = userRepository.findIdByEmail(email); // Método que necesitas agregar en UserRepo

        if (userId != null) {
            // Obtener el rol del usuario
            Optional<UserRole> roleId = userRoleRepo.findRoleIdByUserId(userId);
            if (roleId != null) {
                // Obtener el permiso correspondiente al rol
                Rol role = (Rol) roleRepo.findById(roleId).orElse(null);
                return role != null ? role.getPermiso() : null;
            }
        }

        return null;
    }
}
