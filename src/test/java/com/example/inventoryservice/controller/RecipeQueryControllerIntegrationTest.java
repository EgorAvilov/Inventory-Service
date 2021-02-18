package com.example.inventoryservice.controller;

import com.example.inventoryservice.util.BasicClassTest;
import com.example.inventoryservice.entity.Role;
import com.example.inventoryservice.security.jwt.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;

import static com.jayway.restassured.RestAssured.given;

public class RecipeQueryControllerIntegrationTest extends BasicClassTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    public void whenGetRecipesWithFreshToken_then200() {
        given().header(HEADER, BEARER_PREFIX + jwtTokenProvider.createToken("chef", Collections.singletonList(Role.KITCHEN_CHEF)))
               .when()
               .get("/recipes")
               .then()
               .statusCode(200);
    }

    @Test
    public void whenGetRecipesWithFreshTokenOtherRole_then403() {
        given().header(HEADER, BEARER_PREFIX + jwtTokenProvider.createToken("manager", Collections.singletonList(Role.INVENTORY_MANAGER)))
               .when()
               .get("/recipes")
               .then()
               .statusCode(403);
    }

    @Test
    public void whenGetRecipesWithExpiredToken_then401() {
        given().header(HEADER, EXPIRED_TOKEN)
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
