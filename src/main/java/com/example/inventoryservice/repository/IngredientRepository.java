package com.example.inventoryservice.repository;

import com.example.inventoryservice.entity.Ingredient;
import com.example.inventoryservice.projection.UsedIngredientView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    List<Ingredient> findAllByRestaurantId(Long restaurantId);

    Long countAllByNameAndRestaurantId(String name, Long restaurantId);

    Optional<Ingredient> findByNameAndRestaurantId(String name, Long restaurantId);

    @Query(value = "select d.restaurant_id, rri.recipe_ingredients_id, sum(ri.amount)\n" +
            "from dishes d\n" +
            "         join recipes_recipe_ingredients rri on d.recipe_id = rri.recipe_id\n" +
            "         join recipe_ingredients ri on rri.recipe_ingredients_id = ri.id\n" +
            "group by d.restaurant_id, rri.recipe_ingredients_id, d.date\n" +
            "having d.date between (select date_trunc('day', now()) - interval '1 month')\n" +
            "           and (select (date_trunc('day', now()))) and d.restaurant_id=?1"
            , nativeQuery = true)
    List<UsedIngredientView> findAllUsedIngredientsByRestaurantId(Long restaurantId);
}

