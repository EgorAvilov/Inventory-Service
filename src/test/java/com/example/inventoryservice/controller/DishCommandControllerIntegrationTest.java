package com.example.inventoryservice.controller;


import com.example.inventoryservice.Util.BasicClassTest;
import com.example.inventoryservice.entity.Role;
import com.example.inventoryservice.security.jwt.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class DishCommandControllerIntegrationTest extends BasicClassTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    public void whenCreateDishWithFreshToken_then201() {
        Map<String, String> recipe = new HashMap<>();
        recipe.put("name", "cake");
        Map<Object, Object> dishDto = new HashMap<>();
        dishDto.put("price", 12);
        dishDto.put("recipe", recipe);
        given().header(HEADER, BEARER_PREFIX + jwtTokenProvider.createToken("stuff", Collections.singletonList(Role.KITCHEN_STUFF)))
               .contentType("application/json")
               .body(dishDto)
               .when()
               .post("/dishes")
               .then()
               .statusCode(201)
               .body("price", equalTo(12))
               .body("id", notNullValue())
               .body("recipe.name", equalTo("cake"));

    }

    @Test
    public void whenCreateDishWithFreshTokenOtherRole_then403() {
        Map<String, String> recipe = new HashMap<>();
        recipe.put("name", "cake");
        Map<Object, Object> dishDto = new HashMap<>();
        dishDto.put("id", 0);
        dishDto.put("price", 12);
        dishDto.put("recipe", recipe);
        given().header(HEADER, BEARER_PREFIX + jwtTokenProvider.createToken("chef", Collections.singletonList(Role.KITCHEN_CHEF)))
               .contentType("application/json")
               .body(dishDto)
               .when()
               .post("/dishes")
               .then()
               .statusCode(403);
    }

    @Test
    public void whenCreateDishWithNotExistingRecipe_then404() {
        Map<String, String> recipe = new HashMap<>();
        recipe.put("name", "ice-cream");
        Map<Object, Object> dishDto = new HashMap<>();
        dishDto.put("id", 0);
        dishDto.put("price", 12);
        dishDto.put("recipe", recipe);
        given().header(HEADER, BEARER_PREFIX + jwtTokenProvider.createToken("stuff", Collections.singletonList(Role.KITCHEN_STUFF)))
               .contentType("application/json")
               .body(dishDto)
               .when()
               .post("/dishes")
               .then()
               .statusCode(404);
    }
}
