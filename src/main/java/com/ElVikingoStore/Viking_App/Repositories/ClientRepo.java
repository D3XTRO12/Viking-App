package com.ElVikingoStore.Viking_App.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ElVikingoStore.Viking_App.Models.Client;

@Repository
public interface ClientRepo extends JpaRepository<Client, Long> {

    Client findByDni(int dni);

}
