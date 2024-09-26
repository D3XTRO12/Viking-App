package com.ElVikingoStore.Viking_App.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkOrderDto {
    private Long id;

    private String clientId;
    private String staffId;
    private Long deviceId;

    private String issueDescription;
    private String repairStatus;

}
