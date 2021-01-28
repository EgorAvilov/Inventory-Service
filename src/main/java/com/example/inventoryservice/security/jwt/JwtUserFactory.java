package com.example.inventoryservice.security.jwt;

import com.example.inventoryservice.dto.UserDto;
import com.example.inventoryservice.entity.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {
    public JwtUserFactory() {
    }

    public static JwtUser create(UserDto userDto) {
        return new JwtUser(
                userDto.getId(),
                userDto.getFirstName(),
                userDto.getLastName(),
                mapToGrantedAuthorities(userDto.getUserRole()),
                userDto.getUsername(),
                userDto.getPassword()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> userRoles) {
        return userRoles.stream()
                        .map(role -> new SimpleGrantedAuthority(role.name()))
                        .collect(Collectors.toList());
    }
}