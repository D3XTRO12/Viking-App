package com.ElVikingoStore.Viking_App.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ElVikingoStore.Viking_App.Models.WorkOrder;

@Repository
public interface WorkOrderRepo extends JpaRepository<WorkOrder, Long> {
    List<WorkOrder> findByClient_Dni(Integer clientDni);
}
