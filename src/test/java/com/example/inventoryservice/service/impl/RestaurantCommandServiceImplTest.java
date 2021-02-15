package com.example.inventoryservice.service.impl;

import com.example.inventoryservice.converter.RestaurantConverter;
import com.example.inventoryservice.dto.RestaurantDto;
import com.example.inventoryservice.dto.UserDto;
import com.example.inventoryservice.entity.Restaurant;
import com.example.inventoryservice.entity.Role;
import com.example.inventoryservice.exception.ServiceException;
import com.example.inventoryservice.repository.RestaurantRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RestaurantCommandServiceImplTest {
    @Mock
    RestaurantRepository restaurantRepository;

    @Mock
    RestaurantConverter restaurantConverter;

    @Mock
    UserQueryServiceImpl userService;

    @InjectMocks
    RestaurantCommandServiceImpl restaurantService;

    private UserDto userDto;
    private Restaurant restaurant;
    private RestaurantDto restaurantDto;

    @Before
    public void setUp() {
        restaurantDto = RestaurantDto.builder()
                                     .id(1L)
                                     .name("restaurant")
                                     .build();

        restaurant = Restaurant.builder()
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
    }

    @Test(expected = ServiceException.class)
    public void createServiceExceptionNotUniqueName() {
        //given
        //when
        lenient().when(userService.getCurrentUser())
                 .thenReturn(userDto);
        lenient().when(restaurantConverter.dtoToEntity(restaurantDto))
                 .thenReturn(restaurant);
        when(restaurantRepository.countAllByName(restaurant.getName())).thenReturn(1L);
        restaurantService.create(restaurantDto);
    }

    @Test
    public void create() {
        //given
        //when
        lenient().when(userService.getCurrentUser())
                 .thenReturn(userDto);
        when(restaurantConverter.dtoToEntity(restaurantDto)).thenReturn(restaurant);
        when(restaurantRepository.countAllByName(restaurant.getName())).thenReturn(0L);
        when(restaurantRepository.save(restaurant)).thenReturn(restaurant);
        restaurantService.create(restaurantDto);
        //then
        verify(restaurantConverter).entityToDto(restaurant);
        assertThat(restaurant).extracting(Restaurant::getName)
                              .isEqualTo(restaurantDto.getName());
    }
}
