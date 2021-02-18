package com.example.inventoryservice.controller;

import com.example.inventoryservice.util.BasicClassTest;
import com.example.inventoryservice.entity.Role;
import com.example.inventoryservice.security.jwt.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class RecipeCommandControllerIntegrationTest extends BasicClassTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    public void whenCreateRecipeWithFreshToken_then201() {
        Map<String, String> ingredient = new HashMap<>();
        ingredient.put("name", "sugar");
        Map<Object, Object> recipeIngredient = new HashMap<>();
        recipeIngredient.put("amount", 1.2);
        recipeIngredient.put("ingredient", ingredient);
        List<Object> recipeIngredients = new ArrayList<>() {{
            add(recipeIngredient);
        }};
        Map<Object, Object> recipeDto = new HashMap<>();
        recipeDto.put("name", "jam");
        recipeDto.put("recipeIngredients", recipeIngredients);
        given().header(HEADER, BEARER_PREFIX + jwtTokenProvider.createToken("chef", Collections.singletonList(Role.KITCHEN_CHEF)))
               .contentType("application/json")
               .body(recipeDto)
               .when()
               .post("/recipes")
               .then()
               .statusCode(201)
               .body("name", containsString(String.valueOf(recipeDto.get("name"))));
    }

    @Test
    public void whenCreateRecipeWithFreshTokenOtherRole_then403() {
        Map<String, String> ingredient = new HashMap<>();
        ingredient.put("name", "sugar");

        Map<Object, Object> recipeIngredient = new HashMap<>();
        recipeIngredient.put("amount", 1.2);
        recipeIngredient.put("ingredient", ingredient);

        List<Object> recipeIngredients = new ArrayList<>() {{
            add(recipeIngredient);
        }};
        Map<Object, Object> recipeDto = new HashMap<>();
        recipeDto.put("name", "jam");
        recipeDto.put("recipeIngredients", recipeIngredients);
        given().header(HEADER, BEARER_PREFIX + jwtTokenProvider.createToken("stuff", Collections.singletonList(Role.KITCHEN_STUFF)))
               .contentType("application/json")
               .body(recipeDto)
               .when()
               .post("/recipes")
               .then()
               .statusCode(403);
    }

    @Test
    public void whenCreateRecipeWithExistingRecipeName_then400() {
        Map<String, String> ingredient = new HashMap<>();
        ingredient.put("name", "sugar");
        Map<Object, Object> recipeIngredient = new HashMap<>();
        recipeIngredient.put("amount", 1.2);
        recipeIngredient.put("ingredient", ingredient);
        List<Object> recipeIngredients = new ArrayList<>() {{
            add(recipeIngredient);
        }};
        Map<Object, Object> recipeDto = new HashMap<>();
        recipeDto.put("name", "jam");
        recipeDto.put("recipeIngredients", recipeIngredients);
        given().header(HEADER, BEARER_PREFIX + jwtTokenProvider.createToken("chef", Collections.singletonList(Role.KITCHEN_CHEF)))
               .contentType("application/json")
               .body(recipeDto)
               .when()
               .post("/recipes")
               .then()
               .statusCode(400);
    }
}
