package com.example.inventoryservice.controller;


import com.example.inventoryservice.Util.BasicClassTest;
import com.example.inventoryservice.entity.Role;
import com.example.inventoryservice.security.jwt.JwtTokenProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
public class DishCommandControllerIntegrationTest extends BasicClassTest {

    @Mock
    JwtTokenProvider jwtTokenProvider;


    @Test
    public void whenCreateDishWithFreshToken_then201() {
        Map<String, String> recipe = new HashMap<>();
        recipe.put("name", "cake");
        Map<Object, Object> dishDto = new HashMap<>();
        dishDto.put("id", 0);
        dishDto.put("price", 12);
        dishDto.put("recipe", recipe);
        //jwtTokenProvider.createToken("chef", Collections.singletonList(Role.KITCHEN_CHEF));
        given().header("Authorization", "Bearer_eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdHVmZiIsInJvbGVzIjpbIktJVENIRU5fU1RVRkYiXSwiaWF0IjoxNjEzNDYyNTc2LCJleHAiOjE2MTM0OTg1NzZ9.FTa8UE3PD7jyytPb2SFnPz8dh7aBrkzkXL7CeALhpdk")
               .contentType("application/json")
               .body(dishDto)
               .when()
               .post("/dishes")
               .then()
               .statusCode(201);
        //.body("token",isNotNullValue());
    }

    @Test
    public void whenCreateDishWithFreshTokenOtherRole_then403() {
        Map<String, String> recipe = new HashMap<>();
        recipe.put("name", "cake");
        Map<Object, Object> dishDto = new HashMap<>();
        dishDto.put("id", 0);
        dishDto.put("price", 12);
        dishDto.put("recipe", recipe);
        given().header("Authorization", "Bearer_eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYW5hZ2VyIiwicm9sZXMiOlsiSU5WRU5UT1JZX01BTkFHRVIiXSwiaWF0IjoxNjEzNTQ4ODg1LCJleHAiOjE2MTM1ODQ4ODV9.DK31TW3Hitp1NguBPIGZN_pXifnk85e39zPja47qKRc")
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
        given().header("Authorization", "Bearer_eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdHVmZiIsInJvbGVzIjpbIktJVENIRU5fU1RVRkYiXSwiaWF0IjoxNjEzNDYyNTc2LCJleHAiOjE2MTM0OTg1NzZ9.FTa8UE3PD7jyytPb2SFnPz8dh7aBrkzkXL7CeALhpdk")
               .contentType("application/json")
               .body(dishDto)
               .when()
               .post("/dishes")
               .then()
               .statusCode(404);
    }
}
