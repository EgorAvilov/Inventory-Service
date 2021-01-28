package com.example.inventoryservice.service.impl;

import com.example.inventoryservice.dto.UserDto;
import com.example.inventoryservice.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public UserDto findByUsername(String username) {
        return null;
    }

    @Override
    public UserDto getCurrentUser() {
        return null;
    }

    @Override
    public boolean usernameExists(String username) {
        return false;
    }
}
