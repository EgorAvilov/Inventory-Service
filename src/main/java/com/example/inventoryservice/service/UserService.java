package com.example.inventoryservice.service;

import com.example.inventoryservice.dto.UserDto;

public interface UserService {
    UserDto findByUsername(String username);

    UserDto getCurrentUser();

    boolean usernameExists(String username);
}
