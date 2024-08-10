package VikingoLab.VikingApp.app.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import VikingoLab.VikingApp.app.Models.DiagnosticPoint;

@Repository
public interface DiagnosticPointRepo extends JpaRepository<DiagnosticPoint, Long> {
    List<DiagnosticPoint> findByWorkOrder_Id(Long workOrderId);
}
