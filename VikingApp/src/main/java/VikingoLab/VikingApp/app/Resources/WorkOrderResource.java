package VikingoLab.VikingApp.app.Resources;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import VikingoLab.VikingApp.app.Services.WorkOrderService;
import VikingoLab.VikingApp.app.Models.WorkOrder;

@RestController
@RequestMapping("/work-order")
public class WorkOrderResource {
    @Autowired
    WorkOrderService workOrderService;

    @GetMapping("/search")
    public ResponseEntity<Object> searchWorkOrder(@RequestParam(required = false) Long id, @RequestParam(required = false) Integer clientDni, @RequestParam(required = false) String query) {
        try {
            if (query == null) {
                return ResponseEntity.badRequest().body("Query parameter is required");
            }
    
            System.out.println("Query: " + query); // Imprimir la query recibida
    
            switch (query.toLowerCase()) {
                case "all":
                    System.out.println("Searching all work orders");
                    List<WorkOrder> workOrders = workOrderService.getAll();
                    return ResponseEntity.ok(workOrders);
                case "by-id":
                    if (id == null) {
                        return ResponseEntity.badRequest().body("ID is required for 'by-id' query");
                    }
                    System.out.println("Searching work order by ID");
                    WorkOrder workOrderById = workOrderService.getWorkOrderById(id);
                    return ResponseEntity.ok(workOrderById);
                case "by-client-dni":
                    if (clientDni == null) {
                        return ResponseEntity.badRequest().body("Client DNI is required for 'by-client-dni' query");
                    }
                    System.out.println("Searching work orders by Client DNI");
                    ArrayList<WorkOrder> workOrdersByClientDni = workOrderService.findByClient(clientDni);
                    return ResponseEntity.ok(workOrdersByClientDni);
                default:
                    System.out.println("Invalid query parameter");
                    return ResponseEntity.badRequest().body("Invalid query parameter");
            }
        } catch (NoSuchElementException | NumberFormatException e) {
            System.out.println("Error processing request: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error processing request: " + e.getMessage());
        }
    }
        
    @PostMapping("/save")
    public ResponseEntity<?> saveWorkOrderInstance(@RequestBody WorkOrder WorkOrder){
        if(WorkOrder.getClient() == null || WorkOrder.getStaff() == null || WorkOrder.getDeviceId() == null ){
            return ResponseEntity.badRequest().body("All fields are required");
        }
        return ResponseEntity.ok(workOrderService.createWorkOrder(WorkOrder));
    }
}
