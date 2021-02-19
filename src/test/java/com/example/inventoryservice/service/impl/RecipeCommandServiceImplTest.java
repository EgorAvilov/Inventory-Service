

package com.example.inventoryservice.service.impl;

import com.example.inventoryservice.converter.RecipeConverter;
import com.example.inventoryservice.converter.RestaurantConverter;
import com.example.inventoryservice.dto.*;
import com.example.inventoryservice.entity.*;
import com.example.inventoryservice.exception.ServiceException;
import com.example.inventoryservice.repository.IngredientRepository;
import com.example.inventoryservice.repository.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Collections;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RecipeCommandServiceImplTest {
    @Mock
    RecipeRepository recipeRepository;

    @Mock
    IngredientRepository ingredientRepository;

    @Mock
    RecipeConverter recipeConverter;

    @Mock
    UserQueryServiceImpl userService;

    @Mock
    RestaurantConverter restaurantConverter;

    @InjectMocks
    RecipeCommandServiceImpl recipeService;

    private UserDto userDto;
    private Recipe recipe;
    private RecipeCreateDto recipeCreateDto;
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
        Ingredient ingredient = Ingredient
                .builder()
                .id(1L)
                .amount(BigDecimal.valueOf(2))
                .measureUnit("measureUnit")
                .name("name")
                .restaurant(restaurant)
                .price(BigDecimal.valueOf(2))
                .build();
        IngredientRecipeIngredientDto ingredientRecipeIngredientDto = IngredientRecipeIngredientDto
                .builder()
                .name("ingredient")
                .build();
        RecipeIngredient recipeIngredient = RecipeIngredient.builder()
                .id(1L)
                .ingredient(ingredient)
                .amount(BigDecimal.valueOf(2))
                .build();
        RecipeIngredientDto recipeIngredientDto = RecipeIngredientDto.builder()
                .ingredient(ingredientRecipeIngredientDto)
                .amount(BigDecimal.valueOf(2))
                .build();
        recipe = Recipe.builder()
                .recipeIngredients(Collections.singletonList(recipeIngredient))
                .restaurant(restaurant)
                .name("name")
                .id(1L)
                .build();
        recipeCreateDto = RecipeCreateDto.builder()
                .recipeIngredients(Collections.singletonList(recipeIngredientDto))
                .name("name")
                .percent(BigDecimal.valueOf(23))
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
    public void createServiceExceptionNotUniqueIngredient() {
        //given
        //when
        when(userService.getCurrentUser()).thenReturn(userDto);
        when(restaurantConverter.dtoToEntity(restaurantDto)).thenReturn(restaurant);
        when(recipeConverter.dtoToEntity(recipeCreateDto)).thenReturn(recipe);
        when(recipeRepository.countAllByNameAndRestaurantId(recipe.getName(), recipe.getRestaurant()
                .getId())).thenReturn(1L);
        recipeService.create(recipeCreateDto);
    }

    @Test
    public void create() {
        //given
        //when
        when(userService.getCurrentUser()).thenReturn(userDto);
        when(restaurantConverter.dtoToEntity(restaurantDto)).thenReturn(restaurant);
        when(recipeConverter.dtoToEntity(recipeCreateDto)).thenReturn(recipe);
        when(recipeRepository.countAllByNameAndRestaurantId(recipe.getName(), recipe.getRestaurant()
                .getId())).thenReturn(0L);
        when(recipeRepository.save(recipe)).thenReturn(recipe);
        recipeService.create(recipeCreateDto);
        //then
        verify(recipeConverter).entityToDto(recipe);
        assertThat(recipe).extracting(Recipe::getName)
                .isEqualTo(recipeCreateDto.getName());
    }
}

