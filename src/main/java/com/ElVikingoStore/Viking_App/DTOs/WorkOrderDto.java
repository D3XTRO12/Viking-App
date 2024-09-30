package com.ElVikingoStore.Viking_App.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkOrderDto {
    private UUID id;

    private UUID clientId;
    private UUID staffId;
    private UUID deviceId;

    private String issueDescription;
    private String repairStatus;

}
