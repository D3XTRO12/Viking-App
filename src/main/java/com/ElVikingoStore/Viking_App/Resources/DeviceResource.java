package com.ElVikingoStore.Viking_App.Resources;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import com.ElVikingoStore.Viking_App.DTOs.DeviceDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ElVikingoStore.Viking_App.Models.Device;
import com.ElVikingoStore.Viking_App.Models.User;
import com.ElVikingoStore.Viking_App.Services.DeviceService;
import com.ElVikingoStore.Viking_App.Services.UserService;

@RestController
@RequestMapping("/api/device")
public class DeviceResource {

    @Autowired
    DeviceService deviceService;

    @Autowired
    UserService userService;

    @GetMapping(value = "/search")
    public ResponseEntity<Object> searchDevice(@RequestParam(required = false) UUID id,
                                               @RequestParam(required = false) String serialNumber,
                                               @RequestParam(required = false) String brand,
                                               @RequestParam(required = false) UUID userId,
                                               @RequestParam(required = false) String query) {
        try {
            if (query == null) {
                return ResponseEntity.badRequest().body("Query parameter is required");
            }

            System.out.println("Query: " + query); // Imprimir la query recibida

            switch (query.toLowerCase()) {
                case "all" -> {
                    System.out.println("Searching all devices");
                    List<Device> devices = deviceService.getAll();
                    return ResponseEntity.ok(devices);
                }
                case "by-id" -> {
                    if (id == null) {
                        return ResponseEntity.badRequest().body("ID is required for 'by-id' query");
                    }
                    System.out.println("Searching device by ID");
                    Optional<Device> deviceById = deviceService.getDeviceById(id);
                    return ResponseEntity.ok(deviceById);
                }
                case "by-serial-number" -> {
                    if (serialNumber == null) {
                        return ResponseEntity.badRequest().body("Serial number is required for 'by-serial-number' query");
                    }
                    System.out.println("Searching device by Serial Number");
                    Device deviceBySerialNumber = deviceService.getDeviceBySerialNumber(serialNumber);
                    return ResponseEntity.ok(deviceBySerialNumber);
                }
                case "by-brand" -> {
                    if (brand == null) {
                        return ResponseEntity.badRequest().body("Brand is required for 'by-brand' query");
                    }
                    System.out.println("Searching device by Brand");
                    List<Device> devicesByBrand = deviceService.getDevicesByBrand(brand);
                    return ResponseEntity.ok(devicesByBrand);
                }
                case "by-user-id" -> {
                    if (userId == null) {
                        return ResponseEntity.badRequest().body("User ID is required for 'by-user-id' query");
                    }
                    System.out.println("Searching devices by User ID");
                    Optional<User> user = userService.getUserById(userId);
                    List<Device> devicesByUser = deviceService.getDevicesByUser(user);
                    return ResponseEntity.ok(devicesByUser);
                }
                default -> {
                    System.out.println("Invalid query parameter");
                    return ResponseEntity.badRequest().body("Invalid query parameter");
                }
            }
        } catch (NoSuchElementException | NumberFormatException e) {
            System.out.println("Error processing request: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error processing request: " + e.getMessage());
        }
    }

    @PostMapping("/save")
    public ResponseEntity<String> registerDevice(@Valid @RequestBody DeviceDto deviceDto) {
        String response = deviceService.saveDeviceInstance(deviceDto);
        return ResponseEntity.ok(response);

    }
}
