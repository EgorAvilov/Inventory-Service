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
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class IngredientCommandControllerIntegrationTest extends BasicClassTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    public void whenCreateIngredientWithFreshToken_then201() {
        Map<Object, Object> ingredientDto = new HashMap<>();
        ingredientDto.put("name", "peppers");
        ingredientDto.put("amount", 1);
        ingredientDto.put("measureUnit", "g");
        ingredientDto.put("price", 12.3f);
        given().header(HEADER, BEARER_PREFIX + jwtTokenProvider.createToken("manager", Collections.singletonList(Role.INVENTORY_MANAGER)))
               .contentType("application/json")
               .body(ingredientDto)
               .when()
               .post("/ingredients")
               .then()
               .statusCode(201)
               .body("name", containsString(String.valueOf(ingredientDto.get("name"))))
               .body("amount", equalTo(ingredientDto.get("amount")))
               .body("measureUnit", containsString(String.valueOf(ingredientDto.get("measureUnit"))))
               .body("price", equalTo(ingredientDto.get("price")));
    }

    @Test
    public void whenCreateIngredientWithFreshTokenOtherRole_then403() {
        Map<Object, Object> ingredientDto = new HashMap<>();
        ingredientDto.put("name", "pepper");
        ingredientDto.put("amount", 1);
        ingredientDto.put("measureUnit", "g");
        ingredientDto.put("price", 12.3f);
        given().header(HEADER, BEARER_PREFIX + jwtTokenProvider.createToken("stuff", Collections.singletonList(Role.KITCHEN_STUFF)))
               .contentType("application/json")
               .body(ingredientDto)
               .when()
               .post("/ingredients")
               .then()
               .statusCode(403);
    }

    @Test
    public void whenCreateIngredientWhichExists_then400() {
        Map<Object, Object> ingredientDto = new HashMap<>();
        ingredientDto.put("id", 0);
        ingredientDto.put("name", "sugar");
        ingredientDto.put("amount", 1);
        ingredientDto.put("measureUnit", "g");
        ingredientDto.put("price", 12.3f);
        given().header(HEADER, BEARER_PREFIX + jwtTokenProvider.createToken("manager", Collections.singletonList(Role.INVENTORY_MANAGER)))
               .contentType("application/json")
               .body(ingredientDto)
               .when()
               .post("/ingredients")
               .then()
               .statusCode(400);
    }

    @Test
    public void whenCreateIngredientWithZeroPrice_then400() {
        Map<Object, Object> ingredientDto = new HashMap<>();
        ingredientDto.put("id", 0);
        ingredientDto.put("name", "sugar");
        ingredientDto.put("amount", 1);
        ingredientDto.put("measureUnit", "g");
        ingredientDto.put("price", 0);
        given().header(HEADER, BEARER_PREFIX + jwtTokenProvider.createToken("manager", Collections.singletonList(Role.INVENTORY_MANAGER)))
               .contentType("application/json")
               .body(ingredientDto)
               .when()
               .post("/ingredients")
               .then()
               .statusCode(400);
    }

    @Test
    public void whenCreateIngredientWithEmptyName_then400() {
        Map<Object, Object> ingredientDto = new HashMap<>();
        ingredientDto.put("id", 0);
        ingredientDto.put("name", "");
        ingredientDto.put("amount", 1);
        ingredientDto.put("measureUnit", "g");
        ingredientDto.put("price", 1);
        given().header(HEADER, BEARER_PREFIX + jwtTokenProvider.createToken("manager", Collections.singletonList(Role.INVENTORY_MANAGER)))
               .contentType("application/json")
               .body(ingredientDto)
               .when()
               .post("/ingredients")
               .then()
               .statusCode(400);
    }

    @Test
    public void whenUpdateIngredientAmount_then200() {
        Map<Object, Object> ingredientUpdateAmountDto = new HashMap<>();
        ingredientUpdateAmountDto.put("name", "sugar");
        ingredientUpdateAmountDto.put("amount", 1);
        given().header(HEADER, BEARER_PREFIX + jwtTokenProvider.createToken("manager", Collections.singletonList(Role.INVENTORY_MANAGER)))
               .contentType("application/json")
               .body(ingredientUpdateAmountDto)
               .when()
               .patch("/ingredients/amount")
               .then()
               .statusCode(200)
               .body("name", containsString(String.valueOf(ingredientUpdateAmountDto.get("name"))));
    }

    @Test
    public void whenUpdateIngredientAmountWhichDoesNotExist_then404() {
        Map<Object, Object> ingredientUpdateAmountDto = new HashMap<>();
        ingredientUpdateAmountDto.put("name", "cookies");
        ingredientUpdateAmountDto.put("amount", 1);
        given().header(HEADER, BEARER_PREFIX + jwtTokenProvider.createToken("manager", Collections.singletonList(Role.INVENTORY_MANAGER)))
               .contentType("application/json")
               .body(ingredientUpdateAmountDto)
               .when()
               .patch("/ingredients/amount")
               .then()
               .statusCode(404);
    }

    @Test
    public void whenUpdateIngredientAmountWithZeroAmount_then400() {
        Map<Object, Object> ingredientUpdateAmountDto = new HashMap<>();
        ingredientUpdateAmountDto.put("name", "cookies");
        ingredientUpdateAmountDto.put("amount", 0);
        given().header(HEADER, BEARER_PREFIX + jwtTokenProvider.createToken("manager", Collections.singletonList(Role.INVENTORY_MANAGER)))
               .contentType("application/json")
               .body(ingredientUpdateAmountDto)
               .when()
               .patch("/ingredients/amount")
               .then()
               .statusCode(400);
    }

    @Test
    public void whenUpdateIngredientPrice_then200() {
        Map<Object, Object> ingredientUpdatePriceDto = new HashMap<>();
        ingredientUpdatePriceDto.put("name", "sugar");
        ingredientUpdatePriceDto.put("price", 1);
        given().header(HEADER, BEARER_PREFIX + jwtTokenProvider.createToken("manager", Collections.singletonList(Role.INVENTORY_MANAGER)))
               .contentType("application/json")
               .body(ingredientUpdatePriceDto)
               .when()
               .patch("/ingredients/price")
               .then()
               .statusCode(200)
               .body("name", containsString(String.valueOf(ingredientUpdatePriceDto.get("name"))));
    }

    @Test
    public void whenUpdateIngredientPriceWhichDoesNotExist_then404() {
        Map<Object, Object> ingredientUpdatePriceDto = new HashMap<>();
        ingredientUpdatePriceDto.put("name", "cookies");
        ingredientUpdatePriceDto.put("price", 1);
        given().header(HEADER, BEARER_PREFIX + jwtTokenProvider.createToken("manager", Collections.singletonList(Role.INVENTORY_MANAGER)))
               .contentType("application/json")
               .body(ingredientUpdatePriceDto)
               .when()
               .patch("/ingredients/price")
               .then()
               .statusCode(404);
    }

    @Test
    public void whenUpdateIngredientPriceWithZeroPrice_then400() {
        Map<Object, Object> ingredientUpdatePriceDto = new HashMap<>();
        ingredientUpdatePriceDto.put("name", "cookies");
        ingredientUpdatePriceDto.put("price", 0);
        given().header(HEADER, BEARER_PREFIX + jwtTokenProvider.createToken("manager", Collections.singletonList(Role.INVENTORY_MANAGER)))
               .contentType("application/json")
               .body(ingredientUpdatePriceDto)
               .when()
               .patch("/ingredients/price")
               .then()
               .statusCode(400);
    }
}
