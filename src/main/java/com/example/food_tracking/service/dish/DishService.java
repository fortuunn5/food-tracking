package com.example.food_tracking.service.dish;

import com.example.food_tracking.dto.DishDto;
import com.example.food_tracking.model.dish.Dish;

import java.util.List;

public interface DishService {
    DishDto createDish(DishDto dish);
    DishDto getById(Long id);
    List<DishDto> getAllDishes();
    DishDto updateDish(DishDto dish);
    void deleteDish(Long id);
    void deleteAllDishes();
    List<DishDto> getDishesByIds(List<Long> ids);
}
