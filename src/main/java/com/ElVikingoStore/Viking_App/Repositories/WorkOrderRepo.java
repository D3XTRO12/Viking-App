package com.ElVikingoStore.Viking_App.Repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ElVikingoStore.Viking_App.Models.WorkOrder;

@Repository
public interface WorkOrderRepo extends JpaRepository<WorkOrder, UUID> {
    List<WorkOrder> findByStaffId(UUID staffId); // Asegúrate de que devuelva List
    List<WorkOrder> findByClientId(UUID clientId); // Asegúrate de que devuelva List
}
