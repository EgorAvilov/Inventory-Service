package com.example.inventoryservice.dto;

import com.example.inventoryservice.entity.Restaurant;
import com.example.inventoryservice.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private Long id;
    @NotBlank(message = "Username can't be empty.")
    private String firstName;

    @NotBlank(message = "Username can't be empty.")
    private String lastName;

    private List<Role> userRole = new ArrayList<>();

    @NotBlank(message = "Username can't be empty.")
    private String username;

    @NotBlank(message = "Username can't be empty.")
    private String password;

    private RestaurantDto restaurant;
}
