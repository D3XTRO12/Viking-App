package com.ElVikingoStore.Viking_App.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ElVikingoStore.Viking_App.Models.User;
import com.ElVikingoStore.Viking_App.Models.Device;

@Repository
public interface DeviceRepo extends JpaRepository<Device, Long> {

    Device findBySerialNumber(String serialNumber);
    List<Device> findByBrand(String brand); // Método para buscar dispositivos por marca
    List<Device> findByUser(Optional<User> user);
} 
