package com.example.inventoryservice.service.impl;

import com.example.inventoryservice.converter.UserConverter;
import com.example.inventoryservice.dto.RestaurantDto;
import com.example.inventoryservice.dto.UserDto;
import com.example.inventoryservice.entity.Restaurant;
import com.example.inventoryservice.entity.Role;
import com.example.inventoryservice.entity.User;
import com.example.inventoryservice.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserQueryServiceImplTest {

    @InjectMocks
    UserQueryServiceImpl userService;

    @Mock
    private UserConverter userConverter;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;
    private UserDto userDto;
    private User user;



    @Before
    public void setUp() {
        RestaurantDto restaurantDto = RestaurantDto.builder()
                .id(1L)
                .name("restaurant")
                .build();

        Restaurant restaurant = Restaurant.builder()
                .id(1L)
                .name("restaurant")
                .build();

        userDto = UserDto.builder()
                .firstName("firstName")
                .lastName("lastName")
                .username("username")
                .password("password")
                .userRole(Collections.singletonList(Role.KITCHEN_STUFF))
                .restaurant(restaurantDto)
                .build();
        user = User.builder()
                .firstName("firstName")
                .lastName("lastName")
                .username("username")
                .password("password")
                .userRole(Collections.singletonList(Role.KITCHEN_STUFF))
                .restaurant(restaurant)
                .build();

    }

    @Test
    public void findByUsername() {
        //given
        String username = "username";
        //when
        lenient().when(userRepository.findByUsername(username)).thenReturn(user);
        //then
        assertThat(user.getUsername()).isEqualTo(username);
    }


    @Test
    public void getCurrentUser() {
        //when
        when(securityContext.getAuthentication())
                .thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn("username");
        when(userService.findByUsername("username")).thenReturn(userDto);
        //than
        assertThat(userService.getCurrentUser()).isEqualTo(userDto);
    }
}