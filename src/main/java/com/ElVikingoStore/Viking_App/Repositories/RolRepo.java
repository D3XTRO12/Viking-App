package com.ElVikingoStore.Viking_App.Repositories;

import com.ElVikingoStore.Viking_App.Models.Rol;
import com.ElVikingoStore.Viking_App.Models.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepo extends JpaRepository<Rol, Long> {
    Optional<Object> findById(Optional<UserRole> roleId);
}
