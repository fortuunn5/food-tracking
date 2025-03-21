package com.example.food_tracking.repository.dish;

import com.example.food_tracking.model.dish.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {
    List<Dish> findAllByIdIn(Collection<Long> ids);
}
