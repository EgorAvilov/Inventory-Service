package com.example.inventoryservice.dto;

import com.example.inventoryservice.entity.Restaurant;
import com.example.inventoryservice.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private Long id;

    private String firstName;

    private String lastName;

    private List<Role> userRole = new ArrayList<>();

    private String username;

    private String password;

    private Restaurant restaurant;
}
