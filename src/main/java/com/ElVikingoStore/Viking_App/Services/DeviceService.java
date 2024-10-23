package com.ElVikingoStore.Viking_App.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.ElVikingoStore.Viking_App.DTOs.DeviceDto;
import com.ElVikingoStore.Viking_App.JWT.JwtTokenProvider;
import com.ElVikingoStore.Viking_App.Repositories.UserRepo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ElVikingoStore.Viking_App.Models.User;
import com.ElVikingoStore.Viking_App.Models.Device;
import com.ElVikingoStore.Viking_App.Repositories.DeviceRepo;

import jakarta.transaction.Transactional;
@Schema(name = "DeviceService", description = "Servicio para la gestión de dispositivos")
@Service
public class DeviceService {
    @Autowired
    DeviceRepo deviceRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Operation(summary = "Obtener todos los dispositivos", description = "Obtiene una lista de todos los dispositivos registrados en el sistema")
    public List<DeviceDto> getAllDevicesDto() {
        return deviceRepo.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    @Operation(summary = "Obtener dispositivo por ID", description = "Obtiene un dispositivo por su ID")
    public Optional<Device> getDeviceById(UUID id){
        return deviceRepo.findById(id);
    }
    @Operation(summary = "Obtener dispositivo por número de serie", description = "Obtiene un dispositivo por su número de serie")
    public Optional<DeviceDto> getDeviceDtoById(UUID id) {
        return deviceRepo.findById(id).map(this::convertToDto);
    }
    @Operation(summary = "Obtener dispositivos por usuario", description = "Obtiene una lista de dispositivos asociados a un usuario")
    public List<DeviceDto> getDevicesDtoByUser(Optional<User> user) {
        return deviceRepo.findByUser(user).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    @Operation(summary = "Obtener dispositivo por número de serie", description = "Obtiene un dispositivo por su número de serie")
    public DeviceDto getDeviceDtoBySerialNumber(String serialNumber) {
        Device device = deviceRepo.findBySerialNumber(serialNumber);
        return device != null ? convertToDto(device) : null;
    }
    @Operation(summary = "Obtener dispositivos por marca", description = "Obtiene una lista de dispositivos por marca")
    public List<DeviceDto> getDevicesDtoByBrand(String brand) {
        return deviceRepo.findByBrand(brand).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    @Operation(summary = "Obtener dispositivos por tipo", description = "Obtiene una lista de dispositivos por tipo")
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
    @Operation(summary = "Guardar dispositivo", description = "Guarda un nuevo dispositivo en la base de datos")
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
        return "Device successfully registered";
    }
}
