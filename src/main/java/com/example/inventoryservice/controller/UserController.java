package com.example.inventoryservice.controller;

import com.example.inventoryservice.dto.UserDto;
import com.example.inventoryservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity findAllByRestaurant() {
        return new ResponseEntity<>(userService.findAllByRestaurant(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid UserDto userDto) {
        return new ResponseEntity<>(userService.create(userDto), HttpStatus.CREATED);
    }
}
