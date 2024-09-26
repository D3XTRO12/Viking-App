package com.ElVikingoStore.Viking_App.Services;

import java.util.List;
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
    public WorkOrder getWorkOrderById(Long id) {
        return workOrderRepo.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public WorkOrder saveWorkOrder(WorkOrderDto workOrderDto) {
        // Buscar cliente por ID
        User client = userService.getUserById(Long.parseLong(workOrderDto.getClientId()))
                .orElseThrow(() -> new EntityNotFoundException("Client not found with ID: " + workOrderDto.getClientId()));

        // Buscar staff por ID
        User staff = userService.getUserById(Long.parseLong(workOrderDto.getStaffId()))
                .orElseThrow(() -> new EntityNotFoundException("Staff not found with ID: " + workOrderDto.getStaffId()));

        // Verificar si el staff tiene el rol correcto (rol_id = 1)
        if (!userService.hasRoleId(staff.getEmail(), 1L)) {
            throw new IllegalArgumentException("The specified staff user does not have the required role (rol_id = 1)");
        }

        // Buscar dispositivo por ID
        Device device = deviceService.getDeviceById(workOrderDto.getDeviceId())
                .orElseThrow(() -> new EntityNotFoundException("Device not found with ID: " + workOrderDto.getDeviceId()));

        // Crear la nueva orden de trabajo
        WorkOrder workOrder = new WorkOrder();
        workOrder.setId(workOrderDto.getId()); // Asignar el ID manualmente
        workOrder.setClient(client);
        workOrder.setStaff(staff);
        workOrder.setDevice(device);
        workOrder.setIssueDescription(workOrderDto.getIssueDescription());
        workOrder.setRepairStatus(workOrderDto.getRepairStatus());

        // Guardar y retornar la orden de trabajo
        WorkOrder savedWorkOrder = workOrderRepo.save(workOrder);
        log.info("Work order created successfully with ID: {}", savedWorkOrder.getId());
        return savedWorkOrder;
    }
}