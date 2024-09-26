package com.ElVikingoStore.Viking_App.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceDto {

    private Long id;

    private String type;

    private String brand;

    private String model;

    private String serialNumber;

    private Long userId;
}
