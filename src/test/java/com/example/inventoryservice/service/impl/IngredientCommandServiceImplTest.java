/*

package com.example.inventoryservice.service.impl;

import com.example.inventoryservice.converter.IngredientConverter;
import com.example.inventoryservice.converter.RestaurantConverter;
import com.example.inventoryservice.dto.*;
import com.example.inventoryservice.entity.Ingredient;
import com.example.inventoryservice.entity.Restaurant;
import com.example.inventoryservice.entity.Role;
import com.example.inventoryservice.exception.NoItemException;
import com.example.inventoryservice.exception.ServiceException;
import com.example.inventoryservice.repository.IngredientRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class IngredientCommandServiceImplTest {

    @Mock
    IngredientRepository ingredientRepository;

    @Mock
    IngredientConverter ingredientConverter;

    @Mock
    RestaurantConverter restaurantConverter;

    @Mock
    UserQueryServiceImpl userService;

    @InjectMocks
    IngredientCommandServiceImpl ingredientService;

    private UserDto userDto;
    private Restaurant restaurant;
    private RestaurantDto restaurantDto;
    private Ingredient ingredient;
    private IngredientDto ingredientDto;
    private IngredientUpdateAmountDto ingredientUpdateAmountDto;
    private IngredientUpdatePriceDto ingredientUpdatePriceDto;

    @Before
    public void setUp() {
        restaurantDto = RestaurantDto.builder()
                                     .id(1L)
                                     .build();

        restaurant = Restaurant.builder()
                               .id(1L)
                               .build();
        ingredientUpdateAmountDto = IngredientUpdateAmountDto.builder()
                                                             .amount(BigDecimal.valueOf(1))
                                                             .name("ingredient")
                                                             .build();
        ingredientUpdatePriceDto = IngredientUpdatePriceDto.builder()
                                                           .price(BigDecimal.valueOf(1))
                                                           .name("ingredient")
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
        ingredientDto = IngredientDto
                .builder()
                .id(1L)
                .amount(BigDecimal.valueOf(2))
                .measureUnit("measureUnit")
                .name("name")
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

    @Test(expected = ServiceException.class)
    public void createServiceExceptionNotUniqueIngredient() {
        //given
        //when
        when(userService.getCurrentUser()).thenReturn(userDto);
        when(restaurantConverter.dtoToEntity(restaurantDto)).thenReturn(restaurant);
        when(ingredientConverter.dtoToEntity(ingredientDto)).thenReturn(ingredient);
        when(ingredientRepository.countAllByNameAndRestaurantId(ingredient.getName(), ingredient.getRestaurant()
                                                                                                .getId())).thenReturn(1L);
        ingredientService.create(ingredientDto);
    }

    @Test
    public void create() {
        //given
        //when
        when(userService.getCurrentUser()).thenReturn(userDto);
        when(restaurantConverter.dtoToEntity(restaurantDto)).thenReturn(restaurant);
        when(ingredientConverter.dtoToEntity(ingredientDto)).thenReturn(ingredient);
        when(ingredientRepository.countAllByNameAndRestaurantId(ingredient.getName(), ingredient.getRestaurant()
                                                                                                .getId())).thenReturn(0L);
        when(ingredientRepository.save(ingredient)).thenReturn(ingredient);
        ingredientService.create(ingredientDto);
        //then
        verify(ingredientConverter).entityToDto(ingredient);
        assertThat(ingredient).extracting(Ingredient::getAmount)
                              .isEqualTo(ingredientDto.getAmount());
        assertThat(ingredient).extracting(Ingredient::getName)
                              .isEqualTo(ingredientDto.getName());
        assertThat(ingredient).extracting(Ingredient::getMeasureUnit)
                              .isEqualTo(ingredientDto.getMeasureUnit());
        assertThat(ingredient).extracting(Ingredient::getPrice)
                              .isEqualTo(ingredientDto.getPrice());
    }

    @Test(expected = NoItemException.class)
    public void updateAmountNoItemExceptionNoSuchIngredient() {
        //given
        //when
        when(ingredientConverter.dtoToEntity(ingredientUpdateAmountDto)).thenReturn(ingredient);
        when(ingredientRepository.findByName(ingredient.getName())).thenThrow(new NoItemException("No such ingredient"));
        ingredientService.updateAmount(ingredientUpdateAmountDto);
    }

    @Test
    public void updateAmount() {
        //given
        BigDecimal amount = BigDecimal.valueOf(1L);
        Ingredient persistIngredient = Ingredient
                .builder()
                .id(1L)
                .amount(amount)
                .measureUnit("measureUnit")
                .name("name")
                .restaurant(restaurant)
                .price(BigDecimal.valueOf(1))
                .build();
        //when
        when(ingredientConverter.dtoToEntity(ingredientUpdateAmountDto)).thenReturn(ingredient);
        when(ingredientRepository.findByName(ingredient.getName())).thenReturn(Optional.of(persistIngredient));
        when(ingredientRepository.save(persistIngredient)).thenReturn(persistIngredient);
        ingredientService.updateAmount(ingredientUpdateAmountDto);
//then
        verify(ingredientConverter).entityToDto(persistIngredient);
        assertThat(persistIngredient).extracting(Ingredient::getAmount)
                                     .isEqualTo(ingredientDto.getAmount()
                                                             .add(amount));
    }


    @Test(expected = NoItemException.class)
    public void updatePriceNoItemExceptionNoSuchIngredient() {
        //given
        //when
        when(ingredientConverter.dtoToEntity(ingredientUpdatePriceDto)).thenReturn(ingredient);
        when(ingredientRepository.findByName(ingredient.getName())).thenThrow(new NoItemException("No such ingredient"));
        ingredientService.updatePrice(ingredientUpdatePriceDto);
    }

    @Test
    public void updatePrice() {
        //given
        BigDecimal price = BigDecimal.valueOf(1L);
        Ingredient persistIngredient = Ingredient
                .builder()
                .id(1L)
                .amount(BigDecimal.valueOf(1L))
                .measureUnit("measureUnit")
                .name("name")
                .restaurant(restaurant)
                .price(price)
                .build();
        //when
        when(ingredientConverter.dtoToEntity(ingredientUpdatePriceDto)).thenReturn(ingredient);
        when(ingredientRepository.findByName(ingredient.getName())).thenReturn(Optional.of(persistIngredient));
        when(ingredientRepository.save(persistIngredient)).thenReturn(persistIngredient);
        ingredientService.updatePrice(ingredientUpdatePriceDto);
        //then
        verify(ingredientConverter).entityToDto(persistIngredient);
        assertThat(persistIngredient).extracting(Ingredient::getPrice)
                                     .isEqualTo(ingredientDto.getPrice()
                                                             .add(price));
    }
}
*/
