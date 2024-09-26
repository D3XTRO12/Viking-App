package com.ElVikingoStore.Viking_App.Resources;

import com.ElVikingoStore.Viking_App.DTOs.WorkOrderDto;
import com.ElVikingoStore.Viking_App.Services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@Log4j2
@RequestMapping("/api/work-order")
public class WorkOrderResource {

    private final WorkOrderService workOrderService;
    private final UserService userService;

    @Autowired
    public WorkOrderResource(WorkOrderService workOrderService, UserService userService) {
        this.workOrderService = workOrderService;
        this.userService = userService;
    }

    @PostMapping("/save")
    public ResponseEntity<?> createWorkOrder(@RequestBody WorkOrderDto workOrderDto, Authentication authentication) {
        // Obtener el usuario autenticado
        String email = authentication.getName();

        // Verificar si el usuario tiene el rol de staff (rol_id = 1)
        if (!userService.hasRoleId(email, 1L)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied: User is not staff");
        }

        try {
            WorkOrder createdWorkOrder = workOrderService.saveWorkOrder(workOrderDto);
            return ResponseEntity.ok(createdWorkOrder);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            log.error("Error creating work order", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating work order");
        }
    }
}
