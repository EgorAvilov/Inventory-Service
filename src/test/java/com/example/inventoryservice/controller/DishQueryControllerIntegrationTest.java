package com.example.inventoryservice.controller;

import com.example.inventoryservice.Util.BasicClassTest;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;


public class DishQueryControllerIntegrationTest extends BasicClassTest {

    @Test
    public void whenGetDishesWithFreshToken_then200() {
        given().header("Authorization", "Bearer_eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdHVmZiIsInJvbGVzIjpbIktJVENIRU5fU1RVRkYiXSwiaWF0IjoxNjEzNTQ4Njk0LCJleHAiOjE2MTM1ODQ2OTR9.8_zAYbIUrsW6zihl2SWHDsMhWPO6_MDaKWiWoO_X_zM")
               .when()
               .get("/dishes")
               .then()
               .statusCode(200)
               .body("id", notNullValue());
    }

    @Test
    public void whenGetDishesWithFreshTokenOtherRole_then403() {
        given().header("Authorization", "Bearer_eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYW5hZ2VyIiwicm9sZXMiOlsiSU5WRU5UT1JZX01BTkFHRVIiXSwiaWF0IjoxNjEzNTQ4ODg1LCJleHAiOjE2MTM1ODQ4ODV9.DK31TW3Hitp1NguBPIGZN_pXifnk85e39zPja47qKRc")
               .when()
               .get("/dishes")
               .then()
               .statusCode(403);
    }

    @Test
    public void whenGetDishesWithExpiredToken_then401() {
        given().header("Authorization", "Bearer_eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdHVmZiIsInJvbGVzIjpbIktJVENIRU5fU1RVRkYiXSwiaWF0IjoxNjEzMzk1NjM1LCJleHAiOjE2MTM0MzE2MzV9.594eZYQv10kJI0SxUygaYmAdT3v3_0V0N6J7owChfqU")
               .when()
               .get("/dishes")
               .then()
               .statusCode(401);
    }

    @Test
    public void whenGetDishesWithoutToken_then403() {
        given().get("/dishes")
               .then()
               .statusCode(403);
    }
}
