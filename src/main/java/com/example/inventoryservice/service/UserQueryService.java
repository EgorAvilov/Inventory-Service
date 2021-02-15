package com.example.inventoryservice.service;

import com.example.inventoryservice.dto.UserDto;

import java.util.List;

public interface UserQueryService {
    UserDto findByUsername(String username);

    UserDto getCurrentUser();

    boolean usernameExists(String username);

    List<UserDto> findAllByRestaurant();
}
