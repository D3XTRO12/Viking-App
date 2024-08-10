package VikingoLab.VikingApp.app.Resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import VikingoLab.VikingApp.app.Models.WorkOrder;
import VikingoLab.VikingApp.app.Services.WorkOrderService;
import VikingoLab.VikingApp.app.Services.WorkOrderService.CustomException;

@RestController
@RequestMapping("/work-order")
public class WorkOrderResource {

    private final WorkOrderService workOrderService;

    @Autowired
    public WorkOrderResource(WorkOrderService workOrderService) {
        this.workOrderService = workOrderService;
    }

    @PostMapping("/save")
    public ResponseEntity<?> createWorkOrder(@RequestBody WorkOrder workOrder) {
        try {
            WorkOrder createdWorkOrder = workOrderService.createWorkOrder(workOrder);
            return ResponseEntity.ok(createdWorkOrder);
        } catch (CustomException e) {
            // Manejar la excepción, devolver un mensaje de error al cliente
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
