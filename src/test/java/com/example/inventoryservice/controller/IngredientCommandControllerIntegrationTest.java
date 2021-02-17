package com.example.inventoryservice.controller;

import com.example.inventoryservice.Util.BasicClassTest;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;

public class IngredientCommandControllerIntegrationTest extends BasicClassTest {

    @Test
    public void whenCreateIngredientWithFreshToken_then201() {
        Map<Object, Object> ingredientDto = new HashMap<>();
        ingredientDto.put("id", 0);
        ingredientDto.put("name", "peppers");
        ingredientDto.put("amount", 1);
        ingredientDto.put("measureUnit", "g");
        ingredientDto.put("price", 12.3);
        given().header("Authorization", "Bearer_eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYW5hZ2VyIiwicm9sZXMiOlsiSU5WRU5UT1JZX01BTkFHRVIiXSwiaWF0IjoxNjEzNTQ4ODg1LCJleHAiOjE2MTM1ODQ4ODV9.DK31TW3Hitp1NguBPIGZN_pXifnk85e39zPja47qKRc")
               .contentType("application/json")
               .body(ingredientDto)
               .when()
               .post("/ingredients")
               .then()
               .statusCode(201);
    }

    @Test
    public void whenCreateIngredientWithFreshTokenOtherRole_then403() {
        Map<Object, Object> ingredientDto = new HashMap<>();
        ingredientDto.put("id", 0);
        ingredientDto.put("name", "pepper");
        ingredientDto.put("amount", 1);
        ingredientDto.put("measureUnit", "g");
        ingredientDto.put("price", 12.3);
        given().header("Authorization", "Bearer_eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjaGVmIiwicm9sZXMiOlsiS0lUQ0hFTl9DSEVGIl0sImlhdCI6MTYxMzU1MDYzNywiZXhwIjoxNjEzNTg2NjM3fQ.InHBnQh-EvT9U2Dk3IQIKOf29lWQkM7eAN220bUVHfc")
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
        ingredientDto.put("price", 12.3);
        given().header("Authorization", "Bearer_eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYW5hZ2VyIiwicm9sZXMiOlsiSU5WRU5UT1JZX01BTkFHRVIiXSwiaWF0IjoxNjEzNTUwODUzLCJleHAiOjE2MTM1ODY4NTN9.rmRVKq7naP8Gy6L7THSclGPKR9F6OOptcFQlARnPcGY")
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
        given().header("Authorization", "Bearer_eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYW5hZ2VyIiwicm9sZXMiOlsiSU5WRU5UT1JZX01BTkFHRVIiXSwiaWF0IjoxNjEzNTUwODUzLCJleHAiOjE2MTM1ODY4NTN9.rmRVKq7naP8Gy6L7THSclGPKR9F6OOptcFQlARnPcGY")
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
        given().header("Authorization", "Bearer_eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYW5hZ2VyIiwicm9sZXMiOlsiSU5WRU5UT1JZX01BTkFHRVIiXSwiaWF0IjoxNjEzNTUwODUzLCJleHAiOjE2MTM1ODY4NTN9.rmRVKq7naP8Gy6L7THSclGPKR9F6OOptcFQlARnPcGY")
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
        given().header("Authorization", "Bearer_eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYW5hZ2VyIiwicm9sZXMiOlsiSU5WRU5UT1JZX01BTkFHRVIiXSwiaWF0IjoxNjEzNTUwODUzLCJleHAiOjE2MTM1ODY4NTN9.rmRVKq7naP8Gy6L7THSclGPKR9F6OOptcFQlARnPcGY")
               .contentType("application/json")
               .body(ingredientUpdateAmountDto)
               .when()
               .patch("/ingredients/amount")
               .then()
               .statusCode(200);
    }

    @Test
    public void whenUpdateIngredientAmountWhichDoesNotExist_then404() {
        Map<Object, Object> ingredientUpdateAmountDto = new HashMap<>();
        ingredientUpdateAmountDto.put("name", "cookies");
        ingredientUpdateAmountDto.put("amount", 1);
        given().header("Authorization", "Bearer_eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYW5hZ2VyIiwicm9sZXMiOlsiSU5WRU5UT1JZX01BTkFHRVIiXSwiaWF0IjoxNjEzNTUwODUzLCJleHAiOjE2MTM1ODY4NTN9.rmRVKq7naP8Gy6L7THSclGPKR9F6OOptcFQlARnPcGY")
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
        given().header("Authorization", "Bearer_eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYW5hZ2VyIiwicm9sZXMiOlsiSU5WRU5UT1JZX01BTkFHRVIiXSwiaWF0IjoxNjEzNTUwODUzLCJleHAiOjE2MTM1ODY4NTN9.rmRVKq7naP8Gy6L7THSclGPKR9F6OOptcFQlARnPcGY")
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
        given().header("Authorization", "Bearer_eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYW5hZ2VyIiwicm9sZXMiOlsiSU5WRU5UT1JZX01BTkFHRVIiXSwiaWF0IjoxNjEzNTUwODUzLCJleHAiOjE2MTM1ODY4NTN9.rmRVKq7naP8Gy6L7THSclGPKR9F6OOptcFQlARnPcGY")
               .contentType("application/json")
               .body(ingredientUpdatePriceDto)
               .when()
               .patch("/ingredients/price")
               .then()
               .statusCode(200);
    }

    @Test
    public void whenUpdateIngredientPriceWhichDoesNotExist_then404() {
        Map<Object, Object> ingredientUpdatePriceDto = new HashMap<>();
        ingredientUpdatePriceDto.put("name", "cookies");
        ingredientUpdatePriceDto.put("price", 1);
        given().header("Authorization", "Bearer_eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYW5hZ2VyIiwicm9sZXMiOlsiSU5WRU5UT1JZX01BTkFHRVIiXSwiaWF0IjoxNjEzNTUwODUzLCJleHAiOjE2MTM1ODY4NTN9.rmRVKq7naP8Gy6L7THSclGPKR9F6OOptcFQlARnPcGY")
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
        given().header("Authorization", "Bearer_eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYW5hZ2VyIiwicm9sZXMiOlsiSU5WRU5UT1JZX01BTkFHRVIiXSwiaWF0IjoxNjEzNTUwODUzLCJleHAiOjE2MTM1ODY4NTN9.rmRVKq7naP8Gy6L7THSclGPKR9F6OOptcFQlARnPcGY")
               .contentType("application/json")
               .body(ingredientUpdatePriceDto)
               .when()
               .patch("/ingredients/price")
               .then()
               .statusCode(400);
    }
}
