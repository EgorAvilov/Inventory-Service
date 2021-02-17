package com.example.inventoryservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;


@SpringBootTest(webEnvironment = DEFINED_PORT)
public
class InventoryServiceApplicationTests {

    @Test
    public void contextLoads() {
    }

}
