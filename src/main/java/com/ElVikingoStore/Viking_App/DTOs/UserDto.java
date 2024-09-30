package com.ElVikingoStore.Viking_App.DTOs;

import com.ElVikingoStore.Viking_App.Models.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private UUID id;

    private String name;

    private Integer dni;

    private String userType;

    private String address;

    private String phoneNumber;

    private String secondaryPhoneNumber;

    private String email;

    private String password;

    private String cuit;

    private UUID roleId;

}

