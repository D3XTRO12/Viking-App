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

@RestController
@RequestMapping("/diagnostic-points")
public class DiagnosticPointResource {

    @Autowired
    private DiagnosticPointService diagnosticPointService;

    @Autowired
    private WorkOrderService workOrderService;

    @PostMapping("/add")
public ResponseEntity<?> addDiagnosticPoint(@RequestBody DiagnosticPoint diagnosticPoint) {
    try {
        System.out.println("Entrando al método addDiagnosticPoint en DiagnosticPointResource...");
        System.out.println("DiagnosticPoint recibido: " + diagnosticPoint);
        System.out.println("WorkOrderService es null? " + (workOrderService != null ? "" : "Sí"));

        if (diagnosticPoint.getWorkOrder() == null || diagnosticPoint.getWorkOrder().getId() == null) {
            System.out.println("WorkOrder es null o su ID es null en el DiagnosticPoint recibido.");
            return ResponseEntity.badRequest().body("WorkOrder no especificado o inválido");
        }

        System.out.println("WorkOrder ID antes de buscar: " + diagnosticPoint.getWorkOrder().getId());

        WorkOrder workOrder = workOrderService.getWorkOrderById(diagnosticPoint.getWorkOrder().getId());
        if (workOrder == null) {
            System.out.println("WorkOrder no encontrado.");
            return ResponseEntity.badRequest().body("WorkOrder no encontrado");
        }

        System.out.println("WorkOrder encontrado: " + workOrder);

        diagnosticPoint.setWorkOrder(workOrder);
        System.out.println("Después de asignar WorkOrder a DiagnosticPoint: " + diagnosticPoint.getWorkOrder());

        DiagnosticPoint savedDiagnosticPoint = diagnosticPointService.addDiagnosticPoint(diagnosticPoint);
        System.out.println("Punto de diagnóstico guardado: " + savedDiagnosticPoint);

        return ResponseEntity.ok(savedDiagnosticPoint);
    } catch (Exception e) {
        System.out.println("Error al agregar el punto de diagnóstico: " + e.getMessage());
        return ResponseEntity.badRequest().body("Error al agregar el punto de diagnóstico: " + e.getMessage());
    }
}


    @GetMapping("/by-work-order/{workOrderId}")
    public ResponseEntity<?> getDiagnosticPointsByWorkOrderId(@PathVariable Long workOrderId) {
        try {
            System.out.println("Entrando al método getDiagnosticPointsByWorkOrderId en DiagnosticPointResource...");
            System.out.println("WorkOrder ID: " + workOrderId);

            List<DiagnosticPoint> diagnosticPoints = diagnosticPointService.getDiagnosticPointsByWorkOrderId(workOrderId);
            System.out.println("Puntos de diagnóstico encontrados: " + diagnosticPoints);

            return ResponseEntity.ok(diagnosticPoints);
        } catch (Exception e) {
            System.out.println("Error al obtener los puntos de diagnóstico: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error al obtener los puntos de diagnóstico: " + e.getMessage());
        }
    }
}
