package com.ElVikingoStore.Viking_App.Resources;

import com.ElVikingoStore.Viking_App.DTOs.WorkOrderDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ElVikingoStore.Viking_App.Models.WorkOrder;
import com.ElVikingoStore.Viking_App.Services.WorkOrderService;
import com.ElVikingoStore.Viking_App.Services.WorkOrderService.CustomException;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping("/api/work-order")
public class WorkOrderResource {

    private final WorkOrderService workOrderService;

    //@Autowired
    public WorkOrderResource(WorkOrderService workOrderService) {
        this.workOrderService = workOrderService;
    }

    @PostMapping("/save")
    public ResponseEntity<WorkOrder> createWorkOrder(@RequestBody WorkOrderDto workOrderDto) {
        WorkOrder createdWorkOrder = workOrderService.saveWorkOrder(workOrderDto);
        return ResponseEntity.ok(createdWorkOrder);
    }
}
