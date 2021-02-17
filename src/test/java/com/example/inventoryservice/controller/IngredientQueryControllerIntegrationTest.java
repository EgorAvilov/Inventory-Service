package com.example.inventoryservice.controller;

import com.example.inventoryservice.Util.BasicClassTest;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class IngredientQueryControllerIntegrationTest extends BasicClassTest {

    @Test
    public void whenGetIngredientsWithFreshToken_then200() {
        given().header("Authorization", "Bearer_eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYW5hZ2VyIiwicm9sZXMiOlsiSU5WRU5UT1JZX01BTkFHRVIiXSwiaWF0IjoxNjEzNTUwODUzLCJleHAiOjE2MTM1ODY4NTN9.rmRVKq7naP8Gy6L7THSclGPKR9F6OOptcFQlARnPcGY")
               .when()
               .get("/ingredients")
               .then()
               .statusCode(200)
               .body("id", notNullValue());
    }

    @Test
    public void whenGetIngredientsWithFreshTokenOtherRole_then403() {
        given().header("Authorization", "Bearer_eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdHVmZiIsInJvbGVzIjpbIktJVENIRU5fU1RVRkYiXSwiaWF0IjoxNjEzNTUyMTU3LCJleHAiOjE2MTM1ODgxNTd9.zDMYPbPAeJ7MMknxjcAcIalV1OcJqmLhfHcjBlGed7Q")
               .when()
               .get("/ingredients")
               .then()
               .statusCode(403);
    }

    @Test
    public void whenGetIngredientsWithExpiredToken_then401() {
        given().header("Authorization", "Bearer_eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdHVmZiIsInJvbGVzIjpbIktJVENIRU5fU1RVRkYiXSwiaWF0IjoxNjEzNTUyMTU3LCJleHAiOjE2MTM1ODgxNTd9.zDMYPbPAeJ7MMknxjcAcIalV1OcJqmLhfHcjBlGed7Q")
               .when()
               .get("/ingredients")
               .then()
               .statusCode(401);
    }

    @Test
    public void whenGetIngredientsWithoutToken_then403() {
        given().get("/ingredients")
               .then()
               .statusCode(403);
    }
}
