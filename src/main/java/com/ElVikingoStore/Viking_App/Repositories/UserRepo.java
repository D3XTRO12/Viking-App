package com.ElVikingoStore.Viking_App.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ElVikingoStore.Viking_App.Models.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepo extends JpaRepository<User, UUID> {

    Optional<User> findByDni(Integer dni);

    Optional<User> findByCuit(String cuit);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    UUID findIdByEmail(String email);
}
