package com.ElVikingoStore.Viking_App.Repositories;


import com.ElVikingoStore.Viking_App.Models.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRoleRepo extends JpaRepository<UserRole, Long> {
    List<UserRole> findByRol_Id(Long rolId); // Acceso correcto al ID del rol
    Optional<UserRole> findRoleIdByUserId(Long userId);
}