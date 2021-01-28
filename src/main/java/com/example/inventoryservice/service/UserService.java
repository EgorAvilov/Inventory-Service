package com.example.inventoryservice.service;

import com.example.inventoryservice.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto findByUsername(String username);

    UserDto getCurrentUser();

    boolean usernameExists(String username);

    UserDto create(UserDto userDto);

    List<UserDto> findAllByRestaurant();
}
