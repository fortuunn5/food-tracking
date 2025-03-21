package com.example.food_tracking.mapper;

import com.example.food_tracking.dto.DishDto;
import com.example.food_tracking.model.dish.Dish;

import java.util.List;
import java.util.stream.Collectors;

public class DishMapper {
    public static Dish map(DishDto dishDto) {
        return Dish.builder()
                .id(dishDto.getId())
                .name(dishDto.getName())
                .calories(dishDto.getCalories())
                .proteins(dishDto.getProteins())
                .carbohydrates(dishDto.getCarbohydrates())
                .fats(dishDto.getFats())
                .build();
    }

    public static DishDto map(Dish dish) {
        return DishDto.builder()
                .id(dish.getId())
                .name(dish.getName())
                .calories(dish.getCalories())
                .proteins(dish.getProteins())
                .carbohydrates(dish.getCarbohydrates())
                .fats(dish.getFats())
                .build();
    }

    public static List<Dish> map(List<DishDto> dishDtoList) {
        return dishDtoList.stream().map(DishMapper::map).collect(Collectors.toList());
    }

    public static List<DishDto> mapToDtoList(List<Dish> dishList) {
        return dishList.stream().map(DishMapper::map).collect(Collectors.toList());
    }
}
