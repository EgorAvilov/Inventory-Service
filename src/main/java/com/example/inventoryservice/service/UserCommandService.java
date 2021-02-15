package com.example.inventoryservice.service;

import com.example.inventoryservice.dto.UserDto;

import java.util.List;

public interface UserCommandService {

    UserDto create(UserDto userDto);
}
