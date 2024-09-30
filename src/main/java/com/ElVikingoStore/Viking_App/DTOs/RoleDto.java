package com.ElVikingoStore.Viking_App.DTOs;

import lombok.Data;

import java.util.UUID;
@Data
public class RoleDto {
    private UUID id;
    private String descripcion;
    private String permission;
}
