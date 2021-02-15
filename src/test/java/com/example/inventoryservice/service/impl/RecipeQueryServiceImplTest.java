
package com.example.inventoryservice.service.impl;

import com.example.inventoryservice.converter.RecipeConverter;
import com.example.inventoryservice.dto.*;
import com.example.inventoryservice.entity.*;
import com.example.inventoryservice.repository.RecipeRepository;
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
public class RecipeQueryServiceImplTest {
    @Mock
    RecipeRepository recipeRepository;

    @Mock
    RecipeConverter recipeConverter;

    @Mock
    UserQueryServiceImpl userService;

    @InjectMocks
    RecipeQueryServiceImpl recipeService;

    private UserDto userDto;
    private Recipe recipe;

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
                .price(BigDecimal.valueOf(2))
                .build();
        RecipeIngredient recipeIngredient = RecipeIngredient.builder()
                                                            .id(1L)
                                                            .ingredient(ingredient)
                                                            .amount(BigDecimal.valueOf(2))
                                                            .build();
        recipe = Recipe.builder()
                       .recipeIngredients(Collections.singletonList(recipeIngredient))
                       .restaurant(restaurant)
                       .name("name")
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
        List<Recipe> recipes = new ArrayList<>() {{
            add(recipe);
        }};
        //when
        when(userService.getCurrentUser()).thenReturn(userDto);
        when(recipeRepository.findAllByRestaurantId(userDto.getRestaurant()
                                                           .getId())).thenReturn(recipes);
        recipeService.findAllByRestaurant();
        //then
        verify(recipeConverter).entityToDto(recipes);
        assertThat(recipes).extracting(Recipe::getRestaurant)
                           .extracting(Restaurant::getId)
                           .containsOnly(userDto.getRestaurant()
                                                .getId());
    }
}
