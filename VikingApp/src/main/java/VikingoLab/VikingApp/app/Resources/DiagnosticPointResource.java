package VikingoLab.VikingApp.app.Resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import VikingoLab.VikingApp.app.DTOs.DiagnosticPointResponse;
import VikingoLab.VikingApp.app.Models.DiagnosticPoint;
import VikingoLab.VikingApp.app.Models.WorkOrder;
import VikingoLab.VikingApp.app.Repositories.StorageInterface;
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

    @Autowired
    private StorageInterface storageService;



    // Nueva variable para almacenar la dirección IP
    @Value("${server.address}")
    private String serverAddress; 
    @Value("${server.port}")
    private String serverPort;

    @PostMapping(
    value = "/add",
    consumes = {"multipart/form-data", "application/octet-stream"})
    public ResponseEntity<?> addDiagnosticPoint(
        @RequestPart("diagnosticPoint") DiagnosticPoint diagnosticPoint,
        @RequestPart("file") MultipartFile file) {
        try {
            log.info("Entrando al método addDiagnosticPoint en DiagnosticPointResource...");
            log.info("DiagnosticPoint recibido: {}", diagnosticPoint);
            
            if (file.isEmpty()) {
                log.warn("No se recibió ningún archivo multimedia.");
                return ResponseEntity.badRequest().body("Archivo multimedia no recibido");
            } else {
                log.info("Archivo multimedia recibido: nombre del archivo - {}, tamaño - {} bytes", file.getOriginalFilename(), file.getSize());
            }
    
            if (diagnosticPoint.getWorkOrder() == null || diagnosticPoint.getWorkOrder().getId() == null) {
                log.warn("WorkOrder es null o su ID es null en el DiagnosticPoint recibido.");
                return ResponseEntity.badRequest().body("WorkOrder no especificado o inválido");
            }
    
            WorkOrder workOrder = workOrderService.getWorkOrderById(diagnosticPoint.getWorkOrder().getId());
            if (workOrder == null) {
                log.warn("WorkOrder no encontrado.");
                return ResponseEntity.badRequest().body("WorkOrder no encontrado");
            }
    
            diagnosticPoint.setWorkOrder(workOrder);
    
            String filename = storageService.store(file);
            // Cambia la URL para que utilice el formato solicitado
            String url = "http://" + serverAddress + ":" + serverPort + "/uploads/" + filename;

    
            diagnosticPoint.getMultimediaFiles().add(url);
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

            // Convertir a JSON para un log más legible
            ObjectMapper mapper = new ObjectMapper();
            String jsonResult = mapper.writeValueAsString(diagnosticPoints);
            log.info("Puntos de diagnóstico encontrados: {}", jsonResult);

            return ResponseEntity.ok(diagnosticPoints);
        } catch (JsonProcessingException e) {
            log.error("Error al obtener los puntos de diagnóstico: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Error al obtener los puntos de diagnóstico: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiagnosticPointResponse> getDiagnosticPoint(@PathVariable Long id) {
        DiagnosticPoint diagnosticPoint = diagnosticPointService.findById(id);
        
        DiagnosticPointResponse response = new DiagnosticPointResponse(
            diagnosticPoint.getId(),
            diagnosticPoint.getMultimediaFiles(),
            diagnosticPoint.getDescription(),
            diagnosticPoint.getNotes()
        );

        return ResponseEntity.ok(response);
    }
}
