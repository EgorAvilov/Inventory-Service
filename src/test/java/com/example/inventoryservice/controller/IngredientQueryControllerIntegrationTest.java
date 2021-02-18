package com.example.inventoryservice.controller;

import com.example.inventoryservice.util.BasicClassTest;
import com.example.inventoryservice.entity.Role;
import com.example.inventoryservice.security.jwt.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;

import static com.jayway.restassured.RestAssured.given;

public class IngredientQueryControllerIntegrationTest extends BasicClassTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    public void whenGetIngredientsWithFreshToken_then200() {
        given().header(HEADER, BEARER_PREFIX + jwtTokenProvider.createToken("manager", Collections.singletonList(Role.INVENTORY_MANAGER)))
               .when()
               .get("/ingredients")
               .then()
               .statusCode(200);
    }

    @Test
    public void whenGetIngredientsWithFreshTokenOtherRole_then403() {
        given().header(HEADER, BEARER_PREFIX + jwtTokenProvider.createToken("stuff", Collections.singletonList(Role.KITCHEN_STUFF)))
               .when()
               .get("/ingredients")
               .then()
               .statusCode(403);
    }

    @Test
    public void whenGetIngredientsWithExpiredToken_then401() {
        given().header(HEADER, EXPIRED_TOKEN)
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
