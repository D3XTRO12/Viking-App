package com.ElVikingoStore.Viking_App.DTOs;

import com.ElVikingoStore.Viking_App.Models.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;
@Schema(
        description = "Objeto de transferencia de datos para usuarios",
        title = "UserDto"
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @Schema(
            description = "Identificador único del usuario",
            example = "123e4567-e89b-12d3-a456-426614174000",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private UUID id;

    @Schema(
            description = "Nombre completo del usuario",
            example = "Juan Pérez",
            required = true,
            minLength = 2,
            maxLength = 100
    )
    private String name;

    @Schema(
            description = "Documento Nacional de Identidad",
            example = "12345678",
            required = true,
            minimum = "1000000",
            maximum = "99999999"
    )
    private Integer dni;

    @Schema(
            description = "Tipo de usuario en el sistema",
            example = "end-user",
            allowableValues = {"end-user", "company", "staff"},
            required = true
    )
    private String userType;

    @Schema(
            description = "Dirección del usuario",
            example = "Alemania 1",
            required = true,
            minLength = 5,
            maxLength = 200
    )
    private String address;

    @Schema(
            description = "Número de teléfono principal",
            example = "+54 2604 - 111111",
            required = true,
            pattern = "^\\+?[0-9\\s-]{8,20}$"
    )
    private String phoneNumber;

    @Schema(
            description = "Número de teléfono secundario (opcional)",
            example = "+54 2604 - 111111",
            pattern = "^\\+?[0-9\\s-]{8,20}$"
    )
    private String secondaryPhoneNumber;

    @Schema(
            description = "Correo electrónico del usuario",
            example = "email@email.com",
            pattern = "^[A-Za-z0-9+_.-]+@(.+)$"
    )
    private String email;

    @Schema(
            description = "Contraseña del usuario (requerida para company y staff)",
            example = "miContraseña123",
            writeOnly = true,
            minLength = 8,
            maxLength = 100
    )
    private String password;

    @Schema(
            description = "CUIT de la empresa (solo para usuarios tipo company)",
            example = "20-12345678-9",
            pattern = "^\\d{2}-\\d{8}-\\d{1}$"
    )
    private String cuit;

    @Schema(
            description = "Identificador del rol a asignar",
            example = "123e4567-e89b-12d3-a456-426614174000"
    )
    private UUID roleId;

}

