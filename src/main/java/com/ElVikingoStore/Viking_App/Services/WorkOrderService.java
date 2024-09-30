package com.ElVikingoStore.Viking_App.Services;

import java.util.List;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

import com.ElVikingoStore.Viking_App.DTOs.WorkOrderDto;
import com.ElVikingoStore.Viking_App.Models.Device;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ElVikingoStore.Viking_App.Models.User;
import com.ElVikingoStore.Viking_App.Models.WorkOrder;
import com.ElVikingoStore.Viking_App.Repositories.DeviceRepo;
import com.ElVikingoStore.Viking_App.Repositories.UserRepo;
import com.ElVikingoStore.Viking_App.Repositories.WorkOrderRepo;

import jakarta.persistence.EntityNotFoundException;
@Service
@Slf4j
public class WorkOrderService {

    private final WorkOrderRepo workOrderRepo;
    private final UserService userService;
    private final DeviceService deviceService;

    @Autowired
    public WorkOrderService(WorkOrderRepo workOrderRepo, UserService userService, DeviceService deviceService) {
        this.workOrderRepo = workOrderRepo;
        this.userService = userService;
        this.deviceService = deviceService;
    }

    @Transactional
    public WorkOrder getWorkOrderById(UUID id) {
        return workOrderRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Work order not found with ID: " + id));
    }

    public WorkOrder saveWorkOrder(WorkOrderDto workOrderDto, String staffEmail) {
        log.info("Attempting to save work order: {}", workOrderDto);

        User staff = findUserByEmail(staffEmail);
        User client = findUser(workOrderDto.getClientId(), "Client");
        Device device = findAndValidateDevice(workOrderDto.getDeviceId(), client);

        WorkOrder workOrder = new WorkOrder();
        workOrder.setClient(client);
        workOrder.setStaff(staff);
        workOrder.setDevice(device);
        workOrder.setIssueDescription(workOrderDto.getIssueDescription());
        workOrder.setRepairStatus(workOrderDto.getRepairStatus());

        WorkOrder savedWorkOrder = workOrderRepo.save(workOrder);

        log.info("Work order created and saved successfully with ID: {}", savedWorkOrder.getId());
        return savedWorkOrder;
    }
    private User findUserByEmail(String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            throw new EntityNotFoundException("User not found with email: " + email);
        }
        return user;
    }

    private User findUser(UUID userId, String userType) {
        return userService.getUserById(userId)
                .orElseThrow(() -> new EntityNotFoundException(userType + " not found with ID: " + userId));
    }

    private Device findAndValidateDevice(UUID deviceId, User client) {
        Device device = deviceService.getDeviceById(deviceId)
                .orElseThrow(() -> new EntityNotFoundException("Device not found with ID: " + deviceId));
        if (!device.getUser().getId().equals(client.getId())) {
            throw new IllegalArgumentException("The device does not belong to the specified client");
        }
        return device;
    }

}