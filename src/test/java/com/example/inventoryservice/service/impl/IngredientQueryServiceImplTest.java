
package com.example.inventoryservice.service.impl;

import com.example.inventoryservice.converter.IngredientConverter;
import com.example.inventoryservice.dto.RestaurantDto;
import com.example.inventoryservice.dto.UserDto;
import com.example.inventoryservice.entity.Ingredient;
import com.example.inventoryservice.entity.Restaurant;
import com.example.inventoryservice.entity.Role;
import com.example.inventoryservice.repository.IngredientRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class IngredientQueryServiceImplTest {

    @Mock
    IngredientRepository ingredientRepository;

    @Mock
    IngredientConverter ingredientConverter;

    @Mock
    UserQueryServiceImpl userService;

    @InjectMocks
    IngredientQueryServiceImpl ingredientService;

    private UserDto userDto;
    private Ingredient ingredient;

    @Before
    public void setUp() {
        RestaurantDto restaurantDto = RestaurantDto.builder()
                                                   .id(1L)
                                                   .build();

        Restaurant restaurant = Restaurant.builder()
                                          .id(1L)
                                          .build();
        ingredient = Ingredient
                .builder()
                .id(1L)
                .amount(BigDecimal.valueOf(2))
                .measureUnit("measureUnit")
                .name("name")
                .restaurant(restaurant)
                .price(BigDecimal.valueOf(2))
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

    @Test
    public void findAllByRestaurant() {
        //given
        List<Ingredient> ingredients = new ArrayList<>() {{
            add(ingredient);
        }};
        //when
        when(userService.getCurrentUser()).thenReturn(userDto);
        when(ingredientRepository.findAllByRestaurantId(userDto.getRestaurant()
                                                               .getId())).thenReturn(ingredients);
        ingredientService.findAllByRestaurant();
        //then
        verify(ingredientConverter).entityToDto(ingredients);
        assertThat(ingredients).extracting(Ingredient::getRestaurant)
                               .extracting(Restaurant::getId)
                               .containsOnly(userDto.getRestaurant()
                                                    .getId());
    }
}
