package com.ElVikingoStore.Viking_App.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Schema(
        description = "Objeto de transferencia de datos para dispositivos",
        title = "DeviceDto"
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceDto {
    @Schema(
            description = "Identificador único del dispositivo",
            example = "123e4567-e89b-12d3-a456-426614174000",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private UUID id;
    @Schema(
            description = "Tipo de dispositivo",
            example = "smartphone",
            required = true,
            minLength = 2,
            maxLength = 100
    )
    private String type;
    @Schema(
            description = "Marca del dispositivo",
            example = "Samsung",
            required = true,
            minLength = 2,
            maxLength = 100
    )
    private String brand;
    @Schema(
            description = "Modelo del dispositivo",
            example = "Galaxy S20",
            required = true,
            minLength = 2,
            maxLength = 100
    )
    private String model;
    @Schema(
            description = "Número de serie del dispositivo",
            example = "1234567890",
            required = true,
            minLength = 2,
            maxLength = 100
    )
    private String serialNumber;
    @Schema(
            description = "Identificador único del usuario",
            example = "123e4567-e89b-12d3-a456-426614174000",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private UUID userId;
}
