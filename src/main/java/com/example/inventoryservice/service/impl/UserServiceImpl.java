package com.example.inventoryservice.service.impl;

import com.example.inventoryservice.converter.UserConverter;
import com.example.inventoryservice.dto.UserDto;
import com.example.inventoryservice.entity.User;
import com.example.inventoryservice.repository.UserRepository;
import com.example.inventoryservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.annotation.Retryable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final UserConverter userConverter;

    public UserServiceImpl(UserRepository userRepository, UserConverter userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    @Override
    @Transactional
    public UserDto findByUsername(String username) {
        LOGGER.info("Find user by username {}", username);
        User user = userRepository.findByUsername(username);
        return userConverter.entityToDto(user);
    }

    @Override
    @Transactional
    public UserDto getCurrentUser() {
        LOGGER.info("Get current user");
        Authentication authentication = SecurityContextHolder.getContext()
                                                             .getAuthentication();
        String currentPrincipalName = authentication.getName();
        return findByUsername(currentPrincipalName);
    }


    @Override
    public boolean usernameExists(String username) {
        LOGGER.info("Check for existing user username {}", username);
        return userRepository.findAllByUsername(username) != 0;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Retryable(value = {SQLException.class})
    public UserDto create(UserDto userDto) {
        LOGGER.info("Create user");
        User user = userConverter.dtoToEntity(userDto);
        User persistUser = userRepository.save(user);
        return userConverter.entityToDto(persistUser);
    }

    @Override
    public List<UserDto> findAllByRestaurant() {
        LOGGER.info("Find all users by restaurant");
        UserDto userDto = getCurrentUser();
        List<User> users = userRepository.findAllByRestaurantId(userDto.getRestaurant()
                                                                       .getId());
        return userConverter.entityToDto(users);
    }
}
