package com.ElVikingoStore.Viking_App.Repositories;

import com.ElVikingoStore.Viking_App.Models.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ElVikingoStore.Viking_App.Models.User;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByDni(Integer dni);

    Optional<User> findByCuit(String cuit);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    Long findIdByEmail(String email);
}
