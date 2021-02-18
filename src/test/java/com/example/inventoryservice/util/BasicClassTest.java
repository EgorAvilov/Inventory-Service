package com.example.inventoryservice.util;

import com.jayway.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@SpringBootTest(webEnvironment = DEFINED_PORT)
public class BasicClassTest {

    public static final String BEARER_PREFIX = "Bearer_";
    public static final String HEADER = "Authorization";
    public static final String EXPIRED_TOKEN = "Bearer_eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdHVmZiIsInJvbGVzIjpbIktJVENIRU5fU1RVRkYiXSwiaWF0IjoxNjEzMzk1NjM1LCJleHAiOjE2MTM0MzE2MzV9.594eZYQv10kJI0SxUygaYmAdT3v3_0V0N6J7owChfqU";

    @BeforeAll
    public static void setup() {
        String port = System.getProperty("server.port");
        if (port == null) {
            RestAssured.port = 8080;
        } else {
            RestAssured.port = Integer.parseInt(port);
        }


        String basePath = System.getProperty("server.base");
        if (basePath == null) {
            basePath = "/api";
        }
        RestAssured.basePath = basePath;

        String baseHost = System.getProperty("server.host");
        if (baseHost == null) {
            baseHost = "http://localhost";
        }
        RestAssured.baseURI = baseHost;

    }
}
