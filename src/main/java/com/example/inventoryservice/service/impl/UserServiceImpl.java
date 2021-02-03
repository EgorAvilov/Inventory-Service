package com.example.inventoryservice.service.impl;

import com.example.inventoryservice.converter.UserConverter;
import com.example.inventoryservice.dto.UserDto;
import com.example.inventoryservice.entity.User;
import com.example.inventoryservice.repository.UserRepository;
import com.example.inventoryservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;
    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepository userRepository, UserConverter userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    @Override
    @Transactional
    public UserDto findByUsername(String username) {
        logger.info("Find user by username {}", username);
        User user = userRepository.findByUsername(username);
        return userConverter.entityToDto(user);
    }

    @Override
    @Transactional
    public UserDto getCurrentUser() {
        logger.info("Get current user");
        Authentication authentication = SecurityContextHolder.getContext()
                                                             .getAuthentication();
        String currentPrincipalName = authentication.getName();
        return findByUsername(currentPrincipalName);
    }


    @Override
    public boolean usernameExists(String username) {
        logger.info("Check for existing user username {}", username);
        return userRepository.findAllByUsernameIgnoreCase(username)
                             .size() != 0;
    }

    @Override
    public UserDto create(UserDto userDto) {
        logger.info("Create user");
        User user = userConverter.dtoToEntity(userDto);
        User persistUser = userRepository.save(user);
        return userConverter.entityToDto(persistUser);
    }

    @Override
    public List<UserDto> findAllByRestaurant() {
        logger.info("Find all users by restaurant");
        UserDto userDto = getCurrentUser();
        List<User> users = userRepository.findAllByRestaurant_Id(userDto.getRestaurant()
                                                                        .getId());
        return userConverter.entityToDto(users);
    }
}
