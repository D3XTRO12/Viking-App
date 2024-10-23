package com.ElVikingoStore.Viking_App.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Schema(
        description = "Objeto de transferencia de datos para ordenes de trabajo",
        title = "WorkOrderDto"
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkOrderDto {
    @Schema(
            description = "Identificador único de la orden de trabajo",
            example = "123e4567-e89b-12d3-a456-426614174000",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private UUID id;
    @Schema(
            description = "Identificador único del cliente",
            example = "123e4567-e89b-12d3-a456-426614174000",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private UUID clientId;
    @Schema(
            description = "Identificador único del personal",
            example = "123e4567-e89b-12d3-a456-426614174000",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private UUID staffId;
    @Schema(
            description = "Identificador único del dispositivo",
            example = "123e4567-e89b-12d3-a456-426614174000",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private UUID deviceId;
    @Schema(
            description = "Descripción del problema",
            example = "Pantalla rota",
            required = true,
            minLength = 2,
            maxLength = 200
    )
    private String issueDescription;
    @Schema(
            description = "Estado de la reparación",
            example = "pending",
            allowableValues = {"pending", "in-progress", "completed"},
            required = true
    )
    private String repairStatus;

}
