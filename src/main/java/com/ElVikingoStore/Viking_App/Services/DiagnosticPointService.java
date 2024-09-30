package com.ElVikingoStore.Viking_App.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ElVikingoStore.Viking_App.Models.DiagnosticPoint;
import com.ElVikingoStore.Viking_App.Models.WorkOrder;
import com.ElVikingoStore.Viking_App.Repositories.DiagnosticPointRepo;
import com.ElVikingoStore.Viking_App.Repositories.WorkOrderRepo;

@Service
public class DiagnosticPointService {

    private final DiagnosticPointRepo diagnosticPointRepo;
    private final WorkOrderRepo workOrderRepo;

    public DiagnosticPointService(DiagnosticPointRepo diagnosticPointRepo, WorkOrderRepo workOrderRepo) {
        this.diagnosticPointRepo = diagnosticPointRepo;
        this.workOrderRepo = workOrderRepo; // Asegúrate de que se inyecte aquí correctamente
    }

    public DiagnosticPoint addDiagnosticPoint(DiagnosticPoint diagnosticPoint) {
        System.out.println("Entrando a addDiagnosticPoint...");
        System.out.println("WorkOrderRepo es null? " + (workOrderRepo == null));

        // Verifica si el WorkOrder existe
        WorkOrder workOrder = workOrderRepo.findById(diagnosticPoint.getWorkOrder().getId())
            .orElseThrow(() -> new IllegalArgumentException("Invalid work order ID"));

        diagnosticPoint.setWorkOrder(workOrder);
        return diagnosticPointRepo.save(diagnosticPoint);
    }

    public List<DiagnosticPoint> getDiagnosticPointsByWorkOrderId(UUID workOrderId) {
        return diagnosticPointRepo.findByWorkOrder_Id(workOrderId);
    }

    @Transactional(readOnly = true) // Asegura que haya una sesión activa para la carga
    public DiagnosticPoint findById(UUID id) {
        // Encuentra el DiagnosticPoint por su ID
        DiagnosticPoint diagnosticPoint = diagnosticPointRepo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("DiagnosticPoint not found for ID: " + id));
        
        // Comprobar e inicializar multimediaFiles si es necesario
        if (diagnosticPoint.getMultimediaFiles() == null) {
            diagnosticPoint.setMultimediaFiles(new ArrayList<>());
        }

        return diagnosticPoint;
    }
}
