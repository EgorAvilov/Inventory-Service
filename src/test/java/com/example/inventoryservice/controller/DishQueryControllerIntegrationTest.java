package com.example.inventoryservice.controller;

import com.example.inventoryservice.Util.BasicClassTest;
import com.jayway.restassured.RestAssured;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;


public class DishQueryControllerIntegrationTest extends BasicClassTest {

    @Test
    public void whenGetDishesWithFreshToken_then200() {
        given().header("Authorization", "Bearer_eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdHVmZiIsInJvbGVzIjpbIktJVENIRU5fU1RVRkYiXSwiaWF0IjoxNjEzNDYyNTc2LCJleHAiOjE2MTM0OTg1NzZ9.FTa8UE3PD7jyytPb2SFnPz8dh7aBrkzkXL7CeALhpdk")
               .when()
               .get("/dishes")
               .then()
               .statusCode(200)
               .body("id", notNullValue());
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
