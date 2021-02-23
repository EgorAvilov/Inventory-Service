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
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.lenient;

@RunWith(MockitoJUnitRunner.class)
public class UserCommandServiceImplTest {
    @Mock
    UserRepository userRepository;

    @Mock
    UserConverter userConverter;

    @Mock
    UserCommandServiceImpl userService;

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
    public void create() {
        //given
        //when
        lenient().when(userConverter.dtoToEntity(userDto)).thenReturn(user);
        lenient().when(userRepository.save(user)).thenReturn(user);
        userService.create(userDto);
        //then
        assertThat(user).extracting(User::getFirstName)
                .isEqualTo(userDto.getFirstName());
    }
}