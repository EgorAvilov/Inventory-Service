/*


package com.example.inventoryservice.service.impl;

import com.example.inventoryservice.converter.DishConverter;
import com.example.inventoryservice.converter.RestaurantConverter;
import com.example.inventoryservice.dto.DishCreateDto;
import com.example.inventoryservice.dto.RecipeDishDto;
import com.example.inventoryservice.dto.RestaurantDto;
import com.example.inventoryservice.dto.UserDto;
import com.example.inventoryservice.entity.*;
import com.example.inventoryservice.exception.NoItemException;
import com.example.inventoryservice.exception.ServiceException;
import com.example.inventoryservice.repository.DishRepository;
import com.example.inventoryservice.repository.RecipeIngredientRepository;
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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DishCommandServiceImplTest {

    @Mock
    DishRepository dishRepository;

    @Mock
    RecipeIngredientRepository recipeIngredientRepository;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    DishConverter dishConverter;

    @Mock
    RestaurantConverter restaurantConverter;

    @Mock
    UserQueryServiceImpl userService;

    @InjectMocks
    DishCommandServiceImpl dishService;

    private UserDto userDto;
    private DishCreateDto dishDto;
    private Restaurant restaurant;
    private RestaurantDto restaurantDto;
    private Recipe recipe;
    private Dish dish;
    private RecipeIngredient recipeIngredient;
    private Ingredient ingredient;
    private List<Long> recipeIngredientIds;

    @Before
    public void setUp() {
        restaurantDto = RestaurantDto.builder()
                .id(1L)
                .name("restaurant")
                .build();

        RecipeDishDto recipeDishDto = RecipeDishDto.builder()
                .name("recipe")
                .build();
        dishDto = DishCreateDto.builder()
                .recipe(recipeDishDto)
                .build();
        restaurant = Restaurant.builder()
                .id(1L)
                .name("restaurant")
                .build();
        ingredient = Ingredient
                .builder()
                .id(1L)
                .amount(BigDecimal.valueOf(2))
                .measureUnit("measureUnit")
                .name("name")
                .restaurant(restaurant)
                .price(BigDecimal.valueOf(1))
                .build();
        recipeIngredientIds = new ArrayList<>() {{
            add(1L);
        }};
        recipeIngredient = RecipeIngredient.builder()
                .id(1L)
                .ingredient(ingredient)
                .amount(BigDecimal.valueOf(2))
                .build();

        recipe = Recipe.builder()
                .id(1L)
                .name("recipe")
                .restaurant(restaurant)
                .percent(BigDecimal.valueOf(12))
                .recipeIngredients(Collections.singletonList(recipeIngredient))
                .build();
        dish = Dish.builder()
                .price(BigDecimal.valueOf(1))
                .restaurant(restaurant)
                .recipe(recipe)
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

    @Test(expected = NoItemException.class)
    public void createNoItemExceptionNoSuchRecipe() {
        //when
        when(userService.getCurrentUser()).thenReturn(userDto);
        when(restaurantConverter.dtoToEntity(restaurantDto)).thenReturn(restaurant);
        when(dishConverter.dtoToEntity(dishDto)).thenReturn(dish);
        when(recipeRepository.countAllByNameAndRestaurantId(recipe.getName(), restaurant.getId())).thenReturn(0L);

        dishService.create(dishDto);
        //then
        verify(dishConverter).dtoToEntity(dishDto);
    }

    @Test(expected = ServiceException.class)
    public void createServiceExceptionNotEnoughIngredients() {
        //given
        ingredient.setAmount(BigDecimal.valueOf(1L));
        //when
        when(userService.getCurrentUser()).thenReturn(userDto);
        when(restaurantConverter.dtoToEntity(restaurantDto)).thenReturn(restaurant);
        when(dishConverter.dtoToEntity(dishDto)).thenReturn(dish);
        when(recipeRepository.countAllByNameAndRestaurantId(recipe.getName(), restaurant.getId())).thenReturn(1L);
        when(recipeRepository.findByName(dish.getRecipe()
                .getName())).thenReturn(recipe);
        when(recipeIngredientRepository.findAllByIdIn(recipeIngredientIds)).thenReturn(Collections.singletonList(recipeIngredient));
        dishService.create(dishDto);

    }

    @Test
    public void create() {
        //given
        //when
        when(userService.getCurrentUser()).thenReturn(userDto);
        when(restaurantConverter.dtoToEntity(restaurantDto)).thenReturn(restaurant);
        when(dishConverter.dtoToEntity(dishDto)).thenReturn(dish);
        when(recipeRepository.countAllByNameAndRestaurantId(recipe.getName(), restaurant.getId())).thenReturn(1L);
        when(recipeRepository.findByName(dish.getRecipe()
                .getName())).thenReturn(recipe);
        when(recipeIngredientRepository.findAllByIdIn(recipeIngredientIds)).thenReturn(Collections.singletonList(recipeIngredient));
        dishService.create(dishDto);
        //then
        verify(recipeIngredientRepository).saveAll(Collections.singletonList(recipeIngredient));
        verify(dishRepository).save(dish);
    }
}

*/
