package com.ElVikingoStore.Viking_App.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

import com.ElVikingoStore.Viking_App.DTOs.DeviceDto;
import com.ElVikingoStore.Viking_App.DTOs.WorkOrderDto;
import com.ElVikingoStore.Viking_App.Models.Device;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ElVikingoStore.Viking_App.Models.User;
import com.ElVikingoStore.Viking_App.Models.WorkOrder;
import com.ElVikingoStore.Viking_App.Repositories.WorkOrderRepo;

import jakarta.persistence.EntityNotFoundException;
@Schema(name = "WorkOrderService", description = "Service for managing work orders")
@Service
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
    @Operation(summary = "Get work order by ID", description = "Get a work order by its ID")
    @Transactional
    public WorkOrder getWorkOrderById(UUID id) {
        return workOrderRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Work order not found with ID: " + id));
    }
    @Operation(summary = "Get all work orders", description = "Get all work orders")
    public List<WorkOrderDto> getAllWorkOrders() {
        List<WorkOrder> workOrders = workOrderRepo.findAll();
        return workOrders.stream().map(this::convertToDto).collect(Collectors.toList());
    }
    @Operation(summary = "Get work orders by staff ID", description = "Get all work orders assigned to a staff member")
    public List<WorkOrderDto> getWorkOrdersByStaffId(UUID staffId) {
        List<WorkOrder> workOrders = workOrderRepo.findByStaffId(staffId);
        return workOrders.stream().map(this::convertToDto).collect(Collectors.toList());
    }
    @Operation(summary = "Get work orders by client ID", description = "Get all work orders assigned to a client")
    public List<WorkOrderDto> getWorkOrdersByClientId(UUID clientId) {
        List<WorkOrder> workOrders = workOrderRepo.findByClientId(clientId);
        return workOrders.stream().map(this::convertToDto).collect(Collectors.toList());
    }
    @Operation(summary = "Get work orders by device ID", description = "Get all work orders assigned to a device")
    public List<WorkOrderDto> getWorkOrdersByDeviceId(UUID deviceId){
        List<WorkOrder> workOrders = workOrderRepo.findByDeviceId(deviceId);
        return workOrders.stream().map(this::convertToDto).collect(Collectors.toList());
    }
    @Operation(summary = "Save work order", description = "Save a new work order")
    public WorkOrder saveWorkOrder(WorkOrderDto workOrderDto, String staffEmail) {

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

        return savedWorkOrder;
    }
    @Operation(summary="Buscar usuarios por email", description="Buscar usuarios por email")
    private User findUserByEmail(String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            throw new EntityNotFoundException("User not found with email: " + email);
        }
        return user;
    }
    @Operation(summary="Buscar usuarios por ID", description="Buscar usuarios por ID")
    private User findUser(UUID userId, String userType) {
        return userService.getUserById(userId)
                .orElseThrow(() -> new EntityNotFoundException(userType + " not found with ID: " + userId));
    }
    @Operation(summary="Buscar y validar dispositivo", description="Buscar y validar dispositivo")
    private Device findAndValidateDevice(UUID deviceId, User client) {
        Device device = deviceService.getDeviceById(deviceId)
                .orElseThrow(() -> new EntityNotFoundException("Device not found with ID: " + deviceId));
        if (!device.getUser().getId().equals(client.getId())) {
            throw new IllegalArgumentException("The device does not belong to the specified client");
        }
        return device;
    }
    @Operation(summary="Convertir a DTO", description="Convertir a DTO")
    private WorkOrderDto convertToDto(WorkOrder workOrder) {
        return WorkOrderDto.builder()
                .id(workOrder.getId())
                .clientId(workOrder.getClient().getId())
                .staffId(workOrder.getStaff().getId())
                .deviceId(workOrder.getDevice().getId())
                .issueDescription(workOrder.getIssueDescription())
                .repairStatus(workOrder.getRepairStatus())
                .build();
    }
    @Operation(summary="Actualizar estado de orden de trabajo", description="Actualizar estado de orden de trabajo")
    public void updateWorkOrderStatus(UUID orderId, String newStatus) {
        WorkOrder workOrder = workOrderRepo.findById(orderId).orElseThrow();
        workOrder.setRepairStatus(newStatus);
        workOrderRepo.save(workOrder);
    }
}
