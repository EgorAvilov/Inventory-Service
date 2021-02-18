
package com.example.inventoryservice.service.impl;

import com.example.inventoryservice.converter.DishConverter;
import com.example.inventoryservice.dto.RestaurantDto;
import com.example.inventoryservice.dto.UserDto;
import com.example.inventoryservice.entity.*;
import com.example.inventoryservice.repository.DishRepository;
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
public class DishQueryServiceImplTest {

    @Mock
    DishRepository dishRepository;

    @Mock
    DishConverter dishConverter;

    @Mock
    UserQueryServiceImpl userService;

    @InjectMocks
    DishQueryServiceImpl dishService;

    private UserDto userDto;
    private Dish dish;

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
        Ingredient ingredient = Ingredient
                .builder()
                .id(1L)
                .amount(BigDecimal.valueOf(2))
                .measureUnit("measureUnit")
                .name("name")
                .restaurant(restaurant)
                .price(BigDecimal.valueOf(1))
                .build();
        RecipeIngredient recipeIngredient = RecipeIngredient.builder()
                                                            .id(1L)
                                                            .ingredient(ingredient)
                                                            .amount(BigDecimal.valueOf(2))
                                                            .build();

        Recipe recipe = Recipe.builder()
                              .id(1L)
                              .name("recipe")
                              .restaurant(restaurant)
                              .recipeIngredients(Collections.singletonList(recipeIngredient))
                              .build();
        dish = Dish.builder()
                   .price(BigDecimal.valueOf(1))
                   .restaurant(restaurant)
                   .recipe(recipe)
                   .id(1L)
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
        List<Dish> dishes = new ArrayList<>() {{
            add(dish);
        }};
        //when
        when(userService.getCurrentUser()).thenReturn(userDto);
        when(dishRepository.findAllByRestaurantId(userDto.getRestaurant()
                                                         .getId())).thenReturn(dishes);
        dishService.findAllByRestaurant();
        //then
        verify(dishConverter).entityToDto(dishes);
        assertThat(dishes).extracting(Dish::getRestaurant)
                          .extracting(Restaurant::getId)
                          .containsOnly(userDto.getRestaurant()
                                               .getId());
        assertThat(dishes).extracting(Dish::getRecipe)
                          .extracting(Recipe::getRestaurant)
                          .extracting(Restaurant::getId)
                          .containsOnly(userDto.getRestaurant()
                                               .getId());
    }
}
