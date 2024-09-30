package com.ElVikingoStore.Viking_App.DTOs;

import lombok.Data;

import java.util.UUID;

@Data
public class UserRoleDto {

    private UUID id;

    private UUID userId; // O el objeto User si es necesario

    private UUID roleId; // O el objeto Rol si es necesario
}
