package com.example.inventoryservice.controller;

import com.example.inventoryservice.Util.BasicClassTest;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class RecipeQueryControllerIntegrationTest extends BasicClassTest {

    @Test
    public void whenGetRecipesWithFreshToken_then200() {
        given().header("Authorization", "Bearer_eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjaGVmIiwicm9sZXMiOlsiS0lUQ0hFTl9DSEVGIl0sImlhdCI6MTYxMzU1MjU3OCwiZXhwIjoxNjEzNTg4NTc4fQ.AbqMQHngdDcObxh8tMkndMkfK34sb8KaP2JoBrSCGu8")
               .when()
               .get("/recipes")
               .then()
               .statusCode(200)
               .body("id", notNullValue());
    }

    @Test
    public void whenGetRecipesWithFreshTokenOtherRole_then403() {
        given().header("Authorization", "Bearer_eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYW5hZ2VyIiwicm9sZXMiOlsiSU5WRU5UT1JZX01BTkFHRVIiXSwiaWF0IjoxNjEzNTQ4ODg1LCJleHAiOjE2MTM1ODQ4ODV9.DK31TW3Hitp1NguBPIGZN_pXifnk85e39zPja47qKRc")
               .when()
               .get("/recipes")
               .then()
               .statusCode(403);
    }

    @Test
    public void whenGetRecipesWithExpiredToken_then401() {
        given().header("Authorization", "Bearer_eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdHVmZiIsInJvbGVzIjpbIktJVENIRU5fU1RVRkYiXSwiaWF0IjoxNjEzMzk1NjM1LCJleHAiOjE2MTM0MzE2MzV9.594eZYQv10kJI0SxUygaYmAdT3v3_0V0N6J7owChfqU")
               .when()
               .get("/recipes")
               .then()
               .statusCode(401);
    }

    @Test
    public void whenGetRecipesWithoutToken_then403() {
        given().get("/recipes")
               .then()
               .statusCode(403);
    }
}
