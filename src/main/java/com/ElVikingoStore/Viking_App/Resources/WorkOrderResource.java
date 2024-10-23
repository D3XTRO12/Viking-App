package com.ElVikingoStore.Viking_App.Resources;

import com.ElVikingoStore.Viking_App.DTOs.WorkOrderDto;
import com.ElVikingoStore.Viking_App.Services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.ElVikingoStore.Viking_App.Models.WorkOrder;
import com.ElVikingoStore.Viking_App.DTOs.WorkOrderDto;
import com.ElVikingoStore.Viking_App.Services.WorkOrderService;

import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
@Tag(name = "Work Order Controller", description = "API para la gestión de órdenes de trabajo")
@RestController
@RequestMapping("/api/work-order")
public class WorkOrderResource {
    @Value("${admin.role-id}")
    private String adminRoleId;
    private final WorkOrderService workOrderService;
    private final UserService userService;
    @Autowired
    public WorkOrderResource(WorkOrderService workOrderService, UserService userService) {
        this.workOrderService = workOrderService;
        this.userService = userService;
    }
    @Operation(
            summary = "Buscar Orden de Trabajo",
            description = "Busca una orden de trabajo en la base de datos"
    )
    @GetMapping("/search")
    public ResponseEntity<Object> searchWorkOrder(@RequestParam(required = false) UUID staffId,
                                                  @RequestParam(required = false) UUID clientId,
                                                  @RequestParam(required = false) UUID deviceId,
                                                  @RequestParam(required = false) String query) {
        try {
            if (query == null) {
                return ResponseEntity.badRequest().body("Query parameter is required");
            }

            switch (query.toLowerCase()) {
                case "all" -> {
                    List<WorkOrderDto> workOrders = workOrderService.getAllWorkOrders();
                    return ResponseEntity.ok(workOrders);
                }
                case "by-staffid" -> {
                    if (staffId == null) {
                        return ResponseEntity.badRequest().body("Staff ID is required for 'by-staffId' query");
                    }
                    List<WorkOrderDto> workOrdersByStaff = workOrderService.getWorkOrdersByStaffId(staffId);
                    return ResponseEntity.ok(workOrdersByStaff);
                }
                case "by-clientid" -> {
                    if (clientId == null) {
                        return ResponseEntity.badRequest().body("Client ID is required for 'by-clientId' query");
                    }
                    List<WorkOrderDto> workOrdersByClient = workOrderService.getWorkOrdersByClientId(clientId);
                    return ResponseEntity.ok(workOrdersByClient);
                }
                case "by-deviceid" -> {
                    if (deviceId == null) {
                        return ResponseEntity.badRequest().body("Device ID is required for 'by-deviceId' query");
                    }
                    List<WorkOrderDto> workOrdersByDevice = workOrderService.getWorkOrdersByDeviceId(deviceId);
                    return ResponseEntity.ok(workOrdersByDevice);
                }
                default -> {
                    return ResponseEntity.badRequest().body("Invalid query parameter");
                }
            }
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Work order not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing request: " + e.getMessage());
        }
    }
    @Operation(
            summary = "Guardar Orden de Trabajo",
            description = "Guarda una nueva orden de trabajo en la base de datos"
    )
    @PostMapping(value ="/save")
    public ResponseEntity<?> saveWorkOrder(@RequestBody WorkOrderDto workOrderDto, Authentication authentication) {
        String email = authentication.getName();
        // UUID del rol de staff (asegúrate de que este UUID sea correcto para tu sistema)
        UUID staffRoleId = UUID.fromString(adminRoleId);

        if (!userService.hasRoleId(email, staffRoleId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied: User is not staff");
        }

        try {
            WorkOrder createdWorkOrder = workOrderService.saveWorkOrder(workOrderDto, email);
            return ResponseEntity.ok(createdWorkOrder);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating work order");
        }
    }
    @Operation(
            summary = "Actualizar Estado de Orden de Trabajo",
            description = "Actualiza el estado de una orden de trabajo en la base de datos"
    )
    @PutMapping("/{orderId}")
    public ResponseEntity<Object> updateWorkOrderStatus(@PathVariable UUID orderId, @RequestBody WorkOrderDto workOrderDto) {
        workOrderService.updateWorkOrderStatus(orderId, workOrderDto.getRepairStatus());
        return ResponseEntity.ok("Work order status updated successfully");
    }
}
