package com.example.inventoryservice.service.impl;

import com.example.inventoryservice.entity.Ingredient;
import com.example.inventoryservice.entity.Role;
import com.example.inventoryservice.entity.User;
import com.example.inventoryservice.projection.UsedIngredientView;
import com.example.inventoryservice.repository.IngredientRepository;
import com.example.inventoryservice.repository.UserRepository;
import com.example.inventoryservice.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NotificationServiceImpl implements NotificationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationServiceImpl.class);
    private final IngredientRepository ingredientRepository;
    private final UserRepository userRepository;

    @Autowired
    public NotificationServiceImpl(IngredientRepository ingredientRepository, UserRepository userRepository) {
        this.ingredientRepository = ingredientRepository;
        this.userRepository = userRepository;
    }

    @Override
    public  List<Ingredient> remind(Long restaurantId) {
        List<UsedIngredientView> usedIngredientViews = ingredientRepository.findAllUsedIngredientsByRestaurantId(restaurantId);
        List<Ingredient> soonOutIngredients = new ArrayList<>();
        for (UsedIngredientView usedIngredientView : usedIngredientViews) {
            Ingredient ingredient = ingredientRepository.getOne(usedIngredientView.getRecipeIngredientId());
            if (ingredient.getAmount()
                          .multiply(BigDecimal.valueOf(3))
                          .compareTo(usedIngredientView.getSum()
                                                       .divide(BigDecimal.valueOf(30), 2, RoundingMode.HALF_UP)) <= 0) {
                soonOutIngredients.add(ingredient);
            }
        }
       return soonOutIngredients;
    }
}
