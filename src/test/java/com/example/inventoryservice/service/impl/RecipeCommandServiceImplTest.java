

package com.example.inventoryservice.service.impl;

import com.example.inventoryservice.converter.RecipeConverter;
import com.example.inventoryservice.converter.RestaurantConverter;
import com.example.inventoryservice.dto.*;
import com.example.inventoryservice.entity.*;
import com.example.inventoryservice.exception.NoItemException;
import com.example.inventoryservice.exception.ServiceException;
import com.example.inventoryservice.repository.IngredientRepository;
import com.example.inventoryservice.repository.RecipeRepository;
import org.hibernate.cache.spi.access.CachedDomainDataAccess;
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
import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RecipeCommandServiceImplTest {
    private RecipeIngredient recipeIngredientUpdate1;
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
    private Recipe persistRecipe;
    private RecipeCreateDto recipeCreateDto;
    private RecipeUpdateDto recipeUpdateDto;
    private Restaurant restaurant;
    private RestaurantDto restaurantDto;
    private RecipeIngredientDto recipeIngredientUpdateDto2;
    private RecipeIngredientDto recipeIngredientUpdateDto1;
    private RecipeIngredient recipeIngredientUpdate2;
    private ArrayList<RecipeIngredientDto> recipeIngredientDtoList;

    @Before
    public void setUp() {
        IngredientRecipeIngredientDto ingredientRecipeIngredientUpdateDto1 = IngredientRecipeIngredientDto
                .builder()
                .name("ingredient1")
                .build();
        IngredientRecipeIngredientDto ingredientRecipeIngredientUpdateDto2 = IngredientRecipeIngredientDto
                .builder()
                .name("ingredient1")
                .build();
        recipeIngredientUpdateDto1 = RecipeIngredientDto
                .builder()
                .amount(BigDecimal.valueOf(1))
                .ingredient(ingredientRecipeIngredientUpdateDto1)
                .build();
        recipeIngredientUpdateDto2 = RecipeIngredientDto
                .builder()
                .amount(BigDecimal.valueOf(1))
                .ingredient(ingredientRecipeIngredientUpdateDto2)
                .build();
        recipeIngredientDtoList = new ArrayList<>() {{
            add(recipeIngredientUpdateDto1);
            add(recipeIngredientUpdateDto2);
        }};
        Ingredient ingredient1 = Ingredient.builder()
                .name("ingredient1")
                .build();
        Ingredient ingredient2 = Ingredient.builder()
                .name("ingredient1")
                .build();
        recipeIngredientUpdate1 = RecipeIngredient
                .builder()
                .amount(BigDecimal.valueOf(1))
                .ingredient(ingredient1)
                .build();
        recipeIngredientUpdate2 = RecipeIngredient
                .builder()
                .amount(BigDecimal.valueOf(1))
                .ingredient(ingredient2)
                .build();

        ArrayList<RecipeIngredient> recipeIngredientList = new ArrayList<>() {{
            add(recipeIngredientUpdate1);
            add(recipeIngredientUpdate2);
        }};

        persistRecipe = Recipe.builder()
                .recipeIngredients(recipeIngredientList)
                .restaurant(restaurant)
                .name("name")
                .id(1L)
                .build();

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
                .margin(BigDecimal.valueOf(23))
                .build();
        recipeUpdateDto = RecipeUpdateDto.builder()
                .name("name")
                .margin(BigDecimal.valueOf(23))
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

    @Test(expected = NoItemException.class)
    public void updateNoItemExceptionNoSuchRecipe() {
        //given
        //when
        when(userService.getCurrentUser()).thenReturn(userDto);
        when(restaurantConverter.dtoToEntity(restaurantDto)).thenReturn(restaurant);
        when(recipeConverter.dtoToEntity(recipeUpdateDto)).thenReturn(recipe);
        when(recipeRepository.findByNameAndRestaurantId(recipe.getName(), recipe.getRestaurant()
                .getId())).thenThrow(new NoItemException("No such ingredient"));

        recipeService.update(recipeUpdateDto);
    }

    @Test(expected = ServiceException.class)
    public void updateServiceExceptionIngredientsShouldBeUnique() {
        //given
        recipeUpdateDto.setRecipeIngredients(recipeIngredientDtoList);
        //when
        when(userService.getCurrentUser()).thenReturn(userDto);
        when(restaurantConverter.dtoToEntity(restaurantDto)).thenReturn(restaurant);
        when(recipeConverter.dtoToEntity(recipeUpdateDto)).thenReturn(persistRecipe);
        when(recipeRepository.findByNameAndRestaurantId(recipe.getName(), recipe.getRestaurant()
                .getId())).thenReturn(Optional.ofNullable(persistRecipe));
        recipeService.update(recipeUpdateDto);
    }

    @Test(expected = ServiceException.class)
    public void updateServiceExceptionNullAmountOfIngredient() {
        //given
        recipeUpdateDto.setRecipeIngredients(recipeIngredientDtoList);
        persistRecipe.getRecipeIngredients().get(0).getIngredient().setName("ingredient2");
        persistRecipe.getRecipeIngredients().get(0).setAmount(null);
        //when
        when(userService.getCurrentUser()).thenReturn(userDto);
        when(restaurantConverter.dtoToEntity(restaurantDto)).thenReturn(restaurant);
        when(recipeConverter.dtoToEntity(recipeUpdateDto)).thenReturn(persistRecipe);
        when(recipeRepository.findByNameAndRestaurantId(recipe.getName(), recipe.getRestaurant()
                .getId())).thenReturn(Optional.ofNullable(persistRecipe));
        recipeService.update(recipeUpdateDto);
    }

    @Test(expected = ServiceException.class)
    public void updateServiceExceptionZeroAmountOfIngredient() {
        //given
        recipeUpdateDto.setRecipeIngredients(recipeIngredientDtoList);
        persistRecipe.getRecipeIngredients().get(0).getIngredient().setName("ingredient2");
        persistRecipe.getRecipeIngredients().get(0).setAmount(BigDecimal.valueOf(0));
        //when
        when(userService.getCurrentUser()).thenReturn(userDto);
        when(restaurantConverter.dtoToEntity(restaurantDto)).thenReturn(restaurant);
        when(recipeConverter.dtoToEntity(recipeUpdateDto)).thenReturn(persistRecipe);
        when(recipeRepository.findByNameAndRestaurantId(recipe.getName(), recipe.getRestaurant()
                .getId())).thenReturn(Optional.ofNullable(persistRecipe));
        recipeService.update(recipeUpdateDto);
    }
    @Test
    public void update() {
        //given
        persistRecipe.getRecipeIngredients().get(0).getIngredient().setName("ingredient2");
        //when
        when(userService.getCurrentUser()).thenReturn(userDto);
        when(restaurantConverter.dtoToEntity(restaurantDto)).thenReturn(restaurant);
        when(recipeConverter.dtoToEntity(recipeUpdateDto)).thenReturn(persistRecipe);
        when(recipeRepository.findByNameAndRestaurantId(recipe.getName(), recipe.getRestaurant()
                .getId())).thenReturn(Optional.ofNullable(persistRecipe));
        recipeService.update(recipeUpdateDto);
    }
}

