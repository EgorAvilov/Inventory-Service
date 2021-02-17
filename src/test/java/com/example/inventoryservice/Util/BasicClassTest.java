package com.example.inventoryservice.Util;

import com.jayway.restassured.RestAssured;
import org.junit.BeforeClass;

public class BasicClassTest {

    @BeforeClass
    public static void setup() {
        RestAssured.basePath = "jdbc:postgresql://localhost:5432/postgres_test";
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
