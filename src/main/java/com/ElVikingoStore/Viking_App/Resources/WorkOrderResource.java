package com.ElVikingoStore.Viking_App.Resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ElVikingoStore.Viking_App.Models.WorkOrder;
import com.ElVikingoStore.Viking_App.Services.WorkOrderService;
import com.ElVikingoStore.Viking_App.Services.WorkOrderService.CustomException;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping("/work-order")
public class WorkOrderResource {

    private final WorkOrderService workOrderService;

    //@Autowired
    public WorkOrderResource(WorkOrderService workOrderService) {
        this.workOrderService = workOrderService;
    }

    @PostMapping("/save")
    public ResponseEntity<?> createWorkOrder(@RequestBody WorkOrder workOrder) {
        try {
            log.info("Entrando al método createWorkOrder en WorkOrderResource...");
            log.info("WorkOrder recibido: {}", workOrder);

            WorkOrder createdWorkOrder = workOrderService.createWorkOrder(workOrder);
            log.info("WorkOrder creado: {}", createdWorkOrder);

            return ResponseEntity.ok(createdWorkOrder);
        } catch (CustomException e) {
            log.error("Error al crear el WorkOrder: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
