package com.example.inventoryservice.converter;

import com.example.inventoryservice.dto.RecipeCreateDto;
import com.example.inventoryservice.dto.RecipeDto;
import com.example.inventoryservice.dto.RecipeUpdateDto;
import com.example.inventoryservice.entity.Recipe;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RecipeConverter {
    public RecipeDto entityToDto(Recipe recipe) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(recipe, RecipeDto.class);
    }

    public Recipe dtoToEntity(RecipeDto recipeDto) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(recipeDto, Recipe.class);
    }

    public List<RecipeDto> entityToDto(List<Recipe> recipes) {
        return recipes.stream()
                      .map(this::entityToDto)
                      .collect(Collectors.toList());
    }

    public List<Recipe> dtoToEntity(List<RecipeDto> recipeDtos) {
        return recipeDtos.stream()
                         .map(this::dtoToEntity)
                         .collect(Collectors.toList());
    }

    public Recipe dtoToEntity(RecipeCreateDto recipeDto) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(recipeDto, Recipe.class);
    }

    public Recipe dtoToEntity(RecipeUpdateDto recipeDto) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(recipeDto, Recipe.class);
    }
}
