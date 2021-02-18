package com.example.inventoryservice.controller;

import com.example.inventoryservice.util.BasicClassTest;
import com.example.inventoryservice.entity.Role;
import com.example.inventoryservice.security.jwt.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;

import static com.jayway.restassured.RestAssured.given;

public class DishQueryControllerIntegrationTest extends BasicClassTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    public void whenGetDishesWithFreshToken_then200() {
        given().header(HEADER, BEARER_PREFIX + jwtTokenProvider.createToken("stuff", Collections.singletonList(Role.KITCHEN_STUFF)))
               .when()
               .get("/dishes")
               .then()
               .statusCode(200);
    }

    @Test
    public void whenGetDishesWithFreshTokenOtherRole_then403() {
        given().header(HEADER, BEARER_PREFIX + jwtTokenProvider.createToken("chef", Collections.singletonList(Role.KITCHEN_CHEF)))
               .when()
               .get("/dishes")
               .then()
               .statusCode(403);
    }

    @Test
    public void whenGetDishesWithExpiredToken_then401() {
        given().header(HEADER, EXPIRED_TOKEN)
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
