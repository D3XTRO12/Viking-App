package com.ElVikingoStore.Viking_App.Resources;

import com.ElVikingoStore.Viking_App.DTOs.WorkOrderDto;
import com.ElVikingoStore.Viking_App.Services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ElVikingoStore.Viking_App.Models.WorkOrder;
import com.ElVikingoStore.Viking_App.Services.WorkOrderService;

import lombok.extern.log4j.Log4j2;

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
    }

    @PostMapping(value ="/save", consumes = "application/json", produces = "application/json")
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
}
