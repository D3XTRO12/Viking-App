package VikingoLab.VikingApp.app.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import VikingoLab.VikingApp.app.Models.DiagnosticPoint;
import VikingoLab.VikingApp.app.Models.WorkOrder;
import VikingoLab.VikingApp.app.Repositories.DiagnosticPointRepo;
import VikingoLab.VikingApp.app.Repositories.WorkOrderRepo;

@Service
public class DiagnosticPointService {

    private final DiagnosticPointRepo diagnosticPointRepo;
    private final WorkOrderRepo workOrderRepo;

    @Autowired
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

    public List<DiagnosticPoint> getDiagnosticPointsByWorkOrderId(Long workOrderId) {
        return diagnosticPointRepo.findByWorkOrder_Id(workOrderId);
    }
}
