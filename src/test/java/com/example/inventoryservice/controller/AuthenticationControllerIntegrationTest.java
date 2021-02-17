package com.example.inventoryservice.controller;

import com.example.inventoryservice.Util.BasicClassTest;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class AuthenticationControllerIntegrationTest extends BasicClassTest {

    @Test
    public void loginWithExistingCredentials_then200() {
        Map<String, String> userDto = new HashMap<>();
        userDto.put("username", "chef");
        userDto.put("password", "chef");
        given().contentType("application/json")
               .body(userDto)
               .when()
               .post("/login")
               .then()
               .statusCode(200)
               .body("token", notNullValue());
    }

    @Test
    public void loginWithNotExistingCredentials_then403() {
        Map<String, String> userDto = new HashMap<>();
        userDto.put("username", "chef1");
        userDto.put("password", "chef1");
        given().contentType("application/json")
               .body(userDto)
               .when()
               .post("/login")
               .then()
               .statusCode(403);
    }
}
