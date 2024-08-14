package VikingoLab.VikingApp.app.Resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import VikingoLab.VikingApp.app.Models.DiagnosticPoint;
import VikingoLab.VikingApp.app.Models.WorkOrder;
import VikingoLab.VikingApp.app.Services.DiagnosticPointService;
import VikingoLab.VikingApp.app.Services.WorkOrderService;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping("/diagnostic-points")
public class DiagnosticPointResource {

    @Autowired
    private DiagnosticPointService diagnosticPointService;

    @Autowired
    private WorkOrderService workOrderService;

    @PostMapping("/add")
    public ResponseEntity<?> addDiagnosticPoint(@RequestBody DiagnosticPoint diagnosticPoint) {
        try {
            log.info("Entrando al método addDiagnosticPoint en DiagnosticPointResource...");
            log.info("DiagnosticPoint recibido: {}", diagnosticPoint);
            log.info("WorkOrderService es null? {}", workOrderService != null ? "No" : "Sí");

            if (diagnosticPoint.getWorkOrder() == null || diagnosticPoint.getWorkOrder().getId() == null) {
                log.warn("WorkOrder es null o su ID es null en el DiagnosticPoint recibido.");
                return ResponseEntity.badRequest().body("WorkOrder no especificado o inválido");
            }

            log.info("WorkOrder ID antes de buscar: {}", diagnosticPoint.getWorkOrder().getId());

            WorkOrder workOrder = workOrderService.getWorkOrderById(diagnosticPoint.getWorkOrder().getId());
            if (workOrder == null) {
                log.warn("WorkOrder no encontrado.");
                return ResponseEntity.badRequest().body("WorkOrder no encontrado");
            }

            log.info("WorkOrder encontrado: {}", workOrder);

            diagnosticPoint.setWorkOrder(workOrder);
            log.info("Después de asignar WorkOrder a DiagnosticPoint: {}", diagnosticPoint.getWorkOrder());

            DiagnosticPoint savedDiagnosticPoint = diagnosticPointService.addDiagnosticPoint(diagnosticPoint);
            log.info("Punto de diagnóstico guardado: {}", savedDiagnosticPoint);

            return ResponseEntity.ok(savedDiagnosticPoint);
        } catch (Exception e) {
            log.error("Error al agregar el punto de diagnóstico: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Error al agregar el punto de diagnóstico: " + e.getMessage());
        }
    }

    @GetMapping("/by-work-order/{workOrderId}/client/{clientDni}")
    public ResponseEntity<?> getDiagnosticPointsByWorkOrderAndClient(
            @PathVariable Long workOrderId,
            @PathVariable int clientDni) {
        try {
            log.info("Entrando al método getDiagnosticPointsByWorkOrderAndClient en DiagnosticPointResource...");
            log.info("WorkOrder ID: {}", workOrderId);
            log.info("Client DNI: {}", clientDni);
    
            WorkOrder workOrder = workOrderService.getWorkOrderById(workOrderId);
            if (workOrder == null || !workOrder.getClient().getDni().equals(clientDni)) {
                log.warn("WorkOrder no encontrado o DNI del cliente no coincide.");
                return ResponseEntity.badRequest().body("WorkOrder no encontrado o DNI del cliente no coincide");
            }
    
            List<DiagnosticPoint> diagnosticPoints = diagnosticPointService.getDiagnosticPointsByWorkOrderId(workOrderId);
            log.info("Puntos de diagnóstico encontrados: {}", diagnosticPoints);
    
            return ResponseEntity.ok(diagnosticPoints);
        } catch (Exception e) {
            log.error("Error al obtener los puntos de diagnóstico: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Error al obtener los puntos de diagnóstico: " + e.getMessage());
        }
    }
    
}
