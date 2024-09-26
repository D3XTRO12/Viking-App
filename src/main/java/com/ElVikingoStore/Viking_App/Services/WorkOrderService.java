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
@Slf4j
@Service
public class WorkOrderService {
    @Autowired
    private WorkOrderRepo workOrderRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private DeviceRepo deviceRepo;
    @Autowired
    private UserService userService;
    @Autowired
    private DeviceService deviceService;

    public List<WorkOrder> getAll() {
        return workOrderRepo.findAll();
    }

    public WorkOrder getWorkOrderById(Long id) {
        return workOrderRepo.findById(id).orElse(null);
    }

    public List<WorkOrder> findByClientDni(Integer clientDni) {
        return workOrderRepo.findByClient_Dni(clientDni);
    }

    @Transactional
    public WorkOrder saveWorkOrder(WorkOrderDto workOrderDto) {
        // Buscar cliente por ID
        User client = userService.getUserById(Long.parseLong(workOrderDto.getClientId()))
                .orElseThrow(() -> new EntityNotFoundException("Client not found with ID: " + workOrderDto.getClientId()));

        // Buscar staff por ID
        User staff = userService.getUserById(Long.parseLong(workOrderDto.getStaffId()))
                .orElseThrow(() -> new EntityNotFoundException("Staff not found with ID: " + workOrderDto.getStaffId()));

        // Buscar dispositivo por ID
        Device device = deviceService.getDeviceById(workOrderDto.getDeviceId())
                .orElseThrow(() -> new EntityNotFoundException("Device not found with ID: " + workOrderDto.getDeviceId()));

        // Crear la nueva orden de trabajo
        WorkOrder workOrder = new WorkOrder();
        workOrder.setClient(client);
        workOrder.setStaff(staff);
        workOrder.setDevice(device);
        workOrder.setIssueDescription(workOrderDto.getIssueDescription());
        workOrder.setRepairStatus(workOrderDto.getRepairStatus());

        // Guardar y retornar la orden de trabajo
        return workOrderRepo.save(workOrder);
    }
    public class CustomException extends Exception {
        public CustomException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
