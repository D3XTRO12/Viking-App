package com.ElVikingoStore.Viking_App.DTOs;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserDto {
    private String email;
    private String password;

}
