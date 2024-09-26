package com.ElVikingoStore.Viking_App.DTOs;

import lombok.Data;

@Data
public class UserRoleDto {

    private Long id;

    private Long userId; // O el objeto User si es necesario

    private Long rolId; // O el objeto Rol si es necesario
}
