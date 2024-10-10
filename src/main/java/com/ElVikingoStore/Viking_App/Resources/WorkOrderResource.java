package com.ElVikingoStore.Viking_App.Resources;

import com.ElVikingoStore.Viking_App.DTOs.WorkOrderDto;
import com.ElVikingoStore.Viking_App.Services.UserService;
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

@RestController
@Log4j2
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
        log.info("WorkOrderResource initialized with adminRoleId: {}", adminRoleId);
    }
    @GetMapping("/search")
    public ResponseEntity<Object> searchWorkOrder(@RequestParam(required = false) UUID staffId,
                                                  @RequestParam(required = false) UUID clientId,
                                                  @RequestParam(required = false) String query) {
        log.info("Received search request with query: {}, staffId: {}, clientId: {}", query, staffId, clientId);
        try {
            if (query == null) {
                log.warn("Query parameter is missing");
                return ResponseEntity.badRequest().body("Query parameter is required");
            }

            log.info("Processing query: {}", query);
            switch (query.toLowerCase()) {
                case "all" -> {
                    log.info("Fetching all work orders");
                    List<WorkOrderDto> workOrders = workOrderService.getAllWorkOrders();
                    log.info("Found {} work orders", workOrders.size());
                    return ResponseEntity.ok(workOrders);
                }
                case "by-staffid" -> {
                    if (staffId == null) {
                        log.warn("Staff ID is missing for 'by-staffId' query");
                        return ResponseEntity.badRequest().body("Staff ID is required for 'by-staffId' query");
                    }
                    log.info("Fetching work orders for staff ID: {}", staffId);
                    List<WorkOrderDto> workOrdersByStaff = workOrderService.getWorkOrdersByStaffId(staffId);
                    log.info("Found {} work orders for staff ID: {}", workOrdersByStaff.size(), staffId);
                    return ResponseEntity.ok(workOrdersByStaff);
                }
                case "by-clientid" -> {
                    if (clientId == null) {
                        log.warn("Client ID is missing for 'by-clientId' query");
                        return ResponseEntity.badRequest().body("Client ID is required for 'by-clientId' query");
                    }
                    log.info("Fetching work orders for client ID: {}", clientId);
                    List<WorkOrderDto> workOrdersByClient = workOrderService.getWorkOrdersByClientId(clientId);
                    log.info("Found {} work orders for client ID: {}", workOrdersByClient.size(), clientId);
                    return ResponseEntity.ok(workOrdersByClient);
                }
                default -> {
                    log.warn("Invalid query parameter: {}", query);
                    return ResponseEntity.badRequest().body("Invalid query parameter");
                }
            }
        } catch (NoSuchElementException e) {
            log.error("Work order not found", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Work order not found: " + e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error processing request", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing request: " + e.getMessage());
        }
    }
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
            log.error("Error creating work order", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating work order");
        }
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<Object> updateWorkOrderStatus(@PathVariable UUID orderId, @RequestBody WorkOrderDto workOrderDto) {
        log.info("Received update request for work order {} with new status {}", orderId, workOrderDto.getRepairStatus());
        workOrderService.updateWorkOrderStatus(orderId, workOrderDto.getRepairStatus());
        return ResponseEntity.ok("Work order status updated successfully");
    }
}
