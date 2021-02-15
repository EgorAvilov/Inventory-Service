package com.example.inventoryservice.controller.command;

import com.example.inventoryservice.dto.UserDto;
import com.example.inventoryservice.service.UserCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/users")
public class UserCommandController {
    private final UserCommandService userService;

    @Autowired
    public UserCommandController(UserCommandService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid UserDto userDto) {
        return new ResponseEntity<>(userService.create(userDto), HttpStatus.CREATED);
    }
}