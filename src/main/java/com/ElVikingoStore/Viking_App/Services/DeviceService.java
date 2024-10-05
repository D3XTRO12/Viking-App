package com.ElVikingoStore.Viking_App.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public List<DeviceDto> getAllDevicesDto() {
        return deviceRepo.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    public Optional<Device> getDeviceById(UUID id){
        return deviceRepo.findById(id);
    }
    public Optional<DeviceDto> getDeviceDtoById(UUID id) {
        return deviceRepo.findById(id).map(this::convertToDto);
    }

    public List<DeviceDto> getDevicesDtoByUser(Optional<User> user) {
        return deviceRepo.findByUser(user).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public DeviceDto getDeviceDtoBySerialNumber(String serialNumber) {
        Device device = deviceRepo.findBySerialNumber(serialNumber);
        return device != null ? convertToDto(device) : null;
    }

    public List<DeviceDto> getDevicesDtoByBrand(String brand) {
        return deviceRepo.findByBrand(brand).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private DeviceDto convertToDto(Device device) {
        DeviceDto dto = new DeviceDto();
        dto.setId(device.getId());
        dto.setType(device.getType());
        dto.setBrand(device.getBrand());
        dto.setModel(device.getModel());
        dto.setSerialNumber(device.getSerialNumber());
        dto.setUserId(device.getUser() != null ? device.getUser().getId() : null);
        return dto;
    }
    @Transactional
    public String saveDeviceInstance(DeviceDto deviceDto) {
        // Verificar si el userId está presente en el DTO
        UUID userId = deviceDto.getUserId();
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
