package com.example.inventoryservice.service.impl;

import com.example.inventoryservice.converter.UserConverter;
import com.example.inventoryservice.dto.UserDto;
import com.example.inventoryservice.entity.User;
import com.example.inventoryservice.repository.UserRepository;
import com.example.inventoryservice.service.UserCommandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Service
public class UserCommandServiceImpl implements UserCommandService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserCommandServiceImpl.class);
    private final UserRepository userRepository;
    private final UserConverter userConverter;

    public UserCommandServiceImpl(UserRepository userRepository, UserConverter userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Retryable(value = {SQLException.class})
    public UserDto create(UserDto userDto) {
        LOGGER.info("Create user");
        User user = userConverter.dtoToEntity(userDto);
        User persistUser = userRepository.save(user);
        return userConverter.entityToDto(persistUser);
    }
}
