package com.ElVikingoStore.Viking_App.Repositories;


import com.ElVikingoStore.Viking_App.Models.User;
import com.ElVikingoStore.Viking_App.Models.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRoleRepo extends JpaRepository<UserRole, UUID> {
    List<UserRole> findByRole_Id(UUID roleId); // Acceso correcto al ID del rol
    Optional<UserRole> findRoleIdByUserId(UUID userId);
    Optional<UserRole> findByUser(User user);}