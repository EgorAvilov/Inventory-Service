package com.example.inventoryservice.controller;

import com.example.inventoryservice.entity.Ingredient;
import com.example.inventoryservice.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class WebSocketController {
    private final NotificationService notificationService;

    @Autowired
    public WebSocketController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @MessageMapping("/restaurant/{id}")
    @SendTo("/topic/ingredient")
    public String greeting(@DestinationVariable Long id) {
        List<Ingredient> soonOutIngredients = notificationService.remind(id);
        return "These ingredients are going to run out soon: " + soonOutIngredients.toString();
    }
}