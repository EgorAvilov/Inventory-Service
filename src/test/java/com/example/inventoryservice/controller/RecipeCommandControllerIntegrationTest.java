package com.example.inventoryservice.controller;

import com.example.inventoryservice.Util.BasicClassTest;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;

public class RecipeCommandControllerIntegrationTest extends BasicClassTest {

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
        given().header("Authorization", "Bearer_eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjaGVmIiwicm9sZXMiOlsiS0lUQ0hFTl9DSEVGIl0sImlhdCI6MTYxMzU1MjU3OCwiZXhwIjoxNjEzNTg4NTc4fQ.AbqMQHngdDcObxh8tMkndMkfK34sb8KaP2JoBrSCGu8")
               .contentType("application/json")
               .body(recipeDto)
               .when()
               .post("/recipes")
               .then()
               .statusCode(201);
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
        given().header("Authorization", "Bearer_eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYW5hZ2VyIiwicm9sZXMiOlsiSU5WRU5UT1JZX01BTkFHRVIiXSwiaWF0IjoxNjEzNTQ4ODg1LCJleHAiOjE2MTM1ODQ4ODV9.DK31TW3Hitp1NguBPIGZN_pXifnk85e39zPja47qKRc")
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
        given().header("Authorization", "Bearer_eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjaGVmIiwicm9sZXMiOlsiS0lUQ0hFTl9DSEVGIl0sImlhdCI6MTYxMzU1MjU3OCwiZXhwIjoxNjEzNTg4NTc4fQ.AbqMQHngdDcObxh8tMkndMkfK34sb8KaP2JoBrSCGu8")
               .contentType("application/json")
               .body(recipeDto)
               .when()
               .post("/recipes")
               .then()
               .statusCode(400);
    }
}
