package com.example.inventoryservice.controller.query;

import com.example.inventoryservice.service.UserQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/users")
public class UserQueryController {
    private final UserQueryService userService;

    @Autowired
    public UserQueryController(UserQueryService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity findAllByRestaurant() {
        return new ResponseEntity<>(userService.findAllByRestaurant(), HttpStatus.OK);
    }
}