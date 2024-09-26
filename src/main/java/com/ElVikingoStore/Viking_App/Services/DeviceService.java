package com.ElVikingoStore.Viking_App.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ElVikingoStore.Viking_App.DTOs.DeviceDto;
import com.ElVikingoStore.Viking_App.JWT.JwtTokenProvider;
import com.ElVikingoStore.Viking_App.Repositories.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ElVikingoStore.Viking_App.Models.User;
import com.ElVikingoStore.Viking_App.Models.Device;
import com.ElVikingoStore.Viking_App.Repositories.DeviceRepo;

import jakarta.transaction.Transactional;
@Slf4j
@Service
public class DeviceService {
    @Autowired
    DeviceRepo deviceRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    private JwtTokenProvider tokenProvider;


    public ArrayList<Device> getAll(){
        return (ArrayList<Device>) deviceRepo.findAll();
    }

    public Optional<Device> getDeviceById(Long id){
        return deviceRepo.findById(id);
    }
    public List<Device> getDevicesByUser(Optional<User> user) {
        return deviceRepo.findByUser(user);
    }

    public Device getDeviceBySerialNumber(String serialNumber){
        return deviceRepo.findBySerialNumber(serialNumber);
    }
    public List<Device> getDevicesByBrand(String brand) {
        return deviceRepo.findByBrand(brand); // Asegúrate de que este método esté definido en tu repositorio
    }
    @Transactional
    public String saveDeviceInstance(DeviceDto deviceDto) {
        // Verificar si el userId está presente en el DTO
        Long userId = deviceDto.getUserId();

        // Verificar si el usuario existe en la base de datos
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + userId));

        // Crear una nueva instancia de Device
        Device device = new Device();
        device.setType(deviceDto.getType());
        device.setBrand(deviceDto.getBrand());
        device.setModel(deviceDto.getModel());
        device.setSerialNumber(deviceDto.getSerialNumber());
        device.setUser(user);  // Asignar manualmente el usuario

        // Guardar el dispositivo en la base de datos
        deviceRepo.save(device);

        log.info("Device created successfully for user ID: {}", userId);
        return "Device successfully registered";
    }
}
