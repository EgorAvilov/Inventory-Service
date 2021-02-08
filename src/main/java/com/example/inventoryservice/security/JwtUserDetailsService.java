package com.example.inventoryservice.security;

import com.example.inventoryservice.dto.UserDto;
import com.example.inventoryservice.security.jwt.JwtUser;
import com.example.inventoryservice.security.jwt.JwtUserFactory;
import com.example.inventoryservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;
    Logger logger = LoggerFactory.getLogger(JwtUserDetailsService.class);

    @Autowired
    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto user = userService.findByUsername(username);
        if (user == null) {
            String USER_WITH_USERNAME_MESSAGE = "User with username: ";
            String NOT_FOUND_MESSAGE = " not found";
            logger.error(USER_WITH_USERNAME_MESSAGE + username + NOT_FOUND_MESSAGE);
            throw new UsernameNotFoundException(USER_WITH_USERNAME_MESSAGE + username + NOT_FOUND_MESSAGE);
        }
        JwtUser jwtUser = JwtUserFactory.create(user);
        logger.info("IN loadUserByUsername - user with username: {} successfully loaded", username);
        return jwtUser;
    }
}