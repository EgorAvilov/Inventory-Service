package com.example.inventoryservice.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AuthenticationRequestDto {
    @NotBlank(message = "Username can't be empty.")
    String username;

    @NotBlank(message = "Password can't be empty.")
    String password;
}
