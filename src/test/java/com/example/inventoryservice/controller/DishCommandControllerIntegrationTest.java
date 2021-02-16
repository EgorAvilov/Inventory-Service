package com.example.inventoryservice.controller;

import com.example.inventoryservice.Util.BasicClassTest;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;

public class DishCommandControllerIntegrationTest extends BasicClassTest {

    @Test
    public void whenCreateDishWithFreshToken_then201() {
        Map<String, String> recipe = new HashMap<>();
        recipe.put("name", "cake");
        Map<Object, Object> dishDto = new HashMap<>();
        dishDto.put("id", 0);
        dishDto.put("price", 12);
        dishDto.put("recipe", recipe);
        given().header("Authorization", "Bearer_eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdHVmZiIsInJvbGVzIjpbIktJVENIRU5fU1RVRkYiXSwiaWF0IjoxNjEzNDYyNTc2LCJleHAiOjE2MTM0OTg1NzZ9.FTa8UE3PD7jyytPb2SFnPz8dh7aBrkzkXL7CeALhpdk")
               .contentType("application/json")
               .body(dishDto)
               .when()
               .post("/dishes")
               .then()
               .statusCode(201);
    }

    @Test
    public void whenCreateDishWithNotExistingRecipe_then404() {
        Map<String, String> recipe = new HashMap<>();
        recipe.put("name", "ice-cream");
        Map<Object, Object> dishDto = new HashMap<>();
        dishDto.put("id", 0);
        dishDto.put("price", 12);
        dishDto.put("recipe", recipe);
        given().header("Authorization", "Bearer_eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdHVmZiIsInJvbGVzIjpbIktJVENIRU5fU1RVRkYiXSwiaWF0IjoxNjEzNDYyNTc2LCJleHAiOjE2MTM0OTg1NzZ9.FTa8UE3PD7jyytPb2SFnPz8dh7aBrkzkXL7CeALhpdk")
               .contentType("application/json")
               .body(dishDto)
               .when()
               .post("/dishes")
               .then()
               .statusCode(404);
    }
}
